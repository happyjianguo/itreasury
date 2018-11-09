<%
	/*
	*ҳ�����ƣ�f003-v.jsp
	*���ܣ��⻹����-����
	//*/
%>
<%@page contentType="text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ include file="/common/common.jsp" %>
<%
	/* ����̶����� */
	String strTitle = "[�⻹��ϸ]";
%>					
<%
  try
  {
	
	   /* �û���¼��� */
	   
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
		%>


<%

		String strOfficeName = sessionMng.m_strOfficeName;
		long lCurrencyID=sessionMng.m_lCurrencyID;//���ұ�ʶ
		long lOfficeID=sessionMng.m_lOfficeID;//���´���ʶ
		long lUserID = sessionMng.m_lUserID;
		String strUserName = sessionMng.m_strUserName;
		
	
			
		//�����������ȡ�������

		String strLoanClientName="";//������� ����/����
		strLoanClientName=Env.getClientName();
		String strTmp = "";
		String strAction = "";
		String strControl = "";
		String strBackURL = "";
		String strDisabled = "";
		int backflag = 0;
		long lFreeApplyID = -1;
		
		long lTypeID=-1;
		long lContractID=-1;		//��ͬ��ʾ
		String strContractCode="";	//��ͬ���
		long lClientID=-1;			//��λ��ʾ
		double dAmount = 0;//������
		long nIntervalNum = -1;//��������
		double dBalance = 0;//�������
		double dLoanRate=0;//��������
		String strLoanPurpose="";//�����;
		String strClientName="";	//��λ����
		String strConsignClientName="";	//ί�е�λ����
		String strConsignClientAccount = "";//ί���˻�

		long lPlanID = -1;

		long lLoanPayID=-1;//�ſ�֪ͨ��ID
		String strLoanPayCode=null;	//�ſ�֪ͨ�����
		double dPayAmount = 0;//�ſ���
		double dPayBalance = 0;//�ſ����
		double mPayInterest = 0;//�ſ���Ϣ
		Timestamp dtEndDate = null;//������

		String strFreeCode = "";
		double mFreeAmount = 0;//�⻹���
		double mFreeRate = 0;//�⻹��Ϣ
		Timestamp dtFreeDate = null;//�⻹��
		String strFreeReason = "";//�⻹ԭ��
		long lStatusID = -1;//�⻹״̬
		long lInputUserID = -1;//¼����
		String strInputUserName="";//¼��������
		long lCheckUserID=-1;//�����
		String strCheckUserName="";//���������
		Timestamp dtInputDate=null;//¼������
		Timestamp dtToday = Env.getSystemDate();//��������
		FreeApplyInfo freeinfo = null;
///////control/////////////////////////////////////////////
		//log4j.info("---321-----------1------------");
		strTmp = (String)request.getAttribute("lContractID");//��ͬID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lContractID = Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("lLoanPayID");//�ſID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lLoanPayID = Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("lFreeApplyID");//�⻹ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lFreeApplyID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = (String)request.getAttribute("strAction");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strAction = strTmp.trim();
		}
		
		freeinfo = (FreeApplyInfo)request.getAttribute("freeinfo");
	
///////////////////////////////////////view////////////////////////////////////////////////
				strFreeCode=freeinfo.getFreeCode();
				mFreeAmount=freeinfo.getFreeAmount();						//�⻹���
				mFreeRate=freeinfo.getFreeRate();							//�⻹��Ϣ
				dtFreeDate=freeinfo.getFreeDate();							//�⻹��
				strFreeReason=freeinfo.getFreeReason();						//�⻹ԭ��
				strConsignClientAccount=freeinfo.getConsignClientAccount();	//ί���˻�
				lStatusID=freeinfo.getStatusID();							//�⻹״̬
				
				
				lInputUserID=freeinfo.getInputUserID();//¼����ID
				strInputUserName=freeinfo.getInputUserName();//¼��������
				if(lInputUserID<=0)
				{
					lInputUserID = lUserID;
					strInputUserName = strUserName;
				}
				

				
				
				dtInputDate=freeinfo.getInputDate();//¼������
				//--------------------------------------------------------------------------//
				strContractCode=freeinfo.getContractCode();			//��ͬ���
				dAmount =freeinfo.getLoanAmount();				//������
				nIntervalNum =freeinfo.getIntervalNum();			//����
				dLoanRate=freeinfo.getInterestRate();				//����ִ������
				dBalance =freeinfo.getBalance();					//������δ�����
				strLoanPurpose=freeinfo.getLoanPurpose();			//�����;
				strClientName=freeinfo.getClientName();				//��λ����
				dtEndDate = freeinfo.getEndDate();					//������
				//lPlanID = freeinfo.getPlanVersionID();
				//---------------------------------------------------------------------------//
				strConsignClientName=freeinfo.getConsignClientName();//ί�е�λ����
				strLoanPayCode=freeinfo.getLoanPayCode();			//�ſ�֪ͨ�����
				dPayAmount=freeinfo.getLoanPayAmount();				//�ſ���
				dPayBalance=freeinfo.getLoanPayBalance();			//�ſ����
				mPayInterest=freeinfo.getLoanPayInterest();					//�ſ���Ϣ
				
				
			if ((dBalance <= 0) || (dPayBalance <= 0))
			{
		%>
				<SCRIPT language="JavaScript">
					if (confirm("�˷ſ����Ϊ�㣬�޷������⻹���Ƿ񷵻�������"))
					{
						eval("history.back(-1)");
					}
				</SCRIPT>
		<%

		}
///////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////

%>
<SCRIPT language="javascript" src="/webob/js/Control.js"></SCRIPT>
<SCRIPT language="JavaScript" src="/webob/js/Check.js"></SCRIPT>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top height=60 width="730">
	<TBODY>
	<TR class="tableHeader">
		<TD class=FormTitle height=35><B><B><%=strTitle%></B></B></TD></TR>
	<TR>
		<TD vAlign=top>
		<TABLE align=center border=0 height=60 width=700>
			<TBODY>
			<TR>
				<TD height=23 width=1>&nbsp;</TD>
				<TD colSpan=2 height=23>��ͬ��ţ�<%=DataFormat.formatString(strContractCode)%></TD>
				<TD colSpan=6 height=23></TD>
			</TR>
			<TR>
				<TD height=24 width=1>&nbsp;</TD>
				<TD colSpan=8 height=24>
					<HR>
				</TD>
				<TD align=right height=24 width=1>&nbsp;</TD></TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30><U>��ͬ��������</U></TD>
				<TD align=right height=30 width=1>&nbsp;</TD></TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30>
				<TABLE cellPadding=0 cellSpacing=0 width="100%">
					<TBODY>
					<TR>
						<TD width=200></TD>
						<TD width=200></TD>
						<TD width=100></TD>
						<TD width=100></TD>
						<TD width=400></TD>
					</TR>
					<TR>
						<TD>���λ��</TD>
						<TD colspan=5>
							<INPUT class=box size=80 disabled name="strLoanClientName1" value="<%=DataFormat.formatString(strLoanClientName)%>">
						</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD>�����</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%>
							<INPUT class=tar disabled name="dAmount1" size=18 value="<%=DataFormat.formatListAmount(dAmount)%>">
						</TD>
						<TD>&nbsp;</TD>
						<TD  align=right>�������ޣ�</TD>
						<TD>
							<INPUT class=tar disabled name="nIntervalNum" size=10 value="<%if(nIntervalNum>0){out.print(""+nIntervalNum);}else{out.print("");}%>"> ��
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD>������</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%>
							<INPUT class=tar disabled name="dBalance1" size=18 value="<%=DataFormat.formatListAmount(dBalance)%>">
						</TD>
						<TD>&nbsp;</TD>
						<TD align=right>�������ʣ�</TD>
						<TD>
							<INPUT class=tar disabled name="fInterestRate" size=10 value="<%=DataFormat.formatRate(dLoanRate)%>"> %
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD>�����;:</TD>
						<TD colspan=3>
							<INPUT class=box disabled name="strLoanPurpose" size=45 value="<%=DataFormat.formatString(strLoanPurpose)%>"> 
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					</TBODY>
				</TABLE>
				</TD>
				<TD align=right height=30 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30> <hr></TD>
				<TD align=right height=30 width=1>&nbsp;</TD></TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30>
					<DIV align=center><B>ί�д����⻹֪ͨ��</B></DIV>
				</TD>
				<TD align=right height=30 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=225 width=1>&nbsp;</TD>
				<TD colSpan=8 height=225>
				<TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 height=224 width="100%">
					<TBODY>
					<TR>
						<TD>
						<TABLE border=0 height=136 width="98%">
							<TBODY>
							<TR>
								<TD height=32 width=100></TD>
								<TD height=32 width=100></TD>
								<TD height=32 width=30></TD>
								<TD height=32 width=100></TD>
								<TD height=32 width=100></TD>
								<TD height=32 width=100></TD>
								<TD height=32 width=400></TD>
							</TR>
							<TR>
								<TD colSpan=2>��λ��</TD>
								<TD colSpan=1></TD>
								<TD colspan=4>
									<INPUT class=box size=68 disabled name="strBorrowClientName" value="<%=DataFormat.formatString(strClientName)%>">
								</TD>
							</TR>
							<TR>
								<TD colSpan=2>����ͬ��ţ�</TD>
								<TD colSpan=1></TD>
								<TD>
									<INPUT class=box disabled name="strContractCode" size=18 value="<%=DataFormat.formatString(strContractCode)%>">
								</TD>
								<TD colSpan=2 align=right>�ſ�֪ͨ����ţ�</TD>
								<TD>&nbsp;&nbsp;
									<INPUT class=box disabled name="strLoanPayCode" size=18 value="<%=DataFormat.formatString(strLoanPayCode)%>"></TD>
							</TR>
							<TR>
								<TD colSpan=2>ί�е�λ��</TD>
								<TD colSpan=1></TD>
								<TD colspan=4>
									<INPUT class=box size=68 disabled name="strConsignClientName" value="<%=DataFormat.formatString(strConsignClientName)%>">
								</TD>
							</TR>
							<TR>
								<TD colSpan=2>ί���˺ţ�</TD>
								<TD colSpan=1></TD>
								<TD>
									<INPUT class=box onfocus="nextfield='mFreeAmount'" name="strConsignClientAccount" size=18 value="<%=DataFormat.formatString(strConsignClientAccount)%>">
								</TD>
								<TD colSpan=2></TD>
								<TD height=27></TD>
							</TR>
							<TR>
								<TD colSpan=2>ԭ��</TD>
								<TD colSpan=1>
									<DIV align=left><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
								<TD>
									<INPUT class=tar disabled name="dAmount" size=18 value="<%=DataFormat.formatListAmount(dAmount)%>"> 
								</TD>
								<TD colSpan=2 align=right>���ޣ�</TD>
								<TD>&nbsp;&nbsp;
									<INPUT class=tar disabled name="nIntervalNum" size=10 value="<%if(nIntervalNum>0){out.print(""+nIntervalNum);}else{out.print("");}%>"> ��
								</TD>
							</TR>
							<TR>
								<TD colSpan=2>������</TD>
								<TD colSpan=1><%=sessionMng.m_strCurrencySymbol%></TD>
								<TD>
									<INPUT class=tar disabled name="dBalance" size=18 value="<%=DataFormat.formatListAmount(dBalance)%>"> 
								</TD>
								<TD colSpan=2 align=right>�����գ�</TD>
								<TD>&nbsp;&nbsp;
									<INPUT class=box disabled name="dtEndDate" size=18 value="<%=DataFormat.getDateString(dtEndDate)%>">
								</TD>
							</TR>
							<TR>
								<TD colSpan=2>�ſ�֪ͨ����</TD>
								<TD colSpan=1>
									<DIV align=left><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
								<TD>
									<INPUT class=tar disabled name="dPayAmount" size=18 value="<%=DataFormat.formatListAmount(dPayAmount)%>"> 
								</TD>
								<TD colSpan=2 align=right><font color="#000000">�ſ�֪ͨ����</font></TD>
								<TD><%=sessionMng.m_strCurrencySymbol%>
									<INPUT class=tar disabled name="dPayBalance" size=18 value="<%=DataFormat.formatListAmount(dPayBalance)%>"> 
								</TD>
							</TR>
							<TR>
								<TD colSpan=2><font color='#FF0000'>&nbsp;</font>�⻹��</TD>
								<TD colSpan=1>
									<DIV  align="left"><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
								<TD>
									<script language="javascript">
									<%if(mFreeAmount<0)mFreeAmount=0;%>
										createAmountCtrl("frm", "mFreeAmount", "<%=DataFormat.formatListAmount(mFreeAmount)%>", "dtFreeDate", null);
									</script>
								</TD>
								<TD colSpan=2 align=right><font color='#FF0000'>*</font>&nbsp;�⻹�գ�</TD>
								<TD>&nbsp;&nbsp;
									<fs_c:calendar 
						          	    name="dtFreeDate"
							          	value="" 
							          	properties="nextfield ='mFreeRate'" 
							          	size="18"/>
							       			          	  <script>
	          		$('#dtFreeDate').val('<%if ((dtToday != null)&&(dtFreeDate !=null)) {out.println(DataFormat.getDateString(dtFreeDate));} else {out.println(DataFormat.getDateString(dtToday));}%>');
	          	</script>
							 	</TD>
							</TR>
							<TR>
								<TD colSpan=2>�⻹��Ϣ��</TD>
								<TD colSpan=1><%=sessionMng.m_strCurrencySymbol%></TD>
								<TD>
									<script language="javascript">
									<%if(mFreeRate<0)mFreeRate=0;%>
										createAmountCtrl("frm", "mFreeRate", "<%=DataFormat.formatAmount(mFreeRate)%>", "strFreeReason", null);
									</script>
								</TD>
								<TD colSpan=2 align=right>�⻹��ţ�</TD>
								<TD>&nbsp;&nbsp;
									<INPUT class=box  name="strFreeCode" size=18 value="<%=DataFormat.formatString(strFreeCode)%>" disabled>
								</TD>
							</TR>
							<TR>
								<TD colSpan=3><font color='#FF0000'>*</font>&nbsp;�⻹ԭ��</TD>
								<TD colSpan=4>
										<%
										//log4j.info("reason :"+strFreeReason+"1");
										%>
									<TEXTAREA cols=60 name="strFreeReason" onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" onFocus="nextfield='submitfunction';" ><%=DataFormat.formatString(strFreeReason)%></TEXTAREA> 
								</TD>
							</TR>
							</TBODY>
						</TABLE>
						</TD>
					</TR>
					</TBODY>
				</TABLE>
				</TD>
				<TD align=right height=225 width=1>&nbsp;</TD></TR>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD colSpan=8 height=2>
					<HR>
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD height=2 width=562><U>ִ�мƻ���ϸ</U></TD>
				<TD colSpan=7 height=2></TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<tr> 
				<td width="1" height="2">&nbsp;</td>
				<td width="562" height="2">
					<input type="button" name="Submit4224" onclick="window.open('../query/q094-v.jsp?control=view&isYU=2&nTmpID=<%=freeinfo.getPlanVersionID()%>&isSM=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes')" value="ִ�мƻ�" class="button">
				</td>
				<td colspan="7" height="2"> 
					<div align="right"></div>
				</td>
				<td align="right" height="2" width="1">&nbsp;</td>
			</tr>
			<tr>
				<td width="1" height="2">&nbsp;</td>
				<td colspan="8" height="2"> 
					<div align="right">
			<%
				if((lStatusID==OBConstant.LoanInstrStatus.SUBMIT||lStatusID==OBConstant.LoanInstrStatus.SAVE||lStatusID==0)
				&&(freeinfo.getInputUserID()== sessionMng.m_lUserID||freeinfo.getInputUserID()<=0))
				{
			%>
						<INPUT  class=button name="FreeSubmit" onclick="confirmSave(frm)" type="button" value="�����⻹����">
			<%	}

			%>
						<INPUT  class=button name="back"  type="button" value=" ���� " onclick="backto()"> 
					</div>
				</td>
				<td align="right" height="2" width="1">&nbsp;</td>
			</tr>
			<%	if (lFreeApplyID > 0) 
	{
%>
			<tr >
				<td width="1">&nbsp;</td>
				<td  >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				¼���ˣ�<%=DataFormat.formatString(strInputUserName)%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				¼��ʱ�䣺<%if(dtInputDate != null){out.println(DataFormat.getDateString(dtInputDate));} else {out.println("");}%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				״̬��
					<%if(lStatusID > 0){out.println(OBConstant.LoanInstrStatus.getName(lStatusID));} else {out.println("");}%>
				</td>
			</tr>
<%	}
		else
		{
%>
			<tr>
				<td width="1">&nbsp;</td>
				<td >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				¼���ˣ�<%=DataFormat.formatString(strUserName)%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				¼��ʱ�䣺<%out.println(DataFormat.getDateString(dtToday));%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	״̬��
					<%out.println(OBConstant.LoanInstrStatus.getName(1));%>
				</td>
			</tr>

<%}%>
			<tr>
				<td colspan="10" height="2">&nbsp;</td>
			</tr>

		</table>
		</td>
	</tr>
</table>

<input type=hidden name="strAction" value="save">

<input type=hidden name="lContractID" value="<%=lContractID%>">
<input type=hidden name="lLoanPayID" value="<%=lLoanPayID%>">
<input type=hidden name="lFreeApplyID" value="<%=lFreeApplyID%>">



<P><BR></P>
</form>


<script language="javascript">
function printIt(url)
{
	window.open(url,'','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
}
</script>
<script language="javascript">
function backto()
{
	var act = "<%=strAction%>";	
		if(act=="edit")
                {
				eval("self.location='<%=Env.EBANK_URL%>/loan/query/q003-c.jsp'");	
				}
				else
				{
				eval("self.location='<%=Env.EBANK_URL%>/loan/freeapply/f001-v.jsp'");
				}
}

</script>

<script language="javascript">
function confirmSave(frm)
{
	//alert("1");
	if (<%=dPayBalance%> <= 0)
	{
		alert("�˷ſ����Ϊ�㣬�޷������⻹���뷵��������")
		backto();
		return true;
	}
	var strMsg = "";

	if (!InputValid(frm.strConsignClientAccount, 0, "int", 0, 0, 0,"ί���˺�")) 
	{
		return false;
	}
/*
	if (!checkAmount(frm.mFreeAmount,1,"�⻹���")) 
	{
		return false;
	}
//*/
	if ((reverseFormatAmount1(frm.mFreeAmount.value)) > <%=DataFormat.reverseFormatAmount(DataFormat.formatAmount(dPayBalance))==""?"0":DataFormat.reverseFormatAmount(DataFormat.formatAmount(dPayBalance))%>)
	{
		alert("�⻹���Ӧ��С�ڵ��ڷſ���");
		frm.mFreeAmount.focus();
		return false;
	}
	if (!InputValid(frm.mFreeRate,0, "float",0,0,<%=DataFormat.reverseFormatAmount(DataFormat.formatAmount(mPayInterest))==""?"0":DataFormat.reverseFormatAmount(DataFormat.formatAmount(mPayInterest))%>,"�⻹��Ϣ")) 
	{
		return false;
	}
	if ((reverseFormatAmount1(frm.mFreeRate.value)) > <%=DataFormat.reverseFormatAmount(DataFormat.formatAmount(mPayInterest))==""?"0":DataFormat.reverseFormatAmount(DataFormat.formatAmount(mPayInterest))%>)
	{
		alert("�⻹��ϢӦ��С�ڵ��ڷſ����Ϣ��");
		frm.mFreeRate.focus();
		return false;
	}

	if (((reverseFormatAmount1(frm.mFreeRate.value)) <= 0 )&&((reverseFormatAmount1(frm.mFreeAmount.value)) <= 0))
	{
		alert("�⻹���⻹��Ϣ�����⻹����һ���������ܶ�Ϊ 0 ��");
		frm.mFreeAmount.focus();
		return false;
	}


	if (!checkDate(frm.dtFreeDate,1,"�⻹��")) 
	{
		return false;
	}
	if (!InputValid(frm.strFreeReason,1, "string",1,1,100,"�⻹ԭ��")) 
	{
		return false;
	}

	if (<%=lFreeApplyID%> >0)
	{	strMsg = "ȷ���޸����뵥��";
		if(confirm(strMsg))
		{
		frm.action="<%=Env.EBANK_URL%>/loan/freeapply/f004-c.jsp";
		frm.strAction.value="modify";
		showSending();
		frm.submit();
		return true;
		}
	}else{
	
		if (<%=freeinfo.getStatusID()%> == <%=LOANConstant.FreeApplyStatus.CHECK%>)
		strMsg = "������������ˣ��Ƿ�ȷ���ٴ��ύ��������ˣ�";
		else strMsg = "�Ƿ�������棿";
		if(confirm(strMsg))
		{
			frm.action="<%=Env.EBANK_URL%>/loan/freeapply/f004-c.jsp";
			showSending();
			frm.submit();
			return true;
		}
	
	}
}

</script>
<script language="javascript">
<%
	//���״̬������ˣ��ҵ�¼����¼���ˣ��ſ����ٴ��ύ���룬���򲻿����޸�
	if((lStatusID==LOANConstant.FreeApplyStatus.CHECK && lInputUserID == lUserID)||(lStatusID <=0)||(lStatusID==LOANConstant.FreeApplyStatus.SUBMIT && lInputUserID == lUserID  && lCheckUserID == lUserID))
	{
%>
		firstFocus(frm.strConsignClientAccount);
		//setSubmitFunction("confirmSave(frm)");
<%
	}
	else
	{
%>
		frm.strConsignClientAccount.disabled=true;
		frm.strFreeReason.disabled=true;
		frm.mFreeRate.disabled=true;
		frm.mFreeAmount.disabled=true;
		frm.dtFreeDate.disabled=true;
		firstFocus(frm.back);
		//setSubmitFunction("backto()");
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

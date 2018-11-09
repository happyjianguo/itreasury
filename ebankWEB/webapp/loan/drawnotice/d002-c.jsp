<%
	/*
	*ҳ�����ƣ�f002-c.jsp
	*���ܣ��⻹����
	//*/
%>
<%@page contentType="text/html;charset=gbk"%>

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
				com.iss.itreasury.ebank.obfreeapply.bizlogic.*,
				com.iss.itreasury.loan.loandrawnotice.bizlogic.*,
				com.iss.itreasury.loan.loandrawnotice.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%
	/* ����̶����� */
	String strTitle = "[���Ŵ������֪ͨ��]";
%>					
<%
  try
  {
  	String strOfficeName = sessionMng.m_strOfficeName;
	long lCurrencyID=sessionMng.m_lCurrencyID;//���ұ�ʶ
	long lOfficeID=sessionMng.m_lOfficeID;//���´���ʶ
	long lUserID = sessionMng.m_lUserID;
	
	
	//Log.print("*******����ҳ��--ebank/loan/discountapply/d011-c.jsp*******");
	
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

///////////////////////////////////////////////////////////////////////////////////
		LoanDrawNoticeHome home = (LoanDrawNoticeHome) EJBObject.getEJBHome("LoanDrawNoticeHome");
		LoanDrawNotice ejb = home.create();
		LoanDrawNoticeInfo LDNinfo = new LoanDrawNoticeInfo();
////////////////////////////////////////////////////////////////////*/
		ContractHome contracthome = (ContractHome) EJBObject.getEJBHome("ContractHome");
		Contract contractejb = contracthome.create();
		ContractInfo contractinfo = new ContractInfo();
		YTFormatInfo YTinfo = null;
		ContractAmountInfo Ainfo = null;
////////////////////////////////////////////////////////////////////*/
////////////////////////////////////////////////////////////////////*/
		//�����������ȡ�������

		String strLoanClientName="";//������� ����/����
		strLoanClientName=Env.getClientName();
		String strTmp = "";
		String strAction = "";
		String strControl = "";
		String strBackURL = "";
		int nback=0;
		String strDisabled = "";
		long lLoanType = -1;

		long lContractID=-1;		//��ͬ��ʾ
		String strContractCode="";	//��ͬ���
		Timestamp dtStartDate=null;//��ͬ��ʼ����
		Timestamp dtEndDate=null;//��������
		long lClientID=-1;			//��λ��ʾ
		double dAmount = 0;//������
		long nIntervalNum = -1;//��������
		double dBalance = 0;//�������
		double dUNPayAmount = 0;
		double dLoanRate=0;//��������
		double dChargeRate=0;//��������
		String strLoanPurpose="";//�����;
		String strBorrowClientName="";	//��λ����
		String strBorrowClientCode="";
		String strConsignClientName="";	//ί�е�λ����
		String strConsignClientAccount = "";//ί���˻�

		long lLoanDrawNoticeID = -1;//���ŷſ�֪ͨ��ID
		String strLoanPayCode=null;	//�ſ�֪ͨ�����
		String[] strBankName = {"","","","","","",""};//
		//*
		double[] mBankLoanAmount = {0,0,0,0,0,0};//
		double[] mBankLoanRate = {0,0,0,0,0,0};//
		double[] mBankConsignAmount = {0,0,0,0,0,0};//
		double[] mBankTrustAmount = {0,0,0,0,0,0};//*/
		//*
		String[] strBankLoanAmount = {"","","","","","",""};//
		String[] strBankLoanRate = {"","","","","","",""};//
		String[] strBankConsignAmount = {"","","","","","",""};//
		String[] strBankTrustAmount = {"","","","","","",""};//*/

		double mThisDrawAmount = 0;//
		double mThisDrawTipAmount = 0;//
		double mThisTotalAmount = 0;//
		double[] mThisPayAmount = {0,0,0,0,0,0};//
		double[] mThisTipAmount = {0,0,0,0,0,0};//

		long lStatusID = -1;//״̬
		long lInputUserID = -1;//¼����
		String strInputUserName="";//¼��������
		long lCheckUserID=-1;//�����
		String strCheckUserName="";//���������
		Timestamp dtInputDate=null;//¼������
		Timestamp dtToday = Env.getSystemDate();//��������

///////control/////////////////////////////////////////////

		strTmp = request.getParameter("strAction");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strAction = strTmp.trim();
		}
		strTmp = request.getParameter("lContractID");//��ͬID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lContractID = Long.parseLong(strTmp.trim());
		}
		strTmp = request.getParameter("lLoanDrawNoticeID");//��ͬID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lLoanDrawNoticeID = Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("mThisDrawAmount");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				mThisDrawAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTmp.trim()));
			}
			catch(Exception e)
			{
				;
			}
		}
		
////////save/////////////////////////////////////////////////////////////////////////////		
		if (strControl.equals("save"))
		{
			if (strAction.equals("cancel"))
			{
				Log.print("--------------cancel------------");
				//strControl="view";
				long lResult=ejb.cancelLoanDrawNotice(lLoanDrawNoticeID,lLoanType,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID);
				if(lResult>0)
				{
%>
					<script language="JavaScript">
						alert("ȡ���ɹ���");
						backto('S320')
					</script>
<%
					response.sendRedirect("S001.jsp?control=view&type="+lLoanType);
					return;
				}
				else
				{
%>
					<script language="JavaScript">
						alert("ȡ��ʧ�ܣ�");
						eval("history.back(-1)");
					</script>
<%
				}
			}
			else if (strAction.equals("save"))
			{
				Log.print("--------------save------------");
				//strControl="view";
				//log4j.info("�ſ��ύ");
				LDNinfo = new LoanDrawNoticeInfo();
				LDNinfo.setID(lLoanDrawNoticeID);
				LDNinfo.setContractID(lContractID);
				LDNinfo.setDrawAmount(mThisDrawAmount);
				LDNinfo.setInputUserID(lUserID);
				LDNinfo.setNextCheckUserID(lUserID);
				lLoanDrawNoticeID = ejb.saveLoanDrawNotice(LDNinfo);
				if(lLoanDrawNoticeID > 0)
				{
%>
					<script language="JavaScript">
						alert("�ſ��ύ�ɹ���");
						//eval("S322.jsp?control=view&type=<%=lLoanType%>&lLoanDrawNoticeID=<%=lLoanDrawNoticeID%>");
					</script>
<%
					response.sendRedirect("S004.jsp?control=view&type="+lLoanType+"&lLoanDrawNoticeID="+lLoanDrawNoticeID+"&strAction=count");
					return;
				}
				else
				{
%>
					<script language="JavaScript">
						alert("�ſ��ύʧ�ܣ�");
						eval("history.back(-1)");
					</script>
<%
				}
			}
		}
///////////////view//////////////////////////////////////////////////
		if (strControl.equals("view"))
		{
			if(lLoanDrawNoticeID > 0)// && lContractID <= 0)
			{
				LDNinfo = new LoanDrawNoticeInfo();
				LDNinfo = ejb.findLoanDrawNoticeByID(lLoanDrawNoticeID);
				lContractID = LDNinfo.getContractID();
				if (strAction.equals("count") && (mThisDrawAmount <=0))
				{
					mThisDrawAmount = LDNinfo.getDrawAmount();
					/*
					mThisDrawTipAmount = (dChargeRate*mThisDrawAmount)/100;
					Log.print("��������"+mThisDrawAmount);
					for(int k=0;k<6;k++)
					{
						mThisPayAmount[k] = (mThisDrawAmount*mBankLoanRate[k])/100;//
						mThisTipAmount[k] = dChargeRate*mThisPayAmount[k];
					}//*/
				}
				lInputUserID = LDNinfo.getInputUserID();//¼����
				strInputUserName = LDNinfo.getInputUserName();//¼����
				lCheckUserID = LDNinfo.getNextCheckUserID();//�����
				strCheckUserName = LDNinfo.getCheckUserName();//�����
				lStatusID = LDNinfo.getStatusID();//
			}
			if (lContractID > 0)
			{
				Log.print("Conrtact------------");
				contractinfo = contractejb.findByID(lContractID);
				strContractCode=DataFormat.formatString(contractinfo.getContractCode());			//��ͬ���
				dAmount =contractinfo.getExamineAmount();					//������,��׼���
				nIntervalNum =contractinfo.getIntervalNum();			//����
				dLoanRate=contractinfo.getInterestRate();				//����ִ������
				dChargeRate=contractinfo.getChargeRate();//��������
				///dBalance =contractinfo.getBalance();					//������δ�����
				strBorrowClientName=DataFormat.formatString(contractinfo.getBorrowClientName());	//��λ����
				strBorrowClientCode=DataFormat.formatString(contractinfo.getBorrowClientCode());
				dtStartDate=contractinfo.getLoanStart();//��ͬ��ʼ����
				dtEndDate=contractinfo.getLoanEnd();//��������
				Ainfo = new ContractAmountInfo();
				Ainfo = contractinfo.getAInfo();
				/*
				double dAmountTmp = 0;
				dAmountTmp = Ainfo.getBalanceAmount();
				Log.print("��ͬ���:"+DataFormat.formatAmount(dAmountTmp));
				dAmountTmp = Ainfo.getOpenAmount();
				Log.print("�ѷ��Ž��:"+DataFormat.formatAmount(dAmountTmp));
				dAmountTmp = Ainfo.getRepayAmount();
				Log.print("�ѻ����:"+DataFormat.formatAmount(dAmountTmp));//*/
				dUNPayAmount = Ainfo.getUnPayAmount();
				Log.print("δ���Ž��:"+DataFormat.formatAmount(dUNPayAmount));
				YTinfo = new YTFormatInfo();
				YTinfo = contractinfo.getYTInfo();
				if(YTinfo != null)
				{/*
					strBankName[0]=YTinfo.getBankName1();
					strBankName[1]=YTinfo.getBankName2();
					strBankName[2]=YTinfo.getBankName3();
					strBankName[3]=YTinfo.getBankName4();
					strBankName[4]=YTinfo.getBankName5();
					strBankName[5]=YTinfo.getBankName6();

					mBankLoanAmount[0]=YTinfo.getLoanAmount1();
					mBankLoanAmount[1]=YTinfo.getLoanAmount2();
					mBankLoanAmount[2]=YTinfo.getLoanAmount3();
					mBankLoanAmount[3]=YTinfo.getLoanAmount4();
					mBankLoanAmount[4]=YTinfo.getLoanAmount5();
					mBankLoanAmount[5]=YTinfo.getLoanAmount6();

					mBankLoanRate[0]=YTinfo.getLoanRate1();
					mBankLoanRate[1]=YTinfo.getLoanRate2();
					mBankLoanRate[2]=YTinfo.getLoanRate3();
					mBankLoanRate[3]=YTinfo.getLoanRate4();
					mBankLoanRate[4]=YTinfo.getLoanRate5();
					mBankLoanRate[5]=YTinfo.getLoanRate6();

					mBankConsignAmount[0]=YTinfo.getAssureAmount1();
					mBankConsignAmount[1]=YTinfo.getAssureAmount2();
					mBankConsignAmount[2]=YTinfo.getAssureAmount3();
					mBankConsignAmount[3]=YTinfo.getAssureAmount4();
					mBankConsignAmount[4]=YTinfo.getAssureAmount5();
					mBankConsignAmount[5]=YTinfo.getAssureAmount6();

					mBankTrustAmount[0]=YTinfo.getCreditAmount1();
					mBankTrustAmount[1]=YTinfo.getCreditAmount2();
					mBankTrustAmount[2]=YTinfo.getCreditAmount3();
					mBankTrustAmount[3]=YTinfo.getCreditAmount4();
					mBankTrustAmount[4]=YTinfo.getCreditAmount5();
					mBankTrustAmount[5]=YTinfo.getCreditAmount6();//*/
					strBankName=YTinfo.getBankName();
					strBankLoanAmount=YTinfo.getLoanAmount();
					strBankLoanRate=YTinfo.getLoanRate();
					strBankConsignAmount=YTinfo.getAssureAmount();
					strBankTrustAmount=YTinfo.getCreditAmount();
					for(int i=0;i<6;i++)
					{
						Log.print("---");
						if(strBankLoanAmount[i].length() > 0)
						{
							mBankLoanAmount[i]=Double.parseDouble(DataFormat.reverseFormatAmount(strBankLoanAmount[i]));
						}
						if(strBankLoanRate[i].length() > 0)
						{
							mBankLoanRate[i]=Double.parseDouble(strBankLoanRate[i]);
						}
						if(strBankConsignAmount[i].length() > 0)
						{
							mBankConsignAmount[i]=Double.parseDouble(DataFormat.reverseFormatAmount(strBankConsignAmount[i]));
						}
						if(strBankTrustAmount[i].length() > 0)
						{
							mBankTrustAmount[i]=Double.parseDouble(DataFormat.reverseFormatAmount(strBankTrustAmount[i]));
						}
					}
				}
			}
			else
			{
			}//*
			if (strAction.equals("count"))
			{
				//mThisDrawTipAmount = (dChargeRate*mThisDrawAmount)/100;
				//Log.print("��������ܽ�"+mThisTotalAmount);
				for(int k=0;k<6;k++)
				{
					mThisPayAmount[k] = (mThisDrawAmount*mBankLoanRate[k])/100;//
					if(k > 0)
					{
						mThisTipAmount[k] = (dChargeRate*mThisPayAmount[k])/1000;
						mThisDrawTipAmount += mThisTipAmount[k];
					}
				}
				mThisTotalAmount = mThisDrawAmount + mThisDrawTipAmount;
			}//*/
			Log.print("--------------Conrtact");
		}
		if (dUNPayAmount <= 0)
		{
%>
			<SCRIPT language="JavaScript">
				if (confirm("δ���Ž��Ϊ�㣬�޷��ٷ��Ŵ���Ƿ񷵻�����ҳ�棿"))
				{
					eval("self.location='S001.jsp?control=view&type=<%=lLoanType%>'");
				}
			</SCRIPT>
<%
		}
///////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////

		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>

<script language="JavaScript" src="/webloan/js/Control.js"></script>
<script language="JavaScript" src="/webloan/js/Check.js"></script>

<form name="frm">
<TABLE border=0 class=top height=60 width="54%">
	<TBODY>
	<TR>
		<TD class=FormTitle height=35><B><B><%=DataFormat.formatString(strTitle)%></B></B></TD></TR>
	<TR>
		<TD vAlign=top>
		<TABLE align=center border=0 height=60 width=819>
			<TBODY>
			<TR>
				<TD height=23 width=1>&nbsp;</TD>
				<TD colSpan=2 height=23>��ͬ��ţ�<U><font color=blue><%=DataFormat.formatString(strContractCode)%></font></U></TD>
				<TD colSpan=6 height=23></TD>
			</TR>
			<TR>
				<TD height=24 width=1>&nbsp;</TD>
				<TD colSpan=8 height=24>
					<HR>
				</TD>
				<TD align=right height=24 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30><U>��ͬ����</U></TD>
				<TD align=right height=30 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30>
				<TABLE cellPadding=0 cellSpacing=0 width="100%">
					<TBODY>
					<TR>
						<TD width=1></TD>
						<TD width=200></TD>
						<TD width=30></TD>
						<TD width=200></TD>
						<TD width=200></TD>
						<TD width=200></TD>
						<TD width=200></TD>
						<TD width=30></TD>
						<TD width=200></TD>
					</TR>
					<TR>
						<TD></TD>
						<TD>���λ��</TD>
						<TD></TD>
						<TD colspan=2><U><font color=blue><%=DataFormat.formatString(strBorrowClientName)%></font></U></TD>
						<TD align=right>�ͻ���ţ�</TD>
						<TD>
							<INPUT class=tar disabled name="nIntervalNum" size=18 value="<%=DataFormat.formatString(strBorrowClientCode)%>">
						</TD>
					</TR>
					<TR>
						<TD></TD>
						<TD>��ͬ��</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%></TD>
						<TD>
							<INPUT class=tar disabled name="dAmount1" size=18 value="<%=DataFormat.formatListAmount(dAmount)%>">
						</TD>
						<TD>&nbsp;</TD>
						<TD  align=right>���ޣ�</TD>
						<TD>
							<INPUT class=tar disabled name="nIntervalNum" size=18 value="<%if(nIntervalNum>0){out.print(""+nIntervalNum);}else{out.print("");}%>"> ��
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD></TD>
						<TD>ִ�����ʣ�</TD>
						<TD></TD>
						<TD>
							<INPUT class=tar disabled name="dLoanRate" size=18 value="<%=DataFormat.formatRate(dLoanRate)%>">&nbsp;%
						</TD>
						<TD>&nbsp;</TD>
						<TD align=right>������ʣ�</TD>
						<TD>
							<INPUT class=tar disabled name="dChargeRate" size=18 value="<%=DataFormat.formatRate(dChargeRate)%>">&nbsp;��
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD></TD>
						<TD>��ͬ��ʼ���ڣ�</TD>
						<TD></TD>
						<TD>
							<INPUT class=tar disabled name="fInterestRate" size=18 value="<%=DataFormat.getDateString(dtStartDate)%>">
						</TD>
						<TD>&nbsp;</TD>
						<TD align=right>��ͬ�������ڣ�</TD>
						<TD>
							<INPUT class=tar disabled name="fInterestRate" size=18 value="<%=DataFormat.getDateString(dtEndDate)%>">
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					</TBODY>
				</TABLE>
				</TD>
				<TD align=right height=30 width=1>&nbsp;</TD>
			</TR>
<%
		for(int i=0;i<6;i++)
		{
%>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30>
					<HR>
				</TD>
				<TD align=right height=30 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30>
				<TABLE cellPadding=0 cellSpacing=0 width="100%">
					<TBODY>
					<TR>
						<TD width=1></TD>
						<TD width=200></TD>
						<TD width=20></TD>
						<TD width=200></TD>
						<TD width=20></TD>
						<TD width=200></TD>
						<TD width=20></TD>
						<TD width=200></TD>
						<TD width=20></TD>
						<TD width=200></TD>
						<TD width=300></TD>
					</TR>
<%
			if(i==0)
			{
%>
					<TR>
						<TD>&nbsp;</TD>
						<TD colSpan=8><U>����������</U></TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD>&nbsp;</TD>
						<TD colSpan=2>ǣͷ��/�����У�</TD>
						<TD align=right>&nbsp;</TD>
					</TR>
<%
			}
			else
			{
%>
					<TR>
						<TD>&nbsp;</TD>
						<TD colSpan=2>�μ���<%=i%>��</TD>
						<TD align=right height=30 width=1>&nbsp;</TD>
					</TR>
<%
			}
%>
					<TR>
						<TD>&nbsp;</TD>
						<TD>�������ƣ�</TD>
						<TD>&nbsp;</TD>
						<TD>
							<INPUT class=tar  name="strNameBank<%=i%>" size=18 value="<%=DataFormat.formatString(strBankName[i])%>" readonly>
						</TD>
						<TD>&nbsp;</TD>
						<TD align=right>�д���</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%></TD>
						<TD>
							<INPUT class=tar  name="mBankLoanAmount<%=i%>" size=18 value="<%=DataFormat.formatListAmount(mBankLoanAmount[i])%>" readonly>
						</TD>
						<TD>&nbsp;</TD>
						<TD>�д�������</TD>
						<TD>
							<INPUT class=tar  name="mBankLoanRate<%=i%>" size=18 value="<%=mBankLoanRate[i] <=0?"":DataFormat.formatRate(mBankLoanRate[i])%>" readonly> %
						</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD>&nbsp;</TD>
						<TD>������</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%></TD>
						<TD>
							<INPUT class=tar  name="mBankConsignAmount<%=i%>" size=18 value="<%=DataFormat.formatListAmount(mBankConsignAmount[i])%>" readonly>
						</TD>
						<TD>&nbsp;</TD>
						<TD align=right>���ý�</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%></TD>
						<TD>
							<INPUT class=tar  name="mBankTrustAmount<%=i%>" size=18 value="<%=DataFormat.formatListAmount(mBankTrustAmount[i])%>" readonly>
						</TD>
						<TD>&nbsp;</TD>
						<TD></TD>
						<TD></TD>
						<TD>&nbsp;</TD>
					</TR>
					</TBODY>
				</TABLE>
				</TD>
			</TR>
<%
	}
%>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD colSpan=8 height=2>
					<HR>
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD width=1></TD>
				<TD colSpan=1><font color='#FF0000'>*</font>&nbsp;�����������:
				<%=sessionMng.m_strCurrencySymbol%>
					<script language="javascript">
						createAmountCtrl("frm", "mThisDrawAmount", "<%=DataFormat.formatListAmount(mThisDrawAmount)%>","",null);
					</script>
					<INPUT onfocus="nextfield=''" class=button type="button"  name="CountAmount" value=" �� �� " onclick="confirmfrm(frm,1)">
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD colSpan=8 height=2>
					<HR>
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
		<TR>
			<TD align=center height=16 width="1%">
			<TD colSpan=8 align=center height=16 width="99%">
			<TABLE align=center border=0 class=ItemList height=20 width="99%">
				<TBODY>
				<TR align=center bgColor=#cccccc borderColor=#999999>
					<TD class=ItemTitle height=20 width="20%" colSpan=3>
						<DIV align=center>����������</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="20%" rowSpan=2>
						<DIV align=center><U><font color=blue>���������</font></U></DIV>
					</TD>
					<TD class=ItemTitle height=20 width="20%" rowSpan=2>
						<DIV align=center><U><font color=blue>����Ӧ�������</font></U></DIV>
					</TD>
				</TR>
				<TR align=center bgColor=#cccccc borderColor=#999999>
					<TD class=ItemTitle height=20 width="20%">
						<DIV align=center>��������</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="20%">
						<DIV align=center>�д����</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="20%">
						<DIV align=center>�д�����(%)</DIV>
					</TD>
				</TR>
<%
	if(mThisDrawAmount > 0)
	{
		for(int j=0;j<6;j++)
		{
			if(mBankLoanRate[j] > 0)
			{
%>
				<TR align=center borderColor=#999999>
					<TD class=ItemBody><%=DataFormat.formatString(strBankName[j])%></TD>
					<TD class=ItemBody><%=DataFormat.formatDisabledAmount(mBankLoanAmount[j])%></TD>
					<TD class=ItemBody><%=DataFormat.formatRate(mBankLoanRate[j])%></TD>
					<TD class=ItemBody><%=DataFormat.formatDisabledAmount(mThisPayAmount[j])%></TD>
					<TD class=ItemBody><%=DataFormat.formatDisabledAmount(mThisTipAmount[j])%></TD>
				</TR>
<%
			}
		}
	}
	else
	{
%>
				<TR align=center borderColor=#999999>
					<TD class=ItemBody>&nbsp;</TD>
					<TD class=ItemBody>&nbsp;</TD>
					<TD class=ItemBody>&nbsp;</TD>
					<TD class=ItemBody>&nbsp;</TD>
					<TD class=ItemBody>&nbsp;</TD>
				</TR>
<%
	}
%>
				<TR borderColor=#999999>
					<TD class=SearchBar colSpan=10 height=46>
					<TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar width="200%">
						<TBODY>
						<TR>
							<TD height=14 colSpan=5>
								<B><B>����������ܼ�:</B></B> <%=sessionMng.m_strCurrencySymbol%>
								<INPUT type="text" class=tar name=dAllAmount size=20 value="<%=DataFormat.formatDisabledAmount(mThisTotalAmount,0)%>" disabled >
							</TD>
						</TR>
						<TR>
							<TD height=14 colSpan=5>
							<B><B>����Ӧ��������ܼ�:</B></B> <%=sessionMng.m_strCurrencySymbol%>
							<INPUT type="text" class=tar name=dAllAmount size=20 value="<%=DataFormat.formatDisabledAmount(mThisDrawTipAmount,0)%>" disabled >
							</TD>
						</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
				</TBODY>
			</TABLE>
				<HR>
			</TD>
		</TR>
		<TR borderColor=#999999>
			<TD colSpan=10 height=100 width=800>
			<TABLE border=0 cellPadding=0 cellSpacing=3>
				<TBODY>
				<TR>
					<TD colSpan=1></TD>
					<TD colSpan=1></TD>
					<TD colSpan=1></TD>
					<TD colspan=7 width=700 align=right></TD>
				</TR>
				<TR>
					<TD colSpan=1>&nbsp;</TD>
					<TD colSpan=1>����������</TD>
					<TD colSpan=1>&nbsp;</TD>
					<TD colspan=7 align=right>
						<iframe src="../common/AttachFrame.jsp?lID=<%=lContractID%>&lTypeID=<%=LOANConstant.AttachParentType.LOAN%>&sCaption=���Ӹ���&showOnly=<%=Constant.YesOrNo.NO%>" width=700 height="80" scrolling="Auto" frameborder="0" name="iFrame" >
						</iframe>
					</TD>
				</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD colSpan=8 height=2>
					<HR>
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<tr>
				<td width="1" height="2">&nbsp;</td>
				<td colspan="8" height="2"> 
					<div align="right">
						<INPUT onfocus="nextfield=''" class=button name="SaveSubmit" onclick="confirmfrm(frm,2)" type="button" value=" �ύ " > 
<%
					if(lStatusID==LOANConstant.LoanDrawNoticeStatus.SUBMIT)
					{

%>
						<INPUT class=button name="back" onclick="confirmfrm(frm,3)" type="button" value=" ȡ�� " onKeydown="confirmfrm(frm,3)"> 
<%
					}

%>
						<input type="button" name="print" onclick="printIt('S002-p.jsp?control=view&lContractID=<%=lContractID%>&lLoanDrawNoticeID=<%=lLoanDrawNoticeID%>')" value=" ��ӡ " class="button">
						<INPUT class=button name="back" onclick="backto(frm,<%=nback%>)" type="button" value=" ���� " onKeydown="if(event.keyCode==13) backto(frm);"> 
					</div>
				</td>
				<td align="right" height="2" width="1">&nbsp;</td>
			</tr>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD colSpan=8 height=2>
					<HR>
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD colSpan=5 height=2  align=center>¼���ˣ�<%=DataFormat.formatString(strInputUserName)%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ˣ�<%=DataFormat.formatString(strCheckUserName)%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;״̬��<%=LOANConstant.LoanDrawNoticeStatus.getName(lStatusID)%></TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD colSpan=3>&nbsp;</TD>
				<TD colSpan=2>&nbsp;</TD>
				<TD colSpan=2>&nbsp;</TD>
				<TD colSpan=3>&nbsp;</TD>
			</TR>
			<tr>
				<td colspan="10" height="2">&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<input type=hidden name="control" value="view">
<input type=hidden name="type" value="<%=lLoanType%>">
<input type=hidden name="lContractID" value="<%=lContractID%>">
<INPUT type="hidden" name="lLoanDrawNoticeID" value="<%=lLoanDrawNoticeID%>">
<input type=hidden name="strAction" value="save">
				<input type=hidden name="backurl" value="002">



<P><BR></P>
</form>


<script language="javascript">
function printIt(url)
{
	window.open(url,'','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
}

</script>
<script language="javascript">
function backto(frm,nback)
{
	if(nback==1)
	{
		eval("history.back(-1)");
	}
	else if(nback==2)
	{
		eval("history.back(-1)");
	}
	else
	{
		eval("self.location='S001.jsp?control=view&type=<%=lLoanType%>'");
	}
}

</script>
<script language="javascript">
function check(frm)
{
	if(<%=dUNPayAmount%> == 0)
	{
		alert("�ú�ͬ�ѷſ���ϣ������ٷſ�뷵������ҳ��");
		frm.mThisDrawAmount.focus();
		return false;
	}
	if(reverseFormatAmount1(frm.mThisDrawAmount.value) > <%=dUNPayAmount%> )
	{
		alert("�ſ���Ӧ��С�ڵ���δ���Ž��:");//+<%=DataFormat.formatDisabledAmount(dUNPayAmount)%>);
		frm.mThisDrawAmount.focus();
		return false;
	}
	else
	{
		return true;
	}
}
</script>
<script language="javascript">
function confirmfrm(frm,nAction)
{
	if(nAction == 1)
	{
		if(!checkAmount(document.frm.mThisDrawAmount,1,"�����")) 	return (false);
		frm.control.value="view";
		frm.strAction.value="count";
		frm.action="S002.jsp";
		showSending();
		frm.submit();
		return true;
	}
	if(nAction == 2)
	{
		if(!check(frm)) return false;
		if(!checkAmount(document.frm.mThisDrawAmount,1,"�����")) 	return (false);
		if(confirm("�Ƿ��ύ������룿"))
		{
			frm.control.value="save";
			frm.strAction.value="save";
			frm.action="S002.jsp";
			showSending();
			frm.submit();
			return true;
		}
	}
	if(nAction == 3)
	{
		if(confirm("�Ƿ�ȡ��������룿"))
		{
			frm.control.value="save";
			frm.strAction.value="cancel";
			frm.action="S002.jsp";
			showSending();
			frm.submit();
			return true;
		}
	}
	else
	{
		return false;
	}
}

</script>
<script language="javascript">
		firstFocus(frm.mThisDrawAmount);
		//setSubmitFunction("confirmfrm(frm,2)");
		setFormName("frm");

</script>

<%

}	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		out.flush();
		return;
	}
%>


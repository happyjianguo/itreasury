<%
/*
���Ʊ���
d001-v
Control
*/
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.ebank.obdrawnotice.dataentity.*,
				com.iss.itreasury.ebank.obdrawnotice.bizlogic.*"
				
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%

	String strOfficeName = sessionMng.m_strOfficeName;
	long lClientID = sessionMng.m_lClientID;
	String strUserName = sessionMng.m_strUserName;
	long lUserID = sessionMng.m_lUserID;
	long CurrencyID = sessionMng.m_lCurrencyID;
	String strTitle = "���Ŵ������֪ͨ�����ύ";
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
		
	    
		//�����������ȡ�������
		
		
		

///////////////////////////////////////////////////////////////////////////////////

		DrawNoticeInfo LDNinfo = null;
		YTFormatInfo YTinfo = null;
////////////////////////////////////////////////////////////////////*/
////////////////////////////////////////////////////////////////////*/
		//�����������ȡ�������
		String strLoanClientName="";//������� ����/����
		strLoanClientName=Env.getClientName();
		String strTmp = "";
		String strAction = "";
		String strControl = "";
		String strBackURL = "";
		String strDisabled = "";
		long lLoanType = -1;

		long lContractID=-1;		//��ͬ��ʾ
		String strContractCode="";	//��ͬ���
		Timestamp dtStartDate=null;//��ͬ��ʼ����
		Timestamp dtEndDate=null;//��������
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
		Timestamp dtContractInputDate = null;
		String strContractInputDate = "";

		long lLoanDrawNoticeID = -1;//���ŷſ�֪ͨ��ID
		String strDrawCode="";	//�ſ�֪ͨ�����
		String[] strBankName = {"","","","","","",""};//
		double[] mBankLoanAmount = {0,0,0,0,0,0};//
		double[] mBankLoanRate = {0,0,0,0,0,0};//
		double[] mBankConsignAmount = {0,0,0,0,0,0};//
		double[] mBankTrustAmount = {0,0,0,0,0,0};//
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
		String strInputDate="";//¼������
		Timestamp dtToday = Env.getSystemDate();//��������

///////control/////////////////////////////////////////////


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
		strTmp = request.getParameter("strAction");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strAction = strTmp.trim();
		}
		LDNinfo = (DrawNoticeInfo)request.getAttribute("LDNinfo");
///////////////view//////////////////////////////////////////////////



			lLoanDrawNoticeID = LDNinfo.getID();
			lContractID = LDNinfo.getContractID();

			
			if (mThisDrawAmount <=0)
				{
					mThisDrawAmount = LDNinfo.getDrawAmount();
				}
			lInputUserID = LDNinfo.getInputUserID();//¼����
			strInputUserName = LDNinfo.getInputUserName();//¼����
			lCheckUserID = LDNinfo.getNextCheckUserID();//�����
			strCheckUserName = LDNinfo.getCheckUserName();//�����
			
			lStatusID = LDNinfo.getStatusID();//
			
			
			
		
			if (lContractID > 0)
			{
				strContractCode=DataFormat.formatString(LDNinfo.getContractCode());			//��ͬ���			
				dAmount =LDNinfo.getExamineAmount();					//������,��׼���
				nIntervalNum =LDNinfo.getIntervalNum();			//����
				dLoanRate=LDNinfo.getInterestRate();				//����ִ������
				dChargeRate=LDNinfo.getAgentRate();//��������
				strBorrowClientName=DataFormat.formatString(LDNinfo.getClientName());	//��λ����
				strBorrowClientCode=DataFormat.formatString(LDNinfo.getClientCode());
				dtStartDate=LDNinfo.getLoanStart();//��ͬ��ʼ����
				dtEndDate=LDNinfo.getLoanEnd();//��������
				dUNPayAmount = LDNinfo.getUnPayAmount();
				
				Log.print("δ���Ž��:"+DataFormat.formatAmount(dUNPayAmount));
				YTinfo = new YTFormatInfo();
				YTinfo = LDNinfo.getYTInfo();
				if(YTinfo != null)
				{
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

///////////////////////////////////////////////////////////////////////////////////////
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


///////////////////////////////////////////////////////////////////////////////////////////////////

			
%>



<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
	<TABLE align=center border=0  height=20 width="75%">
		<TBODY>
<%
		for(int j=0;j<6;j++)
		{
		
			if(mBankLoanRate[j] > 0)
			{
			
				if(j==1)
				{
%>
					<tr>
						<td valign="top" colspan=5>&nbsp;</td>
					</tr>
<%
				}
				else
				{
%>
					<tr>
						<td>
							<br clear=all style='page-break-before:always'>
						</td>
					</tr>
<%
				}
%>
					<tr>
						<td valign="top" colspan=5>&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" colspan=5>&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" colspan=5>&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" colspan=5>&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" colspan=5>&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" colspan=5>&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" colspan=5><font size="4"><b>��������</b></font></td>
					</tr>
					<tr>
						<td valign="top" align="center" colspan=5>
							<font size="4"><b>�����зִ����֪ͨ��</b><br><br></font>
						</td>
					</tr>
					<tr>
						<td valign="top" align="right" colspan=5>
							<font size="4">��ţ�<U>����<%=DataFormat.formatString(strDrawCode)%>����&nbsp;</U></font>
						</td>
					</tr>
					<tr>
						<td valign="top" colspan=5>
							<font size="2"><br><br>
								<U>����<%=DataFormat.formatString(strBankName[j])%>����  </U>�У�<br><br><br><br>
<%
										strContractInputDate = DataFormat.getDateString(dtContractInputDate);
%>
								��������<U>  ��<%=strContractInputDate.substring(0,4)%>��  </U>��<U>  ��<%=strContractInputDate.substring(5,7)%>��  </U>��<U>  ��<%=strContractInputDate.substring(8,10)%>��  </U>����˾����м������μ���ǩ����
									<U>  ����  <%=DataFormat.formatString(strContractCode)%>����  </U> �š����Ŵ����ͬ����
									���Ŵ����ˣ�<U>����  <%=DataFormat.formatString(strBorrowClientName)%>����  </U>
<%
										//strInputDate = DataFormat.getDateString(dtInputDate);
										String sTemp = DataFormat.getDateString(dtInputDate);
										String s1="",s2="",s3="";
			if (sTemp.length() > 9)
			{
				s1 = sTemp.substring(0, 4);
				s2 = sTemp.substring(5, 7);
				s3 = sTemp.substring(8, 10);
			}
			else
			{
				s1 = "&nbsp;&nbsp;&nbsp;&nbsp;";
				s2 = "&nbsp;&nbsp;";
				s3 = "&nbsp;&nbsp;";
			}

%>
									����<U>  ��<%=s1%>��  </U>��<U>  ��<%=s2%>��  </U>��<U>  ��<%=s3%>��  </U>������˾�������Ϊ<U>����  <%=DataFormat.formatString(strDrawCode)%>����  </U> ���ɳ��������֪ͨ�����ݡ����Ŵ����ͬ���й����ڴ˱����Ŵ���ĳд���������˾��Ϊ����������з������ɳ��������֪ͨ��<br><br>

								��������˱�����������ܶ����Ҵ�д����<U>����  <%=ChineseCurrency.showChinese( DataFormat.formatAmount(mThisDrawAmount))%>����  &nbsp;</U> ��<br><br>

								��������������Ӧ֧���Ľ�����Ҵ�д����<U>����  <%=ChineseCurrency.showChinese( DataFormat.formatAmount(mThisPayAmount[j]))%>����  &nbsp;</U> ��<br><br>

								���������ѣ�����Ҵ�д����<U>  ����  <%=ChineseCurrency.showChinese( DataFormat.formatAmount(mThisTipAmount[j]))%>����  &nbsp;</U> ��<br><br>

								�����������<U>  ��������  </U>��<U>  ����  </U>��<U>  ����  </U> �ս������������ݡ����Ŵ����ͬ��Ҫ��ʱ�����Ҵ������˻���<br><br>

								�������·����<U>&nbsp;  ������������������������������&nbsp;</U><br><br>

								����������λ��<U>  ����<%=Env.getClientName()%>������&nbsp;</U><br><br>

								�����������У�<U>&nbsp;  ������������������������������&nbsp;</U><br><br>

								�����ˡ����ţ�<U>&nbsp;  ������������������������������&nbsp;</U><br><br><br><br><br>


								��������֪ͨ��<br><br><br><br><br><br>

									��������������������������������������������<%=Env.getClientName()%><br><br><br>
									������������������������������������������
										<U>  ��������  </U>��<U>  ����  </U>��<U>  ����  </U>��<br>
							</font>
						</td>
					</tr>
<%
			}
		}
//	}
//}
//else
//{
	//out.print("��û���ύ�������֪ͨ��");
//	out.print("û�д���ʶ");
//}
%>
	</TBODY>
</table>


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
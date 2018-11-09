<%
/*
控制变量
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
	String strTitle = "银团贷款提款通知单－提交";
//////////////////////////////////////////////////////////////////////////////////////////////

	try
	{
		//判断是否登录//CODE_COMMONMESSAGE_LOGIN=1
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
		//定义变量
		
	    
		//定义变量，获取请求参数
		
		
		

///////////////////////////////////////////////////////////////////////////////////

		DrawNoticeInfo LDNinfo = null;
		YTFormatInfo YTinfo = null;
////////////////////////////////////////////////////////////////////*/
////////////////////////////////////////////////////////////////////*/
		//定义变量，获取请求参数
		String strLoanClientName="";//贷款方名称 华能/大桥
		strLoanClientName=Env.getClientName();
		String strTmp = "";
		String strAction = "";
		String strControl = "";
		String strBackURL = "";
		String strDisabled = "";
		long lLoanType = -1;

		long lContractID=-1;		//合同标示
		String strContractCode="";	//合同编号
		Timestamp dtStartDate=null;//合同开始日期
		Timestamp dtEndDate=null;//结束日期
		double dAmount = 0;//贷款金额
		long nIntervalNum = -1;//贷款期限
		double dBalance = 0;//贷款余额
		double dUNPayAmount = 0;
		double dLoanRate=0;//贷款利率
		double dChargeRate=0;//手续费率
		String strLoanPurpose="";//借款用途
		String strBorrowClientName="";	//借款单位名称
		String strBorrowClientCode="";
		String strConsignClientName="";	//委托单位名称
		String strConsignClientAccount = "";//委托账户
		Timestamp dtContractInputDate = null;
		String strContractInputDate = "";

		long lLoanDrawNoticeID = -1;//银团放款通知单ID
		String strDrawCode="";	//放款通知单编号
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

		long lStatusID = -1;//状态
		long lInputUserID = -1;//录入人
		String strInputUserName="";//录入人名称
		long lCheckUserID=-1;//审核人
		String strCheckUserName="";//审核人名称
		Timestamp dtInputDate=null;//录入日期
		String strInputDate="";//录入日期
		Timestamp dtToday = Env.getSystemDate();//新增日期

///////control/////////////////////////////////////////////


		strTmp = request.getParameter("lContractID");//合同ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lContractID = Long.parseLong(strTmp.trim());
		}
		strTmp = request.getParameter("lLoanDrawNoticeID");//合同ID
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
			lInputUserID = LDNinfo.getInputUserID();//录入人
			strInputUserName = LDNinfo.getInputUserName();//录入人
			lCheckUserID = LDNinfo.getNextCheckUserID();//审核人
			strCheckUserName = LDNinfo.getCheckUserName();//审核人
			
			lStatusID = LDNinfo.getStatusID();//
			
			
			
		
			if (lContractID > 0)
			{
				strContractCode=DataFormat.formatString(LDNinfo.getContractCode());			//合同编号			
				dAmount =LDNinfo.getExamineAmount();					//贷款金额,批准金额
				nIntervalNum =LDNinfo.getIntervalNum();			//期限
				dLoanRate=LDNinfo.getInterestRate();				//贷款执行利率
				dChargeRate=LDNinfo.getAgentRate();//手续费率
				strBorrowClientName=DataFormat.formatString(LDNinfo.getClientName());	//借款单位名称
				strBorrowClientCode=DataFormat.formatString(LDNinfo.getClientCode());
				dtStartDate=LDNinfo.getLoanStart();//合同开始日期
				dtEndDate=LDNinfo.getLoanEnd();//结束日期
				dUNPayAmount = LDNinfo.getUnPayAmount();
				
				Log.print("未发放金额:"+DataFormat.formatAmount(dUNPayAmount));
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
						<td valign="top" colspan=5><font size="4"><b>附件三：</b></font></td>
					</tr>
					<tr>
						<td valign="top" align="center" colspan=5>
							<font size="4"><b>代理行分次提款通知书</b><br><br></font>
						</td>
					</tr>
					<tr>
						<td valign="top" align="right" colspan=5>
							<font size="4">编号：<U>　　<%=DataFormat.formatString(strDrawCode)%>　　&nbsp;</U></font>
						</td>
					</tr>
					<tr>
						<td valign="top" colspan=5>
							<font size="2"><br><br>
								<U>　　<%=DataFormat.formatString(strBankName[j])%>　　  </U>行：<br><br><br><br>
<%
										strContractInputDate = DataFormat.getDateString(dtContractInputDate);
%>
								　　根据<U>  　<%=strContractInputDate.substring(0,4)%>　  </U>年<U>  　<%=strContractInputDate.substring(5,7)%>　  </U>月<U>  　<%=strContractInputDate.substring(8,10)%>　  </U>日我司与贵行及其他参加行签订的
									<U>  　　  <%=DataFormat.formatString(strContractCode)%>　　  </U> 号《银团贷款合同》，
									银团贷款人：<U>　　  <%=DataFormat.formatString(strBorrowClientName)%>　　  </U>
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
									已于<U>  　<%=s1%>　  </U>年<U>  　<%=s2%>　  </U>月<U>  　<%=s3%>　  </U>日向我司发出编号为<U>　　  <%=DataFormat.formatString(strDrawCode)%>　　  </U> 不可撤销的提款通知。根据《银团贷款合同》中贵行在此笔银团贷款的承贷比例，我司作为代理行向贵行发出不可撤销的提款通知：<br><br>

								　　借款人本次申请提款总额（人民币大写）：<U>　　  <%=ChineseCurrency.showChinese( DataFormat.formatAmount(mThisDrawAmount))%>　　  &nbsp;</U> 。<br><br>

								　　按比例贵行应支付的金额（人民币大写）：<U>　　  <%=ChineseCurrency.showChinese( DataFormat.formatAmount(mThisPayAmount[j]))%>　　  &nbsp;</U> 。<br><br>

								　　手续费（人民币大写）：<U>  　　  <%=ChineseCurrency.showChinese( DataFormat.formatAmount(mThisTipAmount[j]))%>　　  &nbsp;</U> 。<br><br>

								　　请贵行于<U>  　　　　  </U>年<U>  　　  </U>月<U>  　　  </U> 日将上述提款金额根据《银团贷款合同》要求按时汇入我代理行账户。<br><br>

								　　汇款路径：<U>&nbsp;  　　　　　　　　　　　　　　　&nbsp;</U><br><br>

								　　开户单位：<U>  　　<%=Env.getClientName()%>　　　&nbsp;</U><br><br>

								　　开户银行：<U>&nbsp;  　　　　　　　　　　　　　　　&nbsp;</U><br><br>

								　　账　　号：<U>&nbsp;  　　　　　　　　　　　　　　　&nbsp;</U><br><br><br><br><br>


								　　谨此通知。<br><br><br><br><br><br>

									　　　　　　　　　　　　　　　　　　　　　　<%=Env.getClientName()%><br><br><br>
									　　　　　　　　　　　　　　　　　　　　　
										<U>  　　　　  </U>年<U>  　　  </U>月<U>  　　  </U>日<br>
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
	//out.print("还没有提交银团提款通知单");
//	out.print("没有传标识");
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
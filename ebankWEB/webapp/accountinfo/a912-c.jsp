<%--
 ҳ������ ��c007.jsp
 ҳ�湦�� : �����ջش�ӡ����ҳ��
 ��    �� ��rxie
 ��    �� ��2003-11-20
 ����˵�� ��ʵ�ֲ���˵����findByIDȡ��������Ϣ
 �޸���ʷ ��
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dao.Sett_TransRepaymentDiscountDAO"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>


<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//ҳ����Ʊ���
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;

    try
	{
		//�ж��Ƿ��¼
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}

		long lID = -1;
		String strPrintPage = "";
        //�������
		long lOfficeID = -1;
		
		
		//ҳ�渨������
		String strContinueSave = null;

		String strExecuteDate = null;
		String strInterestStartDate = null;
		String strModifyTime = null;

		//��ӡ����
		long lShow = -1;
        //��ȡ����
        strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		strContinueSave = (String)request.getAttribute("strContinueSave");
		
		String strTemp = null;
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strPrintPage");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strPrintPage = strTemp;
		}
		
		//TransDiscountDelegation transDiscountDelegation = new TransDiscountDelegation();
		Sett_TransRepaymentDiscountDAO loanDelegation = new Sett_TransRepaymentDiscountDAO();
		
		TransRepaymentDiscountInfo resultInfo = null;
		
		//resultInfo = transDiscountDelegation.repaymentFindDetailByID(lID);
		resultInfo=loanDelegation.findByID(lID);
  		
		PrintInfo printInfo = new PrintInfo();
		
		if(resultInfo != null)
		{
		Log.print("result not null");
			//set ��ӡ����
			   if(resultInfo.getNOfficeID() > 0 )
			{
				printInfo.setOfficeID(resultInfo.getNOfficeID());
				
			}	
			if(resultInfo.getNCurrencyID() > 0 )
			{
				printInfo.setCurrencyID(resultInfo.getNCurrencyID());
			}
			
			if (resultInfo.getNStatusID() > 0)
			{
				printInfo.setStatusID(resultInfo.getNStatusID());
			}
			if (resultInfo.getNTransactionTypeID() > 0)
			{
				printInfo.setTransTypeID(resultInfo.getNTransactionTypeID());
			}
			
			//  ִ���գ���������
			if(resultInfo.getDtExecute() != null )
			{
				printInfo.setExecuteDate(resultInfo.getDtExecute());
			}
			
			//��Ϣ�գ������е�����
			if(resultInfo.getDtInterestStart() != null )
			{
				printInfo.setInterestStartDate(resultInfo.getDtInterestStart());
			}
			if(resultInfo.getSTransNo() != null && resultInfo.getSTransNo().length() > 0)
			{
				printInfo.setTransNo(resultInfo.getSTransNo());
			}
			
			if(resultInfo.getSAbstract() != null && resultInfo.getSAbstract().length() > 0)
			{
				printInfo.setAbstract(resultInfo.getSAbstract());
			}
			
			if(resultInfo.getNInputUserID() > 0 )
			{
				printInfo.setInputUserID(resultInfo.getNInputUserID());
			}
			
			if(resultInfo.getNCheckUserID() > 0 )
			{
				printInfo.setCheckUserID(resultInfo.getNCheckUserID());
			}
			
			
	     
				
		/*	
		//�տ
			if(resultInfo.getNClientID() > 0 )
			{
				printInfo.setReceiveClientID(resultInfo.getNClientID());
			}
			
			if(resultInfo.getNDiscountAccountID()>0)
			{
				printInfo.setReceiveAccountID(resultInfo.getNDiscountAccountID());
			}

			if(resultInfo.getNBankID()>0)
			{
				printInfo.setReceiveBankID(resultInfo.getNBankID());
			}
			*/
			
			//���ݺ�
			if(resultInfo.getNDiscountNoteID() > 0)
			{
				printInfo.setFixedDepositNo(NameRef.getDiscountCredenceNoByID(resultInfo.getNDiscountNoteID()));
			}
			
			//���ֻ�ƱID(���ݴ�IDȡ���ֻ�Ʊ����)
			if(resultInfo.getNDiscountNoteID() > 0)
			{
				printInfo.setDiscountNoteID(resultInfo.getNDiscountNoteID());
			}
			
			//��ƱID,(���ݴ�ID��ȡ��Ʊ����)
			if(resultInfo.getNDiscountBillID() > 0)
			{
				printInfo.setDiscountBillID(resultInfo.getNDiscountBillID());
			}
			
			//���ֵ�λ�˺ţ����տ��λ�����ݴ�ID����ȡ���ֵ�λ����  
			if(resultInfo.getNDiscountAccountID() > 0)
			{
				printInfo.setReceiveAccountID(resultInfo.getNDiscountAccountID());
			}
			
			//�����ջؽ��
			if(resultInfo.getMAmount() != 0.0)
			{
				printInfo.setDiscountAmount(resultInfo.getMAmount());
				printInfo.setAmount(resultInfo.getMAmount());//Ϊ�˴�ӡ ����ת�˽��ƾ֤ �Ľ��
			}
			Log.print("�����ջؽ��:"+printInfo.getDiscountAmount());
			
			//��ǰ�������
			if(resultInfo.getMSumReceiveAmout()!= 0.0)
			{
				printInfo.setCurrentBalance(resultInfo.getMSumReceiveAmout());
			}
			Log.print("�����ջ����:"+printInfo.getCurrentBalance());
			
			//��������ID,ɾ��ԭ���������ջؽ����д�ӡ������ת�˽跽ƾ֤�и��ȡ��Ʊ�����еĻ����˺ţ�
			//���û����Ʊ���򸶿��ʾΪ��
			/*	if (resultInfo.getNBankID() > 0)
				{
					printInfo.setExtClientName(NameRef.getBankNameByID(resultInfo.getNBankID()));
					printInfo.setExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getNBankID()));
				}
				*/
			//���
			if (resultInfo.getNDepositAccountID() > 0)
			{
				printInfo.setPayAccountID(resultInfo.getNDepositAccountID());
				Log.print("����˺ţ�" + printInfo.getPayAccountID());
			}
			
			//��Ʊ���
			if(resultInfo.getMReturnedAmount() != 0.0)
			{
				printInfo.setAmount(resultInfo.getMReturnedAmount());
			}
			
			if (resultInfo.getMOverDueInterest() > 0)
			{
				printInfo.setOverDueAmount(resultInfo.getMOverDueInterest());
			}
			
			Log.print("��Ʊ���:"+resultInfo.getMReturnedAmount());
			//set��ӡ����End
			
			if ((strPrintPage.indexOf("1") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('1','a');
				lShow = 1;
				PrintVoucher.PrintRepaymentDiscount(printInfo,out);//��ӡ�����ջ�ƾ֤
			}
		
			if ((strPrintPage.indexOf("4") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('2','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo,out);//��ӡ��ת��
			}
			
		 /*  if ((strPrintPage.indexOf("3") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('3','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo,out);//��ӡ��ת��
			}
		*/	
		
			if ((strPrintPage.indexOf("b") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('b','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(1,1,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("c") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('c','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(2,1,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("d") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('d','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(3,1,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("e") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('e','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(4,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("f") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('f','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(5,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("g") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('g','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(6,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("h") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('h','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(7,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("i") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('i','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(8,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("j") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('j','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(5,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("k") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('k','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(6,3,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("l") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('l','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(7,3,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("m") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('m','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(8,3,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("n") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('n','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(8,1,printInfo,out);//�״�
			}
			
			
			       if (lShow < 0)
					{
		%>	
					<SCRIPT language=JavaScript>
						alert("��ӡ��ϣ�");
						window.close();
					</script>
		<%
						return;
					}
					
			IPrintTemplate.noShowPrintHeadAndFooter(out,0,lOfficeID);
		}
	%>
<Script Language="JavaScript">
netscape = "";
function keyDown(DnEvents)
{
	k = (netscape) ? DnEvents.which : window.event.keyCode;
	
	if (k == 13)
	 { 
	 	location.href='../accountinfo/a912-c.jsp?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
	 }
}

document.onkeydown = keyDown; // work together to analyze keystrokes
if (netscape) document.captureEvents(Event.KEYDOWN|Event.KEYUP);
</SCRIPT>

		<%

	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;
	}

	request.setAttribute("strActionResult",strActionResult);
/*
	//���ݴ�����������һ����ת��Ŀ��ҳ��
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;

	//ת����һҳ��
	Log.print("Next Page URL:"+strNextPageURL);	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
*/
%>
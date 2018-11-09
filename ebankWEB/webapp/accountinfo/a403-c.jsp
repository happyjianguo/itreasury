<%--
/**
 ҳ������ ��a403-c.jsp
 ҳ�湦�� : ����(֪ͨ)֧ȡ��ӡ����ҳ��
 ��    �� ��kewen hu
 ��    �� ��2004-02-24
 ����˵�� ��ʵ�ֲ���˵����findByIDȡ��������Ϣ
 ����֧ȡ:(֪֧ͨȡ)
 			���˵�������
			����ת�ˣ���/������ƾ֤
			�����Ϣ�Ƹ�֪ͨ��
			���֧ȡƾ֤
 �޸���ʷ ��
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransFixedDepositDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedWithDrawDAO"%>
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

    //�������
    String strTitle = "[�˻���ʷ��ϸ]";
    try {
         /* �û���¼��� */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }
        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

		long lID = -1;
		long lOfficeID = sessionMng.m_lOfficeID;
		String strPrintPage = "";
      
	  //ҳ�渨������
		String strContinueSave = null;
		String strExecuteDate = null;
		String strInterestStartDate = null;
		String strModifyTime = null;
		long lTransactionTypeID = -1;

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
		
		strTemp = (String)request.getParameter("lTransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lTransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		
		//TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();

		//����֧ȡ��Ϣ
		TransFixedDrawInfo resultInfo = null;
		resultInfo = dao.findByID(lID);
		PrintInfo printInfo = new PrintInfo();
		//���֧ȡ��Ϣ
		if(resultInfo!=null)
		{
			if(resultInfo.getOfficeID() > 0 )
			{
				printInfo.setOfficeID(resultInfo.getOfficeID());
			}
			if(resultInfo.getCurrencyID() > 0 )
			{
				printInfo.setCurrencyID(resultInfo.getCurrencyID());
			}
			if(resultInfo.getAbstractID() > 0 )
			{
				printInfo.setAbstractID(resultInfo.getAbstractID());
			}
		
			if(resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
			{
				printInfo.setAbstract(resultInfo.getAbstract());
			}
			if(resultInfo.getInputUserID() > 0 )
			{
				printInfo.setInputUserID(resultInfo.getInputUserID());
			}
			if(resultInfo.getCheckUserID() > 0 )
			{
				printInfo.setCheckUserID(resultInfo.getCheckUserID());
			}
			if(resultInfo.getExecuteDate() != null )
			{
				printInfo.setExecuteDate(resultInfo.getExecuteDate());
			}
			if(resultInfo.getInterestStartDate() != null )
			{
				printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
			}
			if(resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
			{
				printInfo.setTransNo(resultInfo.getTransNo());
			}		

			//����
		if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)	
		{
			if(resultInfo.getAmount() != 0.0 )
			{
				printInfo.setAmount(resultInfo.getAmount());
			}
		}
		if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)	
		{
			if(resultInfo.getDrawAmount() != 0.0 )
			{
				printInfo.setAmount(resultInfo.getDrawAmount());
			}
		}  	
			if(resultInfo.getCapitalExtBankNo() != null && resultInfo.getCapitalExtBankNo().length() > 0)
			{
				request.setAttribute("strExtBankNo",resultInfo.getCapitalExtBankNo());
			}
			if(resultInfo.getInstructionNo() != null && resultInfo.getInstructionNo().length() > 0)
			{
				request.setAttribute("strInstructionNo",resultInfo.getInstructionNo());
			}			
			

		   if(resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
			{
				printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
			}
			if(resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
			{
				printInfo.setExtClientName(resultInfo.getExtClientName());
			}
			
			//�տ��˻���������
			if(resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
			{
				printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
			}
			//�տ��˻���ʡ
			if(resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
			{
				printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
			}
			//�տ��˻�����
			if(resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
			{
				printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
			}

			if(resultInfo.getClientID() > 0 )
			{
				printInfo.setReceiveClientID(resultInfo.getClientID());
			}

			//�������˻�id,����˺�,������ȫ��
			if(resultInfo.getAccountID() > 0)
			{
				printInfo.setPayAccountID(resultInfo.getAccountID());
			}
			
			
			//�տ���˺�Id���ɵ��տ����˺ţ��տ���ȫ��
			if(resultInfo.getCurrentAccountID() > 0)
			{
				printInfo.setReceiveAccountID(resultInfo.getCurrentAccountID());
			}

			//�տ��˿�����id���ɵ�PayBankCode,PayBankName��ReceiveBankCode
			if(resultInfo.getBankID() >0 )
			{
			  printInfo.setReceiveBankID(resultInfo.getBankID());
			}

			//�Ӷ����˻�id
			if(resultInfo.getSubAccountID() > 0)
			{
				printInfo.setSubAccountID(resultInfo.getSubAccountID());
			}

			//�˺��룬�ͻ����ƣ�������ø������˻�idȡ
			
			//��Ϣ
			if(resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				if(resultInfo.getDrawInterest() != 0.0)
				{
					printInfo.setPayableInterest(resultInfo.getDrawInterest());
				}
			}
			else
			{
				if(resultInfo.getTotalInterest() != 0.0)
				{
					printInfo.setPayableInterest(resultInfo.getTotalInterest());
				}
			}
			
			//������Ϣ
			if(resultInfo.getCurrentInterest() != 0.0)
			{
				printInfo.setCurrentInterest(resultInfo.getCurrentInterest());
			}
			Log.print("������Ϣ:"+printInfo.getCurrentInterest());
			
			//������Ϣ
			if(resultInfo.getOtherInterest() != 0.0)
			{
				printInfo.setOtherInterest(resultInfo.getOtherInterest());
			}
			Log.print("������Ϣ:"+printInfo.getOtherInterest());
		
			//��Ϣ�˺�
			if (resultInfo.getReceiveInterestAccountID() > 0)
			{
				printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				
			}
			
			//����
			if(resultInfo.getRate() != 0.0)
			{
				printInfo.setRate(resultInfo.getRate());
			}
			
			//�浥����
			if(resultInfo.getDepositNo()!= null || resultInfo.getDepositNo().length() > 0)
			{
				printInfo.setFixedDepositNo(resultInfo.getDepositNo());
			}
			
			//�������
			if (resultInfo.getTransactionTypeID() > 0 )
			{
				printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
			}
			
			//��ʼ����
			if(resultInfo.getStartDate() != null)
			{
				printInfo.setStartDate(resultInfo.getStartDate());
			}
			
			//��������
			if(resultInfo.getEndDate() != null)
			{
				printInfo.setEndDate(resultInfo.getEndDate());
			}
			
			//�����֪ͨ���������ڵ�����Ϣ��
			if(resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				printInfo.setEndDate(resultInfo.getInterestStartDate());
				Log.print("֪ͨ������:"+printInfo.getEndDate());
			}

		}  		
		
		if(resultInfo != null)
		{
			
			if ((strPrintPage.indexOf("1") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('1','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo,out);//��ӡ���˵�
			}
			if ((strPrintPage.indexOf("4") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('4','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo,out);//��ӡ��ת��
			}
			if ((strPrintPage.indexOf("2") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('2','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo,out);//��ӡ��ת��
			}
			
			if ((strPrintPage.indexOf("3") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('3','a');
				lShow = 1;
				PrintVoucher.PrintShowWithDraw(printInfo,out);//���֧��ƾ֤
			}
			
			if ((strPrintPage.indexOf("7") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('7','a');
				lShow = 1;
				PrintVoucher.PrintDepositInterest(printInfo,out);//�����Ϣ�Ƹ�֪ͨ��
			}
			
			
			/*
			�ر�˵����Ϊ��Ӱ���Ѿ���ɲ�ͨ�����Ե�ƾ֤���ڴ˴�����״�ƾ֤����printInfo������
			�˴����õ���IPrintVourcher�е�PrintTemplate����
			*/
			
			if(resultInfo.getAccountID() > 0 )
			{
			   printInfo.setPayAccountID(resultInfo.getAccountID());
			}
			
			if(resultInfo.getCurrentAccountID() > 0)
			{
			   printInfo.setReceiveAccountID(resultInfo.getCurrentAccountID());	
			}
			
			
			if(resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
			{
				printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
			}
			
			if(resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
			{
			   printInfo.setReceiveExtAccountNo(resultInfo.getExtAcctNo());
			}
			
			if(resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
			{
				printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());
			}
			
			if(resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() >0)
			{
			   printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
			}
			
			if(resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() >0)
			{
			   printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
			}
						
			if ((strPrintPage.indexOf("b") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('b','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(1,1,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("c") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('c','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(2,1,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("d") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('d','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(3,1,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("e") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('e','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(4,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("f") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('f','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(5,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("g") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('g','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(6,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("h") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('h','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(7,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("i") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('i','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(8,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("j") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('j','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(5,2,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("k") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('k','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(6,3,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("l") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('l','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(7,3,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("m") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('m','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(8,3,printInfo,out);//�״�
			}
			if ((strPrintPage.indexOf("n") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('n','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(8,1,printInfo,out);//�״�
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
	 	location.href='../a403-c.jsp?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
	 }
}

document.onkeydown = keyDown; // work together to analyze keystrokes
if (netscape) document.captureEvents(Event.KEYDOWN|Event.KEYUP);
</SCRIPT>

<%

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
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
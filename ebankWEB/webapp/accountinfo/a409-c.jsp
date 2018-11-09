<%--
/**
 ҳ������ ��a409-c.jsp
 ҳ�湦�� : ����ҵ���ӡ����ҳ��
 ��    �� ��kewen hu
 ��    �� ��2004-02-26
 ����˵�� ��ʵ�ֲ���˵����findByIDȡ��������Ϣ
 							��������ҵ���ӡ���������߾�Ϊ���е�����������ӡ�״�
							����-������Ĵ�ӡ(δ��)
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
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO"%>
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
		String strPrintPage = "";
        //�������
		long lOfficeID = sessionMng.m_lOfficeID;
		
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

		//TransSpecialOperationDelegation depositDelegation = new TransSpecialOperationDelegation();
		Sett_TransSpecialOperationDAO dao = new Sett_TransSpecialOperationDAO();

		TransSpecialOperationInfo resultInfo = null;
		
		//resultInfo = depositDelegation.findDetailByID(lID);
		resultInfo = dao.findByID(lID);
  		
		PrintInfo printInfo = new PrintInfo();
		PrintInfo printInfo2 = new PrintInfo();//���2��ƾ֤
		if(resultInfo != null)
		{
		Log.print("result not null");
			printInfo.setTransTypeID(SETTConstant.TransactionType.SPECIALOPERATION);
			printInfo2.setTransTypeID(SETTConstant.TransactionType.SPECIALOPERATION);
			//set ��ӡ����
			  if(resultInfo.getNofficeid() > 0 )
			{
				printInfo.setOfficeID(resultInfo.getNofficeid());
				printInfo2.setOfficeID(resultInfo.getNofficeid());
			}
			
			//����
			if(resultInfo.getNcurrencyid() > 0 )
			{
				printInfo.setCurrencyID(resultInfo.getNcurrencyid());
				printInfo2.setCurrencyID(resultInfo.getNcurrencyid());
			}
			
			//ִ����
			if(resultInfo.getDtexecute() != null )
			{
				printInfo.setExecuteDate(resultInfo.getDtexecute());
				printInfo2.setExecuteDate(resultInfo.getDtexecute());
			}
			
			if(resultInfo.getDtintereststart() != null )
			{
				printInfo.setInterestStartDate(resultInfo.getDtintereststart());
				printInfo2.setInterestStartDate(resultInfo.getDtintereststart());
			}
			
			//���׺�
			if(resultInfo.getStransno() != null && resultInfo.getStransno().length() > 0)
			{
				printInfo.setTransNo(resultInfo.getStransno());
				printInfo2.setTransNo(resultInfo.getStransno());
			}
			
			if(resultInfo.getSabstract() != null && resultInfo.getSabstract().length() > 0)
			{
				printInfo.setAbstract(resultInfo.getSabstract());
				printInfo2.setAbstract(resultInfo.getSabstract());
			}
			
			if(resultInfo.getNabstractid() > 0 )
			{
				printInfo.setAbstractID(resultInfo.getNabstractid());
				printInfo2.setAbstractID(resultInfo.getNabstractid());
			}
			
			if(resultInfo.getNinputuserid() > 0 )
			{
				printInfo.setInputUserID(resultInfo.getNinputuserid());
				printInfo2.setInputUserID(resultInfo.getNinputuserid());
			}
			
			if(resultInfo.getNcheckuserid() > 0 )
			{
				printInfo.setCheckUserID(resultInfo.getNcheckuserid());
				printInfo2.setCheckUserID(resultInfo.getNcheckuserid());
			}
			
			//������Ϣ����
			//���ݽ���������ж��ո�����
			/*
				���⽻��ԭ��
				1���� ��ӡ�� ƾ֤�ظ���
				2���� ��ӡ�� ƾ֤�ؽ跽
			*/
			
			Log.print("�ո�����:��="+resultInfo.getNpaydirection()+":��="+resultInfo.getNreceivedirection());
			if (resultInfo.getNpaydirection()==-1&&resultInfo.getNreceivedirection()==-1)
			{
				resultInfo.setNpaydirection(SETTConstant.DebitOrCredit.DEBIT) ;
				resultInfo.setNreceivedirection(SETTConstant.DebitOrCredit.CREDIT) ;
			}

			if(resultInfo.getNpaydirection() != resultInfo.getNreceivedirection())// �ո�������ͬ
			{
			Log.print("�ո�������ͬ");
				if(resultInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)//����Ϊ�跽
				{
				Log.print("����Ϊ�跽");
					//����˺�
					if(resultInfo.getNpayaccountid() > 0)
					{
						printInfo.setPayAccountID(resultInfo.getNpayaccountid());
					}
					
					//���������
					if(resultInfo.getNpaybankid() > 0)
					{
						printInfo.setPayBankID(resultInfo.getNpaybankid());
						printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getNpaybankid()));	
						printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getNpaybankid()));
						printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getNpaybankid()));//����ת�˲�д��������Ϣ
						printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getNpaybankid()));
						printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getNpaybankid()));
					}
					
					//�������
					if(resultInfo.getNpaygeneralledgertypeid() > 0)
					{
						printInfo.setPayGL(resultInfo.getNpaygeneralledgertypeid());
					}
					//�տ�˺�
					if(resultInfo.getNreceiveaccountid() > 0)
					{
						printInfo.setReceiveAccountID(resultInfo.getNreceiveaccountid());
					}
					
					//�տ������
					if(resultInfo.getNreceivebankid() > 0)
					{
						printInfo.setReceiveBankID(resultInfo.getNreceivebankid());
						printInfo.setReceiveExtClientName(NameRef.getBankNameByID(resultInfo.getNreceivebankid()));	
						printInfo.setReceiveExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getNreceivebankid()));
						printInfo.setReceiveExtRemitInBank(NameRef.getBankNameByID(resultInfo.getNreceivebankid()));//����ת�˲�д��������Ϣ
						printInfo.setReceiveExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getNreceivebankid()));
						printInfo.setReceiveExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getNreceivebankid()));

					}
					
					//�տ���˿�Ŀ
					if(resultInfo.getNreceivegeneralledgertypeid() > 0)
					{
						printInfo.setReceiveGL(resultInfo.getNreceivegeneralledgertypeid());
					}
					
					//������
					if(resultInfo.getMpayamount() != 0.0 )
					{
						printInfo.setAmount(resultInfo.getMpayamount());
					}
					
					/*
					//��������
					if(resultInfo.getSextaccountno() != null && resultInfo.getSextaccountno().length() > 0)
					{
						printInfo.setExtAccountNo(resultInfo.getSextaccountno());
					}
					if(resultInfo.getSextclientname() != null && resultInfo.getSextclientname().length() > 0)
					{
						printInfo.setExtClientName(resultInfo.getSextclientname());
					}
					
					if(resultInfo.getSpayextbankno() != null && resultInfo.getSpayextbankno().length() > 0)
					{
						printInfo.setExtRemitInBank(resultInfo.getSpayextbankno());
					}
					
					if(resultInfo.getSreceiveextbankno() != null && resultInfo.getSreceiveextbankno().length() > 0)
					{
						printInfo.setExtRemitInBank(resultInfo.getSreceiveextbankno());
					}
					if(resultInfo.getSremitinprovince() != null && resultInfo.getSremitinprovince().length() > 0)
					{
						printInfo.setExtRemitInProvince(resultInfo.getSremitinprovince());
					}
					if(resultInfo.getSremitincity() != null && resultInfo.getSremitincity().length() > 0)
					{
						printInfo.setExtRemitInCity(resultInfo.getSremitincity());
					}
					*/
				}
				else//����Ϊ����
				{
				Log.print("����Ϊ����");
					//����˺�
					if(resultInfo.getNpayaccountid() > 0)
					{
						printInfo.setReceiveAccountID(resultInfo.getNpayaccountid());
					}
					
					//���������
					if(resultInfo.getNpaybankid() > 0)
					{
						printInfo.setReceiveBankID(resultInfo.getNpaybankid());
					}
					
					//�������
					if(resultInfo.getNpaygeneralledgertypeid() > 0)
					{
						printInfo.setReceiveGL(resultInfo.getNpaygeneralledgertypeid());
					}
					//�տ�˺�
					if(resultInfo.getNreceiveaccountid() > 0)
					{
						printInfo.setPayAccountID(resultInfo.getNreceiveaccountid());
					}
					
					//�տ������
					if(resultInfo.getNreceivebankid() > 0)
					{
						printInfo.setPayBankID(resultInfo.getNreceivebankid());
					}
					
					//�տ���˿�Ŀ
					if(resultInfo.getNreceivegeneralledgertypeid() > 0)
					{
						printInfo.setPayGL(resultInfo.getNreceivegeneralledgertypeid());
					}
					
					//������
					if(resultInfo.getMpayamount() != 0.0 )
					{
						printInfo.setAmount(resultInfo.getMpayamount());
					}
					
					/*//��������
					if(resultInfo.getSextaccountno() != null && resultInfo.getSextaccountno().length() > 0)
					{
						printInfo.setExtAccountNo(resultInfo.getSextaccountno());
					}
					if(resultInfo.getSextclientname() != null && resultInfo.getSextclientname().length() > 0)
					{
						printInfo.setExtClientName(resultInfo.getSextclientname());
					}
					
					if(resultInfo.getSpayextbankno() != null && resultInfo.getSpayextbankno().length() > 0)
					{
						printInfo.setExtRemitInBank(resultInfo.getSpayextbankno());
					}
					
					if(resultInfo.getSreceiveextbankno() != null && resultInfo.getSreceiveextbankno().length() > 0)
					{
						printInfo.setExtRemitInBank(resultInfo.getSreceiveextbankno());
					}
					if(resultInfo.getSremitinprovince() != null && resultInfo.getSremitinprovince().length() > 0)
					{
						printInfo.setExtRemitInProvince(resultInfo.getSremitinprovince());
					}
					if(resultInfo.getSremitincity() != null && resultInfo.getSremitincity().length() > 0)
					{
						printInfo.setExtRemitInCity(resultInfo.getSremitincity());
					}
					*/
				}
			}
			if(resultInfo.getNpaydirection() == resultInfo.getNreceivedirection())//�ո�������ͬ
			{
			Log.print("�ո�������ͬ");
				if(resultInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT && resultInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
				{//���������Ϊ�跽
					Log.print("���������Ϊ�跽");
					//����˺�
					if(resultInfo.getNpayaccountid() > 0)
					{
						printInfo.setPayAccountID(resultInfo.getNpayaccountid());
					}
					
					//���������
					if(resultInfo.getNpaybankid() > 0)
					{
						printInfo.setPayBankID(resultInfo.getNpaybankid());
					}
					
					//�������
					if(resultInfo.getNpaygeneralledgertypeid() > 0)
					{
						printInfo.setPayGL(resultInfo.getNpaygeneralledgertypeid());
					}
					//�տ�˺�
					if(resultInfo.getNreceiveaccountid() > 0)
					{
						printInfo2.setPayAccountID(resultInfo.getNreceiveaccountid());
					}
					
					//�տ������
					if(resultInfo.getNreceivebankid() > 0)
					{
						printInfo2.setPayBankID(resultInfo.getNreceivebankid());
					}
					
					//�տ���˿�Ŀ
					if(resultInfo.getNreceivegeneralledgertypeid() > 0)
					{
						printInfo2.setPayGL(resultInfo.getNreceivegeneralledgertypeid());
					}
					
					//������
					if(resultInfo.getMpayamount() != 0.0 )
					{
						printInfo.setAmount(resultInfo.getMpayamount());
					}
					//������
					if(resultInfo.getMreceiveamount() != 0.0 )
					{
						printInfo2.setAmount(resultInfo.getMreceiveamount());
					}
					
					
				
				/*
				//��������	
				if(resultInfo.getSextaccountno() != null && resultInfo.getSextaccountno().length() > 0)
					{
						printInfo.setExtAccountNo(resultInfo.getSextaccountno());
						printInfo2.setExtAccountNo(resultInfo.getSextaccountno());
					}
					
					if(resultInfo.getSextclientname() != null && resultInfo.getSextclientname().length() > 0)
					{
						printInfo.setExtClientName(resultInfo.getSextclientname());
						printInfo2.setExtClientName(resultInfo.getSextclientname());
					}
					
					if(resultInfo.getSpayextbankno() != null && resultInfo.getSpayextbankno().length() > 0)
					{
						printInfo.setExtRemitInBank(resultInfo.getSpayextbankno());
						printInfo2.setExtRemitInBank(resultInfo.getSpayextbankno());
					}
					
					if(resultInfo.getSreceiveextbankno() != null && resultInfo.getSreceiveextbankno().length() > 0)
					{
						printInfo.setExtRemitInBank(resultInfo.getSreceiveextbankno());
						printInfo2.setExtRemitInBank(resultInfo.getSreceiveextbankno());
					}
					if(resultInfo.getSremitinprovince() != null && resultInfo.getSremitinprovince().length() > 0)
					{
						printInfo.setExtRemitInProvince(resultInfo.getSremitinprovince());
						printInfo2.setExtRemitInProvince(resultInfo.getSremitinprovince());
					}
					if(resultInfo.getSremitincity() != null && resultInfo.getSremitincity().length() > 0)
					{
						printInfo.setExtRemitInCity(resultInfo.getSremitincity());
						printInfo2.setExtRemitInCity(resultInfo.getSremitincity());
					}
					*/
				}
				if(resultInfo.getNpaydirection() == SETTConstant.DebitOrCredit.CREDIT && resultInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.CREDIT)
				{//���������Ϊ������
					Log.print("���������Ϊ����");
					//����˺�
					if(resultInfo.getNpayaccountid() > 0)
					{
						printInfo.setReceiveAccountID(resultInfo.getNpayaccountid());
						
					}
					
					//���������
					if(resultInfo.getNpaybankid() > 0)
					{
						printInfo.setReceiveBankID(resultInfo.getNpaybankid());
					}
					
					//�������
					if(resultInfo.getNpaygeneralledgertypeid() > 0)
					{
						printInfo.setReceiveGL(resultInfo.getNpaygeneralledgertypeid());
						Log.print("���������Info1:"+resultInfo.getNpaygeneralledgertypeid());
					}
					//�տ�˺�
					if(resultInfo.getNreceiveaccountid() > 0)
					{
						printInfo2.setReceiveAccountID(resultInfo.getNreceiveaccountid());
					}
					
					//�տ������
					if(resultInfo.getNreceivebankid() > 0)
					{
						printInfo2.setReceiveBankID(resultInfo.getNreceivebankid());
					}
					
					//�տ���˿�Ŀ
					if(resultInfo.getNreceivegeneralledgertypeid() > 0)
					{
						printInfo2.setReceiveGL(resultInfo.getNreceivegeneralledgertypeid());
						Log.print("���������Info2:"+resultInfo.getNreceivegeneralledgertypeid());
					}
					
					//������
					if(resultInfo.getMpayamount() != 0.0 )
					{
						printInfo.setAmount(resultInfo.getMpayamount());
					}
					//������
					if(resultInfo.getMreceiveamount() != 0.0 )
					{
						printInfo2.setAmount(resultInfo.getMreceiveamount());
					}

					/*
					//��������
					if(resultInfo.getSextaccountno() != null && resultInfo.getSextaccountno().length() > 0)
					{
						printInfo.setExtAccountNo(resultInfo.getSextaccountno());
						printInfo2.setExtAccountNo(resultInfo.getSextaccountno());
					}
					if(resultInfo.getSextclientname() != null && resultInfo.getSextclientname().length() > 0)
					{
						printInfo.setExtClientName(resultInfo.getSextclientname());
						printInfo2.setExtClientName(resultInfo.getSextclientname());
					}
					
					if(resultInfo.getSpayextbankno() != null && resultInfo.getSpayextbankno().length() > 0)
					{
						printInfo.setExtRemitInBank(resultInfo.getSpayextbankno());
						printInfo2.setExtRemitInBank(resultInfo.getSpayextbankno());
					}
					
					if(resultInfo.getSreceiveextbankno() != null && resultInfo.getSreceiveextbankno().length() > 0)
					{
						printInfo.setExtRemitInBank(resultInfo.getSreceiveextbankno());
						printInfo2.setExtRemitInBank(resultInfo.getSreceiveextbankno());
					}
					if(resultInfo.getSremitinprovince() != null && resultInfo.getSremitinprovince().length() > 0)
					{
						printInfo.setExtRemitInProvince(resultInfo.getSremitinprovince());
						printInfo2.setExtRemitInProvince(resultInfo.getSremitinprovince());
					}
					if(resultInfo.getSremitincity() != null && resultInfo.getSremitincity().length() > 0)
					{
						printInfo.setExtRemitInCity(resultInfo.getSremitincity());
						printInfo2.setExtRemitInCity(resultInfo.getSremitincity());
					}
					*/
				}
			}
			
			//set��ӡ����End
			
			if ((strPrintPage.indexOf("b") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('b','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo,out);//��ӡ���˵�
			}
			if ((strPrintPage.indexOf("d") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('d','a');
				lShow = 1;
				System.out.println(printInfo.getReceiveAccountID() + "******************************is here is *******************************************" + printInfo.getExtClientName());
				PrintVoucher.PrintShowWithDraw(printInfo,out);//��ӡ���֧ȡ
			}
			if ((strPrintPage.indexOf("e") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('e','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo,out);//��ӡ��ת����
			}
			if ((strPrintPage.indexOf("c") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('c','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo,out);//��ӡ��ת����
			}
			//���2��ƾ֤
			if ((strPrintPage.indexOf("f") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('f','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo2,out);//��ӡ���˵�
			}
			if ((strPrintPage.indexOf("h") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('h','a');
				lShow = 1;
				PrintVoucher.PrintShowWithDraw(printInfo2,out);//��ӡ���֧ȡ
			}
			if ((strPrintPage.indexOf("i") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('i','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo2,out);//��ӡ��ת����
			}
			if ((strPrintPage.indexOf("g") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('g','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo2,out);//��ӡ��ת����
			}
				
			IPrintTemplate.noShowPrintHeadAndFooter(out,0,lOfficeID);
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
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
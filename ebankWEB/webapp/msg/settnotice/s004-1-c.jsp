<%--
 ҳ������ ��c014-1.jsp
 ҳ�湦�� : Ӧ����Ϣ�ͷ��ÿ���
 ��    �� ��gqzhang
 ��    �� ��2004-4-2
 ����˵�� ��ʵ�ֲ���˵����findByIDȡ��������Ϣ
			           Ӧ��������Ϣ֪ͨ��
					   ���ڴ���֪ͨ��
					   �����֪ͨ��
   					   �뱾ҳ���ҳ��jsp/print/c014-1.jsp,������ҳ�е�һҳ���ݷ����仯����Ҫ�޸�����ҳ
 �޸���ʷ ��
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.print.ManufacturePrintInfo"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QLoanNotice"%>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.templateinfo.ShowOverLoanNoticeInfo"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryResultInfo"%>	
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>
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
		//������
		//if(!SETTHTML.validateRequest(out, request,response)) return;
		long lOfficeID = -1;
		lOfficeID = sessionMng.m_lOfficeID;
		long lID = -1;
		String strPrintPage = "";
		String strTemp = "";
		//��ӡ����
		long lShow = 1;//�Ƿ���һҳ�ı�־
		
		strTemp = (String)request.getParameter("strPrintPage");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strPrintPage = strTemp;
		}
		
		LoanNoticeInfo resultInfo = null;
		Vector vctLoanNoticeInfo = null;
		
		String strPrintType = (String)request.getParameter("strPrintType");//��ѡ��ӡ1---������ӡ2
		
		if(strPrintType != null)
		{    
			//��װ��ѡ
			Vector resultVec = (Vector)session.getAttribute("resultList");
		    String [] ck = null;
		    ck = request.getParameterValues("ck");
			if(strPrintType.equalsIgnoreCase("1"))//��ѡ��ӡ
			{
     			Log.print("~~~~~~~~~~~~~~~��ѡ~~~~~~~~~~~~~~``");
				if(ck!=null && ck.length>0)
				{	
				     Vector selectVec = new Vector();	
					 
					 if (resultVec != null)
					 {
					    for(int i = 0; i < ck.length; i ++)
						{
						   int selectValue = Integer.valueOf(ck[i]).intValue();	
						   
						   for(int j = 0; j < resultVec.size(); j++)
						   {
						       LoanNoticeInfo loanNoticeInfo = new LoanNoticeInfo();
							   loanNoticeInfo = (LoanNoticeInfo)resultVec.elementAt(j);
							   if(loanNoticeInfo.getID() == selectValue)
							   {
							     selectVec.addElement(loanNoticeInfo);
							   }
						    }//end for j 
						}//end for i
						if(selectVec != null)
					    {  
				          vctLoanNoticeInfo = selectVec;
					    }
					 }
				}
			}
			
			if(strPrintType.equalsIgnoreCase("2"))//������ӡ
			{
			      Log.print("~~~~~~~~~~~~~~~����~~~~~~~~~~~~~~``");
				   if(resultVec != null)
				   { 
					  vctLoanNoticeInfo = resultVec;
				   }
			 }
			
			//�õ��Ժ�ͬ���ܵ�Vector
			Vector vctLoanNotice = null;
			LoanNoticeInfo loanNoticeInfo = null;
			long [] nContractID = new long[500];
			long lTempContractID = -1;
			
			//ȡ����ѡ�еĺ�ͬID
			if (vctLoanNoticeInfo != null)
			{
				int nContractNumber = 0;
				for (int ii = 0; ii < vctLoanNoticeInfo.size(); ii++)
				{
					loanNoticeInfo = (LoanNoticeInfo) vctLoanNoticeInfo.elementAt(ii);
					if(lTempContractID != loanNoticeInfo.getContractID())
					{
						nContractID[nContractNumber++] = loanNoticeInfo.getContractID();
						//Log.print("ѡ�еĺ�ͬID����������������������"+loanNoticeInfo.getContractID());
					}
					lTempContractID = loanNoticeInfo.getContractID();
				}
			}
			//ȡ��ѡ�еĺ�ͬID End
			
			for (int nContractNumber2 = 0; nContractNumber2 < nContractID.length; nContractNumber2++)
			{//���պ�ͬ ѭ����ӡÿ����ͬ�Ļ���
				//Log.print("nContractID============================================"+nContractID[nContractNumber2]);
				
				//��ϸ��Ŀ��Vector
				Vector vctLoanNoticeDetails = new Vector();
				PrintInfo printInfoSum = new PrintInfo();
				//������Ϣ
				double dSumInterest = 0.0;
				//�����
				double dSumCompoundInterest = 0.0;
				//��Ϣ
				double dSumOverDueInterest = 0.0;
				//������
				double dSumCommission = 0.0;
				//�������
				double dSumLoanBalance = 0.0;
				
				//����Ŀ�ʼ�������� ��Ϊ�ͷſ�Ŀ�ʼ�������ڲ�һ��
				Timestamp tsLoanStartDate = null;
				
				if(nContractID[nContractNumber2] > 0)
				{
				
					for (int countAll = 0; countAll < vctLoanNoticeInfo.size(); countAll++)
					{//ȡ��ÿ����ͬ�Ļ��� �� ��ϸ��Ŀ
						PrintInfo printInfo = new PrintInfo();
						
						resultInfo = (LoanNoticeInfo) vctLoanNoticeInfo.elementAt(countAll);
						
						if(resultInfo.getContractID() == nContractID[nContractNumber2])//ȡ�õ�ǰ��ͬ�����зſ�Ļ���
						{
							//���ݴ����˻��жϵ�ǰ��ӡ����ί�л�����Ӫ��֪ͨ��
							if (resultInfo.getAccountID() > 0)
							{
								strTemp = NameRef.getAccountNoByID(resultInfo.getAccountID());
								if (strTemp.length() > 5)
								{
									strTemp = strTemp.substring(3, 5);
								}
							}
							//��ţ�����
							if (resultInfo.getFormYear() != null && resultInfo.getFormYear().length() > 0)
							{
								printInfo.setFormYear(resultInfo.getFormYear());
							}
							Log.print("~~~~~~~~~~����꣺" + printInfo.getFormYear());
							
							//��ţ��ţ�
							if (resultInfo.getFormNo() != null && resultInfo.getFormNo().length() > 0)
							{
								printInfo.setFormNo(resultInfo.getFormNo());
							}
							Log.print("~~~~~~~~~~��ţ�" + printInfo.getFormNo());
							
							//���մ���
							if (resultInfo.getFormNum() > 0)
							{
								printInfo.setFormNum(resultInfo.getFormNum());
							}
							
							//�����
							if (resultInfo.getAccountID() > 0)
							{
								printInfo.setBorrowClientName(NameRef.getClientNameByAccountID(resultInfo.getAccountID()));
							}
							
							Log.print("~~~~~~~~~~~������˻�id��" + resultInfo.getAccountID());
							Log.print("~~~~~~~~~~~��������ƣ�" + NameRef.getClientNameByAccountID(resultInfo.getAccountID()));
							//�����ͬǩ����,���ݺ�ͬId����,��������
							
							//�����ͬ��
							if (resultInfo.getContractID() > 0)
							{
								printInfo.setContractID(resultInfo.getContractID());
							}
							Log.print("~~~~~~~~~~~~~~~~�����ͬ��:" + printInfo.getContractID());
							
							//�ſ�֪ͨ��,��ʱLoanNotice�л�û�и��ֶ�
		
							if (resultInfo.getLoanNoteID() > 0)
							{
								printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
							}
							Log.print("~~~~~~~~~~~~~~~~`�ſ�֪ͨ��Id��" + printInfo.getLoanNoteID());
							//Ӧ֧������
							if (resultInfo.getClearInterestDate() != null)
							{
								printInfo.setClearInterestDate(resultInfo.getClearInterestDate());
							}
							Log.print("~~~~~~~~~~~~~~~~`��Ϣ�գ�" + printInfo.getClearInterestDate());
		
							//������Ϣ
							if (resultInfo.getInterest() != 0.0)
							{
								printInfo.setInterest(resultInfo.getInterest());
							}
							Log.print("~~~~~~~~~~~~~~~~`������Ϣ��" + printInfo.getInterest());
		
							//�����
							if (resultInfo.getCompoundInterest() != 0.0)
							{
								printInfo.setCompoundInterest(resultInfo.getCompoundInterest());
							}
		
							//������
							if (resultInfo.getCommission() != 0.0)
							{
								printInfo.setCommission(resultInfo.getCommission());
							}
		
							//���������ʣ������ݿͻ�Ҫ�󣬽�����������ȡΪ��ͬ���ʣ�
							if (resultInfo.getCommissionRate() != 0.0)
							{
								printInfo.setCommissionRate(resultInfo.getCommissionRate());
							}
							Log.print("~~~~~~~~~~~~~~~~~~~~~~~����������:"+printInfo.getCommissionRate());		
							  //���ڷ�Ϣ
			                 if(resultInfo.getOverDueInterest() != 0.0)
			                 {
			   		            printInfo.setOverDueInterest(resultInfo.getOverDueInterest());
						     }
							Log.print("~~~~~~~~~~~~~~~~~~~~~~~���ڷ�Ϣ:"+printInfo.getOverDueInterest());			    
			  				double tempDouble = printInfo.getCompoundInterest()+printInfo.getInterest()+printInfo.getOverDueInterest();
			   				Log.print("~~~~~~~~~~~~~~~~~~~~~~~�ۺ�:"+tempDouble);		
							
							//�����
							if (resultInfo.getLoanAmount() != 0.0)
							{
								printInfo.setAmount(resultInfo.getLoanAmount());
							}
		
							//�������
							if (resultInfo.getLoanBalance() != 0.0)
							{
								printInfo.setLoanBalance(resultInfo.getLoanBalance());
							}
							Log.print("~~~~~~~~~~~~~~~~~~~~~~~�������:"+printInfo.getLoanBalance());		
		
							//��ͬ����
							if (resultInfo.getOriginalContractRate() != 0.0)
							{
								printInfo.setContractRate(resultInfo.getOriginalContractRate());
							}
							Log.print("��������������������ͬ����:" + printInfo.getContractRate());
		
							//ִ������
							if (resultInfo.getExecuteRate() != 0.0)
							{
								printInfo.setExecuteRate(resultInfo.getExecuteRate());
							}
							Log.print("����������������������ǰִ������:" + printInfo.getExecuteRate());
		
							//�������ʵ�������
							if (resultInfo.getExecuteRateValidDate() != null)
							{
								printInfo.setAdjustRateDate(resultInfo.getExecuteRateValidDate());
							}
							
							//��������Ϣ
							if (resultInfo.getNewExecuteRate() != 0.0)
							{
								printInfo.setExecuteRateNew(resultInfo.getNewExecuteRate());
							}
							Log.print("������������������������ִ������:" + printInfo.getExecuteRateNew());
		
							if (printInfo.getExecuteRateNew() == printInfo.getExecuteRate())
							{
								Log.print("����������������������ǰ��ִ���������~~~~~~~~~~~~~~~~~~~~~~`");
							}
							else
							{
								Log.print("����������������������ǰ��ִ�����ʲ���~~~~~~~~~~~~~~~~~~~~~~~");
							}
		
							//�������ޣ��£�
							if (resultInfo.getLoanTerm() > 0)
							{
								printInfo.setLoanTerm(resultInfo.getLoanTerm());
							}
		
							//��������(��ʼ��)
							if (resultInfo.getLoanStartDate() != null)
							{
								printInfo.setStartDate(resultInfo.getLoanStartDate());//�õ��ſ�Ŀ�ʼ����
								Log.print("~~~~~~~~~~~~~~~~~~��ʼ���ڣ�" + printInfo.getStartDate());
								tsLoanStartDate = resultInfo.getLoanStartDate();
							}
		
							//�������ޣ������գ�
							if (resultInfo.getLoanEndDate() != null)
							{
								printInfo.setEndDate(resultInfo.getLoanEndDate());
								Log.print("~~~~~~~~~~~~~~~~~~�������ڣ�" + printInfo.getEndDate());
							}
							
							//��Ϣ��
							if (resultInfo.getInterestStartDate() != null)
							{
								printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
							}
							Log.print("�������������������ϴν�Ϣ��:"+printInfo.getInterestStartDate());
							
							//�������ڣ���ִ����
							if (resultInfo.getExecuteDate() != null)
							{
								printInfo.setExecuteDate(resultInfo.getExecuteDate());
							}
							//�ر�����
							printInfo.setExecuteDate(DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)));
							Log.print("����������������������ִ������:" + printInfo.getExecuteDate());
		
							//������
							if (resultInfo.getAssureClientName() != null && resultInfo.getAssureClientName().length() > 0)
							{
								printInfo.setAssureName(resultInfo.getAssureClientName());
							}
		
							//������ͬ
							if (resultInfo.getAssureContractID() > 0)
							{
								printInfo.setAssureContractID(resultInfo.getAssureContractID());
							}
							Log.print("~~~~~~~~~~~~~~~~������ͬ��:" + printInfo.getAssureContractID());
							//��ȷ����info
							//String RemitAddress = "";//���·��
							//String ReceiveUnit = "";//�տλ
							//String ReceiveBank = "";//��������
							
							//������Ϣ
								dSumInterest += DataFormat.formatDouble(resultInfo.getInterest(),2);
							//�����
								dSumCompoundInterest += DataFormat.formatDouble(resultInfo.getCompoundInterest(),2);
							//��Ϣ
			   		            dSumOverDueInterest += DataFormat.formatDouble(resultInfo.getOverDueInterest(),2);
							//������
								dSumCommission += DataFormat.formatDouble(resultInfo.getCommission(),2);
							//�������
								Log.print("==========�������֮ǰ��"+dSumLoanBalance);
								dSumLoanBalance = DataFormat.formatDouble(resultInfo.getLoanBalance(),2);
								Log.print("==========��������"+DataFormat.formatDouble(resultInfo.getLoanBalance(),2));
								Log.print("==========�������֮��"+dSumLoanBalance);
								
								//������ϸ��Vector
								Log.print("����Vector");
								vctLoanNoticeDetails.addElement(printInfo);
								Log.print("����Vector End");
						}
					}//end for ÿ����ͬ�Ļ��� �� ��ϸ��Ŀ
							//��ӡ��Ϣ֪ͨ��
							Log.print("��ʼ��ӡ ��ͬΪ�ϼƵ� ��Ϣ֪ͨ��");
							PrintInfo pi = null;
							if(vctLoanNoticeDetails.size() >0)
							{
								pi = (PrintInfo) vctLoanNoticeDetails.elementAt(0);
							}
							
							//��ţ�����
								printInfoSum.setFormYear(pi.getFormYear());
							//��� ��
								printInfoSum.setFormNo(pi.getFormNo());
							//�����
								printInfoSum.setBorrowClientName(pi.getBorrowClientName());
							//�����ͬ��
								printInfoSum.setContractID(pi.getContractID());
								Log.print("==========�����ͬ��======="+pi.getContractID());
							//�ſ�֪ͨ��,��ʱLoanNotice�л�û�и��ֶ�
								printInfoSum.setLoanNoteID(pi.getLoanNoteID());
							//Ӧ֧������
								printInfoSum.setClearInterestDate(pi.getClearInterestDate());
							//�����
								printInfoSum.setAmount(pi.getAmount());
							//��ͬ����
								printInfoSum.setContractRate(pi.getContractRate());
								
							
							//�ж����ʵ��������Ƿ�����Ϣ�պͽ�Ϣ��֮��
							if(pi.getAdjustRateDate() != null && pi.getInterestStartDate() != null && pi.getClearInterestDate() != null)
							{
							   Log.print("�������ڣ�"+pi.getAdjustRateDate());
							   Log.print("�ϴν�Ϣ�գ�"+pi.getInterestStartDate());
							   Log.print("��Ϣ�գ�"+pi.getClearInterestDate());
							   if(pi.getAdjustRateDate().after(pi.getInterestStartDate()) && (pi.getAdjustRateDate().before(pi.getClearInterestDate()) || pi.getAdjustRateDate()== pi.getClearInterestDate()))
							   {//������������Ϣ�պͽ�Ϣ��֮��
								  Log.print("������������Ϣ�պͽ�Ϣ��֮��");
								  //ִ������
								   printInfoSum.setExecuteRate(pi.getExecuteRateNew());
							     //�������ʵ�������
									 Log.print("��������:"+pi.getAdjustRateDate());
								  printInfoSum.setAdjustRateDate(pi.getAdjustRateDate());
							      //��������Ϣ
								  printInfoSum.setExecuteRateNew(pi.getExecuteRateNew());
							   }
							   else if(!pi.getAdjustRateDate().after(pi.getInterestStartDate()))
							   { 
								 //������������Ϣ��֮ǰ��������Ϣ�յ���
								 Log.print("������������Ϣ��֮ǰ��������Ϣ�յ���");
								 if(pi.getExecuteRateNew()>0 &&(pi.getExecuteRate() != pi.getExecuteRateNew()))
								 {  
									 Log.print("��ִ�����ʴ�����");
									 printInfoSum.setExecuteRate(pi.getExecuteRateNew());
									   //�������ʵ�������
								      printInfoSum.setAdjustRateDate(null);
							          //��������Ϣ
								      printInfoSum.setExecuteRateNew(0.0);
								 }
								 else
								 {   
									 Log.print("��ִ�����ʵ�����");
									 printInfoSum.setExecuteRate(pi.getExecuteRate());
									 //�������ʵ�������
								     printInfoSum.setAdjustRateDate(null);
							         //��������Ϣ
								     printInfoSum.setExecuteRateNew(0.0);
								 }
							   }
							}
							else
							{
							   Log.print("û�з������ʵ���");
							   if(pi.getExecuteRateNew() == 0)
							   {   
								  //ִ������
								  printInfoSum.setExecuteRate(pi.getExecuteRate()); 
								 
								  //�������ʵ�������
								  printInfoSum.setAdjustRateDate(null);
							      //��������Ϣ
								 printInfoSum.setExecuteRateNew(0.0);
							   }
							   else
							   { 
								  //��������Ϣ
								  printInfoSum.setExecuteRate(pi.getExecuteRateNew());
								  //�������ʵ�������
								 printInfoSum.setAdjustRateDate(null);
							      //��������Ϣ
								printInfoSum.setExecuteRateNew(0.0);
							   }
							
							}	
							
								//�������ޣ��£�
								printInfoSum.setLoanTerm(pi.getLoanTerm());
								//��������(��ʼ��)
								printInfoSum.setStartDate(tsLoanStartDate);
								
								//�������ޣ������գ�
								printInfoSum.setEndDate(pi.getEndDate());
								
								//�������ڣ���ִ����
								printInfoSum.setExecuteDate(pi.getExecuteDate());
								//������Ϣ
								printInfoSum.setInterest(dSumInterest);
								//�����
								printInfoSum.setCompoundInterest(dSumCompoundInterest);
								//��Ϣ
								 printInfoSum.setOverDueInterest(dSumOverDueInterest);
								//������
								printInfoSum.setCommission(dSumCommission);
								//���������ʣ����ݿͻ�Ҫ�󣬽�����������ȡΪ��ͬ���ʣ�
							 	printInfoSum.setCommissionRate(pi.getCommissionRate());	
								//�������
								printInfoSum.setLoanBalance(dSumLoanBalance);
								//�ϴν�Ϣ��
								printInfoSum.setLatestInterestClearDate(pi.getInterestStartDate());
								
								Log.print("�������������������ϴν�Ϣ��:"+printInfoSum.getLatestInterestClearDate());
							Log.print("ȡ�û��ܽ������");
							
							if ((strPrintPage.indexOf("1") >= 0))
							{
								if ((strPrintPage.indexOf("1") >= 0) && lShow != 1)
								{
									out.println("<br clear=all style='page-break-before:always'>");
								}
								lShow++;
								//���ݴ����˻��жϵ�ǰ��ӡ����ί�л�����Ӫ��֪ͨ��
								if (strTemp.equals("08")) //��Ӫ
								{
									Log.print("������������������������ӡӦ����Ӫ������Ϣ֪ͨ��");
									PrintVoucher.PrintLoanInterestNotice(printInfoSum, out); //��ӡӦ��������Ϣ֪ͨ��
								}
								if (strTemp.equals("09")) //ί��
								{
									Log.print("������������������������ӡӦ��ί�д�����Ϣ֪ͨ��");
									PrintVoucher.PrintConsignLoanInterestAdviceNotice(printInfoSum, out); //��ӡӦ��ί�д�����Ϣ֪ͨ��
								}
									out.println("<br clear=all style='page-break-before:always'>");
								//��ӡ �ſ����ϸ��Ŀ
								PrintVoucher.PrintLoanInterestNoticeDetails(vctLoanNoticeDetails,out);
									Log.print("������������������������ӡ�ſ����ϸ��Ŀ");
								
							}
							//��ӡ��Ϣ֪ͨ�� End
					Log.print("��ӡ ��ͬΪ�ϼƵ� ��Ϣ֪ͨ�� ���� End");
				}// end if nContractID > 0
			}
			//�õ��Ժ�ͬ���ܵ�Vector ����

			Log.print("<br><br>~~~~~~~~~~~~~~~������Ϣ֪ͨ����" + vctLoanNoticeInfo.size() + "<br><br>");

			IPrintTemplate.showVoucherPrintHead(out, true, "A4", "", 1, sessionMng.m_lOfficeID);

			
			
			
		 }
		else
		{
		 Log.print(" s004-1 keyֵΪ��");
		}
	
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, "��ӡ����֪ͨ��","",1);
		return;
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
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
*/
%>
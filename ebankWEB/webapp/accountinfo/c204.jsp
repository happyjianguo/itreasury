<%--
 ҳ������ ��v017.jsp
 ҳ�湦�� : �ʽ�����ƾ֤��ӡ-����ҳ��
 ��    �� ��xgzhang
 ��    �� ��2005-09-09
 ����˵�� ����ʵ��˵����
 	1���շ�ƾ֤:���˵�������ת�˽跽ƾ֤������ת�˴���ƾ֤
 	2������ƾ֤:���˵�������ת�˽跽ƾ֤������ת�˴���ƾ֤
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
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
<%@ page import="com.iss.itreasury.settlement.print.ManufacturePrintInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo"%>
<%@ page import="com.iss.itreasury.settlement.obinstruction.dataentity.OBFinanceInfo"%>
<%@ page import="com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo"%> 
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransOnePayMultiReceiveDAO"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
	
<%
	//ҳ����Ʊ���
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
	//�������
	String strPrintPage = null;
	String strTemp = null;
	String strPageLoaderKey = null;
	long lOfficeID = sessionMng.m_lOfficeID;
	long lID = -1;
	long lTransactionTypeID = -1; 
	long receiveAccountId = -2;
	long payAccountId = -2;
	int resultCount = 0;
	//��ӡ����
	long lShow = -1;
	long lPrintType = -1;//1:�״�	
	
	//��ѯ�������
	QueryTransactionInfo[] resultInfos = null;   
	QueryTransactionInfo queryTransactionInfo = null;
	PrintInfo printInfo = null;
	ManufacturePrintInfo manufacturePrintInfo = null;
		
			   
	try {
		//������
		
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
		
        strTemp = (String)request.getAttribute("strPrintPage");//��ӡ�ĵ�������
		if (strTemp != null && strTemp.trim().length() > 0){
			strPrintPage = strTemp.trim();
		}
		strTemp = (String) request.getAttribute("_pageLoaderKey");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strPageLoaderKey = strTemp.trim();
		}
		resultInfos = (QueryTransactionInfo[]) request
					.getAttribute(Constant.PageControl.SearchResults);
		if(resultInfos != null)
				resultCount = resultInfos.length;
		manufacturePrintInfo = new ManufacturePrintInfo();
		for (int i=0;i<resultCount;i++) {
			queryTransactionInfo = resultInfos[i];
		 	if(queryTransactionInfo != null) {
				lID = queryTransactionInfo.getID();
				lTransactionTypeID = queryTransactionInfo.getTransactionTypeID();
				receiveAccountId = queryTransactionInfo.getReceiveAccountID();
				payAccountId = queryTransactionInfo.getPayAccountID();
				Log.print("<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>");
				//printInfo = manufacturePrintInfo.getOneMutiRepaymentPrintInfo(lID);
				 Sett_TransOnePayMultiReceiveDAO dao = new Sett_TransOnePayMultiReceiveDAO();
 				 TransOnePayMultiReceiveInfo resultInfo = null;
		         resultInfo = dao.findByID(lID);
  		 		 printInfo = new PrintInfo();
 				if(resultInfo != null)
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

			//���׺�
			if(resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
			{
				printInfo.setTransNo(resultInfo.getTransNo());
			}

			//���ݽ��׺Ų�����ع��˽��׼�¼��Ϣ
			TransOnePayMultiReceiveInfo  findCondition = new TransOnePayMultiReceiveInfo();//��ѯ����
			TransOnePayMultiReceiveInfo  transOnePayMultiReceiveInfo = null;//��ѯ���
			TransOnePayMultiReceiveInfo  tempTransOnePayMultiReceiveInfo = null;//�ڱ����Ĺ����д洢�ո�����Ϣ 
			Collection resultCollection = null;
			boolean bFlagPay = false;
			long lIndexPay = 0;
			boolean bFlagReceive = false;
			long lIndexReceive = 0;

			if(resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
			{
				findCondition.setTransNo(resultInfo.getTransNo());
				//resultCollection = depositDelegation.findByConditions(findCondition,-1,false);
				resultCollection = dao.findByConditions(findCondition,-1,false);
				
				if (resultCollection != null)
				{
					Iterator tempIterator = resultCollection.iterator();
					
					if(resultInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY)//�����ǰ��¼�Ǹ���
					{
						Log.print("��ǰ��¼Ϊ����");
						while (tempIterator.hasNext())
						{
						   transOnePayMultiReceiveInfo = (TransOnePayMultiReceiveInfo)tempIterator.next();
							
							if((transOnePayMultiReceiveInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY) && (transOnePayMultiReceiveInfo.getId() != resultInfo.getId()))
							{
								//���ں͵�ǰ��¼��ͬ�ĸ���
								bFlagPay = true;
								lIndexPay++ ;
							}

							if(transOnePayMultiReceiveInfo.getTypeID() == SETTConstant.ReceiveOrPay.RECEIVE)
							{
								bFlagReceive = true;
								lIndexReceive++ ;
								tempTransOnePayMultiReceiveInfo = transOnePayMultiReceiveInfo;
							}
						 }
						if(bFlagPay == false)
						{//��ǰ��¼��Ψһ�ĸ���
							Log.print("��ǰ��¼ΪΨһ����");
							printInfo.setAmount(resultInfo.getAmount());

							if (lIndexReceive == 1)
							{//�շ�Ψһ
							   Log.print("�շ�Ψһ");
							  //���ø�������Ϣ
								//if (resultInfo.getIsInternalVirement() > 0)//if�ڲ�ת��
								//{
									printInfo.setPayAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˺�)
								{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));	
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID()));//����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
								}
								if (resultInfo.getIsGL() > 0)//if����ת��
								{
									printInfo.setPayGL(resultInfo.getPayGL());
								}
							    //�����շ���Ϣ
								if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0)//if�ڲ�ת��
								{
									printInfo.setReceiveAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
								}
								if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0)//if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˺�)
								{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());	
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());//����ת�˲�д��������Ϣ
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());	
								}
								if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0)//if����ת��
								{
									printInfo.setReceiveGL(resultInfo.getReceiveGL());
								}
							}
							else
							{//�շ���Ψһ
	 							Log.print("�շ���Ψһ");
							 //�����ø�������Ϣ
							   //if (resultInfo.getAccountID() > 0)//if�ڲ�ת��
								//{
									Log.print("����-Ϊ�ڲ�ת��");
									printInfo.setPayAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˺�)
								{
									Log.print("����-Ϊ����");

										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));	
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID()));//����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
								}
								if (resultInfo.getIsGL() > 0)//if����ת��
								{
									Log.print("����-Ϊ����");
									printInfo.setPayGL(resultInfo.getPayGL());
									Log.print("����ID"+resultInfo.getPayGL());
								}
							}
						}
						else
						{
							//��ǰ��¼����Ψһ�ĸ���
							if (lIndexReceive == 1)
							{//�շ�Ψһ
								printInfo.setAmount(resultInfo.getAmount());
								
								//���ø�����Ϣ
								//if (resultInfo.getIsInternalVirement() > 0)//if�ڲ�ת��
								//{
									printInfo.setPayAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˺�)
								{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));	
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID()));//����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));	
								}
								if (resultInfo.getIsGL() > 0)//if����ת��
								{
									printInfo.setPayGL(resultInfo.getPayGL());
								}
							    //�����շ���Ϣ
								if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0)//if�ڲ�ת��
								{
									printInfo.setReceiveAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
								}
								
								if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0)//if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˺�)
								{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());	
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());//����ת�˲�д��������Ϣ
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());	
								}
								if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0)//if����ת��
								{
									printInfo.setReceiveGL(resultInfo.getReceiveGL());
								}
							}
						}
						
					}
					else//�����ǰ��¼���շ�
					{
						Log.print("��ǰ��¼Ϊ�շ�");
						while (tempIterator.hasNext())
						{
						   transOnePayMultiReceiveInfo = (TransOnePayMultiReceiveInfo)tempIterator.next();
							
							if(transOnePayMultiReceiveInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY)
							{
								bFlagPay = true;
								lIndexPay++ ;
								tempTransOnePayMultiReceiveInfo = transOnePayMultiReceiveInfo;
							}
							if((transOnePayMultiReceiveInfo.getTypeID() == SETTConstant.ReceiveOrPay.RECEIVE) && (transOnePayMultiReceiveInfo.getId() != resultInfo.getId()))
							{
								//�����뵱ǰ��¼��ͬ���շ�
								bFlagReceive = true;
								lIndexReceive++ ;
							}
						 }
						if(bFlagReceive == false)
						{//��ǰ��¼��Ψһ���շ�
							Log.print("��ǰ��¼ΪΨһ�շ�");
							printInfo.setAmount(resultInfo.getAmount());
							if (lIndexPay == 1)
							{//����Ψһ
							  //�����շ�����Ϣ
								//if (resultInfo.getIsInternalVirement() > 0)//if�ڲ�ת��
								//{
									printInfo.setReceiveAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˺�)
								{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());	
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());//����ת�˲�д��������Ϣ
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());	
								}
								if (resultInfo.getIsGL() > 0)//if����ת��
								{
									printInfo.setReceiveGL(resultInfo.getReceiveGL());
								}
							    //���ø�����Ϣ
								if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0)//if�ڲ�ת��
								{
									printInfo.setPayAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
								}
								if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0)//if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˺�)
								{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));	
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));//����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(tempTransOnePayMultiReceiveInfo.getBankID()));
								}
								if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0)//if����ת��
								{
									printInfo.setPayGL(resultInfo.getPayGL());
								}
							}
							else
							{//������Ψһ
							 //�������շ�����Ϣ
							   //if (resultInfo.getIsInternalVirement() > 0)//if�ڲ�ת��
								//{
									printInfo.setReceiveAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˺�)
								{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());	
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());//����ת�˲�д��������Ϣ
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());	
								}
								if (resultInfo.getIsGL() > 0)//if����ת��
								{
									printInfo.setReceiveGL(resultInfo.getReceiveGL());
								}
							}
						}
						else
						{
							Log.print("��ǰ��¼����Ψһ���շ�");
							//��ǰ��¼����Ψһ���շ�
							if (lIndexPay == 1)
							{//����Ψһ
								Log.print("����Ψһ");
								printInfo.setAmount(resultInfo.getAmount());
								
								//�����շ���Ϣ
								//if (resultInfo.getIsInternalVirement() > 0)//if�ڲ�ת��
								//{
									Log.print("�շ�Ϊ�ڲ�ת��");
									printInfo.setReceiveAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˺�)
								{
									Log.print("�շ�Ϊ����");
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());	
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());//����ת�˲�д��������Ϣ
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());	
								}
								if (resultInfo.getIsGL() > 0)//if����ת��
								{
									printInfo.setReceiveGL(resultInfo.getReceiveGL());
								}
							    //���ø�����Ϣ
								if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0)//if�ڲ�ת��
								{
									Log.print("�����ڲ�ת��");
									printInfo.setPayAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
								}
								if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0)//if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˺�)
								{
									Log.print("��������");
										printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));	
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));//����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(tempTransOnePayMultiReceiveInfo.getBankID()));
								}
								if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0)//if����ת��
								{
									Log.print("��������");
									printInfo.setPayGL(resultInfo.getPayGL());
								}
							}
						}
					}
				}
			}
					
				}
				
				if(printInfo != null) {
					//��ӡ�շ����˵�
					if ((strPrintPage.indexOf("A") >= 0) && receiveAccountId != -2 && receiveAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('A','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowIn(printInfo,out);//��ӡ���˵�
						Log.print("��ӡ�շ����˵�");
					}
					//��ӡ�������˵�
					if ((strPrintPage.indexOf("B") >= 0) && payAccountId != -2 && payAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('B','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowIn(printInfo,out);//��ӡ���˵�
						Log.print("��ӡ�������˵�");
					}
					//��ӡ�շ���ת��
					if ((strPrintPage.indexOf("C") >= 0) && receiveAccountId != -2 && receiveAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('C','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowDebtor(printInfo,out);//��ӡ��ת��
					}
					//��ӡ������ת��
					if ((strPrintPage.indexOf("D") >= 0) && payAccountId != -2 && payAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('D','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowDebtor(printInfo,out);//��ӡ��ת��
					}
					//��ӡ�շ���ת��
					if ((strPrintPage.indexOf("E") >= 0) && receiveAccountId != -2 && receiveAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('E','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowCredit(printInfo,out);//��ӡ��ת��
					}
					//��ӡ������ת��
					if ((strPrintPage.indexOf("F") >= 0) && payAccountId != -2 && payAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('F','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowCredit(printInfo,out);//��ӡ��ת��
					}
				}
			}	
		}
	if(lShow < 0) {
%>
	<SCRIPT language=JavaScript>
		alert("��ӡ��ϣ�");
		window.close();
	</script>
<%
	return;
	}
	IPrintTemplate.noShowPrintHeadAndFooter(out,0,lOfficeID);
//	settPrint.showPrintVoucher(out,sessionMng,lPrintType) ;	
%>
<Script Language="JavaScript">
netscape = "";
function keyDown(DnEvents)
{
	k = (netscape) ? DnEvents.which : window.event.keyCode;
	
}

document.onkeydown = keyDown; // work together to analyze keystrokes
if (netscape) document.captureEvents(Event.KEYDOWN|Event.KEYUP);
</SCRIPT>
<%		
	} catch( Exception exp ) {
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;
	}
%>
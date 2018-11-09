<%--
/**
 页面名称 ：a410-c.jsp
 页面功能 : 一付多收打印控制页面
 作    者 ：kewen hu
 日    期 ：2004-02-24
 特殊说明 ：实现操作说明：findByID取得所有信息
     		进账单
			特种转账借方传票
			特种转账贷方传票
			存款支取凭证
 修改历史 ：
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

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
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransOnePayMultiReceiveDAO"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;

    //标题变量
    String strTitle = "[账户历史信息]";
    try {
         /* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

		long lID = -1;
		String strPrintPage = "";
		long lShow = -1;//是否是打印套打的凭证
        //定义变量
		double dAmount = 0.0;
		String strBankChequeNo = "";
		long lBankID = -1;
		long lBillBankID = -1;
		String strBillNo = "";
		long lBillTypeID = -1;
		long lCashFlowID = -1;
		String strCheckAbstractStr = "";
		String strConfirmAbstractStr = "";
		long lConfirmOfficeID = -1;
		long lConfirmUserID = -1;
		String strConsignPassword = "";
		long lConsignReceiveTypeID = -1;
		String strConsignVoucherNo = "";
		String strDeclarationNo = "";
		String strExtBankNo = "";
		String strInstructionNo = "";
		java.sql.Timestamp tsModifyTime = null;
		long lPayAccountID = -1;
		long lReceiveAccountID = -1;
		long lSignUserID = -1;
		long lSingleBankAccountTypeID = -1;
		long lStatusID = -1;
		long lReceiveClientID = -1;
		long lPayClientID = -1;
		long lAbstractID = -1;
		String strExtAccountNo = "";
		String strExtClientName = "";
		String strRemitInBank = "";
		String strRemitInCity = "";
		String strRemitInProvince = "";
		String strAbstractStr = "";
		long lCheckUserID = -1;
		long lCurrencyID = -1;
		java.sql.Timestamp tsExecuteDate = null;
		long lInputUserID = -1;
		java.sql.Timestamp tsInterestStartDate = null;
		long lOfficeID = sessionMng.m_lOfficeID;
		long lTransactionTypeID = -1;
		String strTransNo = "";
		java.sql.Timestamp tsInputDate = null;
		//页面辅助变量
		String strContinueSave = null;

		String strExecuteDate = null;
		String strInterestStartDate = null;
		String strModifyTime = null;

        //读取数据
        strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		strContinueSave = (String)request.getAttribute("strContinueSave");

		String strTemp = null;
		strTemp = (String)request.getParameter("lID");//交易ID
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}

		strTemp = (String)request.getParameter("strPrintPage");//打印的单据类型
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strPrintPage = strTemp;
		}

        //TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();		
		Sett_TransOnePayMultiReceiveDAO dao = new Sett_TransOnePayMultiReceiveDAO();

		TransOnePayMultiReceiveInfo resultInfo = null;
		
		//resultInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(lID);
		resultInfo = dao.findByID(lID);
  		
		PrintInfo printInfo = new PrintInfo();

		if(resultInfo != null)
		{
		Log.print("result not null");
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

			//交易号
			if(resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
			{
				printInfo.setTransNo(resultInfo.getTransNo());
			}

			//根据交易号查找相关勾账交易记录信息
			TransOnePayMultiReceiveInfo  findCondition = new TransOnePayMultiReceiveInfo();//查询条件
			TransOnePayMultiReceiveInfo  transOnePayMultiReceiveInfo = null;//查询结果
			TransOnePayMultiReceiveInfo  tempTransOnePayMultiReceiveInfo = null;//在遍历的过程中存储收付方信息 
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
					
					if(resultInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY)//如果当前记录是付方
					{
						Log.print("当前记录为付方");
						while (tempIterator.hasNext())
						{
						   transOnePayMultiReceiveInfo = (TransOnePayMultiReceiveInfo)tempIterator.next();
							
							if((transOnePayMultiReceiveInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY) && (transOnePayMultiReceiveInfo.getId() != resultInfo.getId()))
							{
								//存在和当前记录不同的付方
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
						{//当前记录是唯一的付方
							Log.print("当前记录为唯一付方");
							printInfo.setAmount(resultInfo.getAmount());

							if (lIndexReceive == 1)
							{//收方唯一
							   Log.print("收方唯一");
							  //设置付方的信息
								//if (resultInfo.getIsInternalVirement() > 0)//if内部转账
								//{
									printInfo.setPayAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账号)
								{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));	
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID()));//银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
								}
								if (resultInfo.getIsGL() > 0)//if总账转账
								{
									printInfo.setPayGL(resultInfo.getPayGL());
								}
							    //设置收方信息
								if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0)//if内部转账
								{
									printInfo.setReceiveAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
								}
								if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0)//if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账号)
								{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());	
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());//银行转账不写开户行信息
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());	
								}
								if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0)//if总账转账
								{
									printInfo.setReceiveGL(resultInfo.getReceiveGL());
								}
							}
							else
							{//收方不唯一
	 							Log.print("收方不唯一");
							 //仅设置付方的信息
							   //if (resultInfo.getAccountID() > 0)//if内部转账
								//{
									Log.print("付方-为内部转账");
									printInfo.setPayAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账号)
								{
									Log.print("付方-为银行");

										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));	
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID()));//银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
								}
								if (resultInfo.getIsGL() > 0)//if总账转账
								{
									Log.print("付方-为总账");
									printInfo.setPayGL(resultInfo.getPayGL());
									Log.print("总账ID"+resultInfo.getPayGL());
								}
							}
						}
						else
						{
							//当前记录不是唯一的付方
							if (lIndexReceive == 1)
							{//收方唯一
								printInfo.setAmount(resultInfo.getAmount());
								
								//设置付方信息
								//if (resultInfo.getIsInternalVirement() > 0)//if内部转账
								//{
									printInfo.setPayAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账号)
								{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));	
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID()));//银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));	
								}
								if (resultInfo.getIsGL() > 0)//if总账转账
								{
									printInfo.setPayGL(resultInfo.getPayGL());
								}
							    //设置收方信息
								if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0)//if内部转账
								{
									printInfo.setReceiveAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
								}
								
								if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0)//if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账号)
								{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());	
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());//银行转账不写开户行信息
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());	
								}
								if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0)//if总账转账
								{
									printInfo.setReceiveGL(resultInfo.getReceiveGL());
								}
							}
						}
						
					}
					else//如果当前记录是收方
					{
						Log.print("当前记录为收方");
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
								//存在与当前记录不同的收方
								bFlagReceive = true;
								lIndexReceive++ ;
							}
						 }
						if(bFlagReceive == false)
						{//当前记录是唯一的收方
							Log.print("当前记录为唯一收方");
							printInfo.setAmount(resultInfo.getAmount());
							if (lIndexPay == 1)
							{//付方唯一
							  //设置收方的信息
								//if (resultInfo.getIsInternalVirement() > 0)//if内部转账
								//{
									printInfo.setReceiveAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账号)
								{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());	
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());//银行转账不写开户行信息
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());	
								}
								if (resultInfo.getIsGL() > 0)//if总账转账
								{
									printInfo.setReceiveGL(resultInfo.getReceiveGL());
								}
							    //设置付方信息
								if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0)//if内部转账
								{
									printInfo.setPayAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
								}
								if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0)//if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账号)
								{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));	
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));//银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(tempTransOnePayMultiReceiveInfo.getBankID()));
								}
								if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0)//if总账转账
								{
									printInfo.setPayGL(resultInfo.getPayGL());
								}
							}
							else
							{//付方不唯一
							 //仅设置收方的信息
							   //if (resultInfo.getIsInternalVirement() > 0)//if内部转账
								//{
									printInfo.setReceiveAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账号)
								{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());	
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());//银行转账不写开户行信息
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());	
								}
								if (resultInfo.getIsGL() > 0)//if总账转账
								{
									printInfo.setReceiveGL(resultInfo.getReceiveGL());
								}
							}
						}
						else
						{
							Log.print("当前记录不是唯一的收方");
							//当前记录不是唯一的收方
							if (lIndexPay == 1)
							{//付方唯一
								Log.print("付方唯一");
								printInfo.setAmount(resultInfo.getAmount());
								
								//设置收方信息
								//if (resultInfo.getIsInternalVirement() > 0)//if内部转账
								//{
									Log.print("收方为内部转账");
									printInfo.setReceiveAccountID(resultInfo.getAccountID());
								//}
								if (resultInfo.getIsBank() > 0)//if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账号)
								{
									Log.print("收方为银行");
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());	
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());//银行转账不写开户行信息
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());	
								}
								if (resultInfo.getIsGL() > 0)//if总账转账
								{
									printInfo.setReceiveGL(resultInfo.getReceiveGL());
								}
							    //设置付方信息
								if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0)//if内部转账
								{
									Log.print("付方内部转账");
									printInfo.setPayAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
								}
								if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0)//if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账号)
								{
									Log.print("付方银行");
										printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));	
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));//银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(tempTransOnePayMultiReceiveInfo.getBankID()));
								}
								if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0)//if总账转账
								{
									Log.print("付方总账");
									printInfo.setPayGL(resultInfo.getPayGL());
								}
							}
						}
					}
				}
			}
			if ((strPrintPage.indexOf("1") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('1','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo,out);//打印进账单
				Log.print("打印进账单");
			}
			if ((strPrintPage.indexOf("4") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('4','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo,out);//打印特转借
			}
			if ((strPrintPage.indexOf("2") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('2','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo,out);//打印特转贷
			}
			if ((strPrintPage.indexOf("3") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('3','a');
				lShow = 1;
				PrintVoucher.PrintShowWithDraw(printInfo,out);//打印存款支取凭证
			}
			if ((strPrintPage.indexOf("b") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('b','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(1,1,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("c") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('c','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(2,1,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("d") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('d','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(3,1,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("e") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('e','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(4,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("f") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('f','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(5,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("g") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('g','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(6,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("h") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('h','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(7,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("i") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('i','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(8,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("j") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('j','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(5,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("k") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('k','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(6,3,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("l") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('l','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(7,3,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("m") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('m','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(8,3,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("n") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('n','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(8,1,printInfo,out);//套打
			}
			
			if (lShow < 0)
			{
%>	
			<SCRIPT language=JavaScript>
				alert("打印完毕！");
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
	 	location.href='../a410-c.jsp?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
	 }
}

document.onkeydown = keyDown; // work together to analyze keystrokes
if (netscape) document.captureEvents(Event.KEYDOWN|Event.KEYUP);
</SCRIPT>

<%
        request.setAttribute("strActionResult",strActionResult);
/*
        //根据处理结果设置下一步跳转的目标页面
        strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;
        //转向下一页面
        Log.print("Next Page URL:"+strNextPageURL);
        //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
        rd.forward( request,response );
*/
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>

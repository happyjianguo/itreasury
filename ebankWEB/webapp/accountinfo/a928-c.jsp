<%--
 页面名称 ：c015.jsp
 页面功能 : 多笔贷款收回打印控制页面
 作    者 ：rxie
 日    期 ：2003-11-17
 特殊说明 ：实现操作说明：findByID取得所有信息
 			付款：存款支取凭证
			自营、委托：贷款收回凭证，利息通知单
			委托：特种转账贷方凭证
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO" %>
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

    try
	{
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		long lID = -1;
		String strTransNo = "";
		String strPrintPage = "";
        //定义变量
		long lOfficeID = sessionMng.m_lOfficeID;
		
		//页面辅助变量
		String strContinueSave = null;

		String strExecuteDate = null;
		String strInterestStartDate = null;
		String strModifyTime = null;

		//打印变量
		long lShow = -1;
        //读取数据
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
		
		strTemp = (String)request.getAttribute("strTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransNo = strTemp;
		}
		
		strTemp = (String)request.getAttribute("strPrintPage");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strPrintPage = strTemp;
		}
		
		//TransLoanDelegation transLoanDelegation = new TransLoanDelegation();
		Sett_TransRepaymentLoanDAO transLoanDelegation = new Sett_TransRepaymentLoanDAO();

		Collection resultCollection = null;

		TransRepaymentLoanInfo resultInfo = null;//当前记录info

		TransRepaymentLoanInfo transRepaymentLoanInfo = null;
        
		TransRepaymentLoanInfo tempPayTransRepaymentLoanInfo = null;//存储付款信息
		TransRepaymentLoanInfo tempTransRepaymentLoanInfo = null;//存储贷款回收信息

		resultCollection = transLoanDelegation.getRepaymentCollectionByTransNo(strTransNo);

		PrintInfo printInfo = new PrintInfo();//用于打印其他凭证
		PrintInfo printInfo1 = new PrintInfo();//用于打印存款支取凭证
		
		
		//标识变量
		boolean bFlagPay = false;
		long lIndexPay = 0;
		boolean bFlagReceive = false;
		long lIndexReceive = 0;
		Iterator tempIterator = null;
		
		if(resultCollection != null && resultCollection.size() > 0)
		{		
		      //设置公用信息
			   //resultInfo = transLoanDelegation.repaymentFindDetailByID(lID);
			   resultInfo=transLoanDelegation.findByID(lID);
	           if(resultInfo != null)
			   {
			   //办事处
		        if(resultInfo.getOfficeID() > 0 )
				{
					printInfo1.setOfficeID(resultInfo.getOfficeID());
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				
				//币种
				if(resultInfo.getCurrencyID() > 0 )
				{
					printInfo1.setCurrencyID(resultInfo.getCurrencyID());
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				
				//执行日
				if(resultInfo.getExecute() != null )
				{
					printInfo1.setExecuteDate(resultInfo.getExecute());
					printInfo.setExecuteDate(resultInfo.getExecute());
				}
				
				//起息日
				if(resultInfo.getInterestStart() != null )
				{
					printInfo1.setInterestStartDate(resultInfo.getInterestStart());
					printInfo.setInterestStartDate(resultInfo.getInterestStart());
				}
				
				//交易号
				if(resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo1.setTransNo(resultInfo.getTransNo());
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				
				//摘要
				if(resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo1.setAbstract(resultInfo.getAbstract());
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				
				//摘要ID
				if(resultInfo.getAbstractID() > 0 )
				{
					printInfo1.setAbstractID(resultInfo.getAbstractID());
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				
				//录入人
				if(resultInfo.getInputUserID() > 0 )
				{
					printInfo1.setInputUserID(resultInfo.getInputUserID());
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				
				//复核人
				if(resultInfo.getCheckUserID() > 0 )
				{
					printInfo1.setCheckUserID(resultInfo.getCheckUserID());
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				
				//合同号
				if(resultInfo.getLoanContractID() > 0)
				{
					printInfo1.setContractID(resultInfo.getLoanContractID());
					printInfo.setContractID(resultInfo.getLoanContractID());
				}
				
				//放款通知单号
				if(resultInfo.getLoanNoteID() > 0)
				{
					printInfo1.setLoanNoteID(resultInfo.getLoanNoteID());
					printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
				}
				
				//金额
				if(resultInfo.getAmount() != 0.0)
				{
				   printInfo1.setAmount(resultInfo.getAmount());
				   printInfo.setAmount(resultInfo.getAmount());
				}
				
				if(resultInfo.getTransDirectionID() == SETTConstant.MultiLoanType.PAYMENT)//为付方
				{ 
				  tempIterator = resultCollection.iterator();
				  while (tempIterator.hasNext())
				  {
					    transRepaymentLoanInfo = (TransRepaymentLoanInfo)tempIterator.next();
						
						
						
						if((transRepaymentLoanInfo.getTransDirectionID() == SETTConstant.MultiLoanType.PAYMENT) && (transRepaymentLoanInfo.getID() != resultInfo.getID()))
						{
							//存在和当前记录不同的付方
							bFlagPay = true;
							lIndexPay++ ;
						}
						
						if(transRepaymentLoanInfo.getTransDirectionID() == SETTConstant.MultiLoanType.TRUSTLOAN ||transRepaymentLoanInfo.getTransDirectionID() == SETTConstant.MultiLoanType.CONSIGNLOAN)
						{
							bFlagReceive = true;
							lIndexReceive++ ;
							tempTransRepaymentLoanInfo = transRepaymentLoanInfo;
						}	
				     }	
						if(bFlagPay == false)//当前记录为唯一的付方
						{
						 
						  Log.print("\n~~~~~~唯一付~~~~~~~\n");
						    /***特殊处理，记录付方信息，打印本金收回凭证中付方信息***/
						   // tempPayTransRepaymentLoanInfo = resultInfo;
						  
							//设置付方信息
						    //走活期账号
							 if(resultInfo.getDepositAccountID() > 0)
							 {
								printInfo1.setPayAccountID(resultInfo.getDepositAccountID());
							 }
							 
							 //走外部银行
							 if(resultInfo.getBankID() > 0)
							 {
							    if(resultInfo.getClientID() > 0 )
			                    {
				                  printInfo1.setExtClientName(NameRef.getClientNameByID(resultInfo.getClientID()));
			                    }
								printInfo1.setExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
							    printInfo1.setExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID()));
							 }
							 
							  //设置金额
							  if(resultInfo.getAmount() != 0.0 )
			                  {
				                printInfo1.setAmount(resultInfo.getAmount());
			                  }
							 
						     if (lIndexReceive == 1)//收方唯一
						     {
						      //设置收方信息
							   if(tempTransRepaymentLoanInfo != null)
							   {
							  	 if(tempTransRepaymentLoanInfo.getLoanAccountID()>0)
			                     {
				                    printInfo1.setReceiveAccountID(tempTransRepaymentLoanInfo.getLoanAccountID());
			                     }
							   }	
						  }
						}
						else//当前记录不是唯一的付方
						{ 
						  if (lIndexReceive == 1)//收方唯一
						  {
						     //设置付方信息
						    //走活期账号
							 if(resultInfo.getDepositAccountID() > 0)
							 {
								printInfo1.setPayAccountID(resultInfo.getDepositAccountID());
							 }
							 
							 //走外部银行
							 if(resultInfo.getBankID() > 0)
							 {
							    if(resultInfo.getClientID() > 0 )
			                    {
				                  printInfo1.setExtClientName(NameRef.getClientNameByID(resultInfo.getClientID()));
			                    }
								printInfo1.setExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
							    printInfo1.setExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID()));
							 }
							 
							  //设置金额
							  if(resultInfo.getAmount() != 0.0 )
			                  {
				                printInfo1.setAmount(resultInfo.getAmount());
			                  }
						  }
					   }
				}
				else//为收方
				{
				    
				   tempIterator = resultCollection.iterator();
				   while (tempIterator.hasNext())
				    {
					    transRepaymentLoanInfo = (TransRepaymentLoanInfo)tempIterator.next();
						
						if((transRepaymentLoanInfo.getTransDirectionID() == SETTConstant.MultiLoanType.PAYMENT) && (transRepaymentLoanInfo.getID() != resultInfo.getID()))
						{
							//存在和当前记录不同的付方
							bFlagPay = true;
							lIndexPay++ ;
							tempTransRepaymentLoanInfo = transRepaymentLoanInfo;
						}	
				     }	
					
					
					//收款方
					if(resultInfo.getLoanAccountID()>0)
					{
						printInfo.setReceiveAccountID(resultInfo.getLoanAccountID());
					}
						
					//付款方客户	
					if(resultInfo.getClientID() > 0 )
					{
						printInfo.setPayClientID(resultInfo.getClientID());
					}
					
					if(resultInfo.getDepositAccountID()>0)
					{
						printInfo.setPayAccountID(resultInfo.getDepositAccountID());
					}
					
					
					if(resultInfo.getBankID()>0)
					{
						printInfo.setPayBankID(resultInfo.getBankID());
					}
					
					//当前余额
					if(resultInfo.getCurrentBalance()!= 0.0)
					{
						printInfo.setCurrentBalance(resultInfo.getCurrentBalance());
					}
					
					if(resultInfo.getLoanContractID() > 0)
					{
						printInfo.setContractID(resultInfo.getLoanContractID());
					}
					
					if(resultInfo.getLoanNoteID() > 0)
					{
						printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
					}
					
					if(resultInfo.getPayInterestAccountID() > 0)
					{
						printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
					}
					
					if(resultInfo.getReceiveInterestAccountID() > 0)
					{
						printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
					}
					
					if(resultInfo.getPaySuretyAccountID() > 0)
					{
						printInfo.setPaySuretyFeeAccountID(resultInfo.getPaySuretyAccountID());
					}
					
					if(resultInfo.getReceiveSuretyAccountID() > 0)
					{
						printInfo.setReceiveSuretyFeeAccountID(resultInfo.getReceiveSuretyAccountID());
					}
					
					if(resultInfo.getCommissionAccountID() > 0)
					{
						printInfo.setPayCommissionAccountID(resultInfo.getCommissionAccountID());
					}
					
					if(resultInfo.getFreeFormID() > 0)
					{
						printInfo.setFreeFormID(resultInfo.getFreeFormID());
					}
			
					if(resultInfo.getBillNo() != null && resultInfo.getBillNo().length() > 0)
					{
						printInfo.setBillNo(resultInfo.getBillNo());
					}		
					
					if(resultInfo.getBillTypeID() > 0)
					{
						printInfo.setBillTypeID(resultInfo.getBillTypeID());
					}
					
					if(resultInfo.getBillBankID() > 0)
					{
						printInfo.setBillBankID(resultInfo.getBillBankID());
					}
					
					if(resultInfo.getExtBankNo() != null && resultInfo.getExtBankNo().length() > 0)
					{
						printInfo.setExtBankNo(resultInfo.getExtBankNo());
					}	
					
					if(resultInfo.getPreFormID() > 0)
					{
						printInfo.setPreFormID(resultInfo.getPreFormID());
					}
					
					if(resultInfo.getInterestBankID() > 0)
					{
						printInfo.setPayInterestBankID(resultInfo.getInterestBankID());
					}
					
					if(resultInfo.getSuretyBankID() > 0)
					{
						printInfo.setPaySuertyFeeBankID(resultInfo.getSuretyBankID());
					}
					
					if(resultInfo.getCommissionBankID() > 0)
					{
						printInfo.setPayCommissionBankID(resultInfo.getCommissionBankID());
					}
					
					if(resultInfo.getInterest() != 0.0 )
					{
						printInfo.setInterest(resultInfo.getInterest());
					}
					
					if(resultInfo.getInterestReceiveAble() != 0.0 )
					{
						printInfo.setInterestReceivable(resultInfo.getInterestReceiveAble());
					}
					
					if(resultInfo.getInterestIncome() != 0.0 )
					{
						printInfo.setInterestIncome(resultInfo.getInterestIncome());
					}
					
					if(resultInfo.getInterestTax() != 0.0 )
					{
						printInfo.setInterestTax(resultInfo.getInterestTax());
					}
					
					if(resultInfo.getCompoundInterest() != 0.0 )
					{
						printInfo.setCompoundInterest(resultInfo.getCompoundInterest());
					}
					
					if(resultInfo.getOverDueInterest() != 0.0 )
					{
						printInfo.setOverDueInterest(resultInfo.getOverDueInterest());
					}
					
					if(resultInfo.getSuretyFee() != 0.0 )
					{
						printInfo.setSuretyFee(resultInfo.getSuretyFee());
					}
					
					if(resultInfo.getCommission() != 0.0 )
					{
						printInfo.setCommission(resultInfo.getCommission());
					}
					
					if(resultInfo.getAdjustInterest() != 0.0 )
					{
						printInfo.setAdjustInterest(resultInfo.getAdjustInterest());
					}
					
								
					if(resultInfo.getRealInterest() != 0.0 )
					{
						printInfo.setRealInterest(resultInfo.getRealInterest());
					}
					
					if(resultInfo.getRealInterestReceiveAble() != 0.0 )
					{
						printInfo.setRealInterestReceivable(resultInfo.getRealInterestReceiveAble());
					}
					
					if(resultInfo.getRealInterestIncome() != 0.0 )
					{
						printInfo.setRealInterestIncome(resultInfo.getRealInterestIncome());
					}
					
					if(resultInfo.getRealInterestTax() != 0.0 )
					{
						printInfo.setRealInterestTax(resultInfo.getRealInterestTax());
					}
					
					if(resultInfo.getRealCompoundInterest() != 0.0 )
					{
						printInfo.setRealCompoundInterest(resultInfo.getRealCompoundInterest());
					}
					
					if(resultInfo.getCompoundInterestStart()!= null)
					{
						printInfo.setCompoundInterestStart(resultInfo.getCompoundInterestStart());
					}
					Log.print("~~~~~~~~~~~复利起始日："+printInfo.getCompoundInterestStart());
					
					if(resultInfo.getCompoundAmount()!= 0.0)
					{
						printInfo.setCompoundAmount(resultInfo.getCompoundAmount());
					}
					Log.print("~~~~~~~~~~~复利本金："+printInfo.getCompoundAmount());
					
					if(resultInfo.getCompoundRate()!= 0.0)
					{
						printInfo.setCompoundRate(resultInfo.getCompoundRate());
					}
					
					Log.print("~~~~~~~~~~~复利利率："+printInfo.getCompoundRate());
				
					if(resultInfo.getRealOverDueInterest() != 0.0 )
					{
						printInfo.setRealOverDueInterest(resultInfo.getRealOverDueInterest());
					}
						
					if(resultInfo.getOverDueStart()!= null)
					{
						printInfo.setOverDueStart(resultInfo.getOverDueStart());
					}
					
					if(resultInfo.getOverDueAmount()!= 0.0)
					{
						printInfo.setOverDueAmount(resultInfo.getOverDueAmount());
					}
					
					if(resultInfo.getOverDueRate() != 0.0 )
					{
						printInfo.setOverDueRate(resultInfo.getOverDueRate());
					}	
			
					if(resultInfo.getRealSuretyFee() != 0.0 )
					{
						printInfo.setRealSuretyFee(resultInfo.getRealSuretyFee());
					}
					
					if(resultInfo.getRealCommission() != 0.0 )
					{
						printInfo.setRealCommission(resultInfo.getRealCommission());
					}
					
					//业务类型
					if(resultInfo.getTransactionTypeID() > 0 )
					{
						printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
					}
					Log.print("~~~~~~~~~~~业务类型:"+printInfo.getTransTypeID());
		
					//贷款单位
					if(resultInfo.getConsignAccountID() > 0)
					{
						printInfo.setConsignAccountID(resultInfo.getConsignAccountID());
					}
				}

			if(resultInfo.getTransDirectionID() == SETTConstant.MultiLoanType.PAYMENT)//为付方
			{
			  if ((strPrintPage.indexOf("3") >= 0) )
			  {
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('3','a');
				lShow = 1;
				PrintVoucher.PrintShowWithDraw(printInfo1,out);//存款支取凭证
			  }
		   } 
		
		    
			//账号,说明仅考虑一付的情况
			if(tempTransRepaymentLoanInfo != null)
			{
				Log.print("付方信息不为空！");
				if(tempTransRepaymentLoanInfo.getDepositAccountID()>0)
				{
					printInfo.setPayAccountID(tempTransRepaymentLoanInfo.getDepositAccountID());
				}
				
				if(tempTransRepaymentLoanInfo.getBankID()>0)
				{
					printInfo.setPayBankID(tempTransRepaymentLoanInfo.getBankID());
				}
			}	
						
		     if ((strPrintPage.indexOf("s") >= 0) )
			 {
				Log.print("~~~~~~~~~~打印贷款收回凭证");
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('s','a');
				lShow = 1;
				Log.print("~~~~~~~~~~~~~账号："+NameRef.getAccountNoByID(resultInfo.getLoanAccountID()));
				
				if(resultInfo.getLoanAccountID() > 0)
				{
				    strTemp = NameRef.getAccountNoByID(resultInfo.getLoanAccountID());
					 if(strTemp.length() > 5)
					 {
							strTemp = strTemp.substring(3,5);
					 }
				    
					 if(strTemp.equals("08"))//自营
				     {
				       Log.print("~~~~~~~~~~自营贷款收回凭证");
				       PrintVoucher.PrintTrustRepaymentLoan(printInfo,out);//自营贷款收回凭证
				     }
				
					 if(strTemp.equals("09"))//委托
					 {
					  Log.print("~~~~~~~~~~委托贷款收回凭证");
					  PrintVoucher.PrintConsignRepaymentLoan(printInfo,out);//委托贷款收回凭证
					 }
				}
			 }
			
			
			if ((strPrintPage.indexOf("0") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('0','a');
				lShow = 1;
				long lAccountTypeID = -1;
				if(resultInfo.getLoanAccountID() > 0)
				{
				    lAccountTypeID = NameRef.getAccountTypeByID(resultInfo.getLoanAccountID());
				
					if (SETTConstant.AccountType.isTrustAccountType(lAccountTypeID)) //自营
					{
						//交易类型
						printInfo.setTransTypeID(SETTConstant.TransactionType.TRUSTLOANRECEIVE);
						//账户类型
						printInfo.setAccountTypeID(lAccountTypeID);
					}
					if (SETTConstant.AccountType.isConsignAccountType(lAccountTypeID)) //委贷
					{
						//交易类型
						printInfo.setTransTypeID(SETTConstant.TransactionType.CONSIGNLOANRECEIVE);
						//账户类型
						printInfo.setAccountTypeID(lAccountTypeID);
					}
					
					
					 PrintVoucher.PrintInterestNotice(printInfo,out);//利息通知单
				}
				
			}
		
			if (printInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{//委托贷款
				//特种转账付方
				if(resultInfo.getConsignAccountID()>0)
				{
					printInfo.setPayAccountID(resultInfo.getConsignAccountID());
				}
				
				//特种转账收方方
				if(resultInfo.getConsignDepositAccountID()>0)
				{
					printInfo.setReceiveAccountID(resultInfo.getConsignDepositAccountID());
					printInfo.setSpecialAccountID(resultInfo.getConsignDepositAccountID());
				}
				if ((strPrintPage.indexOf("2") >= 0) )
			    {
					if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
					strPrintPage = strPrintPage.replace('2','a');
					lShow = 1;
					PrintVoucher.PrintShowCredit(printInfo,out);//特种转账
			    }
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
    }
	%>
<Script Language="JavaScript">
netscape = "";
function keyDown(DnEvents)
{
	k = (netscape) ? DnEvents.which : window.event.keyCode;
	
	if (k == 13)
	 { 
	 	location.href='../control/c015.jsp?lID=<%=lID%>&strTransNo<%=strTransNo%>&strPrintPage=<%=strPrintPage%>';
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
		sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;
	}

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
%>
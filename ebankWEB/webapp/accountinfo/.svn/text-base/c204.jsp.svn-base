<%--
 页面名称 ：v017.jsp
 页面功能 : 资金上收凭证打印-控制页面
 作    者 ：xgzhang
 日    期 ：2005-09-09
 特殊说明 ：简单实现说明：
 	1、收方凭证:进账单、特种转账借方凭证、特种转账贷发凭证
 	2、付发凭证:进账单、特种转账借方凭证、特种转账贷发凭证
 修改历史 ：
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
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
	//定义变量
	String strPrintPage = null;
	String strTemp = null;
	String strPageLoaderKey = null;
	long lOfficeID = sessionMng.m_lOfficeID;
	long lID = -1;
	long lTransactionTypeID = -1; 
	long receiveAccountId = -2;
	long payAccountId = -2;
	int resultCount = 0;
	//打印变量
	long lShow = -1;
	long lPrintType = -1;//1:套打	
	
	//查询结果变量
	QueryTransactionInfo[] resultInfos = null;   
	QueryTransactionInfo queryTransactionInfo = null;
	PrintInfo printInfo = null;
	ManufacturePrintInfo manufacturePrintInfo = null;
		
			   
	try {
		//请求检测
		
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
		
        strTemp = (String)request.getAttribute("strPrintPage");//打印的单据类型
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
					
				}
				
				if(printInfo != null) {
					//打印收方进账单
					if ((strPrintPage.indexOf("A") >= 0) && receiveAccountId != -2 && receiveAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('A','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowIn(printInfo,out);//打印进账单
						Log.print("打印收方进账单");
					}
					//打印付方进账单
					if ((strPrintPage.indexOf("B") >= 0) && payAccountId != -2 && payAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('B','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowIn(printInfo,out);//打印进账单
						Log.print("打印付方进账单");
					}
					//打印收方特转借
					if ((strPrintPage.indexOf("C") >= 0) && receiveAccountId != -2 && receiveAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('C','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowDebtor(printInfo,out);//打印特转借
					}
					//打印付方特转借
					if ((strPrintPage.indexOf("D") >= 0) && payAccountId != -2 && payAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('D','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowDebtor(printInfo,out);//打印特转借
					}
					//打印收方特转贷
					if ((strPrintPage.indexOf("E") >= 0) && receiveAccountId != -2 && receiveAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('E','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowCredit(printInfo,out);//打印特转贷
					}
					//打印付方特转贷
					if ((strPrintPage.indexOf("F") >= 0) && payAccountId != -2 && payAccountId != -1) {
						if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
						if(i == (resultInfos.length -1)) {	
							strPrintPage = strPrintPage.replace('F','a');
						}	
						lShow = 1;
						IPrintVoucher.PrintShowCredit(printInfo,out);//打印特转贷
					}
				}
			}	
		}
	if(lShow < 0) {
%>
	<SCRIPT language=JavaScript>
		alert("打印完毕！");
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
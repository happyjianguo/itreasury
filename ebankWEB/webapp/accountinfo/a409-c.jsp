<%--
/**
 页面名称 ：a409-c.jsp
 页面功能 : 特殊业务打印控制页面
 作    者 ：kewen hu
 日    期 ：2004-02-26
 特殊说明 ：实现操作说明：findByID取得所有信息
 							对于特殊业务打印若出现两边均为银行的情况，仅需打印套打
							银行-总账类的打印(未定)
 修改历史 ：
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
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;

    //标题变量
    String strTitle = "[账户历史明细]";
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
		PrintInfo printInfo2 = new PrintInfo();//金额2的凭证
		if(resultInfo != null)
		{
		Log.print("result not null");
			printInfo.setTransTypeID(SETTConstant.TransactionType.SPECIALOPERATION);
			printInfo2.setTransTypeID(SETTConstant.TransactionType.SPECIALOPERATION);
			//set 打印参数
			  if(resultInfo.getNofficeid() > 0 )
			{
				printInfo.setOfficeID(resultInfo.getNofficeid());
				printInfo2.setOfficeID(resultInfo.getNofficeid());
			}
			
			//币种
			if(resultInfo.getNcurrencyid() > 0 )
			{
				printInfo.setCurrencyID(resultInfo.getNcurrencyid());
				printInfo2.setCurrencyID(resultInfo.getNcurrencyid());
			}
			
			//执行日
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
			
			//交易号
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
			
			//公用信息结束
			//根据借贷方向来判断收付方向
			/*
				特殊交易原则：
				1、借 打印在 凭证地付方
				2、贷 打印在 凭证地借方
			*/
			
			Log.print("收复方向:付="+resultInfo.getNpaydirection()+":收="+resultInfo.getNreceivedirection());
			if (resultInfo.getNpaydirection()==-1&&resultInfo.getNreceivedirection()==-1)
			{
				resultInfo.setNpaydirection(SETTConstant.DebitOrCredit.DEBIT) ;
				resultInfo.setNreceivedirection(SETTConstant.DebitOrCredit.CREDIT) ;
			}

			if(resultInfo.getNpaydirection() != resultInfo.getNreceivedirection())// 收复方向不相同
			{
			Log.print("收复方向不相同");
				if(resultInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT)//付方为借方
				{
				Log.print("付方为借方");
					//付款方账号
					if(resultInfo.getNpayaccountid() > 0)
					{
						printInfo.setPayAccountID(resultInfo.getNpayaccountid());
					}
					
					//付款方开户行
					if(resultInfo.getNpaybankid() > 0)
					{
						printInfo.setPayBankID(resultInfo.getNpaybankid());
						printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getNpaybankid()));	
						printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getNpaybankid()));
						printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getNpaybankid()));//银行转账不写开户行信息
						printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getNpaybankid()));
						printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getNpaybankid()));
					}
					
					//付款方总账
					if(resultInfo.getNpaygeneralledgertypeid() > 0)
					{
						printInfo.setPayGL(resultInfo.getNpaygeneralledgertypeid());
					}
					//收款方账号
					if(resultInfo.getNreceiveaccountid() > 0)
					{
						printInfo.setReceiveAccountID(resultInfo.getNreceiveaccountid());
					}
					
					//收款方开户行
					if(resultInfo.getNreceivebankid() > 0)
					{
						printInfo.setReceiveBankID(resultInfo.getNreceivebankid());
						printInfo.setReceiveExtClientName(NameRef.getBankNameByID(resultInfo.getNreceivebankid()));	
						printInfo.setReceiveExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getNreceivebankid()));
						printInfo.setReceiveExtRemitInBank(NameRef.getBankNameByID(resultInfo.getNreceivebankid()));//银行转账不写开户行信息
						printInfo.setReceiveExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getNreceivebankid()));
						printInfo.setReceiveExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getNreceivebankid()));

					}
					
					//收款方总账科目
					if(resultInfo.getNreceivegeneralledgertypeid() > 0)
					{
						printInfo.setReceiveGL(resultInfo.getNreceivegeneralledgertypeid());
					}
					
					//本金金额
					if(resultInfo.getMpayamount() != 0.0 )
					{
						printInfo.setAmount(resultInfo.getMpayamount());
					}
					
					/*
					//银行资料
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
				else//付方为贷方
				{
				Log.print("付方为贷方");
					//付款方账号
					if(resultInfo.getNpayaccountid() > 0)
					{
						printInfo.setReceiveAccountID(resultInfo.getNpayaccountid());
					}
					
					//付款方开户行
					if(resultInfo.getNpaybankid() > 0)
					{
						printInfo.setReceiveBankID(resultInfo.getNpaybankid());
					}
					
					//付款方总账
					if(resultInfo.getNpaygeneralledgertypeid() > 0)
					{
						printInfo.setReceiveGL(resultInfo.getNpaygeneralledgertypeid());
					}
					//收款方账号
					if(resultInfo.getNreceiveaccountid() > 0)
					{
						printInfo.setPayAccountID(resultInfo.getNreceiveaccountid());
					}
					
					//收款方开户行
					if(resultInfo.getNreceivebankid() > 0)
					{
						printInfo.setPayBankID(resultInfo.getNreceivebankid());
					}
					
					//收款方总账科目
					if(resultInfo.getNreceivegeneralledgertypeid() > 0)
					{
						printInfo.setPayGL(resultInfo.getNreceivegeneralledgertypeid());
					}
					
					//本金金额
					if(resultInfo.getMpayamount() != 0.0 )
					{
						printInfo.setAmount(resultInfo.getMpayamount());
					}
					
					/*//银行资料
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
			if(resultInfo.getNpaydirection() == resultInfo.getNreceivedirection())//收复方向相同
			{
			Log.print("收付方向相同");
				if(resultInfo.getNpaydirection() == SETTConstant.DebitOrCredit.DEBIT && resultInfo.getNreceivedirection() == SETTConstant.DebitOrCredit.DEBIT)
				{//如果两方都为借方
					Log.print("如果两方都为借方");
					//付款方账号
					if(resultInfo.getNpayaccountid() > 0)
					{
						printInfo.setPayAccountID(resultInfo.getNpayaccountid());
					}
					
					//付款方开户行
					if(resultInfo.getNpaybankid() > 0)
					{
						printInfo.setPayBankID(resultInfo.getNpaybankid());
					}
					
					//付款方总账
					if(resultInfo.getNpaygeneralledgertypeid() > 0)
					{
						printInfo.setPayGL(resultInfo.getNpaygeneralledgertypeid());
					}
					//收款方账号
					if(resultInfo.getNreceiveaccountid() > 0)
					{
						printInfo2.setPayAccountID(resultInfo.getNreceiveaccountid());
					}
					
					//收款方开户行
					if(resultInfo.getNreceivebankid() > 0)
					{
						printInfo2.setPayBankID(resultInfo.getNreceivebankid());
					}
					
					//收款方总账科目
					if(resultInfo.getNreceivegeneralledgertypeid() > 0)
					{
						printInfo2.setPayGL(resultInfo.getNreceivegeneralledgertypeid());
					}
					
					//本金金额
					if(resultInfo.getMpayamount() != 0.0 )
					{
						printInfo.setAmount(resultInfo.getMpayamount());
					}
					//本金金额
					if(resultInfo.getMreceiveamount() != 0.0 )
					{
						printInfo2.setAmount(resultInfo.getMreceiveamount());
					}
					
					
				
				/*
				//银行资料	
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
				{//如果两方都为贷方方
					Log.print("如果两方都为贷方");
					//付款方账号
					if(resultInfo.getNpayaccountid() > 0)
					{
						printInfo.setReceiveAccountID(resultInfo.getNpayaccountid());
						
					}
					
					//付款方开户行
					if(resultInfo.getNpaybankid() > 0)
					{
						printInfo.setReceiveBankID(resultInfo.getNpaybankid());
					}
					
					//付款方总账
					if(resultInfo.getNpaygeneralledgertypeid() > 0)
					{
						printInfo.setReceiveGL(resultInfo.getNpaygeneralledgertypeid());
						Log.print("付款方总账类Info1:"+resultInfo.getNpaygeneralledgertypeid());
					}
					//收款方账号
					if(resultInfo.getNreceiveaccountid() > 0)
					{
						printInfo2.setReceiveAccountID(resultInfo.getNreceiveaccountid());
					}
					
					//收款方开户行
					if(resultInfo.getNreceivebankid() > 0)
					{
						printInfo2.setReceiveBankID(resultInfo.getNreceivebankid());
					}
					
					//收款方总账科目
					if(resultInfo.getNreceivegeneralledgertypeid() > 0)
					{
						printInfo2.setReceiveGL(resultInfo.getNreceivegeneralledgertypeid());
						Log.print("付款方总账类Info2:"+resultInfo.getNreceivegeneralledgertypeid());
					}
					
					//本金金额
					if(resultInfo.getMpayamount() != 0.0 )
					{
						printInfo.setAmount(resultInfo.getMpayamount());
					}
					//本金金额
					if(resultInfo.getMreceiveamount() != 0.0 )
					{
						printInfo2.setAmount(resultInfo.getMreceiveamount());
					}

					/*
					//银行资料
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
			
			//set打印参数End
			
			if ((strPrintPage.indexOf("b") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('b','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo,out);//打印进账单
			}
			if ((strPrintPage.indexOf("d") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('d','a');
				lShow = 1;
				System.out.println(printInfo.getReceiveAccountID() + "******************************is here is *******************************************" + printInfo.getExtClientName());
				PrintVoucher.PrintShowWithDraw(printInfo,out);//打印存款支取
			}
			if ((strPrintPage.indexOf("e") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('e','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo,out);//打印特转贷借
			}
			if ((strPrintPage.indexOf("c") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('c','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo,out);//打印特转贷贷
			}
			//金额2地凭证
			if ((strPrintPage.indexOf("f") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('f','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo2,out);//打印进账单
			}
			if ((strPrintPage.indexOf("h") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('h','a');
				lShow = 1;
				PrintVoucher.PrintShowWithDraw(printInfo2,out);//打印存款支取
			}
			if ((strPrintPage.indexOf("i") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('i','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo2,out);//打印特转贷借
			}
			if ((strPrintPage.indexOf("g") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('g','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo2,out);//打印特转贷贷
			}
				
			IPrintTemplate.noShowPrintHeadAndFooter(out,0,lOfficeID);
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
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
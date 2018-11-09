<%--
 页面名称 ：c014-1.jsp
 页面功能 : 应付利息和费用匡算
 作    者 ：gqzhang
 日    期 ：2004-4-2
 特殊说明 ：实现操作说明：findByID取得所有信息
			           应付贷款利息通知单
					   逾期催收通知书
					   贷款到期通知书
   					   与本页相关页：jsp/print/c014-1.jsp,若这两页中的一页内容发生变化，需要修改其他页
 修改历史 ：
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
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
   
    try
	{
		//请求检测
		//if(!SETTHTML.validateRequest(out, request,response)) return;
		long lOfficeID = -1;
		lOfficeID = sessionMng.m_lOfficeID;
		long lID = -1;
		String strPrintPage = "";
		String strTemp = "";
		//打印变量
		long lShow = 1;//是否下一页的标志
		
		strTemp = (String)request.getParameter("strPrintPage");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strPrintPage = strTemp;
		}
		
		LoanNoticeInfo resultInfo = null;
		Vector vctLoanNoticeInfo = null;
		
		String strPrintType = (String)request.getParameter("strPrintType");//勾选打印1---批量打印2
		
		if(strPrintType != null)
		{    
			//组装勾选
			Vector resultVec = (Vector)session.getAttribute("resultList");
		    String [] ck = null;
		    ck = request.getParameterValues("ck");
			if(strPrintType.equalsIgnoreCase("1"))//勾选打印
			{
     			Log.print("~~~~~~~~~~~~~~~勾选~~~~~~~~~~~~~~``");
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
			
			if(strPrintType.equalsIgnoreCase("2"))//批量打印
			{
			      Log.print("~~~~~~~~~~~~~~~批量~~~~~~~~~~~~~~``");
				   if(resultVec != null)
				   { 
					  vctLoanNoticeInfo = resultVec;
				   }
			 }
			
			//得到以合同汇总的Vector
			Vector vctLoanNotice = null;
			LoanNoticeInfo loanNoticeInfo = null;
			long [] nContractID = new long[500];
			long lTempContractID = -1;
			
			//取得所选中的合同ID
			if (vctLoanNoticeInfo != null)
			{
				int nContractNumber = 0;
				for (int ii = 0; ii < vctLoanNoticeInfo.size(); ii++)
				{
					loanNoticeInfo = (LoanNoticeInfo) vctLoanNoticeInfo.elementAt(ii);
					if(lTempContractID != loanNoticeInfo.getContractID())
					{
						nContractID[nContractNumber++] = loanNoticeInfo.getContractID();
						//Log.print("选中的合同ID＝＝＝＝＝＝＝＝＝＝＝"+loanNoticeInfo.getContractID());
					}
					lTempContractID = loanNoticeInfo.getContractID();
				}
			}
			//取得选中的合同ID End
			
			for (int nContractNumber2 = 0; nContractNumber2 < nContractID.length; nContractNumber2++)
			{//按照合同 循环打印每个合同的汇总
				//Log.print("nContractID============================================"+nContractID[nContractNumber2]);
				
				//明细条目的Vector
				Vector vctLoanNoticeDetails = new Vector();
				PrintInfo printInfoSum = new PrintInfo();
				//贷款利息
				double dSumInterest = 0.0;
				//贷款复利
				double dSumCompoundInterest = 0.0;
				//罚息
				double dSumOverDueInterest = 0.0;
				//手续费
				double dSumCommission = 0.0;
				//贷款余额
				double dSumLoanBalance = 0.0;
				
				//贷款的开始结束日期 因为和放款单的开始结束日期不一致
				Timestamp tsLoanStartDate = null;
				
				if(nContractID[nContractNumber2] > 0)
				{
				
					for (int countAll = 0; countAll < vctLoanNoticeInfo.size(); countAll++)
					{//取得每个合同的汇总 和 明细条目
						PrintInfo printInfo = new PrintInfo();
						
						resultInfo = (LoanNoticeInfo) vctLoanNoticeInfo.elementAt(countAll);
						
						if(resultInfo.getContractID() == nContractID[nContractNumber2])//取得当前合同的所有放款单的汇总
						{
							//根据贷款账户判断当前打印的是委托还是自营的通知书
							if (resultInfo.getAccountID() > 0)
							{
								strTemp = NameRef.getAccountNoByID(resultInfo.getAccountID());
								if (strTemp.length() > 5)
								{
									strTemp = strTemp.substring(3, 5);
								}
							}
							//编号（）年
							if (resultInfo.getFormYear() != null && resultInfo.getFormYear().length() > 0)
							{
								printInfo.setFormYear(resultInfo.getFormYear());
							}
							Log.print("~~~~~~~~~~编号年：" + printInfo.getFormYear());
							
							//编号（号）
							if (resultInfo.getFormNo() != null && resultInfo.getFormNo().length() > 0)
							{
								printInfo.setFormNo(resultInfo.getFormNo());
							}
							Log.print("~~~~~~~~~~编号：" + printInfo.getFormNo());
							
							//催收次数
							if (resultInfo.getFormNum() > 0)
							{
								printInfo.setFormNum(resultInfo.getFormNum());
							}
							
							//借款人
							if (resultInfo.getAccountID() > 0)
							{
								printInfo.setBorrowClientName(NameRef.getClientNameByAccountID(resultInfo.getAccountID()));
							}
							
							Log.print("~~~~~~~~~~~借款人账户id：" + resultInfo.getAccountID());
							Log.print("~~~~~~~~~~~借款人名称：" + NameRef.getClientNameByAccountID(resultInfo.getAccountID()));
							//贷款合同签订日,根据合同Id查找,无需设置
							
							//贷款合同号
							if (resultInfo.getContractID() > 0)
							{
								printInfo.setContractID(resultInfo.getContractID());
							}
							Log.print("~~~~~~~~~~~~~~~~贷款合同号:" + printInfo.getContractID());
							
							//放款通知单,暂时LoanNotice中还没有该字段
		
							if (resultInfo.getLoanNoteID() > 0)
							{
								printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
							}
							Log.print("~~~~~~~~~~~~~~~~`放款通知单Id：" + printInfo.getLoanNoteID());
							//应支付日期
							if (resultInfo.getClearInterestDate() != null)
							{
								printInfo.setClearInterestDate(resultInfo.getClearInterestDate());
							}
							Log.print("~~~~~~~~~~~~~~~~`结息日：" + printInfo.getClearInterestDate());
		
							//贷款利息
							if (resultInfo.getInterest() != 0.0)
							{
								printInfo.setInterest(resultInfo.getInterest());
							}
							Log.print("~~~~~~~~~~~~~~~~`贷款利息：" + printInfo.getInterest());
		
							//贷款复利
							if (resultInfo.getCompoundInterest() != 0.0)
							{
								printInfo.setCompoundInterest(resultInfo.getCompoundInterest());
							}
		
							//手续费
							if (resultInfo.getCommission() != 0.0)
							{
								printInfo.setCommission(resultInfo.getCommission());
							}
		
							//手续费利率，（根据客户要求，将手续费利率取为合同利率）
							if (resultInfo.getCommissionRate() != 0.0)
							{
								printInfo.setCommissionRate(resultInfo.getCommissionRate());
							}
							Log.print("~~~~~~~~~~~~~~~~~~~~~~~手续费利率:"+printInfo.getCommissionRate());		
							  //逾期罚息
			                 if(resultInfo.getOverDueInterest() != 0.0)
			                 {
			   		            printInfo.setOverDueInterest(resultInfo.getOverDueInterest());
						     }
							Log.print("~~~~~~~~~~~~~~~~~~~~~~~逾期罚息:"+printInfo.getOverDueInterest());			    
			  				double tempDouble = printInfo.getCompoundInterest()+printInfo.getInterest()+printInfo.getOverDueInterest();
			   				Log.print("~~~~~~~~~~~~~~~~~~~~~~~综合:"+tempDouble);		
							
							//贷款本金
							if (resultInfo.getLoanAmount() != 0.0)
							{
								printInfo.setAmount(resultInfo.getLoanAmount());
							}
		
							//贷款余额
							if (resultInfo.getLoanBalance() != 0.0)
							{
								printInfo.setLoanBalance(resultInfo.getLoanBalance());
							}
							Log.print("~~~~~~~~~~~~~~~~~~~~~~~贷款余额:"+printInfo.getLoanBalance());		
		
							//合同利率
							if (resultInfo.getOriginalContractRate() != 0.0)
							{
								printInfo.setContractRate(resultInfo.getOriginalContractRate());
							}
							Log.print("～～～～～～～～～合同利率:" + printInfo.getContractRate());
		
							//执行利率
							if (resultInfo.getExecuteRate() != 0.0)
							{
								printInfo.setExecuteRate(resultInfo.getExecuteRate());
							}
							Log.print("～～～～～～～～～调整前执行利率:" + printInfo.getExecuteRate());
		
							//贷款利率调整日期
							if (resultInfo.getExecuteRateValidDate() != null)
							{
								printInfo.setAdjustRateDate(resultInfo.getExecuteRateValidDate());
							}
							
							//调整后年息
							if (resultInfo.getNewExecuteRate() != 0.0)
							{
								printInfo.setExecuteRateNew(resultInfo.getNewExecuteRate());
							}
							Log.print("～～～～～～～～～调整后执行利率:" + printInfo.getExecuteRateNew());
		
							if (printInfo.getExecuteRateNew() == printInfo.getExecuteRate())
							{
								Log.print("～～～～～～～～～调整前后执行利率相等~~~~~~~~~~~~~~~~~~~~~~`");
							}
							else
							{
								Log.print("～～～～～～～～～调整前后执行利率不等~~~~~~~~~~~~~~~~~~~~~~~");
							}
		
							//贷款期限（月）
							if (resultInfo.getLoanTerm() > 0)
							{
								printInfo.setLoanTerm(resultInfo.getLoanTerm());
							}
		
							//贷款期限(起始日)
							if (resultInfo.getLoanStartDate() != null)
							{
								printInfo.setStartDate(resultInfo.getLoanStartDate());//得到放款单的开始日期
								Log.print("~~~~~~~~~~~~~~~~~~起始日期：" + printInfo.getStartDate());
								tsLoanStartDate = resultInfo.getLoanStartDate();
							}
		
							//贷款期限（到期日）
							if (resultInfo.getLoanEndDate() != null)
							{
								printInfo.setEndDate(resultInfo.getLoanEndDate());
								Log.print("~~~~~~~~~~~~~~~~~~结束日期：" + printInfo.getEndDate());
							}
							
							//起息日
							if (resultInfo.getInterestStartDate() != null)
							{
								printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
							}
							Log.print("＝＝＝＝＝＝＝＝＝上次结息日:"+printInfo.getInterestStartDate());
							
							//盖章日期，即执行日
							if (resultInfo.getExecuteDate() != null)
							{
								printInfo.setExecuteDate(resultInfo.getExecuteDate());
							}
							//特别设置
							printInfo.setExecuteDate(DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)));
							Log.print("～～～～～～～～～～～执行日期:" + printInfo.getExecuteDate());
		
							//担保人
							if (resultInfo.getAssureClientName() != null && resultInfo.getAssureClientName().length() > 0)
							{
								printInfo.setAssureName(resultInfo.getAssureClientName());
							}
		
							//担保合同
							if (resultInfo.getAssureContractID() > 0)
							{
								printInfo.setAssureContractID(resultInfo.getAssureContractID());
							}
							Log.print("~~~~~~~~~~~~~~~~担保合同号:" + printInfo.getAssureContractID());
							//待确定的info
							//String RemitAddress = "";//汇款路径
							//String ReceiveUnit = "";//收款单位
							//String ReceiveBank = "";//开户银行
							
							//贷款利息
								dSumInterest += DataFormat.formatDouble(resultInfo.getInterest(),2);
							//贷款复利
								dSumCompoundInterest += DataFormat.formatDouble(resultInfo.getCompoundInterest(),2);
							//罚息
			   		            dSumOverDueInterest += DataFormat.formatDouble(resultInfo.getOverDueInterest(),2);
							//手续费
								dSumCommission += DataFormat.formatDouble(resultInfo.getCommission(),2);
							//贷款余额
								Log.print("==========贷款余额之前："+dSumLoanBalance);
								dSumLoanBalance = DataFormat.formatDouble(resultInfo.getLoanBalance(),2);
								Log.print("==========贷款余额差额："+DataFormat.formatDouble(resultInfo.getLoanBalance(),2));
								Log.print("==========贷款余额之后："+dSumLoanBalance);
								
								//填入明细的Vector
								Log.print("填入Vector");
								vctLoanNoticeDetails.addElement(printInfo);
								Log.print("填入Vector End");
						}
					}//end for 每个合同的汇总 和 明细条目
							//打印利息通知书
							Log.print("开始打印 合同为合计的 利息通知书");
							PrintInfo pi = null;
							if(vctLoanNoticeDetails.size() >0)
							{
								pi = (PrintInfo) vctLoanNoticeDetails.elementAt(0);
							}
							
							//编号（）年
								printInfoSum.setFormYear(pi.getFormYear());
							//编号 号
								printInfoSum.setFormNo(pi.getFormNo());
							//借款人
								printInfoSum.setBorrowClientName(pi.getBorrowClientName());
							//贷款合同号
								printInfoSum.setContractID(pi.getContractID());
								Log.print("==========贷款合同号======="+pi.getContractID());
							//放款通知单,暂时LoanNotice中还没有该字段
								printInfoSum.setLoanNoteID(pi.getLoanNoteID());
							//应支付日期
								printInfoSum.setClearInterestDate(pi.getClearInterestDate());
							//贷款本金
								printInfoSum.setAmount(pi.getAmount());
							//合同利率
								printInfoSum.setContractRate(pi.getContractRate());
								
							
							//判断利率调整日期是否在起息日和结息日之间
							if(pi.getAdjustRateDate() != null && pi.getInterestStartDate() != null && pi.getClearInterestDate() != null)
							{
							   Log.print("调整日期："+pi.getAdjustRateDate());
							   Log.print("上次结息日："+pi.getInterestStartDate());
							   Log.print("算息日："+pi.getClearInterestDate());
							   if(pi.getAdjustRateDate().after(pi.getInterestStartDate()) && (pi.getAdjustRateDate().before(pi.getClearInterestDate()) || pi.getAdjustRateDate()== pi.getClearInterestDate()))
							   {//调整日期在起息日和结息日之间
								  Log.print("调整日期在起息日和结息日之间");
								  //执行利率
								   printInfoSum.setExecuteRate(pi.getExecuteRateNew());
							     //贷款利率调整日期
									 Log.print("调整日期:"+pi.getAdjustRateDate());
								  printInfoSum.setAdjustRateDate(pi.getAdjustRateDate());
							      //调整后年息
								  printInfoSum.setExecuteRateNew(pi.getExecuteRateNew());
							   }
							   else if(!pi.getAdjustRateDate().after(pi.getInterestStartDate()))
							   { 
								 //调整日期在起息日之前，或在起息日当日
								 Log.print("调整日期在起息日之前，或在起息日当日");
								 if(pi.getExecuteRateNew()>0 &&(pi.getExecuteRate() != pi.getExecuteRateNew()))
								 {  
									 Log.print("新执行利率大于零");
									 printInfoSum.setExecuteRate(pi.getExecuteRateNew());
									   //贷款利率调整日期
								      printInfoSum.setAdjustRateDate(null);
							          //调整后年息
								      printInfoSum.setExecuteRateNew(0.0);
								 }
								 else
								 {   
									 Log.print("新执行利率等于零");
									 printInfoSum.setExecuteRate(pi.getExecuteRate());
									 //贷款利率调整日期
								     printInfoSum.setAdjustRateDate(null);
							         //调整后年息
								     printInfoSum.setExecuteRateNew(0.0);
								 }
							   }
							}
							else
							{
							   Log.print("没有发生利率调整");
							   if(pi.getExecuteRateNew() == 0)
							   {   
								  //执行利率
								  printInfoSum.setExecuteRate(pi.getExecuteRate()); 
								 
								  //贷款利率调整日期
								  printInfoSum.setAdjustRateDate(null);
							      //调整后年息
								 printInfoSum.setExecuteRateNew(0.0);
							   }
							   else
							   { 
								  //调整后年息
								  printInfoSum.setExecuteRate(pi.getExecuteRateNew());
								  //贷款利率调整日期
								 printInfoSum.setAdjustRateDate(null);
							      //调整后年息
								printInfoSum.setExecuteRateNew(0.0);
							   }
							
							}	
							
								//贷款期限（月）
								printInfoSum.setLoanTerm(pi.getLoanTerm());
								//贷款期限(起始日)
								printInfoSum.setStartDate(tsLoanStartDate);
								
								//贷款期限（到期日）
								printInfoSum.setEndDate(pi.getEndDate());
								
								//盖章日期，即执行日
								printInfoSum.setExecuteDate(pi.getExecuteDate());
								//贷款利息
								printInfoSum.setInterest(dSumInterest);
								//贷款复利
								printInfoSum.setCompoundInterest(dSumCompoundInterest);
								//罚息
								 printInfoSum.setOverDueInterest(dSumOverDueInterest);
								//手续费
								printInfoSum.setCommission(dSumCommission);
								//手续费利率（根据客户要求，将手续费利率取为合同利率）
							 	printInfoSum.setCommissionRate(pi.getCommissionRate());	
								//贷款余额
								printInfoSum.setLoanBalance(dSumLoanBalance);
								//上次结息日
								printInfoSum.setLatestInterestClearDate(pi.getInterestStartDate());
								
								Log.print("＝＝＝＝＝＝＝＝＝上次结息日:"+printInfoSum.getLatestInterestClearDate());
							Log.print("取得汇总结果结束");
							
							if ((strPrintPage.indexOf("1") >= 0))
							{
								if ((strPrintPage.indexOf("1") >= 0) && lShow != 1)
								{
									out.println("<br clear=all style='page-break-before:always'>");
								}
								lShow++;
								//根据贷款账户判断当前打印的是委托还是自营的通知书
								if (strTemp.equals("08")) //自营
								{
									Log.print("～～～～～～～～～～～打印应付自营贷款利息通知单");
									PrintVoucher.PrintLoanInterestNotice(printInfoSum, out); //打印应付贷款利息通知书
								}
								if (strTemp.equals("09")) //委托
								{
									Log.print("～～～～～～～～～～～打印应付委托贷款利息通知单");
									PrintVoucher.PrintConsignLoanInterestAdviceNotice(printInfoSum, out); //打印应付委托贷款利息通知书
								}
									out.println("<br clear=all style='page-break-before:always'>");
								//打印 放款单的明细条目
								PrintVoucher.PrintLoanInterestNoticeDetails(vctLoanNoticeDetails,out);
									Log.print("～～～～～～～～～～～打印放款单的明细条目");
								
							}
							//打印利息通知书 End
					Log.print("打印 合同为合计的 利息通知书 结束 End");
				}// end if nContractID > 0
			}
			//得到以合同汇总的Vector 结束

			Log.print("<br><br>~~~~~~~~~~~~~~~共有利息通知单：" + vctLoanNoticeInfo.size() + "<br><br>");

			IPrintTemplate.showVoucherPrintHead(out, true, "A4", "", 1, sessionMng.m_lOfficeID);

			
			
			
		 }
		else
		{
		 Log.print(" s004-1 key值为空");
		}
	
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, "打印贷款通知书","",1);
		return;
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
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
*/
%>
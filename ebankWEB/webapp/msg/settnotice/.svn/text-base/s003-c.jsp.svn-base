<%--
 页面名称 ：c012.jsp
 页面功能 : 应付利息和费用匡算
 作    者 ：rxie
 日    期 ：2003-11-17
 特殊说明 ：实现操作说明：findByID取得所有信息
			           应付贷款利息通知单
					   逾期催收通知书
					   贷款到期通知书
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
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QLoanNotice"%>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.settlement.print.templateinfo.ShowOverLoanNoticeInfo"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryResultInfo"%>	

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
					
					 for(int i = 0; i < ck.length; i++)
					 {
					 	       int selectValue = Integer.valueOf(ck[i]).intValue();	
						  	   LoanNoticeInfo loanNoticeInfo = new LoanNoticeInfo();
							   loanNoticeInfo=(LoanNoticeInfo)resultVec.elementAt(selectValue);
							   selectVec.addElement(loanNoticeInfo);
					  }//end for
					   if(selectVec != null)
					   {  
						   vctLoanNoticeInfo = selectVec;
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
			
			
			
		    int i = 0;
			Log.print("<br><br>~~~~~~~~~~~~~~~共有利息通知单："+vctLoanNoticeInfo.size()+"<br><br>");
		
		   if(vctLoanNoticeInfo != null)
		   {
			for( i = 0; i < vctLoanNoticeInfo.size(); i++)
			{
				Log.print("~~~~~~~~~~~~~~~打印利息通知单："+i);
				resultInfo = (LoanNoticeInfo)vctLoanNoticeInfo.elementAt(i);
				PrintInfo printInfo = new PrintInfo();
	         
			   //编号（）年
			    if(resultInfo.getFormYear() != null && resultInfo.getFormYear().length() > 0)
				{
				  printInfo.setFormYear(resultInfo.getFormYear());
				}
				Log.print("~~~~~~~~~~编号年："+printInfo.getFormYear());
			  
				 //编号（号）
				 if(resultInfo.getFormNo() != null && resultInfo.getFormNo().length() > 0)
				 {
				 	printInfo.setFormNo(resultInfo.getFormNo());
				 }
				 Log.print("~~~~~~~~~~编号："+printInfo.getFormNo());
				 
				 //催收次数
				 if(resultInfo.getFormNum() > 0)
				 {
				 	printInfo.setFormNum(resultInfo.getFormNum());
				 }
			  
			     //借款人
				 if(resultInfo.getAccountID() > 0)
				 {
				   printInfo.setBorrowClientName(NameRef.getClientNameByAccountID(resultInfo.getAccountID()));
				 }
				 
				Log.print("~~~~~~~~~~~借款人账户id："+resultInfo.getAccountID());
				Log.print("~~~~~~~~~~~借款人名称："+NameRef.getClientNameByAccountID(resultInfo.getAccountID()));
				//贷款合同签订日,根据合同Id查找,无需设置
				
				//贷款合同号
				if(resultInfo.getContractID() > 0)
				{
					printInfo.setContractID(resultInfo.getContractID());
				}
		        Log.print("~~~~~~~~~~~~~~~~贷款合同号:"+printInfo.getContractID());
				
				//放款通知单,暂时LoanNotice中还没有该字段
				
				if(resultInfo.getLoanNoteID() > 0)
				{
					printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
				}
				Log.print("~~~~~~~~~~~~~~~~`放款通知单Id："+printInfo.getLoanNoteID());
				
				//应支付日期
				if(resultInfo.getClearInterestDate() != null)
				{
					printInfo.setClearInterestDate(resultInfo.getClearInterestDate());
				}
				Log.print("~~~~~~~~~~~~~~~~`结息日："+printInfo.getClearInterestDate());
			
			  //贷款利息
				if(resultInfo.getInterest() != 0.0)
			    {
				   printInfo.setInterest(resultInfo.getInterest());
				}
				Log.print("~~~~~~~~~~~~~~~~`贷款利息："+printInfo.getInterest());
				
			  //贷款复利
			   if(resultInfo.getCompoundInterest() != 0.0)
			   {
			   		printInfo.setCompoundInterest(resultInfo.getCompoundInterest());
			   }	
				
			   //手续费
			   if(resultInfo.getCommission() != 0.0)
			   {
			   		printInfo.setCommission(resultInfo.getCommission());
			   }	
			   
			   //手续费利率
			   if(resultInfo.getCommissionRate() != 0.0)
			   {
			   		printInfo.setCommissionRate(resultInfo.getCommissionRate());
			   }
			   
				//贷款本金
				if(resultInfo.getLoanAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getLoanAmount());
				}
				
				//贷款余额
				if(resultInfo.getLoanBalance() != 0.0)
				{
					printInfo.setLoanBalance(resultInfo.getLoanBalance());
				}
				
				//合同利率
				if(resultInfo.getOriginalContractRate() != 0.0)
				{
					printInfo.setContractRate(resultInfo.getOriginalContractRate());
				}
				Log.print("～～～～～～～～～合同利率:"+printInfo.getContractRate());
				
				
				//执行利率
				if(resultInfo.getExecuteRate() != 0.0)
				{
					printInfo.setExecuteRate(resultInfo.getExecuteRate());
				}
				Log.print("～～～～～～～～～调整前执行利率:"+printInfo.getExecuteRate());
				
				//贷款利率调整日期
				if(resultInfo.getExecuteRateValidDate() != null)
				{
					printInfo.setAdjustRateDate(resultInfo.getExecuteRateValidDate());
				}
				
				//调整后年息
			    if(resultInfo.getNewExecuteRate() != 0.0)
				{
				  printInfo.setExecuteRateNew(resultInfo.getNewExecuteRate());
				}
				Log.print("～～～～～～～～～调整后执行利率:"+printInfo.getExecuteRateNew());
				
				if(printInfo.getExecuteRateNew() == printInfo.getExecuteRate())
				{
				Log.print("～～～～～～～～～调整前后执行利率相等~~~~~~~~~~~~~~~~~~~~~~`");
				}
				else
				{
				Log.print("～～～～～～～～～调整前后执行利率不等~~~~~~~~~~~~~~~~~~~~~~~");
				}
			
				//贷款期限（月）
				if(resultInfo.getLoanTerm() > 0)
				{
					printInfo.setLoanTerm(resultInfo.getLoanTerm());
				}
				
				//贷款期限(起始日)
				if(resultInfo.getLoanStartDate() != null)
				{
					printInfo.setStartDate(resultInfo.getLoanStartDate());
					Log.print("~~~~~~~~~~~~~~~~~~起始日期："+printInfo.getStartDate());
				}
				
				//贷款期限（到期日）
			   if(resultInfo.getLoanEndDate() != null)
			   {
			   		printInfo.setEndDate(resultInfo.getLoanEndDate());
					Log.print("~~~~~~~~~~~~~~~~~~结束日期："+printInfo.getEndDate());					
			   }
			
			   //盖章日期，即执行日
			   if(resultInfo.getExecuteDate() != null)
			   {
			  	 printInfo.setExecuteDate(resultInfo.getExecuteDate());	
			   }
			  	//特别设置
			   printInfo.setExecuteDate(DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)));	
			   Log.print("～～～～～～～～～～～执行日期:"+printInfo.getExecuteDate());
			   
			   
			   //担保人
			   if(resultInfo.getAssureClientName() != null && resultInfo.getAssureClientName().length() > 0)
			   {
			     printInfo.setAssureName(resultInfo.getAssureClientName());
			   }
			   
			   //担保合同
			   if(resultInfo.getAssureContractID() > 0)
			   {
			   	printInfo.setAssureContractID(resultInfo.getAssureContractID());
			   }
			    Log.print("~~~~~~~~~~~~~~~~担保合同号:"+printInfo.getAssureContractID());
//待确定的info
//String RemitAddress = "";//汇款路径
//String ReceiveUnit = "";//收款单位
//String ReceiveBank = "";//开户银行

				//根据贷款账户判断当前打印的是委托还是自营的通知书
				  if(resultInfo.getAccountID() > 0)
				  {
				     strTemp = NameRef.getAccountNoByID(resultInfo.getAccountID());
					 if(strTemp.length() > 5)
					 {
							strTemp = strTemp.substring(3,5);
					  }
				  }
				
			   if ((strPrintPage.indexOf("1") >= 0) )
			   {
				 if(i == vctLoanNoticeInfo.size()-1)
				 {
				   strPrintPage = strPrintPage.replace('1','a');
				 }
				 if((strPrintPage.indexOf("1") >= 0)&& lShow != 1)
				  {
				    out.println("<br clear=all style='page-break-before:always'>");
				  }	
				  lShow ++;
				  
				  if(strTemp.equals("08"))//自营
				  {
				     Log.print("～～～～～～～～～～～打印应付自营贷款利息通知单");
					 PrintVoucher.PrintLoanInterestNotice(printInfo,out);//打印应付贷款利息通知书
				  }
				  if(strTemp.equals("09"))//委托
				  {
				  	Log.print("～～～～～～～～～～～打印应付委托贷款利息通知单");
				  	PrintVoucher.PrintConsignLoanInterestAdviceNotice(printInfo,out);//打印应付委托贷款利息通知书
				  }	 
			   }
			
			   
			 if ((strPrintPage.indexOf("3") >= 0) )
			   {
				 if(i == vctLoanNoticeInfo.size()-1)
				 {
				   strPrintPage = strPrintPage.replace('2','a');
				 }
				
				if((strPrintPage.indexOf("3") >= 0)&& lShow != 1)
				 {
				   out.println("<br clear=all style='page-break-before:always'>");
				 }
				   lShow ++;
				
				PrintVoucher.PrintOverLoanNotice(printInfo,out);//打印逾期贷款催收通知书
			   }
			   
			   if ((strPrintPage.indexOf("2") >= 0) )
			   {
				 if(i == vctLoanNoticeInfo.size()-1)
				 {
				   strPrintPage = strPrintPage.replace('3','a');
				 }
				  if((strPrintPage.indexOf("2") >= 0)&& lShow != 1)
				  {
				   out.println("<br clear=all style='page-break-before:always'>");
				  }
				lShow ++;
				
				 if(strTemp.equals("08"))//自营
				  {
				     Log.print("～～～～～～～～～～～打印应付自营贷款到期通知书");
					 PrintVoucher.PrintLoanAtTermNotice(printInfo,out);//打印自营贷款到期通知书
				  }
				  if(strTemp.equals("09"))//委托
				  {
				  	Log.print("～～～～～～～～～～～打印应付委托贷款到期通知书");
				  	PrintVoucher.PrintConsignLoanAtTermNotice(printInfo,out);//打印应付委托贷款到期通知书
				  }	 
			    }
			}//end for	
			if(i == (vctLoanNoticeInfo.size()- 1))
			{
			 %>	
			<SCRIPT language=JavaScript>
				alert("打印完毕！");
				window.close();
			</script>
            <%
			}//end if
			
			IPrintTemplate.showVoucherPrintHead(out,true,"A4","",1,sessionMng.m_lOfficeID);
		}//end if 
		}
		else
		{
		 Log.print("s003 key值为空");
		}
	
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, "打印贷款通知书","",1);
		return;
    }

	request.setAttribute("strActionResult",strActionResult);
%>

<%--
 页面名称 ：l025-c.jsp
 页面功能 : [贷款还款]--利息费用清还--提交贷款清还
 作    者 ：gqzhang
 日    期 ：2004年2月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
				 com.iss.itreasury.settlement.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.OBClientInfo,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[贷款还款]--利息费用清还";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/capital/loanrepayment/l025-c.jsp*******");
	
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		//定义变量
		String strTemp = "";
        long lID = -1;
		long lOfficeID = sessionMng.m_lOfficeID;
		long lClientID = sessionMng.m_lClientID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
		long lTransType = OBConstant.SettInstrType.INTERESTFEEPAYMENT;
		long lPayerAcctID = -1;
		long lPayeeAcctID = -1;
		double dBalance = 0.0;
		Timestamp tsClearInterest = null;
		double dAmount = 0.0;
		Timestamp tsExecuteDate = null;
		String sNote = "";
		long lContractID = -1;
		long lLoanNoteID = -1;
		Timestamp tsPayDate = null; 
		long lConfirmUserID = sessionMng.m_lUserID;
		double dInterest = 0.0; //应付贷款利息
        double dCompoundInterest = 0.0; //应付复利
   		double dOverDueInterest = 0.0; //应付逾期罚息
		double dInterestTax = 0.0;//应付利息税费 
		double dSuretyFee = 0.0;//应付担保费
   		double dCommission = 0.0; //应付手续费
		double dRealInterest = 0.0; //实付贷款利息
	    double dRealCompoundInterest = 0.0; //实付复利
	    double dRealOverDueInterest = 0.0; //实付逾期罚息
		double dRealInterestTax = 0.0;//实付利息税费 
		double dRealSuretyFee = 0.0;//实际支付担保费
	    double dRealCommission = 0.0; //应付手续费
	    double dRealTotal = 0.0; //实付利息和费用之和
		
		long lSubAccountID = -1;//子账户
        
		double dInterestReceiveable = 0.0;//计提利息
		double dInterestIncome = 0.0;//本次利息
		double dRealInterestReceiveable = 0.0;//实付计提利息
		double dRealInterestIncome = 0.0;//实付本次利息
		
		//为打印所加字段
	    Timestamp tsInterestStart = null; //贷款利息起息日
	    double dInterestRate = 0.00; //贷款利息利率	
	 	Timestamp tsCompInterestStart = null; //复利起息日
	 	double dCompRate = 0.00; //复利利率
	 	double dCompoundAmount = 0.00; //复利本金
	 	Timestamp tsOverDueStart = null; //罚息起息日
	 	double dOverDueRate = 0.00; //罚息利率
	 	double dOverDueAmount = 0.00; //应付逾期本金
	 	Timestamp tsSuretyStart = null; //担保费起息日
	 	double dSuretyRate = 0.00; //担保费率
	 	Timestamp tsCommissionStart = null; //手续费起息日
	 	double dCommissionRate = 0.00; //手续费率
        
		long lLoanAcctID = -1; 
		 
		//获取信息
		 
		 strTemp = (String)request.getAttribute("lID");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lID = Long.valueOf(strTemp).longValue();
		 }		
         
		  strTemp = (String)request.getAttribute("lPayerAcctID");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lPayerAcctID = Long.valueOf(strTemp).longValue();
		 }		
         
		 strTemp = (String)request.getAttribute("lContractID");// 贷款合同id
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lContractID = Long.valueOf(strTemp).longValue();
		 }
		 
		  strTemp = (String)request.getAttribute("lLoanNoteID");// 放款通知单id
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lLoanNoteID = Long.valueOf(strTemp).longValue();
		 }
		 
		 strTemp = (String)request.getAttribute("dBalance");//贷款余额
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			dBalance = DataFormat.parseNumber(strTemp);
		 }
		 
		  strTemp = (String)request.getAttribute("lLoanNoteIDCtrl");//贷款放款日期
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			tsPayDate =  DataFormat.getDateTime(strTemp);
		 }
         
		  strTemp = (String)request.getAttribute("lLoanNoteIDAccountID");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lPayeeAcctID = Long.valueOf(strTemp).longValue();
		 }	
		 Log.print("\n\n===============lPayeeAcctID 账户id:"+lPayeeAcctID);
		 Log.print("\n\n===============lPayeeAcctID 账号:"+com.iss.itreasury.settlement.util.NameRef.getAccountNoByID(lPayeeAcctID));
		 
		   strTemp = (String)request.getAttribute("tsClearInterest");//结息日
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			tsClearInterest =  DataFormat.getDateTime(strTemp);
		 }
		 
		  strTemp = (String)request.getAttribute("dAmount");//本金金额
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dAmount = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		  
		  strTemp = (String)request.getAttribute("tsExecuteDate");//执行日
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			tsExecuteDate =  DataFormat.getDateTime(strTemp);
		 }
		 
		 strTemp = (String)request.getAttribute("sNote");// 汇款用途/摘要
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			sNote = strTemp.trim();
		 }
		 
		 strTemp = (String)request.getAttribute("dInterest");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		  strTemp = (String)request.getAttribute("dInterestReceiveable");//计提利息
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			dInterestReceiveable = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			
		 }
		 
		 strTemp = (String)request.getAttribute("dInterestIncome");//本次利息
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dInterestIncome = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		 
		 strTemp = (String)request.getAttribute("dCompoundInterest");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dCompoundInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		  strTemp = (String)request.getAttribute("dOverDueInterest");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dOverDueInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		   strTemp = (String)request.getAttribute("dSuretyFee");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dSuretyFee = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		 strTemp = (String)request.getAttribute("dCommission");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dCommission = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		 
		  strTemp = (String)request.getAttribute("dRealInterest");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dRealInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		 strTemp = (String)request.getAttribute("dRealInterestReceiveable");//实付计提利息
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			dRealInterestReceiveable = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			
		 }
		 
		 strTemp = (String)request.getAttribute("dRealInterestIncome");//实付本次利息
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dRealInterestIncome = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		 
		 strTemp = (String)request.getAttribute("dRealCompoundInterest");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dRealCompoundInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		  strTemp = (String)request.getAttribute("dRealOverDueInterest");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dRealOverDueInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		   strTemp = (String)request.getAttribute("dRealSuretyFee");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dRealSuretyFee = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		 strTemp = (String)request.getAttribute("dRealCommission");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dRealCommission = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		 strTemp = (String)request.getAttribute("dInterestTax");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dInterestTax = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		 strTemp = (String)request.getAttribute("dRealInterestTax");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dRealInterestTax = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		 
		 
		 strTemp = (String)request.getAttribute("lSubAccountID");//子账户ID
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lSubAccountID = Long.valueOf(strTemp).longValue();
		 }
		 
		 	 //为打印所加字段
		   strTemp = (String)request.getAttribute("tsInterestStart");//贷款利息起息日
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			tsInterestStart =  DataFormat.getDateTime(strTemp);
		 }
		
		 strTemp = (String)request.getAttribute("dInterestRate");//贷款利息利率
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dInterestRate = Double.valueOf(strTemp).doubleValue();
		 }
		
		  strTemp = (String)request.getAttribute("tsCompInterestStart");//复利起息日
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			tsCompInterestStart =  DataFormat.getDateTime(strTemp);
		 }
		
		 strTemp = (String)request.getAttribute("dCompRate");//复利利率
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dCompRate = Double.valueOf(strTemp).doubleValue();
		 }
	 	
	 	 strTemp = (String)request.getAttribute("dCompoundAmount");//复利本金
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dCompoundAmount = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		
		
		  strTemp = (String)request.getAttribute("tsOverDueStart");//罚息起息日
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			tsOverDueStart =  DataFormat.getDateTime(strTemp);
		 }
		
		 strTemp = (String)request.getAttribute("dOverDueRate");//罚息利率
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dOverDueRate = Double.valueOf(strTemp).doubleValue();
		 }
	 	
		 strTemp = (String)request.getAttribute("dOverDueAmount");//应付逾期本金
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dOverDueAmount = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		 }
		
	 	
		 strTemp = (String)request.getAttribute("tsSuretyStart");//担保费起息日
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			tsSuretyStart =  DataFormat.getDateTime(strTemp);
		 }
	 	
	 	 strTemp = (String)request.getAttribute("dSuretyRate");//担保费率
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dSuretyRate = Double.valueOf(strTemp).doubleValue();
		 }
		
		 strTemp = (String)request.getAttribute("tsCommissionStart");//手续费起息日
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			tsCommissionStart =  DataFormat.getDateTime(strTemp);
		 }
	 	
		 strTemp = (String)request.getAttribute("dCommissionRate");//手续费率
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			 dCommissionRate = Double.valueOf(strTemp).doubleValue();
		 }
		 
	     strTemp = (String)request.getAttribute("lLoanNoteIDAccountID");//收款方账户ID（即贷款账户id）
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lLoanAcctID = Long.valueOf(strTemp).longValue();
		 }
		 
		FinanceInfo financeInfo = new FinanceInfo();
		financeInfo.setID(lID);
		financeInfo.setOfficeID(lOfficeID);
		financeInfo.setClientID(lClientID);
		financeInfo.setCurrencyID(lCurrencyID);
		financeInfo.setTransType(lTransType);
		financeInfo.setPayerAcctID(lPayerAcctID);
		//financeInfo.setPayeeAcctID(lPayeeAcctID);
		financeInfo.setDepositBalance(dBalance);
		financeInfo.setPayeeAcctID(lLoanAcctID);
		Log.print(":)lLoanAcctID=========:"+lLoanAcctID);
		Log.print(":)lPayeeAcctID=========:"+lPayeeAcctID);
		financeInfo.setClearInterest(tsClearInterest);
		financeInfo.setAmount(dAmount);
		financeInfo.setExecuteDate(tsExecuteDate);
		financeInfo.setNote(sNote);
		financeInfo.setContractID(lContractID);
		financeInfo.setLoanNoteID(lLoanNoteID);
		financeInfo.setPayDate(tsPayDate);
		financeInfo.setConfirmUserID(lConfirmUserID);
		financeInfo.setInterest(dInterest);
        financeInfo.setCompoundInterest(dCompoundInterest);
   		financeInfo.setOverdueInterest(dOverDueInterest);
		financeInfo.setSuretyFee(dSuretyFee);
   		financeInfo.setCommission(dCommission);
		financeInfo.setInterestTax(dInterestTax);
   		financeInfo.setRealInterestTax(dRealInterestTax);
		financeInfo.setRealInterest(dRealInterest);
        financeInfo.setRealCompoundInterest(dRealCompoundInterest);
   		financeInfo.setRealOverdueInterest(dRealOverDueInterest);
		financeInfo.setRealSuretyFee(dRealSuretyFee);
   		financeInfo.setRealCommission(dRealCommission);
		financeInfo.setSubAccountID(lSubAccountID);
		
		financeInfo.setInterestReceiveable(dInterestReceiveable);
		financeInfo.setInterestIncome(dInterestIncome);
		financeInfo.setRealInterestReceiveable(dRealInterestReceiveable);
		financeInfo.setRealInterestIncome(dRealInterestIncome);
		
		//为打印所加字段
		financeInfo.setInterestStart(tsInterestStart);
		financeInfo.setInterestRate(dInterestRate);
		financeInfo.setCompoundStart(tsCompInterestStart);
		financeInfo.setCompoundRate(dCompRate);
		financeInfo.setCompoundAmount(dCompoundAmount);
		financeInfo.setOverDueStart(tsOverDueStart);
		financeInfo.setOverDueRate(dOverDueRate);
		financeInfo.setOverDueAmount(dOverDueAmount);
		financeInfo.setSuretyStart(tsSuretyStart);
		financeInfo.setSuretyRate(dSuretyRate);
		financeInfo.setCommissionStart(tsCommissionStart);
		financeInfo.setCommissionRate(dCommissionRate);
		
		OBFinanceInstrHome  obFinanceInstrHome = null;
        OBFinanceInstr      obFinanceInstr = null;
        obFinanceInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
        obFinanceInstr = obFinanceInstrHome.create();
		
		Log.print("---------------clientid="+financeInfo.getOfficeID()+"---CurrencyID="+ financeInfo.getCurrencyID());
		
		lID = obFinanceInstr.addCapitalTrans(financeInfo);
		if(lID > 0)
		{
			 //进行findByID方法
			 FinanceInfo resultInfo = null;
			 resultInfo = obFinanceInstr.findByID(lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
			 if(resultInfo != null)
			 {
			    request.setAttribute("ShowTag","1");//仅作为标记
				request.setAttribute("resultInfo",resultInfo);
			 }
			
		}
		else
		{
		  Log.print("addCaptialTrans error");
		}
		
	   /* 获取上下文环境 */
       ServletContext sc = getServletContext();
       /* 设置返回地址 */
	  
	  
	  
	  //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/loanrepayment/l026-v.jsp");
	//分发
	RequestDispatcher rd1 = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	  
	  
	   /* forward到结果页面 */
	   rd1.forward(request, response);
%>
<%	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>

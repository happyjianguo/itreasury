<%--
/**
 页面名称 ：a408-c.jsp
 页面功能 : 总账类打印控制页面
 作    者 ：kewen hu
 日    期 ：2004-02-24
 特殊说明 ：实现操作说明：findByID取得所有信息
 			进账单
			特种转账借方凭证
			特种转账待方凭证
			存款支取凭证
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
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransGeneralLedgerDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transgeneralledger.dao.Sett_TransGeneralLedgerDAO"%>
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

        //TransGeneralLedgerDelegation glDelegation = new TransGeneralLedgerDelegation();
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();

		TransGeneralLedgerInfo resultInfo = null;
		
		resultInfo = dao.findByID(lID);
  		
		PrintInfo printInfo1 = new PrintInfo();
		PrintInfo printInfo2 = new PrintInfo();
		PrintInfo printInfo3 = new PrintInfo();
		PrintInfo printInfo4 = new PrintInfo();
				
		if(resultInfo != null)
		{
		Log.print("result not null");
			//set 打印参数
			
			//officeID
			 if(resultInfo.getOfficeID() > 0 )
			{
				printInfo1.setOfficeID(resultInfo.getOfficeID());
				printInfo2.setOfficeID(resultInfo.getOfficeID());
				printInfo3.setOfficeID(resultInfo.getOfficeID());
				printInfo4.setOfficeID(resultInfo.getOfficeID());
			}	
			
			//币种
			if(resultInfo.getCurrencyID() > 0 )
			{
				printInfo1.setCurrencyID(resultInfo.getCurrencyID());
				printInfo2.setCurrencyID(resultInfo.getCurrencyID());
				printInfo3.setCurrencyID(resultInfo.getCurrencyID());
				printInfo4.setCurrencyID(resultInfo.getCurrencyID());
			}
			
			//执行日
			if(resultInfo.getExecuteDate() != null )
			{
				printInfo1.setExecuteDate(resultInfo.getExecuteDate());
				printInfo2.setExecuteDate(resultInfo.getExecuteDate());
				printInfo3.setExecuteDate(resultInfo.getExecuteDate());
				printInfo4.setExecuteDate(resultInfo.getExecuteDate());
			}
			
			//起息日
			if(resultInfo.getInterestStartDate() != null )
			{
				printInfo1.setInterestStartDate(resultInfo.getInterestStartDate());
				printInfo2.setInterestStartDate(resultInfo.getInterestStartDate());
				printInfo3.setInterestStartDate(resultInfo.getInterestStartDate());
				printInfo4.setInterestStartDate(resultInfo.getInterestStartDate());
			}
			
			//交易号
			if(resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
			{
				printInfo1.setTransNo(resultInfo.getTransNo());
				printInfo2.setTransNo(resultInfo.getTransNo());
				printInfo3.setTransNo(resultInfo.getTransNo());
				printInfo4.setTransNo(resultInfo.getTransNo());
			}
			
			//录入人
			if(resultInfo.getInputUserID() > 0 )
			{
				printInfo1.setInputUserID(resultInfo.getInputUserID());
				printInfo2.setInputUserID(resultInfo.getInputUserID());
				printInfo3.setInputUserID(resultInfo.getInputUserID());
				printInfo4.setInputUserID(resultInfo.getInputUserID());
			}
			
			//复核人
			if(resultInfo.getCheckUserID() > 0 )
			{
				printInfo1.setCheckUserID(resultInfo.getCheckUserID());
			    printInfo2.setCheckUserID(resultInfo.getCheckUserID());
				printInfo3.setCheckUserID(resultInfo.getCheckUserID());
				printInfo4.setCheckUserID(resultInfo.getCheckUserID());
			}
			
			//摘要
			if(resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
			{
				printInfo1.setAbstract(resultInfo.getAbstract());
				printInfo2.setAbstract(resultInfo.getAbstract());
				printInfo3.setAbstract(resultInfo.getAbstract());
				printInfo4.setAbstract(resultInfo.getAbstract());
			}
			
			//标准摘要ID
			if(resultInfo.getAbstractID() > 0 )
			{
				printInfo1.setAbstractID(resultInfo.getAbstractID());
				printInfo2.setAbstractID(resultInfo.getAbstractID());
				printInfo3.setAbstractID(resultInfo.getAbstractID());
				printInfo4.setAbstractID(resultInfo.getAbstractID());
			}
			
			//SETTConstant.DebitOrCredit.DEBIT --借
			//SETTConstant.DebitOrCredit.CREDIT --贷
			//仅考虑借-贷/贷-借之间一对多的关系	
			//*****************以下结构待优化
			if(resultInfo.getAmount() > 0)
			{
				if(resultInfo.getDirection() == SETTConstant.DebitOrCredit.DEBIT)//1为借方
				{
					if ((resultInfo.getDirOne() !=SETTConstant.DebitOrCredit.DEBIT) && (resultInfo.getDirTwo() !=SETTConstant.DebitOrCredit.DEBIT)&&(resultInfo.getDirThree() !=SETTConstant.DebitOrCredit.DEBIT))
					{
						//1是唯一的借方
						printInfo1.setPayAccountID(resultInfo.getAccountID());
						printInfo1.setAmount(resultInfo.getAmount());
						Log.print("1");
						
						if(resultInfo.getAmountOne() > 0 && resultInfo.getDirOne() == SETTConstant.DebitOrCredit.CREDIT)
						{
							printInfo2.setPayAccountID(resultInfo.getAccountID());
							printInfo2.setReceiveGL(resultInfo.getGeneralLedgerOne());
							printInfo2.setAmount(resultInfo.getAmountOne());
							
							if((resultInfo.getAmountTwo() <= 0) && (resultInfo.getAmountThree() <= 0))//一借一贷
							{
								printInfo1.setReceiveGL(resultInfo.getGeneralLedgerOne());
							}
						}
							
						if(resultInfo.getAmountTwo() > 0 && resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.CREDIT)		
						{
							printInfo3.setPayAccountID(resultInfo.getAccountID());
							printInfo3.setReceiveGL(resultInfo.getGeneralLedgerTwo());
							printInfo3.setAmount(resultInfo.getAmountTwo());
							
							if((resultInfo.getAmountOne() <= 0) && (resultInfo.getAmountThree() <= 0))//一借一贷
							{
								printInfo1.setReceiveGL(resultInfo.getGeneralLedgerTwo());
							}
						}
						
						if(resultInfo.getAmountThree() > 0 && resultInfo.getDirThree() == SETTConstant.DebitOrCredit.CREDIT)		
						{
							printInfo4.setPayAccountID(resultInfo.getAccountID());
							printInfo4.setReceiveGL(resultInfo.getGeneralLedgerThree());						
							printInfo4.setAmount(resultInfo.getAmountThree());	
							if((resultInfo.getAmountOne() <= 0) && (resultInfo.getAmountTwo() <= 0))//一借一贷
							{
								printInfo1.setReceiveGL(resultInfo.getGeneralLedgerThree());
							}					
						}	
					}
					else//1不是唯一的借方
					{
						
						Log.print("2");
						//查找2，3，4中唯一的贷方
						if (resultInfo.getDirOne() == SETTConstant.DebitOrCredit.CREDIT && (resultInfo.getDirTwo() != SETTConstant.DebitOrCredit.CREDIT) && (resultInfo.getDirThree() != SETTConstant.DebitOrCredit.CREDIT))
						{//2是唯一的贷方
							printInfo1.setPayAccountID(resultInfo.getAccountID());
							printInfo1.setAmount(resultInfo.getAmount());
							printInfo1.setReceiveGL(resultInfo.getGeneralLedgerOne());	
							
							printInfo2.setReceiveGL(resultInfo.getGeneralLedgerOne());
							printInfo2.setAmount(resultInfo.getAmountOne());
							
							if(resultInfo.getAmountTwo() > 0 && resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.DEBIT)		
							{
								printInfo3.setReceiveGL(resultInfo.getGeneralLedgerOne());	
								printInfo3.setPayGL(resultInfo.getGeneralLedgerTwo());	
								printInfo3.setAmount(resultInfo.getAmountTwo());
							}	
						
							if(resultInfo.getAmountThree() > 0 && resultInfo.getDirThree() == SETTConstant.DebitOrCredit.DEBIT)		
							{	
								printInfo4.setReceiveGL(resultInfo.getGeneralLedgerOne());	
								printInfo4.setPayGL(resultInfo.getGeneralLedgerThree());	
								printInfo4.setAmount(resultInfo.getAmountThree());
							}	
						}
						
						if (resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.CREDIT && (resultInfo.getDirOne() != SETTConstant.DebitOrCredit.CREDIT) && resultInfo.getDirThree() != SETTConstant.DebitOrCredit.CREDIT)
						{//3是唯一的贷方
							printInfo1.setPayAccountID(resultInfo.getAccountID());
							printInfo1.setAmount(resultInfo.getAmount());
							printInfo1.setReceiveGL(resultInfo.getGeneralLedgerTwo());	
							
							printInfo3.setReceiveGL(resultInfo.getGeneralLedgerTwo());
							printInfo3.setAmount(resultInfo.getAmountTwo());
							
							if(resultInfo.getAmountOne() > 0 && resultInfo.getDirOne() == SETTConstant.DebitOrCredit.DEBIT)		
							{	
								printInfo2.setReceiveGL(resultInfo.getGeneralLedgerTwo());	
								printInfo2.setPayGL(resultInfo.getGeneralLedgerOne());	
								printInfo2.setAmount(resultInfo.getAmountOne());	
							}	
							
							if(resultInfo.getAmountThree() > 0 && resultInfo.getDirThree() == SETTConstant.DebitOrCredit.DEBIT)		
							{
								printInfo4.setReceiveGL(resultInfo.getGeneralLedgerTwo());
								printInfo4.setPayGL(resultInfo.getGeneralLedgerThree());	
								printInfo4.setAmount(resultInfo.getAmountOne());	
						    }	
						}
						
						if (resultInfo.getDirThree() == SETTConstant.DebitOrCredit.CREDIT && (resultInfo.getDirOne() != SETTConstant.DebitOrCredit.CREDIT) && resultInfo.getDirTwo() != SETTConstant.DebitOrCredit.CREDIT)
						{//4是唯一的贷方
							printInfo1.setReceiveGL(resultInfo.getGeneralLedgerThree());
							printInfo1.setPayAccountID(resultInfo.getAccountID());
  							printInfo1.setAmount(resultInfo.getAmount());
							
							printInfo4.setReceiveGL(resultInfo.getGeneralLedgerThree());
							printInfo4.setAmount(resultInfo.getAmountThree());	
							
							if(resultInfo.getAmountOne() > 0 && resultInfo.getDirOne() == SETTConstant.DebitOrCredit.DEBIT)		
							{
								printInfo2.setReceiveGL(resultInfo.getGeneralLedgerThree());
								printInfo2.setPayGL(resultInfo.getGeneralLedgerOne());	
								printInfo2.setAmount(resultInfo.getAmountOne());	
							}
							
							if(resultInfo.getAmountTwo() > 0 && resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.DEBIT)		
							{							
								printInfo3.setReceiveGL(resultInfo.getGeneralLedgerThree());
								printInfo3.setPayGL(resultInfo.getGeneralLedgerTwo());	
								printInfo3.setAmount(resultInfo.getAmountTwo());	
							}	
						}
					}
				}
				else//1为贷方
				{
					if ((resultInfo.getDirOne() !=SETTConstant.DebitOrCredit.CREDIT) && (resultInfo.getDirTwo() !=SETTConstant.DebitOrCredit.CREDIT)&&(resultInfo.getDirThree() !=SETTConstant.DebitOrCredit.CREDIT))
					{
						//1是唯一的贷方
						printInfo1.setReceiveAccountID(resultInfo.getAccountID());
						printInfo1.setAmount(resultInfo.getAmount());
						
						if(resultInfo.getAmountOne() > 0 && resultInfo.getDirOne() == SETTConstant.DebitOrCredit.DEBIT)
						{	
							printInfo2.setReceiveAccountID(resultInfo.getAccountID());
							printInfo2.setPayGL(resultInfo.getGeneralLedgerOne());
							printInfo2.setAmount(resultInfo.getAmountOne());
							
							if((resultInfo.getAmountTwo() <= 0) && (resultInfo.getAmountThree() <= 0))//一借一贷
							{
								printInfo1.setPayGL(resultInfo.getGeneralLedgerOne());
							}	
						}	
						
						if(resultInfo.getAmountTwo() > 0 && resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.DEBIT)
						{	
							printInfo3.setReceiveAccountID(resultInfo.getAccountID());
							printInfo3.setPayGL(resultInfo.getGeneralLedgerTwo());
							printInfo3.setAmount(resultInfo.getAmountTwo());
							
							if((resultInfo.getAmountOne() <= 0) && (resultInfo.getAmountThree() <= 0))//一借一贷
							{
								printInfo1.setPayGL(resultInfo.getGeneralLedgerTwo());
							}
						}
					
						if(resultInfo.getAmountThree() > 0 && resultInfo.getDirThree() == SETTConstant.DebitOrCredit.DEBIT)
					    {				
							printInfo4.setReceiveAccountID(resultInfo.getAccountID());
							printInfo4.setPayGL(resultInfo.getGeneralLedgerThree());
							printInfo4.setAmount(resultInfo.getAmountThree());
							
							if((resultInfo.getAmountOne() <= 0) && (resultInfo.getAmountTwo() <= 0))//一借一贷
							{
								printInfo1.setPayGL(resultInfo.getGeneralLedgerThree());
							}
						}	
					}
					else//1不是唯一的贷方
					{
						
						//查找2，3，4中唯一的借方
						
						if (resultInfo.getDirOne() == SETTConstant.DebitOrCredit.DEBIT && (resultInfo.getDirTwo() != SETTConstant.DebitOrCredit.DEBIT) && resultInfo.getDirThree() != SETTConstant.DebitOrCredit.DEBIT)
						{//2是唯一的借方
							printInfo1.setReceiveAccountID(resultInfo.getAccountID());
							printInfo1.setAmount(resultInfo.getAmount());
							printInfo1.setPayGL(resultInfo.getGeneralLedgerOne());	
							
							printInfo2.setPayGL(resultInfo.getGeneralLedgerOne());
							printInfo2.setAmount(resultInfo.getAmountOne());
							
							if(resultInfo.getAmountTwo() > 0 && resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.CREDIT)
						    {
								printInfo3.setPayGL(resultInfo.getGeneralLedgerOne());	
								printInfo3.setReceiveGL(resultInfo.getGeneralLedgerTwo());	
								printInfo3.setAmount(resultInfo.getAmountTwo());	
							}
							
							if(resultInfo.getAmountThree() > 0 && resultInfo.getDirThree() == SETTConstant.DebitOrCredit.CREDIT)
						    {
	    						printInfo4.setPayGL(resultInfo.getGeneralLedgerOne());	
								printInfo4.setReceiveGL(resultInfo.getGeneralLedgerThree());	
								printInfo4.setAmount(resultInfo.getAmountThree());	
							}	
						}
						
						if (resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.DEBIT && (resultInfo.getDirOne() != SETTConstant.DebitOrCredit.DEBIT) && resultInfo.getDirThree() != SETTConstant.DebitOrCredit.DEBIT)
						{//3是唯一的借方
							printInfo1.setReceiveAccountID(resultInfo.getAccountID());
							printInfo1.setAmount(resultInfo.getAmount());
							printInfo1.setPayGL(resultInfo.getGeneralLedgerTwo());	
							
							printInfo3.setPayGL(resultInfo.getGeneralLedgerTwo());
							printInfo3.setAmount(resultInfo.getAmountTwo());
							
							if(resultInfo.getAmountOne() > 0 && resultInfo.getDirOne() == SETTConstant.DebitOrCredit.CREDIT)
						    {
								printInfo2.setPayGL(resultInfo.getGeneralLedgerTwo());
								printInfo2.setReceiveGL(resultInfo.getGeneralLedgerOne());
								printInfo2.setAmount(resultInfo.getAmountOne());
							}	
								
							if(resultInfo.getAmountThree() > 0 && resultInfo.getDirThree() == SETTConstant.DebitOrCredit.CREDIT)
						    {	
								printInfo4.setPayGL(resultInfo.getGeneralLedgerTwo());
								printInfo4.setReceiveGL(resultInfo.getGeneralLedgerThree());
								printInfo4.setAmount(resultInfo.getAmountThree());
							}	
						}
						
						if (resultInfo.getDirThree() == SETTConstant.DebitOrCredit.DEBIT && (resultInfo.getDirOne() != SETTConstant.DebitOrCredit.DEBIT) && resultInfo.getDirTwo() != SETTConstant.DebitOrCredit.DEBIT)
						{//4是唯一的借方
							printInfo1.setReceiveAccountID(resultInfo.getAccountID());
							printInfo1.setAmount(resultInfo.getAmount());
							printInfo1.setPayGL(resultInfo.getGeneralLedgerThree());
							
							printInfo4.setPayGL(resultInfo.getGeneralLedgerThree());
							printInfo4.setAmount(resultInfo.getAmountThree());	
							
							if(resultInfo.getAmountOne() > 0 && resultInfo.getDirOne() == SETTConstant.DebitOrCredit.CREDIT)
						    {
								printInfo2.setPayGL(resultInfo.getGeneralLedgerThree());
								printInfo2.setReceiveGL(resultInfo.getGeneralLedgerOne());
								printInfo2.setAmount(resultInfo.getAmountOne());			
							}	
							
							if(resultInfo.getAmountTwo() > 0 && resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.CREDIT)
						    {				
								printInfo3.setPayGL(resultInfo.getGeneralLedgerThree());
								printInfo3.setReceiveGL(resultInfo.getGeneralLedgerTwo());
								printInfo3.setAmount(resultInfo.getAmountTwo());	
							}							
						}	
					}
				}
			
			}
			else
			{//仅有考虑2,3,4的情况
				if(resultInfo.getAmountOne()>0)//2的金额存在
				{
				  if(resultInfo.getDirOne() == SETTConstant.DebitOrCredit.DEBIT)//2为借方
				  {	
				   if ((resultInfo.getDirTwo() !=SETTConstant.DebitOrCredit.DEBIT)&&(resultInfo.getDirThree() !=SETTConstant.DebitOrCredit.DEBIT))
					{//2是唯一的借方
						printInfo2.setPayGL(resultInfo.getGeneralLedgerOne());
						printInfo2.setAmount(resultInfo.getAmountOne());
						
						if(resultInfo.getAmountTwo() > 0 && resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.CREDIT)
						{
							printInfo3.setPayGL(resultInfo.getGeneralLedgerOne());	
							printInfo3.setReceiveGL(resultInfo.getGeneralLedgerTwo());	
							printInfo3.setAmount(resultInfo.getAmountTwo());	
							
							if(resultInfo.getAmountThree() <= 0)//一借一贷
							{
								printInfo2.setReceiveGL(resultInfo.getGeneralLedgerTwo());	
							}							
						}
						
						if(resultInfo.getAmountThree() > 0 && resultInfo.getDirThree() == SETTConstant.DebitOrCredit.CREDIT)
						{
							printInfo4.setPayGL(resultInfo.getGeneralLedgerOne());	
							printInfo4.setReceiveGL(resultInfo.getGeneralLedgerThree());	
							printInfo4.setAmount(resultInfo.getAmountThree());		
							
							if(resultInfo.getAmountTwo() <= 0)//一借一贷
							{
								printInfo2.setReceiveGL(resultInfo.getGeneralLedgerThree());	
							}							
						}
					}
					else//2不是唯一的借方
					{
						printInfo2.setPayGL(resultInfo.getGeneralLedgerOne());
						printInfo2.setAmount(resultInfo.getAmountOne());
						//查找3，4中唯一的贷方
						if ((resultInfo.getDirTwo() ==  SETTConstant.DebitOrCredit.CREDIT) && (resultInfo.getDirThree() !=  SETTConstant.DebitOrCredit.CREDIT))
						{//3是唯一的贷方
							printInfo2.setReceiveGL(resultInfo.getGeneralLedgerTwo());
							printInfo3.setReceiveGL(resultInfo.getGeneralLedgerTwo());
							
							printInfo3.setAmount(resultInfo.getAmountTwo());
							
							if(resultInfo.getAmountThree() > 0 && resultInfo.getDirThree() == SETTConstant.DebitOrCredit.DEBIT)
							{
								printInfo4.setReceiveGL(resultInfo.getGeneralLedgerTwo());
								printInfo4.setPayGL(resultInfo.getGeneralLedgerThree());	
								printInfo4.setAmount(resultInfo.getAmountThree());								
							}
						}
						if ((resultInfo.getDirThree() ==  SETTConstant.DebitOrCredit.CREDIT) && (resultInfo.getDirTwo() !=  SETTConstant.DebitOrCredit.CREDIT))
						{//4是唯一的贷方
						    printInfo2.setReceiveGL(resultInfo.getGeneralLedgerThree());
							
							printInfo4.setReceiveGL(resultInfo.getGeneralLedgerThree());
							printInfo4.setAmount(resultInfo.getAmountThree());
							
							if(resultInfo.getAmountTwo() > 0 && resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.DEBIT)
							{
								printInfo3.setReceiveGL(resultInfo.getGeneralLedgerThree());
								printInfo3.setPayGL(resultInfo.getGeneralLedgerTwo());	
								printInfo3.setAmount(resultInfo.getAmountTwo());								
							}
							
						}
					}
				  }	
				  else//2为贷方
				  {
				  	 if ((resultInfo.getDirTwo() != SETTConstant.DebitOrCredit.CREDIT)&&(resultInfo.getDirThree() != SETTConstant.DebitOrCredit.CREDIT))
					{//2是唯一的贷方
						printInfo2.setReceiveGL(resultInfo.getGeneralLedgerOne());
						printInfo2.setAmount(resultInfo.getAmountOne());
						
						if(resultInfo.getAmountTwo() > 0 && resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.DEBIT)
						{
							printInfo3.setReceiveGL(resultInfo.getGeneralLedgerOne());
							printInfo3.setAmount(resultInfo.getAmountTwo());
							printInfo3.setPayGL(resultInfo.getGeneralLedgerTwo());
							
							if(resultInfo.getAmountThree() <= 0)//一借一贷
							{
							   printInfo2.setPayGL(resultInfo.getGeneralLedgerTwo());
							}
						}	
							
						if(resultInfo.getAmountThree() > 0 && resultInfo.getDirThree() == SETTConstant.DebitOrCredit.DEBIT)
						{
							printInfo4.setReceiveGL(resultInfo.getGeneralLedgerOne());
							printInfo4.setPayGL(resultInfo.getGeneralLedgerThree());	
							printInfo4.setAmount(resultInfo.getAmountThree());	
							
							if(resultInfo.getAmountTwo() <= 0)//一借一贷
							{
							   printInfo2.setPayGL(resultInfo.getGeneralLedgerThree());
							}							
						}
						
					}
					else//2不是唯一的贷方
					{
						//查找3，4中唯一的借方
						if((resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.DEBIT)&&(resultInfo.getDirThree() != SETTConstant.DebitOrCredit.DEBIT))
						{//3是唯一的借方
						   printInfo2.setPayGL(resultInfo.getGeneralLedgerTwo());
						   printInfo2.setAmount(resultInfo.getAmountOne());
						   printInfo2.setReceiveGL(resultInfo.getGeneralLedgerOne());
						   
						   printInfo3.setPayGL(resultInfo.getGeneralLedgerTwo());
						   printInfo3.setAmount(resultInfo.getGeneralLedgerTwo());
						   
						  if(resultInfo.getAmountThree() > 0 && resultInfo.getDirThree() == SETTConstant.DebitOrCredit.CREDIT)
						  {
						  	   printInfo4.setPayGL(resultInfo.getGeneralLedgerTwo());
							   printInfo4.setReceiveGL(resultInfo.getGeneralLedgerThree());
							   printInfo4.setAmount(resultInfo.getAmountThree());
						   }	   
						}
						
						if((resultInfo.getDirThree() == SETTConstant.DebitOrCredit.DEBIT)&&(resultInfo.getDirTwo() != SETTConstant.DebitOrCredit.DEBIT))
						{//4是唯一的借方
						   printInfo2.setPayGL(resultInfo.getGeneralLedgerThree());
						   printInfo2.setAmount(resultInfo.getAmountOne());
						   printInfo2.setReceiveGL(resultInfo.getGeneralLedgerOne());
						   
						   if(resultInfo.getAmountTwo() > 0 && resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.CREDIT)
						   {
							   printInfo3.setPayGL(resultInfo.getGeneralLedgerThree());
							   printInfo3.setReceiveGL(resultInfo.getGeneralLedgerTwo());
							   printInfo3.setAmount(resultInfo.getGeneralLedgerTwo());
						   }
						   
						   printInfo4.setPayGL(resultInfo.getGeneralLedgerThree());
						   printInfo4.setAmount(resultInfo.getAmountThree());
						}
					}
				  }
				}
				else
				{
					//仅考虑3，4的借和贷情况
					if(resultInfo.getDirTwo() == SETTConstant.DebitOrCredit.DEBIT)//3为借方
					{
					Log.print("仅存在3,4!");
					Log.print("3为借方");
					 //设置3.4收付信息
					 printInfo3.setPayGL(resultInfo.getGeneralLedgerTwo());
					 printInfo3.setReceiveGL(resultInfo.getGeneralLedgerThree());
					 printInfo3.setAmount(resultInfo.getAmountTwo());
					 printInfo4.setPayGL(resultInfo.getGeneralLedgerTwo());
					 printInfo4.setAmount(resultInfo.getAmountThree());
					 printInfo4.setReceiveGL(resultInfo.getGeneralLedgerThree());
					 
					}
					else//如果3为贷方
					{
					Log.print("3为贷方");
					//设置3.4的收付信息
					 printInfo3.setPayGL(resultInfo.getGeneralLedgerThree());
					 printInfo3.setReceiveGL(resultInfo.getGeneralLedgerTwo());
					 printInfo3.setAmount(resultInfo.getAmountTwo());
					 printInfo4.setPayGL(resultInfo.getGeneralLedgerThree());
					 printInfo4.setReceiveGL(resultInfo.getGeneralLedgerTwo());
					 printInfo4.setAmount(resultInfo.getAmountThree());
					}
				}
			}
			
			
			//set打印参数End
			
			if ((strPrintPage.indexOf("b") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('b','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo1,out);//打印进账单1
			}
			if ((strPrintPage.indexOf("e") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('e','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo1,out);//打印特转借1
			}
			if ((strPrintPage.indexOf("c") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('c','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo1,out);//打印特转贷1
			}
			if ((strPrintPage.indexOf("d") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('d','a');
				lShow = 1;
				PrintVoucher.PrintShowWithDraw(printInfo1,out);//打印存款支取凭证1
			}
			
			if ((strPrintPage.indexOf("f") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('f','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo2,out);//打印进账单2
			}
			if ((strPrintPage.indexOf("i") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('i','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo2,out);//打印特转借2
			}
			if ((strPrintPage.indexOf("g") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('g','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo2,out);//打印特转贷2
			}
			if ((strPrintPage.indexOf("h") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('h','a');
				lShow = 1;
				PrintVoucher.PrintShowWithDraw(printInfo2,out);//打印存款支取凭证2
			}
			
			if ((strPrintPage.indexOf("j") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('j','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo3,out);//打印进账单3
			}
			if ((strPrintPage.indexOf("m") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('m','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo3,out);//打印特转借3
			}
			if ((strPrintPage.indexOf("k") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('k','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo3,out);//打印特转贷3
			}
			if ((strPrintPage.indexOf("l") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('l','a');
				lShow = 1;
				PrintVoucher.PrintShowWithDraw(printInfo3,out);//打印存款支取凭证3
			}
			if ((strPrintPage.indexOf("n") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('n','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo4,out);//打印进账单4
			}
			if ((strPrintPage.indexOf("y") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('y','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo4,out);//打印特转借4
			}
			if ((strPrintPage.indexOf("o") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('o','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo4,out);//打印特转贷4
			}
			if ((strPrintPage.indexOf("x") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('x','a');
				lShow = 1;
				PrintVoucher.PrintShowWithDraw(printInfo4,out);//打印存款支取凭证4
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
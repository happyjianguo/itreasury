<%--
 页面名称 ：a913-c.jsp
 页面功能 : （信托/委托）贷款发放处理控制页面
 作    者 ：qqgd
 日    期 ：
 特殊说明 ：实现操作说明：
				1、新增暂存
				2、修改暂存
				3、新增保存
				4、修改保存
				5、删除
				6、继续
				7、保存前检查
 修改历史 ：
--%>


<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBCheckTransDirectionDao"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//页面控制变量
    String strNextPageURL 			= "";								//跳转到URL
	String strSuccessPageURL 		= "";								//操作成功后跳转到URL
	String strFailPageURL 			= "";								//操作失败后跳转到URL
	String strAction 				= null;								//操作代码
	String strActionResult 			= Constant.ActionResult.FAIL;		//操作结果代码
	String strToPrint 				= "";								//是否打印提示信息
	
	//TransLoanDelegation loanDelegation = new TransLoanDelegation();		//定义delegation
	Sett_TransGrantLoanDAO loanDelegation=new Sett_TransGrantLoanDAO();
	
	long lID 						= -1;								//ID
    try
	{
		
		//请求检测
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

		
		//页面辅助变量
		String strContinueSave 				= null;
		String strExecuteDate 				= null;
		String strInterestStartDate 		= null;
		String strModifyTime 				= null;
		
        //定义变量
		long lOfficeID = sessionMng.m_lOfficeID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
		long lInputUserID = sessionMng.m_lUserID;
		String strTransNo = "";
		long lTransactionTypeID = -1;//交易类型
		
		//---------------------信托/委托公用
		long lLoanClientID = -1;//贷款客户ID
		long lLoanAccountID = -1;//贷款账户ID
		long lLoanContractID = -1;//贷款合同ID
		long lLoanPayFormID = -1;//放款通知单ID
		long lPayInterestAccountID = -1;//信托/委托-结息账户		

		//---------------------信托/委托公用
		
		//------------------信托专用
		
		boolean blnIsKeepAccount	= false;		//是否后补记账处理
		long lPaySuretyFeeAccountID = -1;			//信托-贷款方账号
		long lReceiveSuretyFeeAccountID = -1;		//信托-担保方账号
		
		//------------------信托专用
		
		
		
		//------------------委托专用
		
		long lReceiveInterestAccountID 	= -1; 		//委托方收息账户
		long lCommisionPayMode			= -1;		//手续费收取方式
		long lCommissionAccountID 		= -1;    	//支付手续费账号
		double dInterestRateID			= -1;		//利率
		Timestamp tsValidDate			= null;		//有效日期
		
		long lCashFlow					= -1;  		//现金流向
		long lConsignDepositClientAccountID		= -1;		//委托存款账号
		
		//------------------委托专用
		
		
		//---------------------信托/委托公用
		
		long lDepositAccountID = -1;//贷款发放至活期账户
		
		long lPayTypeID = -1;//付款方式
		long lBankID = -1;//汇出行
		String strExtAcctNo = "";//汇入收款单位账号
		String strExtAcctName = "";//汇入收款单位户名
		String strProvince = "";//汇入地(省)
		String strCity = "";//汇入地(市)
		String strBankName = "";//汇入行
		String strExtBankNo = null;//提入行
		
		long lAbstractID = -1;//摘要ID
		String strAbstract = "";//摘要描述
		
		double dAmount = 0.0;//金额
		java.sql.Timestamp tsExecute = null;//执行日
		java.sql.Timestamp tsInputDate = null;//录入日期
		java.sql.Timestamp tsInterestStart = null;//起息日
		java.sql.Timestamp tsModify = null;//访问日期
		long lStatusID = -1;//交易状态
		
		//---------------------信托/委托公用
		
        //--页面控制参数
        strAction = (String)request.getAttribute("strAction");					
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");	
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		strContinueSave = (String)request.getAttribute("strContinueSave");
		//--页面控制参数
		
		String strTemp = null;
		strTemp = (String)request.getAttribute("lLoanClientID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lLoanClientID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lLoanAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lLoanAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lLoanContractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lLoanContractID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lLoanPayFormID");		//放款通知单
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lLoanPayFormID = Long.valueOf(strTemp).longValue();
		}

		strTemp = (String)request.getAttribute("blnIsKeepAccount");		//是否后补记账处理
		if (strTemp !=null && strTemp.trim().equals("true")){
			blnIsKeepAccount = true;
		}
		
		strTemp = (String)request.getAttribute("lAbstractIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("lAbstractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAbstractID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("dAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dAmount = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		}
		strTemp = (String)request.getAttribute("tsExecute");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsExecute = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("tsInputDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsInputDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("tsInterestStart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsInterestStart = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("tsModify");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsModify = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lTransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lTransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lStatusID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strTransNo = strTemp;
		}
		
		strTemp = (String)request.getAttribute("strExtBankNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strExtBankNo = strTemp.trim();
		}
		
		double dRate = 0.0;//利率
		strTemp = (String)request.getAttribute("txtRate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dRate = Double.valueOf(strTemp).doubleValue();
		}
		if (dRate > 0.0)
		{
			//利率大于零
			if (lTransactionTypeID==SETTConstant.TransactionType.CONSIGNLOANGRANT){		//如果是委托
				strTemp=(String)request.getAttribute("chkPayInterestAccount");
				if (strTemp!=null && strTemp.equals("1")){
					strTemp = (String)request.getAttribute("lPayInterestAccountID");
					if (strTemp != null && strTemp.trim().length() > 0)
					{
					    lPayInterestAccountID = Long.valueOf(strTemp).longValue();
					}
				}
				
			}
			else if (lTransactionTypeID==SETTConstant.TransactionType.TRUSTLOANGRANT){	//如果是信托
				strTemp = (String)request.getAttribute("lPayInterestAccountID");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
				    lPayInterestAccountID = Long.valueOf(strTemp).longValue();
				}
			}
			
			
			strTemp=(String)request.getAttribute("chkReceiveInterestAccount");				//委托
			if (strTemp!=null && strTemp.equals("1")){		//选中了委托方收息账户
				strTemp = (String)request.getAttribute("lReceiveInterestAccountID");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					lReceiveInterestAccountID = Long.valueOf(strTemp).longValue();
				}
			}
			
		}
		
		double dSuretyRate = 0.0;//担保费
		strTemp = (String)request.getAttribute("txtSuretyRate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dSuretyRate = Double.valueOf(strTemp).doubleValue();
		}
		if (dSuretyRate > 0.0)
		{
			//担保费大于零
			strTemp = (String)request.getAttribute("lDebtorAccountID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lPaySuretyFeeAccountID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lSuretyAccountID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lReceiveSuretyFeeAccountID = Long.valueOf(strTemp).longValue();
			}
		}
		
		String strRadioType = "";
		strTemp = (String)request.getAttribute("radio1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strRadioType = strTemp;
		}
		if (strRadioType.equals("1"))
		{
			//活期账户方式
			strTemp = (String)request.getAttribute("lDepositAccountID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lDepositAccountID = Long.valueOf(strTemp).longValue();
			}
		}
		else if (strRadioType.equals("2"))
		{
			//银行付款方式
			strTemp = (String)request.getAttribute("lPayTypeID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lPayTypeID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lRemitOutBranchID");

			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lBankID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lExtReceiveAccountID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strExtAcctNo = strTemp;
			}
			strTemp = (String)request.getAttribute("strExtAcctName");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strExtAcctName = strTemp;
			}
			strTemp = (String)request.getAttribute("strProvince");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strProvince = strTemp;
			}
			strTemp = (String)request.getAttribute("strCity");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strCity = strTemp;
			}
			strTemp = (String)request.getAttribute("lRemitInBankIDCtrl");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strBankName = strTemp;
			}
		}
		
		
		//-----------------委托专用

		
		strTemp=(String)request.getAttribute("chkCommissionAccountID");
		if (strTemp!=null && strTemp.equals("1")){		//选中了支付手续费方账户
			strTemp = (String)request.getAttribute("lCommissionAccountID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lCommissionAccountID = Long.valueOf(strTemp).longValue();   
			}
		}
		
		
		strTemp=(String)request.getAttribute("chkInterestRateID");
		if (strTemp!=null && strTemp.equals("1")){		//选中了利息税费率
			strTemp = (String)request.getAttribute("lInterestRateIDCtrl");		//取得利息税费率
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dInterestRateID = Double.valueOf(strTemp).doubleValue();
			}
			strTemp = (String)request.getAttribute("tsValidDate");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    tsValidDate = DataFormat.getDateTime(strTemp);
			}
		}
		
		
		strTemp = (String)request.getAttribute("lCashFlow");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lCashFlow = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lConsignDepositClientAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lConsignDepositClientAccountID = Long.valueOf(strTemp).longValue();
		}
		//-----------------委托专用
		
		
		/*
	
		strTemp = (String)request.getAttribute("dInterestTaxRate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dInterestTaxRate = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("tsInterestTaxRateVauleDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsInterestTaxRateVauleDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("KeepAccount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    KeepAccount = 
		}
		strTemp = (String)request.getAttribute("lPayCommisionAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lPayCommisionAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lReceiveInterestAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lReceiveInterestAccountID = Long.valueOf(strTemp).longValue();
		}

		strTemp = (String)request.getAttribute("lConsignDepositAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lConsignDepositAccountID = Long.valueOf(strTemp).longValue();
		}
		*/

		//向实体类中设置数据
        TransGrantLoanInfo info = new TransGrantLoanInfo();
        
		info.setLoanAccountID(lLoanAccountID);				//贷款账户
		info.setLoanContractID(lLoanContractID);			//合同号
		info.setLoanNoteID(lLoanPayFormID);					//放款通知单号
		info.setKeepAccount(blnIsKeepAccount);				//是否后补记账处理
		info.setID(lID);									//ID
		info.setTransactionTypeID(lTransactionTypeID);		//交易类型
		info.setTransNo(strTransNo);						//交易号
		info.setAbstract(strAbstract);						//摘要
		info.setAbstractID(lAbstractID);					//摘要ID
		info.setAmount(dAmount);							//金额
		info.setBankID(lBankID);							//银行代码
		info.setCity(strCity);								//城市代码
		info.setCurrencyID(lCurrencyID);					//币种
		info.setExecute(tsExecute);							//执行日
		info.setExtAcctName(strExtAcctName);				//外部客户名称
		info.setExtAcctNo(strExtAcctNo);					//外部账号
		info.setBankName(strBankName);						//外部汇入行名称
		info.setInputDate(tsInputDate);						//录入时间
		info.setInputUserID(lInputUserID);					//录入人ID
		info.setInterestStart(tsInterestStart);				//起息日
		info.setOfficeID(lOfficeID);						//办事处ID
		info.setPayTypeID(lPayTypeID);						//付款方式
		info.setProvince(strProvince);						//省份
		info.setDepositAccountID(lDepositAccountID);		//活期账号
		info.setPayInterestAccountID(lPayInterestAccountID);//付息账号
		info.setPaySuretyFeeAccountID(lPaySuretyFeeAccountID);//负担保费账号
		info.setReceiveSuretyFeeAccountID(lReceiveSuretyFeeAccountID);//收担保费账号
		info.setModify(tsModify);							//更改日期
		info.setStatusID(lStatusID);						//交易状态
		info.setExtBankNo(strExtBankNo);					//提入行

		info.setReceiveInterestAccountID(lReceiveInterestAccountID);			//收息账号
		info.setPayCommisionAccountID(lCommissionAccountID);					//付手续费账号
		info.setInterestTaxRate(dInterestRateID);								//利息税费率
		Log.print("利息税费率:"+dInterestRateID);
		info.setInterestTaxRateVauleDate(tsValidDate);							//利息税费率生效时间
		info.setConsignDepositAccountID(lConsignDepositClientAccountID);		//委托存款账号
		
		/*
		info.setInterestTaxRate(dInterestTaxRate);
		info.setInterestTaxRateVauleDate(tsInterestTaxRateVauleDate);
		info.setKeepAccount(KeepAccount);
		info.setPayCommisionAccountID(lPayCommisionAccountID);
		info.setReceiveInterestAccountID(lReceiveInterestAccountID);
		info.setConsignDepositAccountID(lConsignDepositAccountID);
		*/
		
		/*----------------为了判断收付款方而设-----------*/
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();
		long lOBReturn = obdao.CheckAccountID(strTransNo, "", sessionMng.m_lClientID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lOBReturn));
		/*--------end of qqgd adding fragment---------------*/		
        
        //根据请求操作，完成业务处理的调用
        if("Query".equals(strAction))
        {
			//strTransNo
			
			if (strTransNo != null && !strTransNo.equals(""))
			{
				//lID = loanDelegation.grantGetIDByTransNo(strTransNo);
				lID=loanDelegation.getIDByTransNo(strTransNo);
			}
			Log.print("明细strTransNo == "+strTransNo);
			Log.print("明细ID == "+lID);
			
			TransGrantLoanInfo resultinfo = null;
			//resultinfo = loanDelegation.grantFindDetailByID(lID);  //Sett_TransGrantLoanDAO dao.findByID(id);
			resultinfo=loanDelegation.findByID(lID);
			if (resultinfo != null)
			{
	            LoanPayFormDetailInfo payforminfo = new LoanPayFormDetailInfo();
				
				payforminfo.setLoadNoteID(resultinfo.getLoanNoteID());
				Log.print("lLoanPayFormID = "+payforminfo.getLoadNoteID());
				
				//payforminfo = loanDelegation.grantNext(payforminfo);//Sett_TransGrantLoanDAO dao.findPayFormDetailByCondition(info);
				payforminfo=loanDelegation.findPayFormDetailByCondition(payforminfo);
				
				if(payforminfo != null )
	            {
	                strActionResult = Constant.ActionResult.SUCCESS;
					request.setAttribute("LoanPayFormDetailInfo",payforminfo);
					request.setAttribute("TransGrantLoanInfo",resultinfo);
				}
			}
		}
        else if(String.valueOf(SETTConstant.Actions.NEXTSTEP).equals(strAction))
        {
			Log.print("操作——继续");
            LoanPayFormDetailInfo payforminfo = new LoanPayFormDetailInfo();
			payforminfo.setLoadNoteID(lLoanPayFormID);
			Log.print("lLoanPayFormID = "+lLoanPayFormID);
			Log.print("lLoanLoadNoteID = "+payforminfo.getLoadNoteID());
			//payforminfo = loanDelegation.grantNext(payforminfo);
			payforminfo=loanDelegation.findPayFormDetailByCondition(payforminfo);
			if(payforminfo != null )
            {
                strActionResult = Constant.ActionResult.SUCCESS;
				request.setAttribute("LoanPayFormDetailInfo",payforminfo);
				request.setAttribute("TransGrantLoanInfo",info);
            }
			else
			{
				strActionResult = Constant.ActionResult.FAIL;
				//sessionMng.getActionMessages().addMessage("未找到该放款通知单对应的纪录");
			}
        }
        else if(String.valueOf(SETTConstant.Actions.CREATETEMPSAVE).equals(strAction))
        {
			Log.print("操作——新增暂存");
			long lTempsaveResult = 1;	//loanDelegation.grantTempSave(info);
			if(lTempsaveResult > 0 )
            {
            	request.setAttribute("lID",""+lTempsaveResult);
                strActionResult = Constant.ActionResult.SUCCESS;
                //strToPrint = "暂存成功,是否打印?";
                //sessionMng.getActionMessages().addMessage("暂存成功");
            }
        }
        else if(String.valueOf(SETTConstant.Actions.CREATESAVE).equals(strAction))
        {
			Log.print("操作——新增保存");
			long lSaveResult = 1;	//loanDelegation.grantSave(info);
			if(lSaveResult > 0)
			{
				request.setAttribute("lID",""+lSaveResult);
				strActionResult = Constant.ActionResult.SUCCESS;
				//sessionMng.getActionMessages().addMessage("保存成功");
				//request.setAttribute("CreateSave","Success");
				//strToPrint = "保存成功,是否打印?";
			}
        }
		else if(String.valueOf(SETTConstant.Actions.MODIFYTEMPSAVE).equals(strAction))
        {
			Log.print("操作——修改暂存");
			long lTempsaveResult =1;	// loanDelegation.grantTempSave(info);
            if(lTempsaveResult > 0 )
            {
            	request.setAttribute("lID",""+lTempsaveResult);
            	//strToPrint = "修改暂存成功,是否打印?";
                strActionResult = Constant.ActionResult.SUCCESS;
                //sessionMng.getActionMessages().addMessage("修改暂存成功");
				strSuccessPageURL = "../control/c002.jsp";
            }
        }
		else if(String.valueOf(SETTConstant.Actions.MODIFYSAVE).equals(strAction))
        {
			Log.print("操作——修改保存");
			long lSaveResult = 1;	//loanDelegation.grantSave(info);
            if(lSaveResult > 0 )
            {
            	request.setAttribute("lID",""+lSaveResult);
            	//strToPrint = "修改保存成功,是否打印?";
                strActionResult = Constant.ActionResult.SUCCESS;
				//sessionMng.getActionMessages().addMessage("修改保存成功");
				strSuccessPageURL = "../control/c002.jsp";
            }
        }
        else if(String.valueOf(SETTConstant.Actions.DELETE).equals(strAction))
        {
			Log.print("操作——删除");
			
			long lDeleteResult = 1;	//loanDelegation.grantDelete(info);
            if(lDeleteResult > 0)
            {
                strActionResult = Constant.ActionResult.SUCCESS;
                //sessionMng.getActionMessages().addMessage("删除成功");
				strSuccessPageURL = "../control/c002.jsp";
            }
        }
		else if(String.valueOf(SETTConstant.Actions.TODETAIL).equals(strAction))
		{
			Log.print("操作——进入修改页面明细");
			
			TransGrantLoanInfo resultinfo = null;
			Log.print("明细ID == "+lID);
			//resultinfo = loanDelegation.grantFindDetailByID(lID);
			resultinfo=loanDelegation.findByID(lID);
			if (resultinfo != null)
			{
	            LoanPayFormDetailInfo payforminfo = new LoanPayFormDetailInfo();
				
				payforminfo.setLoadNoteID(resultinfo.getLoanNoteID());
				Log.print("lLoanPayFormID = "+payforminfo.getLoadNoteID());
				
				//payforminfo = loanDelegation.grantNext(payforminfo);
				payforminfo=loanDelegation.findPayFormDetailByCondition(payforminfo);
				if(payforminfo != null )
	            {
	                strActionResult = Constant.ActionResult.SUCCESS;
					request.setAttribute("LoanPayFormDetailInfo",payforminfo);
					request.setAttribute("TransGrantLoanInfo",resultinfo);
				}
			}
		}
        else
        {
            Log.print("无效操作");
        }
	}
	catch( Exception exp )
	{
		Log.print("出现异常");
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;
		
		try
		{
			if (String.valueOf(SETTConstant.Actions.CREATESAVE).equals(strAction) ||
				String.valueOf(SETTConstant.Actions.CREATETEMPSAVE).equals(strAction) ||
				String.valueOf(SETTConstant.Actions.MODIFYTEMPSAVE).equals(strAction) ||
				String.valueOf(SETTConstant.Actions.MODIFYSAVE).equals(strAction) ||
				String.valueOf(SETTConstant.Actions.DELETE).equals(strAction) )
			{
			Log.print("获得子账户信息");
				TransGrantLoanInfo resultinfo = null;
				Log.print("lID == "+lID);
				//resultinfo = loanDelegation.grantFindDetailByID(lID);
				resultinfo=loanDelegation.findByID(lID);
				if (resultinfo != null)
				{
		            LoanPayFormDetailInfo payforminfo = new LoanPayFormDetailInfo();
					
					payforminfo.setLoadNoteID(resultinfo.getLoanNoteID());
					Log.print("lLoanPayFormID = "+payforminfo.getLoadNoteID());
					
					//payforminfo = loanDelegation.grantNext(payforminfo);
					payforminfo=loanDelegation.findPayFormDetailByCondition(payforminfo);
					if(payforminfo != null )
		            {
						request.setAttribute("LoanPayFormDetailInfo",payforminfo);
						request.setAttribute("TransGrantLoanInfo",resultinfo);
					}
				}
			}
		}
		catch (Exception e)
		{
			Log.print("异常中，出现异常");
			e.printStackTrace();
			//sessionMng.getActionMessages().addMessage(e);
			strActionResult = Constant.ActionResult.FAIL;
		}
	}

	request.setAttribute("strActionResult",strActionResult);
	request.setAttribute("CreateSave",strToPrint);				//保存打印信息
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
%>
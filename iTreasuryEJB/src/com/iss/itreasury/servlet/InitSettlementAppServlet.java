//Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
//Decompiler options: packimports(3) 
//Source File Name:   TestDBServlet.java
package com.iss.itreasury.servlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iss.itreasury.bankportal.integration.client.BPClientAgent;
import com.iss.itreasury.fcinterface.bizlogic.SapInstructionHandle;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceHome;
import com.iss.itreasury.securities.securitiesgeneralledger.bizlogic.SecuritiesGeneralLedgerHome;
import com.iss.itreasury.settlement.account.bizlogic.AccountHome;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookHome;
import com.iss.itreasury.settlement.autotask.BudgetAutoTask;
import com.iss.itreasury.settlement.bankbill.bizlogic.BankBillHome;
import com.iss.itreasury.settlement.consignvoucher.bizlogic.ConsignVoucherHome;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerHome;
import com.iss.itreasury.settlement.integration.service.bankservice.SETTBankServiceImp;
import com.iss.itreasury.settlement.interest.bizlogic.AutoInterestSettlementTask;
import com.iss.itreasury.settlement.interest.bizlogic.CompoundInterestSettingHome;
import com.iss.itreasury.settlement.interest.bizlogic.InterestFeeSetting;
import com.iss.itreasury.settlement.offbalance.bizlogic.OffBalanceHome;
import com.iss.itreasury.settlement.offbalanceregister.bizlogic.OffBalanceRegisterHome;
import com.iss.itreasury.settlement.remind.process.RemindTask;
import com.iss.itreasury.settlement.settadjustinterestrate.bizlogic.SettAdjustInterestRateHome;
import com.iss.itreasury.settlement.settcontract.bizlogic.SettContractHome;
import com.iss.itreasury.settlement.settpaynotice.bizlogic.SettPayNoticeHome;
import com.iss.itreasury.settlement.transcurrentdeposit.bizlogic.TransCurrentDepositHome;
import com.iss.itreasury.settlement.transdiscount.bizlogic.TransDiscountHome;
import com.iss.itreasury.settlement.transfee.bizlogic.TransFeeHome;
import com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDepositHome;
import com.iss.itreasury.settlement.transgeneralledger.bizlogic.TransGeneralLedgerHome;
import com.iss.itreasury.settlement.transinterest.bizlogic.TransInterestHome;
import com.iss.itreasury.settlement.transinternallend.bizlogic.TransInternalLendHome;
import com.iss.itreasury.settlement.transloan.bizlogic.TransLoanHome;
import com.iss.itreasury.settlement.transmargindeposit.bizlogic.TransMarginDepositHome;
import com.iss.itreasury.settlement.transsecurities.bizlogic.TransSecuritiesHome;
import com.iss.itreasury.settlement.transspecial.bizlogic.TransSpecialHome;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Scheduler;

public class InitSettlementAppServlet extends HttpServlet
{
	Log4j logger = new Log4j(Constant.ModuleType.SETTLEMENT);

	public InitSettlementAppServlet()
	{
	}
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		getResourceJNDIName();
		lookupJNDIName();
		initBankServicePackage();
		initAutoRunTask();
		initSapService();
	}
	public void destroy()
	{
	}
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		System.out.println("Application Context Path : " + request.getContextPath());
		File file = new File("/");
		System.out.println("root path: " + file.getPath());
		file.mkdirs();
		lookupJNDIName();
		////////////////////////////////////
		out.close();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}
	public String getServletInfo()
	{
		return "Short description";
	}
	public void getResourceJNDIName()
	{
		ServletConfig config = getServletConfig();
		String strResourceJNDIName = config.getInitParameter("resource-jndi-name");
		Env.RESOURCE_JNDI_NAME = strResourceJNDIName;
	}
	public void lookupJNDIName()
	{
		try
		{
			// 1.
			AccountHome home = (AccountHome) EJBHomeFactory.getFactory().lookUpHome(AccountHome.class);
		}
		catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			// 2.
			AccountBookHome home = (AccountBookHome) EJBHomeFactory.getFactory().lookUpHome(AccountBookHome.class);
		}
		catch (IException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			// 3.
			BankBillHome home = (BankBillHome) EJBHomeFactory.getFactory().lookUpHome(BankBillHome.class);
		}
		catch (IException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try
		{
			// 4.
			ConsignVoucherHome home = (ConsignVoucherHome) EJBHomeFactory.getFactory().lookUpHome(ConsignVoucherHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 5.
			GeneralLedgerHome home = (GeneralLedgerHome) EJBHomeFactory.getFactory().lookUpHome(GeneralLedgerHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 6.
			TransCurrentDepositHome home = (TransCurrentDepositHome) EJBHomeFactory.getFactory().lookUpHome(TransCurrentDepositHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 7.
			TransDiscountHome home = (TransDiscountHome) EJBHomeFactory.getFactory().lookUpHome(TransDiscountHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 8.
			TransFeeHome home = (TransFeeHome) EJBHomeFactory.getFactory().lookUpHome(TransFeeHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 9.
			TransFixedDepositHome home = (TransFixedDepositHome) EJBHomeFactory.getFactory().lookUpHome(TransFixedDepositHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 10.
			TransGeneralLedgerHome home = (TransGeneralLedgerHome) EJBHomeFactory.getFactory().lookUpHome(TransGeneralLedgerHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 11.
			TransInterestHome home = (TransInterestHome) EJBHomeFactory.getFactory().lookUpHome(TransInterestHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 12.
			TransLoanHome home = (TransLoanHome) EJBHomeFactory.getFactory().lookUpHome(TransLoanHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 13.
			TransSpecialHome home = (TransSpecialHome) EJBHomeFactory.getFactory().lookUpHome(TransSpecialHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 14.
			CompoundInterestSettingHome home = (CompoundInterestSettingHome) EJBHomeFactory.getFactory().lookUpHome(CompoundInterestSettingHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 18.
			TransSecuritiesHome home =
				(TransSecuritiesHome) EJBHomeFactory.getFactory().lookUpHome(TransSecuritiesHome.class);
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		try
		{
			// 19.
			SecuritiesGeneralLedgerHome home =
				(SecuritiesGeneralLedgerHome) EJBHomeFactory.getFactory().lookUpHome(SecuritiesGeneralLedgerHome.class,"Sett_SecuritiesGeneralLedgerHome");
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 20.
			DeliveryOrderServiceHome home =
				(DeliveryOrderServiceHome) EJBHomeFactory.getFactory().lookUpHome(DeliveryOrderServiceHome.class,"Sett_DeliveryOrderServiceHome");
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		try
		{
			// 21.
			SettPayNoticeHome home = (SettPayNoticeHome) EJBHomeFactory.getFactory().lookUpHome(SettPayNoticeHome.class,"SettPayNoticeHome");
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		
		try
		{
			// 22.
			SettContractHome home = (SettContractHome) EJBHomeFactory.getFactory().lookUpHome(SettContractHome.class,"SettContractHome");
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		try
		{
			// 23.
			SettAdjustInterestRateHome home = (SettAdjustInterestRateHome) EJBHomeFactory.getFactory().lookUpHome(SettAdjustInterestRateHome.class,"SettAdjustInterestRateHome");
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 25.
		    OffBalanceRegisterHome home = (OffBalanceRegisterHome) EJBHomeFactory.getFactory().lookUpHome(OffBalanceRegisterHome.class,"OffBalanceRegisterHome");
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try
		{
			// 24.
		    OffBalanceHome home = (OffBalanceHome) EJBHomeFactory.getFactory().lookUpHome(OffBalanceHome.class,"OffBalanceHome");
		}
		catch (IException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}	
		
		
		try
		{
			// 后添加
			TransMarginDepositHome home = (TransMarginDepositHome) EJBHomeFactory.getFactory().lookUpHome(TransMarginDepositHome.class);
		}
		catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			//26.
			TransInternalLendHome home = (TransInternalLendHome) EJBHomeFactory.getFactory().lookUpHome(TransInternalLendHome.class);
		}
		catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 设置自动执行任务
	 */
	public void initAutoRunTask()
	{
		Scheduler scheduler = Scheduler.getInstance();
		logger.info("开始设置自动执行任务...");
//注销自动结息		
//		logger.info("开始设置自动结息任务.........");
//		AutoInterestSettlementTask[] interestSettlementTasks = InterestFeeSetting.getAllAutoTask();
//		if (interestSettlementTasks != null) {
//			for (int i = 0; i < interestSettlementTasks.length; i++) {
//				try {
//					scheduler.addTask(interestSettlementTasks[i]);
//				} catch (Exception e) {
//					logger.info(e.getMessage());
//				}
//			}
//		}
//		logger.info("结束设置自动结息任务。");

		logger.info("开始设置业务提醒任务...");
		RemindTask remindtask = new RemindTask();
		try {
			scheduler.addTask(remindtask);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("结束设置业务提醒任务。");		

		logger.info("开始执行用款计划自动任务...");
		BudgetAutoTask budgetAutoTask = new BudgetAutoTask();
		try{
			
			scheduler.addTask(budgetAutoTask);
		}catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("结束执行用款计划自动任务。");
		
		/*
		// 设置银行接口相关的自动任务
		if(Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID,false))
		{
			if(Config.getBoolean(Config.INTEGRATION_SERVICE_REMINDBANKACCOUNTTRANSINFOTASK_ISVALID,true))
			{
				logger.info("开始设置银行账户收款交易提醒任务...");
				RemindBankAccountTransInfoTask rbatTask = new RemindBankAccountTransInfoTask();
				try
				{
					scheduler.addTask(rbatTask);
				}
				catch (Exception e)
				{
					logger.info(e.getMessage());
				}
				logger.info("结束设置银行账户收款交易提醒任务。");
				
			}
			
			if(Config.getBoolean(Config.INTEGRATION_SERVICE_REMINDBANKACCOUNTBALANCETASK_ISVALID,true))
			{
				logger.info("开始账户余额提醒任务...");
				
				RemindBankAccountBalanceTask remindBalancetask = new RemindBankAccountBalanceTask();
				try
				{
					scheduler.addTask(remindBalancetask);
				}
				catch (Exception e)
				{
					logger.info(e.getMessage());
				}
				logger.info("结束账户余额提醒任务。");
			}
			
			try
			{
				logger.info("开始设置交易自动同步任务...");			
				RecieveBankTransInfoToTurnIn turnIn= new RecieveBankTransInfoToTurnIn();
				turnIn.initTaskPlans();	
				logger.info("设置交易自动同步任务结束。");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("设置交易自动任务同步失败。");
			}
			
			logger.info("开始设置自动导入昨日余额任务...");
			try {
				ImportEveryDayBalanceData simport = new ImportEveryDayBalanceData();
				simport.initTaskPlans();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("设置自动导入昨日余额任务失败。");
			}
			logger.info("设置自动导入昨日余额任务结束。");
			*/
//			logger.info("开始设置自动发送银行转账指令任务...");
//			SendBankInstructionAutoTask sendTask = new SendBankInstructionAutoTask();
//			try
//			{
//				scheduler.addTask(sendTask);
//			}
//			catch (Exception e)
//			{
//				logger.info(e.getMessage());
//			}
//			logger.info("结束设置自动发送银行转账指令任务。");
			
			/*logger.info("开始设置自动上收计划任务...");
			AutoTurnIn autoTurnIn = new AutoTurnIn();
	    	try
			{
	    		autoTurnIn.initTask();
	    		logger.debug("初始化当前数据库中设置的所有自动上收计划成功。");
			}
	    	catch(Exception e)
			{
	    		e.printStackTrace();
	    		logger.error("初始化当前数据库中设置的所有自动上收计划失败。");
	    	}
	    	logger.info("结束设置自动上收计划任务");
		}
		*/

		// modify by kenny(2007-07-10) 去掉应用程序释放链接的自动任务
		/*
		logger.info("开始自动清除数据库连接任务：");
		CloseConnectionTask closeConnectionTask = new CloseConnectionTask();
		try
		{
			scheduler.addTask(closeConnectionTask);
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
		}
		logger.info("结束自动清除数据库连接任务：");
		*/

		logger.info("结束设置自动执行任务。");
	}
	
	private void initBankServicePackage()
	{
		try{
		boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
		if (!bIsValid){
			logger.info("没有配置结算服务接口");
			return;
		}
		String IP="";
		String port="";

		logger.info("启动结算服务");

		SETTBankServiceImp service=new SETTBankServiceImp();
		IP=Config.getProperty( Config.INTEGRATION_SERVICE_SETTSERVICEIP ,"");
		port=Config.getProperty( Config.INTEGRATION_SERVICE_SETTSERVICEPORT ,"");
		logger.info("结算服务 IP = "+IP);
		logger.info("结算服务 Port = " +port);
		
		if (IP==null || IP=="" || port==null || port==""){
			logger.info("IP或Port为空，无法启动结算服务");
			return;
		}
		service.startBankService(IP,Integer.valueOf(port).intValue());
		logger.info("结算服务加载完毕");

		IP=Config.getProperty( Config.INTEGRATION_SERVICE_BPSERVICEIP ,"");
		port=Config.getProperty( Config.INTEGRATION_SERVICE_BPSERVICEPORT ,"");
		
		logger.info("资金监控服务 IP = "+IP);
		logger.info("资金监控服务 Port = " +port);
		
		if (IP==null || IP=="" || port==null || port==""){
			logger.info("IP或Port为空，无法启动监控服务");
			throw new Exception("IP或Port为空，无法连接资金监控接口");
		}
		BPClientAgent.init(IP,Integer.valueOf(port).intValue());
		logger.info("监控服务加载完毕");

		}catch (Exception e){
			e.printStackTrace();
			logger.info("结算、监控服务启动出错");
		}
	}
	
	/**
	 * SAP服务监控（财企接口）
	 */
	public void initSapService()
	{
		try{
			boolean bIsValid = Config.getBoolean(Config.GLOBAL_FCINTERFACE_SAP, false);
			if (!bIsValid){
				logger.info("没有配置SAP服务接口");
				return;
			}
			SapInstructionHandle handle = new SapInstructionHandle();
			handle.startServers();
		}catch (Exception e){
			e.printStackTrace();
			logger.info("SAP服务服务启动出错");
		}
	}
	
}
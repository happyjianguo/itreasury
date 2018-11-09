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
			// �����
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
	 * �����Զ�ִ������
	 */
	public void initAutoRunTask()
	{
		Scheduler scheduler = Scheduler.getInstance();
		logger.info("��ʼ�����Զ�ִ������...");
//ע���Զ���Ϣ		
//		logger.info("��ʼ�����Զ���Ϣ����.........");
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
//		logger.info("���������Զ���Ϣ����");

		logger.info("��ʼ����ҵ����������...");
		RemindTask remindtask = new RemindTask();
		try {
			scheduler.addTask(remindtask);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("��������ҵ����������");		

		logger.info("��ʼִ���ÿ�ƻ��Զ�����...");
		BudgetAutoTask budgetAutoTask = new BudgetAutoTask();
		try{
			
			scheduler.addTask(budgetAutoTask);
		}catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("����ִ���ÿ�ƻ��Զ�����");
		
		/*
		// �������нӿ���ص��Զ�����
		if(Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID,false))
		{
			if(Config.getBoolean(Config.INTEGRATION_SERVICE_REMINDBANKACCOUNTTRANSINFOTASK_ISVALID,true))
			{
				logger.info("��ʼ���������˻��տ����������...");
				RemindBankAccountTransInfoTask rbatTask = new RemindBankAccountTransInfoTask();
				try
				{
					scheduler.addTask(rbatTask);
				}
				catch (Exception e)
				{
					logger.info(e.getMessage());
				}
				logger.info("�������������˻��տ����������");
				
			}
			
			if(Config.getBoolean(Config.INTEGRATION_SERVICE_REMINDBANKACCOUNTBALANCETASK_ISVALID,true))
			{
				logger.info("��ʼ�˻������������...");
				
				RemindBankAccountBalanceTask remindBalancetask = new RemindBankAccountBalanceTask();
				try
				{
					scheduler.addTask(remindBalancetask);
				}
				catch (Exception e)
				{
					logger.info(e.getMessage());
				}
				logger.info("�����˻������������");
			}
			
			try
			{
				logger.info("��ʼ���ý����Զ�ͬ������...");			
				RecieveBankTransInfoToTurnIn turnIn= new RecieveBankTransInfoToTurnIn();
				turnIn.initTaskPlans();	
				logger.info("���ý����Զ�ͬ�����������");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("���ý����Զ�����ͬ��ʧ�ܡ�");
			}
			
			logger.info("��ʼ�����Զ����������������...");
			try {
				ImportEveryDayBalanceData simport = new ImportEveryDayBalanceData();
				simport.initTaskPlans();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("�����Զ����������������ʧ�ܡ�");
			}
			logger.info("�����Զ���������������������");
			*/
//			logger.info("��ʼ�����Զ���������ת��ָ������...");
//			SendBankInstructionAutoTask sendTask = new SendBankInstructionAutoTask();
//			try
//			{
//				scheduler.addTask(sendTask);
//			}
//			catch (Exception e)
//			{
//				logger.info(e.getMessage());
//			}
//			logger.info("���������Զ���������ת��ָ������");
			
			/*logger.info("��ʼ�����Զ����ռƻ�����...");
			AutoTurnIn autoTurnIn = new AutoTurnIn();
	    	try
			{
	    		autoTurnIn.initTask();
	    		logger.debug("��ʼ����ǰ���ݿ������õ������Զ����ռƻ��ɹ���");
			}
	    	catch(Exception e)
			{
	    		e.printStackTrace();
	    		logger.error("��ʼ����ǰ���ݿ������õ������Զ����ռƻ�ʧ�ܡ�");
	    	}
	    	logger.info("���������Զ����ռƻ�����");
		}
		*/

		// modify by kenny(2007-07-10) ȥ��Ӧ�ó����ͷ����ӵ��Զ�����
		/*
		logger.info("��ʼ�Զ�������ݿ���������");
		CloseConnectionTask closeConnectionTask = new CloseConnectionTask();
		try
		{
			scheduler.addTask(closeConnectionTask);
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
		}
		logger.info("�����Զ�������ݿ���������");
		*/

		logger.info("���������Զ�ִ������");
	}
	
	private void initBankServicePackage()
	{
		try{
		boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
		if (!bIsValid){
			logger.info("û�����ý������ӿ�");
			return;
		}
		String IP="";
		String port="";

		logger.info("�����������");

		SETTBankServiceImp service=new SETTBankServiceImp();
		IP=Config.getProperty( Config.INTEGRATION_SERVICE_SETTSERVICEIP ,"");
		port=Config.getProperty( Config.INTEGRATION_SERVICE_SETTSERVICEPORT ,"");
		logger.info("������� IP = "+IP);
		logger.info("������� Port = " +port);
		
		if (IP==null || IP=="" || port==null || port==""){
			logger.info("IP��PortΪ�գ��޷������������");
			return;
		}
		service.startBankService(IP,Integer.valueOf(port).intValue());
		logger.info("�������������");

		IP=Config.getProperty( Config.INTEGRATION_SERVICE_BPSERVICEIP ,"");
		port=Config.getProperty( Config.INTEGRATION_SERVICE_BPSERVICEPORT ,"");
		
		logger.info("�ʽ��ط��� IP = "+IP);
		logger.info("�ʽ��ط��� Port = " +port);
		
		if (IP==null || IP=="" || port==null || port==""){
			logger.info("IP��PortΪ�գ��޷�������ط���");
			throw new Exception("IP��PortΪ�գ��޷������ʽ��ؽӿ�");
		}
		BPClientAgent.init(IP,Integer.valueOf(port).intValue());
		logger.info("��ط���������");

		}catch (Exception e){
			e.printStackTrace();
			logger.info("���㡢��ط�����������");
		}
	}
	
	/**
	 * SAP�����أ�����ӿڣ�
	 */
	public void initSapService()
	{
		try{
			boolean bIsValid = Config.getBoolean(Config.GLOBAL_FCINTERFACE_SAP, false);
			if (!bIsValid){
				logger.info("û������SAP����ӿ�");
				return;
			}
			SapInstructionHandle handle = new SapInstructionHandle();
			handle.startServers();
		}catch (Exception e){
			e.printStackTrace();
			logger.info("SAP���������������");
		}
	}
	
}
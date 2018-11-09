// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name: TestDBServlet.java
package com.iss.itreasury.servlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iss.itreasury.securities.apply.bizlogic.ApplyHome;
import com.iss.itreasury.securities.deliveryorder.bizlogic.DeliveryOrderHome;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceHome;
import com.iss.itreasury.securities.notice.bizlogic.NoticeHome;
import com.iss.itreasury.securities.register.bizlogic.RegisterHome;
import com.iss.itreasury.securities.securitiesaccount.bizlogic.SecuritiesAccountHome;
import com.iss.itreasury.securities.securitiescontract.bizlogic.SecuritiesContractHome;
import com.iss.itreasury.securities.securitiesgeneralledger.bizlogic.SecuritiesGeneralLedgerHome;
import com.iss.itreasury.securities.setting.bizlogic.SettingHome;
import com.iss.itreasury.securities.stock.bizlogic.StockHome;
import com.iss.itreasury.securities.endprocess.bizlogic.EndProcessHome;
import com.iss.itreasury.securities.securitiescontractplan.bizlogic.SecuritiesContractPlanHome;
import com.iss.itreasury.securities.util.DailyTask;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Scheduler;
///
public class InitSecuritiesAppServlet extends HttpServlet {
    Log4j logger = new Log4j(Constant.ModuleType.SECURITIES);
    //记录初始化次数
    private static long lInitCount = 0;
    public InitSecuritiesAppServlet() {
	}
	public void init(ServletConfig config) throws ServletException {	
		super.init(config);
		//Modify by leiyang 20081119
		//注释自动任务
		/*if (lInitCount == 0)
		{
		    super.init(config);
			getResourceJNDIName();
			lookupJNDIName();
		    initAutoRunTask();
		}*/
	}
	public void destroy() {
	}
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	public String getServletInfo() {
		return "Short description";
	}
	public void getResourceJNDIName() {
		ServletConfig config = getServletConfig();
		String strResourceJNDIName = config.getInitParameter("resource-jndi-name");
		Env.RESOURCE_JNDI_NAME = strResourceJNDIName;
	}
	public void lookupJNDIName() {
		try {
			// 1.
			ApplyHome home = (ApplyHome) EJBHomeFactory.getFactory().lookUpHome(ApplyHome.class);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// 2.
			DeliveryOrderHome home = (DeliveryOrderHome) EJBHomeFactory.getFactory().lookUpHome(DeliveryOrderHome.class);
		} catch (IException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// 3.
			DeliveryOrderServiceHome home = (DeliveryOrderServiceHome) EJBHomeFactory.getFactory().lookUpHome(DeliveryOrderServiceHome.class);
		} catch (IException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			// 4.
			NoticeHome home = (NoticeHome) EJBHomeFactory.getFactory().lookUpHome(NoticeHome.class);
		} catch (IException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {
			// 5.
			RegisterHome home = (RegisterHome) EJBHomeFactory.getFactory().lookUpHome(RegisterHome.class);
		} catch (IException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {
			// 6.
			SecuritiesAccountHome home = (SecuritiesAccountHome) EJBHomeFactory.getFactory().lookUpHome(SecuritiesAccountHome.class);
		} catch (IException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {
			// 7.
			SecuritiesGeneralLedgerHome home = (SecuritiesGeneralLedgerHome) EJBHomeFactory.getFactory().lookUpHome(SecuritiesGeneralLedgerHome.class);
		} catch (IException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {
			// 8.
			StockHome home = (StockHome) EJBHomeFactory.getFactory().lookUpHome(StockHome.class);
		} catch (IException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
        try {
            // 9.
            SettingHome home = (SettingHome) EJBHomeFactory.getFactory().lookUpHome(SettingHome.class);
        } catch (IException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }
        try {
            // 10.
            EndProcessHome home = (EndProcessHome) EJBHomeFactory.getFactory().lookUpHome(EndProcessHome.class);
        } catch (IException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }        
        try {
            // 11.
            SecuritiesContractHome home = (SecuritiesContractHome) EJBHomeFactory.getFactory().lookUpHome(SecuritiesContractHome.class);
        } catch (IException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }        
        try {
            // 12.
            SecuritiesContractPlanHome home = (SecuritiesContractPlanHome) EJBHomeFactory.getFactory().lookUpHome(SecuritiesContractPlanHome.class);
        } catch (IException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }        
	}
	/**
	 * 设置自动执行任务
	 */
	public void initAutoRunTask()
	{
		Scheduler scheduler = Scheduler.getInstance();
		logger.info("开始设置自动执行任务：");
		logger.info("===============================");
		
		logger.info("开始设置申请书状态更新任务：");
		DailyTask dailytask = new DailyTask();
		try
		{
			scheduler.addTask(dailytask);
			lInitCount++;
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
		}
		logger.info("结束设置申请书状态更新任务：");
				
		logger.info("===============================");
		logger.info("结束设置自动执行任务。");
	}
}
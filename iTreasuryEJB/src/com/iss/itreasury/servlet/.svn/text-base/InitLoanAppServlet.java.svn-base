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

import com.iss.itreasury.loan.awake.bizlogic.AwakeTask;
import com.iss.itreasury.loan.util.DailyTask;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Scheduler;
//
public class InitLoanAppServlet extends HttpServlet {
	Log4j logger = new Log4j(Constant.ModuleType.LOAN);;
	public InitLoanAppServlet() {
	}
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		initAutoRunTask();
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
	/**
	 * 设置自动执行任务
	 */
	public void initAutoRunTask() {
		Scheduler scheduler = Scheduler.getInstance();
		logger.info("开始信贷设置业务提醒任务：");
		AwakeTask awaketask = new AwakeTask();
		try {
			scheduler.addTask(awaketask);
			logger.info("add task loan ok!");
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("结束信贷设置自动执行任务。");

		logger.info("开始信贷授信状态更新任务：");
		DailyTask dailyTask = new DailyTask();
		try {
			scheduler.addTask(dailyTask);
			logger.info("add task loan ok!");
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("结束信贷授信状态更新任务。");
	}
}
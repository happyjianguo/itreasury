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

//import com.iss.itreasury.treasuryplan.awake.bizlogic.AwakeTask;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Scheduler;
//
public class InitPlanAppServlet extends HttpServlet {
	Log4j logger = new Log4j(Constant.ModuleType.PLAN);;
	public InitPlanAppServlet() {
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
		logger.info("开始设置资金计划业务提醒任务：");
		//AwakeTask awaketask = new AwakeTask();
		try {
			//scheduler.addTask(awaketask);
			logger.info("add plan task ok!");
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("结束设置资金计划自动执行任务。");
	}
}
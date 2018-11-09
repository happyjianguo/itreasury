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
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.bill.blankvoucher.bizlogic.*;
import com.iss.itreasury.bill.draft.bizlogic.*;
import com.iss.itreasury.bill.venture.bizlogic.VentureHome;
///
public class InitBillAppServlet extends HttpServlet
{
	Log4j logger = new Log4j(Constant.ModuleType.SECURITIES);
	//��¼��ʼ������
	private static long lInitCount = 0;
	public InitBillAppServlet()
	{
	}
	public void init(ServletConfig config) throws ServletException
	{
		if (lInitCount == 0)
		{
			super.init(config);
			getResourceJNDIName();
			lookupJNDIName();
			initAutoRunTask();
		}
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
			DraftHome home = (DraftHome) EJBHomeFactory.getFactory().lookUpHome(DraftHome.class);
		}
		catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			// 2.
			BlankVoucherHome home = (BlankVoucherHome) EJBHomeFactory.getFactory().lookUpHome(BlankVoucherHome.class);
		}
		catch (IException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			// 3.
			VentureHome home = (VentureHome) EJBHomeFactory.getFactory().lookUpHome(VentureHome.class);
		}
		catch (IException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * �����Զ�ִ������
	 */
	public void initAutoRunTask()
	{
		//		Scheduler scheduler = Scheduler.getInstance();
	}
}
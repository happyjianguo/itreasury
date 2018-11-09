// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TestDBServlet.java

package com.iss.itreasury.servlet;

import java.sql.Connection;
import com.iss.itreasury.util.Database;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

public class TestDBServlet extends HttpServlet
{

	public TestDBServlet()
	{
	}

	public void init(ServletConfig config)
		throws ServletException
	{
		super.init(config);
	}

	public void destroy()
	{
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection con = null;
		////////////////////////////////////
		try
		{
			con = Database.get_type4_connection();
			if( con != null)
			{
				con.close();
				con = null;
				out.println("get connection by type4 : OK");
			}
			else
				out.println("get connection by type4 : failed");
		}
		catch(Exception exception)
		{
			out.println("get connection by type4 : failed");
		}
		////////////////////////////////////
		out.println("<br>");
		try
		{
			con = Database.get_type2_connection();
			if( con != null)
			{
				con.close();
				con = null;
				out.println("get connection by type2 : OK");
			}
			else
				out.println("get connection by type2 : failed");
		}
		catch(Exception exception1)
		{
			out.println("get connection by type2 : failed");
		}
		////////////////////////////////////
		out.println("<br>");
		try
		{
			con = Database.get_jdbc_connection();
			if( con != null)
			{
				con.close();
				con = null;
				out.println("get connection by jdbc : OK");
			}
			else
				out.println("get connection by jdbc : failed");
		}
		catch(Exception exception2)
		{
			out.println("get connection by jdbc : failed");
		}
		////////////////////////////////////
		out.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		processRequest(request, response);
	}

	public String getServletInfo()
	{
		return "Short description";
	}
}
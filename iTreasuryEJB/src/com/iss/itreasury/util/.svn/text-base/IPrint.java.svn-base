/*
 * Created on 2003-8-14
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.SettlementDAO;

/**
 * @author rxie
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class IPrint extends ITreasuryDAO
{
	/*
	控件说明
	clsid BB44CA57-0BDF-455F-8E69-03091F2C9153
	此控件需要使用WebBrowser控件（IE内置）
	属性列表
	PaperSize  	纸张类型如A4
	PaperSource 	送纸类型 如 自动选择
	Header		页眉 如 lsySoft&b&b
	Footer		页脚 如 &b&b第&p/&P页
	Orientation	纸张方向 1纵向2横向
	LeftMargin	左边距 以毫米为单位如12
	rightMargin	右边距
	topMargin	上边距
	bottomMargin	下边距
	方法列表
	setWebBrowser(wb) 设置要使用WebBrowser对象是哪个
	PageSettor.regist("true") 为注册 false为未注册
	getSetting()	  读取打印设置
	SetUp()		  设置打印要素
	pgSetup()	  页面设置信息框
	PreView()	  打印预览
	showRegInfo()	  显示注册信息
	*/

	//报表打印设置
	public static final String PRINT_REPORT_HEADER = ""; //页眉
	public static final String PRINT_REPORT_FOOTER = "&b第&p页/共&P页&b"; //页脚
	
	/*
	 * 显示 报表的CSS样式
	 */
	private static void showCSS(JspWriter out)
	{
		try
		{
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
			out.println("\n");
//			out.println("<link rel=\"stylesheet\" href=\"/websett/template.css\" type=\"text/css\">\n");
			out.println("<style type=\"text/css\">\n");
			out.println("<!--\n");
			out.println(".table1 {  border: 1px #000000 solid}\n");
			out.println(".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-lefttoprightbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}\n");
			out.println(".td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}\n");
			out.println(".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}\n");
			out.println(".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".small-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
			out.println(".small-td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
			out.println(".small-td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
			out.println(".small-td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
			out.println(".small-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 9px}\n");
			out.println(".small-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n");
			out.println(".small-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n");
			out.println(".small-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 9px}\n");
			out.println(".small-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 9px}\n");
			out.println(".small-td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 9px}\n");
			out.println(".small-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n");
			out.println("-->\n");
			out.println("</style>\n");
		}
		catch (Exception e)
		{
		}
	}
	/*
	 * 显示打印控件得主题部分
	 */
	private static void showPrintScriptObject(JspWriter out)
	{
		try
		{
			out.println(" <OBJECT ID=\"PageSettor\" \n");
			out.println(" CLASSID=\"CLSID:C8D19F3D-3A3A-458C-89BA-64E6FF51C327\" \n");
			out.println(" CODEBASE=\"/webscript/ISSlib.CAB#version=1,0,0,0\"> \n");
			out.println(" </OBJECT> \n");
			out.println(" <OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb> \n");
			out.println(" </OBJECT> \n");
		}
		catch (Exception e)
		{
		}
	}
	/*
	 * 显示需要打印的属性
	 */
	private static void showPrintScriptDefer(JspWriter out,long lOfficeID,String strPortrait,String strPaperSize)
	{
		try
		{
			//从数据库中取得设置的页面边距信息
			Connection conn1 = Database.getConnection();
			PreparedStatement ps1 = conn1.prepareStatement("select nPrintTop,nPrintLeft from Office where id=" + lOfficeID);
			ResultSet rs1 = ps1.executeQuery();
			int nPrintTop = 50;
			int nPrintLeft = 40;
			if (rs1.next())
			{
				nPrintTop = rs1.getInt("nPrintTop");
				nPrintLeft = rs1.getInt("nPrintLeft");
			}
			rs1.close();
			rs1 = null;
			ps1.close();
			ps1 = null;
			conn1.close();
			conn1 = null;
			//从数据库中取得设置的页面边距信息 end
			out.println(" <script defer> \n");
			out.println(" function window.onload() \n");
			out.println(" { \n");
			out.println(" PageSettor.regist(\"isoftstone\"); \n");
			out.println(" PageSettor.setWebBrowser(wb); \n");
			out.println(" PageSettor.PaperSize = \"" + strPaperSize + "\"; \n");
			if(strPortrait.equalsIgnoreCase("true"))
			{
				out.println(" PageSettor.Orientation = 1; \n");
			}
			else
			{
				out.println(" PageSettor.Orientation = 2; \n");
			}
			out.println(" PageSettor.Footer = '"+PRINT_REPORT_FOOTER+"'; \n");
			out.println(" PageSettor.Header = '"+PRINT_REPORT_HEADER+"'; \n");
			out.println(" PageSettor.LeftMargin =" + nPrintLeft + "; \n");
			out.println(" PageSettor.rightMargin = 0; \n");
			out.println(" PageSettor.topMargin = " + nPrintTop + "; \n");
			out.println(" PageSettor.bottomMargin = 0; \n");
			out.println(" PageSettor.SetUp(); \n");
			out.println(" } \n");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * long lTypeID 0:打印凭证类型为全打凭证，需要设置页边距、横向、纸张 边距从数据库中取得
	 * 1:打印凭证类型为套打凭证，页边距为0，不用设置横向和纸张
	 */
	public static void noShowPrintHeadAndFooter(JspWriter out, long lTypeID, long lOfficeID)
	{
		try
		{
			showPrintScriptObject(out);
			out.println("<script defer>\n");
			out.println(" PageSettor.regist(\"isoftstone\"); \n");
			out.println("  PageSettor.setWebBrowser(wb); \n");
			out.println("  PageSettor.Header = \"\";\n");
			out.println("  PageSettor.Footer = \"\";\n");
			if (lTypeID == 1)
			{
				out.println(" PageSettor.LeftMargin = 0; \n");
				out.println(" PageSettor.rightMargin = 0; \n");
				out.println(" PageSettor.topMargin = 0; \n");
				out.println(" PageSettor.bottomMargin = 0; \n");
			}
			else
			{
				Connection conn = null;
				conn = Database.getConnection();
				PreparedStatement ps = conn.prepareStatement("select nPrintTop,nPrintLeft from Office where id=" + lOfficeID);
				ResultSet rs = ps.executeQuery();
				int nPrintTop = 50;
				int nPrintLeft = 40;
				if (rs.next())
				{
					nPrintTop = rs.getInt("nPrintTop");
					nPrintLeft = rs.getInt("nPrintLeft");
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				out.println(" PageSettor.PaperSize = 'A5'; \n");
				out.println(" PageSettor.Orientation = 2; \n");
				out.println(" PageSettor.LeftMargin = " + nPrintLeft + "; \n");
				out.println(" PageSettor.RightMargin = 0; \n");
				out.println(" PageSettor.TopMargin = " + nPrintTop + "; \n");
				out.println(" PageSettor.BottomMargin = 0; \n");
				out.println(" PageSettor.SetUp(); \n");
			}
			out.println("  PageSettor.PrintDocument(true); ");
			out.println("</script>\n");
		}
		catch (Exception e)
		{
		}
	}

	public static void noShowPrintHeadAndFooterForA4(JspWriter out, long lTypeID, long lOfficeID)
	{
		try
		{
			showPrintScriptObject(out);
			out.println("<script defer>\n");
			out.println(" PageSettor.regist(\"isoftstone\"); \n");
			out.println("  PageSettor.setWebBrowser(wb); \n");
			out.println("  PageSettor.Header = \"\";\n");
			out.println("  PageSettor.Footer = \"\";\n");
			if (lTypeID == 1)
			{
				out.println(" PageSettor.LeftMargin = 0; \n");
				out.println(" PageSettor.rightMargin = 0; \n");
				out.println(" PageSettor.topMargin = 0; \n");
				out.println(" PageSettor.bottomMargin = 0; \n");
			}
			else
			{
				Connection conn = null;
				conn = Database.getConnection();
				PreparedStatement ps = conn.prepareStatement("select nPrintTop,nPrintLeft from Office where id=" + lOfficeID);
				ResultSet rs = ps.executeQuery();
				int nPrintTop = 50;
				int nPrintLeft = 40;
				if (rs.next())
				{
					nPrintTop = rs.getInt("nPrintTop");
					nPrintLeft = rs.getInt("nPrintLeft");
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				out.println(" PageSettor.PaperSize = 'A4'; \n");
				out.println(" PageSettor.Orientation = 1; \n");
				out.println(" PageSettor.LeftMargin = " + nPrintLeft + "; \n");
				out.println(" PageSettor.rightMargin = 0; \n");
				out.println(" PageSettor.topMargin = " + nPrintTop + "; \n");
				out.println(" PageSettor.bottomMargin = 0; \n");
				out.println(" PageSettor.SetUp(); \n");
			}
			out.println("  PageSettor.PrintDocument(true); ");
			out.println("</script>\n");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 显示报表的表头（用于任意纸上的报表打印，页面边距从数据库中取得）
	 * 
	 * @param out
	 * @param bPortrait
	 *            是否横向
	 * @param strPapaerSize
	 *            纸张大小
	 * @param strPrinter
	 *            打印机名称 - 保留
	 * @param nNo
	 *            报表编号 - 保留
	 * @param strAbstract
	 *            打印备注 以示和正常报表打印区分
	 */
	public static void showPrintHead(JspWriter out, boolean bPortrait, String strPaperSize, String strPrinter, int nNo, long lOfficeID) throws Exception
	{
		String strPortrait = "";
		if (bPortrait)
		{
			strPortrait = "true";
		}
		else
		{
			strPortrait = "false";
		}
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title></title>\n");
		
		showCSS(out);
		
		out.println("</head>\n");
		
		showPrintScriptObject(out);
		
		out.println("<script defer>\n");
		out.println("	function window.onload()\n");
		out.println("	{\n");
		//added by xierui
		Connection conn1 = Database.getConnection();
		PreparedStatement ps1 = conn1.prepareStatement("select nPrintTop,nPrintLeft from Office where id=" + lOfficeID);
		ResultSet rs1 = ps1.executeQuery();
		int nPrintTop = 50;
		int nPrintLeft = 40;
		if (rs1.next())
		{
			nPrintTop = rs1.getInt("nPrintTop");
			nPrintLeft = rs1.getInt("nPrintLeft");
		}
		rs1.close();
		rs1 = null;
		ps1.close();
		ps1 = null;
		conn1.close();
		conn1 = null;
		out.println(" PageSettor.regist(\"isoftstone\"); \n");
		out.println(" PageSettor.setWebBrowser(wb); \n");
		out.println(" PageSettor.Header = \"\";\n");
		out.println(" PageSettor.Footer = '&b第&p页/共&P页&b';\n");
		out.println(" PageSettor.LeftMargin =" + nPrintLeft + "; \n");
		out.println(" PageSettor.rightMargin = 0; \n");
		out.println(" PageSettor.topMargin = " + nPrintTop + "; \n");
		out.println(" PageSettor.bottomMargin = 0; \n");
		if(strPortrait.equalsIgnoreCase("true"))
		{
			out.println(" PageSettor.Orientation=1; \n");
		}
		else
		{
			out.println(" PageSettor.Orientation=2; \n");
		}
		out.println(" PageSettor.PaperSize=\"" + strPaperSize + "\"; \n");
		out.println(" PageSettor.SetUp(); \n");
		//end added
		out.println("	}\n");
		out.println("	function document.onkeydown(DnEvents)\n");
		out.println("	{\n");
		out.println("		k =  window.event.keyCode;\n");
		out.println("		if(k==13)\n");
		out.println("		{\n");
		out.println("			if (confirm(\"是否打印？\"))\n");
		out.println("			{\n");
		out.println("				PageSettor.PrintDocument(true);\n");
		out.println("			}\n");
		out.println("		}\n");
		out.println("		if(k==32)\n");
		out.println("		{\n");
		out.println("			if (confirm(\"是否预览？\"))\n");
		out.println("			{\n");
		out.println("				PageSettor.PreView();\n");
		out.println("			}\n");
		out.println("		}\n");
		out.println("}	\n");
		out.println("</script>\n");
		out.println("\n");
	}
	public static void showPrintHead(JspWriter out, boolean bPortrait,boolean bIsPrinter, String strPaperSize, String strPrinter, int nNo, long lOfficeID) throws Exception
	{
		String strPortrait = "";
		if (bPortrait)
		{
			strPortrait = "true";
		}
		else
		{
			strPortrait = "false";
		}
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title></title>\n");
		
		showCSS(out);
		
		out.println("</head>\n");
		
		showPrintScriptObject(out);
		
		out.println("<script defer>\n");
		out.println("	function window.onload()\n");
		out.println("	{\n");
		//added by xierui
		Connection conn1 = Database.getConnection();
		PreparedStatement ps1 = conn1.prepareStatement("select nPrintTop,nPrintLeft from Office where id=" + lOfficeID);
		ResultSet rs1 = ps1.executeQuery();
		int nPrintTop = 50;
		int nPrintLeft = 40;
		if (rs1.next())
		{
			nPrintTop = rs1.getInt("nPrintTop");
			nPrintLeft = rs1.getInt("nPrintLeft");
		}
		rs1.close();
		rs1 = null;
		ps1.close();
		ps1 = null;
		conn1.close();
		conn1 = null;
		out.println(" PageSettor.regist(\"isoftstone\"); \n");
		out.println(" PageSettor.setWebBrowser(wb); \n");
		out.println(" PageSettor.Header = \"\";\n");
		out.println(" PageSettor.Footer = '&b第&p页/共&P页&b';\n");
		out.println(" PageSettor.LeftMargin =" + nPrintLeft + "; \n");
		out.println(" PageSettor.rightMargin = 0; \n");
		out.println(" PageSettor.topMargin = " + nPrintTop + "; \n");
		out.println(" PageSettor.bottomMargin = 0; \n");
		if(strPortrait.equalsIgnoreCase("true"))
		{
			out.println(" PageSettor.Orientation=1; \n");
		}
		else
		{
			out.println(" PageSettor.Orientation=2; \n");
		}
		out.println(" PageSettor.PaperSize=\"" + strPaperSize + "\"; \n");
		out.println(" PageSettor.SetUp(); \n");
		if(bIsPrinter)
		{
			out.println("  PageSettor.PrintDocument(true);\n");
		}
		//end added
		out.println("	}\n");
		if(!bIsPrinter)
		{
			out.println("	function document.onkeydown(DnEvents)\n");
			out.println("	{\n");
			out.println("		k =  window.event.keyCode;\n");
			out.println("		if(k==13)\n");
			out.println("		{\n");
			out.println("			if (confirm(\"是否打印？\"))\n");
			out.println("			{\n");
			out.println("				PageSettor.PrintDocument(true);\n");
			out.println("			}\n");
			out.println("		}\n");
			out.println("		if(k==32)\n");
			out.println("		{\n");
			out.println("			if (confirm(\"是否预览？\"))\n");
			out.println("			{\n");
			out.println("				//factory.printing.printer=\"\";可以写打印机的名称\n");
			out.println("				PageSettor.PreView();\n");
			out.println("			}\n");
			out.println("		}\n");
			out.println("}	\n");
		}
		out.println("</script>\n");
		out.println("\n");
	}

	/**
	 * 显示报表的表头（用于Excel导出报表打印，页面边距从数据库中取得）
	 * 
	 * @param out
	 * @param bPortrait
	 *            是否横向
	 * @param strPapaerSize
	 *            纸张大小
	 * @param strPrinter
	 *            打印机名称 - 保留
	 * @param nNo
	 *            报表编号 - 保留
	 * @param strAbstract
	 *            打印备注 以示和正常报表打印区分
	 */
	public static void showPrintHeadForExcel(JspWriter out, boolean bPortrait, String strPaperSize, String strPrinter, int nNo, long lOfficeID) throws Exception
	{
		String strPortrait = "";
		if (bPortrait)
		{
			strPortrait = "true";
		}
		else
		{
			strPortrait = "false";
		}
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title></title>\n");
		
		showCSS(out);
		
		out.println("</head>\n");
		
		showPrintScriptObject(out);
		
		out.println("<script defer>\n");
		out.println("	function window.onload()\n");
		out.println("	{\n");
		//added by xierui
		Connection conn1 = Database.getConnection();
		PreparedStatement ps1 = conn1.prepareStatement("select nPrintTop,nPrintLeft from Office where id=" + lOfficeID);
		ResultSet rs1 = ps1.executeQuery();
		int nPrintTop = 50;
		int nPrintLeft = 40;
		if (rs1.next())
		{
			nPrintTop = rs1.getInt("nPrintTop");
			nPrintLeft = rs1.getInt("nPrintLeft");
		}
		rs1.close();
		rs1 = null;
		ps1.close();
		ps1 = null;
		conn1.close();
		conn1 = null;
		out.println(" PageSettor.regist(\"isoftstone\"); \n");
		out.println(" PageSettor.setWebBrowser(wb); \n");
		out.println(" PageSettor.Header = \"\";\n");
		out.println(" PageSettor.Footer = '&b第&p页/共&P页&b';\n");
		out.println(" PageSettor.LeftMargin =" + nPrintLeft + "; \n");
		out.println(" PageSettor.rightMargin = 0; \n");
		out.println(" PageSettor.topMargin = " + nPrintTop + "; \n");
		out.println(" PageSettor.bottomMargin = 0; \n");
		if(strPortrait.equalsIgnoreCase("true"))
		{
			out.println(" PageSettor.Orientation=1; \n");
		}
		else
		{
			out.println(" PageSettor.Orientation=2; \n");
		}
		out.println(" PageSettor.PaperSize=\"" + strPaperSize + "\"; \n");
		out.println(" PageSettor.SetUp(); \n");
		//end added
		out.println("	}\n");
		out.println("	function document.onkeydown(DnEvents)\n");
		out.println("	{\n");
		out.println("		k =  window.event.keyCode;\n");
		out.println("		if(k==13)\n");
		out.println("		{\n");
		out.println("			if (confirm(\"是否打印？\"))\n");
		out.println("			{\n");
		out.println("				PageSettor.PrintDocument(true);\n");
		out.println("			}\n");
		out.println("		}\n");
		out.println("		if(k==32)\n");
		out.println("		{\n");
		out.println("			if (confirm(\"是否预览？\"))\n");
		out.println("			{\n");
		out.println("				PageSettor.PreView();\n");
		out.println("			}\n");
		out.println("		}\n");
		out.println("}	\n");
		out.println("</script>\n");
		out.println("\n");
	}

	/**
	 * 用于任意纸上的凭证打印（类似于报表的凭证使用），页面边距从数据库中取得（没有页码标识）
	 * 
	 * @param out
	 * @param bPortrait
	 *            是否横向
	 * @param strPapaerSize
	 *            纸张大小
	 * @param strPrinter
	 *            打印机名称 - 保留
	 * @param nNo
	 *            报表编号 - 保留
	 * @param strAbstract
	 *            打印备注 以示和正常报表打印区分
	 */
	public static void showVoucherPrintHead(JspWriter out, boolean bPortrait, String strPaperSize, String strPrinter, int nNo, long lOfficeID) throws Exception
	{
		String strPortrait = "";
		if (bPortrait)
		{
			strPortrait = "true";
		}
		else
		{
			strPortrait = "false";
		}
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title></title>\n");
		
		showCSS(out);
		
		out.println("</head>\n");
		
		showPrintScriptObject(out);
		
		out.println("<script defer>\n");
		out.println("	function window.onload()\n");
		out.println("	{\n");
		//added by xierui
		Connection conn1 = Database.getConnection();
		PreparedStatement ps1 = conn1.prepareStatement("select nPrintTop,nPrintLeft from Office where id=" + lOfficeID);
		ResultSet rs1 = ps1.executeQuery();
		int nPrintTop = 50;
		int nPrintLeft = 40;
		if (rs1.next())
		{
			nPrintTop = rs1.getInt("nPrintTop");
			nPrintLeft = rs1.getInt("nPrintLeft");
		}
		rs1.close();
		rs1 = null;
		ps1.close();
		ps1 = null;
		conn1.close();
		conn1 = null;
		out.println(" PageSettor.regist(\"isoftstone\"); \n");
		out.println(" PageSettor.setWebBrowser(wb); \n");
		out.println(" PageSettor.Header = \"\";\n");
		out.println(" PageSettor.Footer = '&b第&p页/共&P页&b';\n");
		out.println(" PageSettor.LeftMargin =" + nPrintLeft + "; \n");
		out.println(" PageSettor.rightMargin = 0; \n");
		out.println(" PageSettor.topMargin = " + nPrintTop + "; \n");
		out.println(" PageSettor.bottomMargin = 0; \n");
		if(strPortrait.equalsIgnoreCase("true"))
		{
			out.println(" PageSettor.Orientation=1; \n");
		}
		else
		{
			out.println(" PageSettor.Orientation=2; \n");
		}
		out.println(" PageSettor.PaperSize=\"" + strPaperSize + "\"; \n");
		out.println(" PageSettor.SetUp(); \n");
		//end added
		out.println("	}\n");
		out.println("	function document.onkeydown(DnEvents)\n");
		out.println("	{\n");
		out.println("		k =  window.event.keyCode;\n");
		out.println("		if(k==13)\n");
		out.println("		{\n");
		out.println("			if (confirm(\"是否打印？\"))\n");
		out.println("			{\n");
		out.println("				PageSettor.PrintDocument(true);\n");
		out.println("			}\n");
		out.println("		}\n");
		out.println("		if(k==32)\n");
		out.println("		{\n");
		out.println("			if (confirm(\"是否预览？\"))\n");
		out.println("			{\n");
		out.println("				PageSettor.PreView();\n");
		out.println("			}\n");
		out.println("		}\n");
		out.println("}	\n");
		out.println("</script>\n");
		out.println("\n");
	}
	/**
	 *显示套打模版，信贷模块调用
	 *strCode数组内所存sCode顺序需要和字符串变量的顺序一致
	 *
	 */
	public static void showTemplateLoan(
		JspWriter out,
		String[] strCode,
		long lTemplateID,
		String str1,
		String str2,
		String str3,
		String str4,
		String str5,
		String str6,
		String str7,
		String str8,
		String str9,
		String str10,
		String str11,
		String str12,
		String str13,
		String str14,
		String str15,
		String str16,
		String str17,
		String str18,
		String str19,
		String str20,
		String str21,
		String str22,
		String str23,
		String str24,
		String str25,
		String str26,
		String str27,
		String str28)
		throws Exception
	{
		/*
		try
		{
			//创建EJB
			com.iss.itreasury.loan.bizlogic.loanfunc.LoanFuncHome home =
				(com.iss.itreasury.loan.bizlogic.loanfunc.LoanFuncHome) CommonObject.getEJBHome("LoanFuncHome");
			com.iss.itreasury.loan.bizlogic.loanfunc.LoanFunc ejb = home.create();
			//  LoanFuncEJB ejb = new LoanFuncEJB();
			Collection c = ejb.findPrintOptionDetailsByTemplateID(lTemplateID); //用常量表示
			PrintOptionInfo printOptionInfo = null;
			int lparmNumber = 0;
			out.println("<html>" + "<head>");
			////noShowPrintHeadAndFooter(out);
			out.println(
				"<title>打印模版设置</title>"
					+ "<STYLE type=text/css>"
					+ "A.map:visited {     COLOR: #FF6600;  TEXT-DECORATION: none}"
					+ "A.map:hover {  COLOR: #FFCC00;  TEXT-DECORATION: underline}"
					+ "A.g:link { COLOR: #666600;  TEXT-DECORATION: none}"
					+ "A.g:visited {   COLOR: #666600;  TEXT-DECORATION: none}"
					+ "A.g:hover {    COLOR: #FF9900;  TEXT-DECORATION: underline}"
					+ "A:link {   COLOR: #000000;  TEXT-DECORATION: none}"
					+ "A:visited {    COLOR: #000000;  TEXT-DECORATION: none}"
					+ "A:hover {  COLOR: #CC0000;  TEXT-DECORATION: underline}"
					+ "A.title:link { COLOR: #ffffff;  TEXT-DECORATION: none}"
					+ "A.title:visited {  COLOR: #ffffff;  TEXT-DECORATION: none}"
					+ "A.title:hover {     COLOR: #ffffff;  TEXT-DECORATION: underline}"
					+ "A.line:link {  COLOR: #3300FF;  TEXT-DECORATION: underline}"
					+ "A.line:visited {   COLOR: #3300FF;  TEXT-DECORATION: underline}"
					+ "A.line:hover {  COLOR: #0066FF;  TEXT-DECORATION: underline}"
					+ "A.line1:link { COLOR: #666666;  TEXT-DECORATION: underline}"
					+ "A.line1:visited {  COLOR: #666666;  TEXT-DECORATION: underline}"
					+ "A.line1:hover {     COLOR: #CC0000;  TEXT-DECORATION: underline}"
					+ "</STYLE>"
					+ "</head>"
					+ "<body >");
			if (c != null)
			{
				Iterator it = c.iterator();
				while (it.hasNext())
				{
					printOptionInfo = (PrintOptionInfo) it.next();
					System.out.println(":::printOptionInfo.m_strCode:::=" + printOptionInfo.m_strCode);
					String strDisplayDetails = "";
					for (int i = 1; i <= strCode.length; i++)
					{
						System.out.println(":::strCode[i-1]::=" + strCode[i - 1]);
						if (strCode[i - 1].equals(printOptionInfo.m_strCode))
						{
							lparmNumber = i;
							System.out.println(":::strCode[i-1]::=" + strCode[i - 1]);
							break;
						}
						if (i == strCode.length)
							lparmNumber = 0;
					}
					switch (lparmNumber)
					{
						case 1 :
							strDisplayDetails = str1;
							break;
						case 2 :
							strDisplayDetails = str2;
							break;
						case 3 :
							strDisplayDetails = str3;
							break;
						case 4 :
							strDisplayDetails = str4;
							break;
						case 5 :
							strDisplayDetails = str5;
							break;
						case 6 :
							strDisplayDetails = str6;
							break;
						case 7 :
							strDisplayDetails = str7;
							break;
						case 8 :
							strDisplayDetails = str8;
							break;
						case 9 :
							strDisplayDetails = str9;
							break;
						case 10 :
							strDisplayDetails = str10;
							break;
						case 11 :
							strDisplayDetails = str11;
							break;
						case 12 :
							strDisplayDetails = str12;
							break;
						case 13 :
							strDisplayDetails = str13;
							break;
						case 14 :
							strDisplayDetails = str14;
							break;
						case 15 :
							strDisplayDetails = str15;
							break;
						case 16 :
							strDisplayDetails = str16;
							break;
						case 17 :
							strDisplayDetails = str17;
							break;
						case 18 :
							strDisplayDetails = str18;
							break;
						case 19 :
							strDisplayDetails = str19;
							break;
						case 20 :
							strDisplayDetails = str20;
							break;
						case 21 :
							strDisplayDetails = str21;
							break;
						case 22 :
							strDisplayDetails = str22;
							break;
						case 23 :
							strDisplayDetails = str23;
							break;
						case 24 :
							strDisplayDetails = str24;
							break;
						case 25 :
							strDisplayDetails = str25;
							break;
						case 26 :
							strDisplayDetails = str26;
							break;
						case 27 :
							strDisplayDetails = str27;
							break;
						case 28 :
							strDisplayDetails = str28;
							break;
					}
					if (printOptionInfo.m_lTypeID == 1)
					{
						String strDisplayDetailsTemp = "";
						strDisplayDetailsTemp = strDisplayDetails.substring(0, 1);
						for (int i = 1; i < strDisplayDetails.length(); i++)
						{
							if (!(strDisplayDetails.substring(i, i + 1).equals(".") || strDisplayDetails.substring(i, i + 1).equals(",")))
								strDisplayDetailsTemp += "&nbsp;&nbsp;" + strDisplayDetails.substring(i, i + 1);
						}
						strDisplayDetails = strDisplayDetailsTemp;
					}
					double dLeft = printOptionInfo.m_dDetailsLeft + printOptionInfo.m_dTemplateLeft;
					double dTop = printOptionInfo.m_dDetailsTop + printOptionInfo.m_dTemplateTop;
					out.println(
						"<div id=" + printOptionInfo.m_lTemplateDetailsID + "  style=\"position:absolute; left:" + dLeft + "px;  top:" + dTop + "px\"  > ");
					if (printOptionInfo.m_lFiledWidth > 0)
					{
						out.print("<table width=\"");
						out.print(printOptionInfo.m_lFiledWidth);
						out.println("\">");
						out.println("<tr>");
						out.println("<td>");
					}
					if (printOptionInfo.m_lIsBold == 1)
						out.println("<b>");
					if (printOptionInfo.m_lIsItalic == 1)
						out.println("<i>");
					out.println(
						"<font style=\"font-size:"
							+ printOptionInfo.m_lSize
							+ "px\""
							+ "face="
							+ printOptionInfo.m_strFont
							+ ">"
							+ strDisplayDetails
							+ "</font>");
					if (printOptionInfo.m_lIsItalic == 1)
						out.println("</i>");
					if (printOptionInfo.m_lIsBold == 1)
						out.println("</b>");
					if (printOptionInfo.m_lFiledWidth > 0)
					{
						out.println("</td>");
						out.println("</tr>");
						out.println("</table>");
					}
					out.println("</div>");
				}
			}
			out.println("</body>" + "</html>");
		}
		catch (Exception e)
		{
		}
		*/
	}
	/**
	 * 显示报表的表头
	 * @param out
	 * @param bPortrait 是否横向
	 * @param strPapaerSize 纸张大小
	 * @param strPrinter 打印机名称 - 保留
	 * @param nNo 报表编号 - 保留
	 */
	public static void showLOANPrintHead(JspWriter out, boolean bPortrait, String strPaperSize, String strPrinter, int nNo) throws IException
	{
		/*
		  String strPortrait="";
		  if(bPortrait)
		  {
		      strPortrait="true";
		  }
		  else
		  {
		      strPortrait="false";
		  }
		  out.println("<html>\n" );
		  out.println("<head>\n" );
		  out.println("<title></title>\n" );
		  out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n" );
		  out.println("\n" );
		  out.println("<link rel=\"stylesheet\" href=\"/graphics/template.css\" type=\"text/css\">\n" );
		  out.println("<style type=\"text/css\">\n" );
		  out.println("<!--\n" );
		  out.println(".table1 {  border: 1px #000000 solid}\n" );
		  out.println(".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n" );
		  out.println(".td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n" );
		  out.println(".td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n" );
		  out.println(".td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n" );
		  out.println(".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}\n" );
		  out.println(".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n" );
		  out.println(".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n" );
		  out.println(".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}\n" );
		  out.println(".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}\n" );
		  out.println(".td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}\n" );
		  out.println(".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n" );
		  out.println(".small-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n" );
		  out.println(".small-td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n" );
		  out.println(".small-td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n" );
		  out.println(".small-td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n" );
		  out.println(".small-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 9px}\n" );
		  out.println(".small-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n" );
		  out.println(".small-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n" );
		  out.println(".small-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 9px}\n" );
		  out.println(".small-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 9px}\n" );
		  out.println(".small-td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 9px}\n" );
		  out.println(".small-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n" );
		  out.println("-->\n" );
		  out.println("</style>\n" );
		  out.println("</head>\n" );
		  out.println("<!-- MeadCo Security Manager -->\n" );
		  out.println("<object viewastext style=\"display:none\"\n" );
		  out.println(" classid=\"clsid:5445be81-b796-11d2-b931-002018654e2e\"\n" );
		  out.println(" codebase=\"/graphics/smsx.cab#Version=6,1,429,14\">\n" );
		  out.println(" <param name=\"GUID\" value=\"{E74B584D-C36B-11D6-A31F-0020ED1AFD98}\">\n" );
		  out.println(" <param name=\"Path\" value=\"/graphics/sxlic.mlf\">\n" );
		  out.println(" <param name=\"Revision\" value=\"2\">\n" );
		  out.println("</object>\n" );
		  out.println("\n" );
		  out.println("<!-- MeadCo ScriptX -->\n" );
		  out.println("<object viewastext id=\"factory\" style=\"display:none\"\n" );
		  out.println(" classid=\"clsid:1663ed61-23eb-11d2-b92f-008048fdd814\"\n" );
		  out.println(" codebase=\"/graphics/smsx.cab#Version=6,1,429,14\">\n" );
		  out.println("</object>\n" );
		  out.println("<script defer>\n" );
		  out.println(" function window.onload()\n" );
		  out.println(" {\n" );
		  out.println("     factory.printing.header = \"" + Notes.PRINT_REPORT_HEADER + "\"\n" );
		  out.println("     factory.printing.footer = \"" + Notes.PRINT_REPORT_FOOTER + "\"\n" );
		  out.println("     factory.printing.leftMargin = " + Notes.PRINT_REPORT_LEFTMARGIN + "\n" );
		  out.println("     factory.printing.topMargin =" + Notes.PRINT_REPORT_TOPMARGIN + "\n" );
		  out.println("     factory.printing.rightMargin = " + Notes.PRINT_REPORT_RIGHTMARGIN + "\n" );
		  out.println("     factory.printing.bottomMargin = " + Notes.PRINT_REPORT_BOTTOMMARGIN + "\n" );
		  out.println("     factory.printing.portrait=" + strPortrait + ";//横向\n" );
		  out.println("     factory.printing.paperSize=\"" + strPaperSize + "\";\n" );
		  out.println(" }\n" );
		  out.println(" function document.onkeydown(DnEvents)\n" );
		  out.println(" {\n" );
		  out.println("     k =  window.event.keyCode;\n" );
		  out.println("     if(k==13)\n" );
		  out.println("     {\n" );
		  out.println("         if (confirm(\"是否打印？\"))\n" );
		  out.println("         {\n" );
		  out.println("             //factory.printing.printer=\"\";可以写打印机的名称\n" );
		  out.println("             factory.printing.Print(true);\n" );
		  out.println("         }\n" );
		  out.println("     }\n" );
		  out.println("     if(k==32)\n" );
		  out.println("     {\n" );
		  out.println("         if (confirm(\"是否预览？\"))\n" );
		  out.println("         {\n" );
		  out.println("             //factory.printing.printer=\"\";可以写打印机的名称\n" );
		  out.println("             factory.printing.Preview();\n" );
		  out.println("         }\n" );
		  out.println("     }\n" );
		  out.println("}    \n" );
		  out.println("</script>\n" );
		  out.println("\n" );
		*/
	}
	/**
	 * 显示报表的表头（用于B5凭证纸上的报表打印，页面边距从数据库中取得）
	 * @param out
	 * @param bPortrait 是否横向
	 * @param strPapaerSize 纸张大小
	 * @param strPrinter 打印机名称 - 保留
	 * @param nNo 报表编号 - 保留
	 * @param strAbstract 打印备注 以示和正常报表打印区分
	 */
	public static void showLOANPrintHead(JspWriter out, boolean bPortrait, String strPaperSize, String strPrinter, int nNo, SessionMng sessionMng)
		throws Exception
	{
		String strPortrait = "";
		if (bPortrait)
		{
			strPortrait = "true";
		}
		else
		{
			strPortrait = "false";
		}
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title></title>\n");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
		out.println("\n");
		out.println("<link rel=\"stylesheet\" href=\"/webloan/template.css\" type=\"text/css\">\n");
		out.println("<style type=\"text/css\">\n");
		out.println("<!--\n");
		out.println(".table1 {  border: 1px #000000 solid}\n");
		out.println(
			".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}\n");
		out.println(
			".td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}\n");
		out.println(
			".td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px}\n");
		out.println(
			".td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px}\n");
		out.println(
			".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}\n");
		out.println(
			".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}\n");
		out.println(
			".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}\n");
		out.println(
			".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}\n");
		out.println(
			".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}\n");
		out.println(
			".td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}\n");
		out.println(
			".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}\n");
		out.println(
			".small-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}\n");
		out.println(
			".small-td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}\n");
		out.println(
			".small-td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px}\n");
		out.println(
			".small-td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px}\n");
		out.println(
			".small-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}\n");
		out.println(
			".small-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}\n");
		out.println(
			".small-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}\n");
		out.println(
			".small-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}\n");
		out.println(
			".small-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}\n");
		out.println(
			".small-td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}\n");
		out.println(
			".small-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}\n");
		out.println(
			"td {font-size: 12px}\n");
		out.println(
			".f14 {	font-size: 14px}");
		out.println("-->\n");
		out.println("</style>\n");
		out.println("</head>\n");
		
		showPrintScriptObject(out);
		
		out.println("<script defer>\n");
		out.println(" function window.onload()\n");
		out.println(" {\n");
		//added by xierui
		Connection conn1 = Database.getConnection();
		PreparedStatement ps1 = conn1.prepareStatement("select nPrintTop,nPrintLeft from Office where id=" + sessionMng.m_lOfficeID);
		ResultSet rs1 = ps1.executeQuery();
		int nPrintTop = 50;
		int nPrintLeft = 40;
		if (rs1.next())
		{
			nPrintTop = rs1.getInt("nPrintTop");
			nPrintLeft = rs1.getInt("nPrintLeft");
		}
		rs1.close();
		rs1 = null;
		ps1.close();
		ps1 = null;
		conn1.close();
		conn1 = null;
		out.println(" PageSettor.regist(\"isoftstone\"); \n");
		out.println(" PageSettor.setWebBrowser(wb); \n");
		out.println(" PageSettor.Header = \"\";\n");
		out.println(" PageSettor.Footer = '&b第&p页/共&P页&b';\n");
		out.println(" PageSettor.LeftMargin =" + nPrintLeft + "; \n");
		out.println(" PageSettor.rightMargin = 0; \n");
		out.println(" PageSettor.topMargin = " + nPrintTop + "; \n");
		out.println(" PageSettor.bottomMargin = 0; \n");
		out.println(" PageSettor.Orientation=2; \n");
		out.println(" PageSettor.PaperSize='A4'; \n");
		out.println(" PageSettor.SetUp(); \n");
		//end added
		out.println(" }\n");
		out.println(" function document.onkeydown(DnEvents)\n");
		out.println(" {\n");
		out.println("     k =  window.event.keyCode;\n");
		out.println("     if(k==13)\n");
		out.println("     {\n");
		out.println("         if (confirm(\"是否打印？\"))\n");
		out.println("         {\n");
		out.println("             PageSettor.PrintDocument(true);\n");
		out.println("         }\n");
		out.println("     }\n");
		out.println("     if(k==32)\n");
		out.println("     {\n");
		out.println("         if (confirm(\"是否预览？\"))\n");
		out.println("         {\n");
		out.println("             PageSettor.PreView();\n");
		out.println("         }\n");
		out.println("     }\n");
		out.println("}    \n");
		out.println("</script>\n");
		out.println("\n");
	}

}

/*
 * Created on 2003-8-14
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.print.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.print.dataentity.PrintSettingInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.SessionMng;


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
	PageSettor.regist("isoftstone") 为注册
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
			out.println(".table-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 2px}\n");
			out.println(".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-topleftright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 1px;FONT-SIZE: 12px}\n");
			out.println(".td-topleftrightbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}\n");
			out.println(".td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}\n");
			out.println(".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-toprightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}\n");
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
	

	/**
	 * 打印控件输出打印属性
	 * @param out
	 * @param sessionMng
	 * @param userPrintInfo 用户设置的一些打印属性，主要是顶空、左空 
	 * 						优先使用用户的设置
	 * 						注意：1、当边距都为0时，需要设置如：userSettingInfo.setBottomMargin(0);
	 * 							  2、页眉页脚不需要显示时，需要设置如：userPrintInfo.setHeader("noShow");
	 * @param strPaperSize 纸张大小 如："A4"
	 * @param lPortrait 1：纵向  2：横向
	 * @param isPrint 是否需要自动弹出打印机选择窗口
	 */
	private static void showPrintScriptDefer(JspWriter out,SessionMng sessionMng,PrintSettingInfo userPrintInfo,String strPaperSize,long lPortrait,boolean isPrint) throws Exception
	{
		try
		{

			PrintSettingBean bean = new PrintSettingBean();
			PrintSettingInfo printSettingInfo = new PrintSettingInfo();
			//从数据库中查询 改办事处、币种、模块设置的打印参数
			printSettingInfo = bean.queryPrintSettingInfo(sessionMng);
			
			//控件注册
			out.println(" <OBJECT ID=\"PageSettor\" \n");
			out.println(" CLASSID=\"CLSID:C8D19F3D-3A3A-458C-89BA-64E6FF51C327\" \n");
			out.println(" CODEBASE=\"/webscript/ISSlib.CAB#version=1,0,0,0\"> \n");
			out.println(" </OBJECT> \n");
			out.println(" <OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb> \n");
			out.println(" </OBJECT> \n");
			//控件设置属性信息
			out.println(" <script defer> ");
			out.println(" function window.onload() ");
			out.println(" { \n");
			out.println(" PageSettor.regist(\"isoftstone\"); ");
			out.println(" PageSettor.setWebBrowser(wb); ");
			out.println(" PageSettor.PaperSize = \"" + strPaperSize + "\"; ");
			out.println(" PageSettor.Orientation = "+ lPortrait +"; ");//1纵向2横向
			
			if(userPrintInfo.getFooter() != null && userPrintInfo.getFooter().length() >0)
			{
				if(userPrintInfo.getFooter().equalsIgnoreCase("noShow"))
				{
					out.println(" PageSettor.Footer = ''; ");
				}
				else
				{
					out.println(" PageSettor.Footer = '"+ userPrintInfo.getFooter() +"'; ");
				}
			}
			else
			{
				out.println(" PageSettor.Footer = '"+ printSettingInfo.getFooter() +"'; ");
			}
			if(userPrintInfo.getHeader() != null && userPrintInfo.getHeader().length() >0)
			{
				if(userPrintInfo.getHeader().equalsIgnoreCase("noShow"))
				{
					out.println(" PageSettor.Header = ''; ");
				}
				else
				{
					out.println(" PageSettor.Header = '"+ userPrintInfo.getHeader() +"'; ");
				}
			}
			else
			{
				out.println(" PageSettor.Header = '"+ printSettingInfo.getHeader() +"'; ");
			}
			if(userPrintInfo.getLeftMargin() >= 0)
			{
				out.println(" PageSettor.LeftMargin =" + userPrintInfo.getLeftMargin() + "; ");
			}
			else
			{
				out.println(" PageSettor.LeftMargin =" + printSettingInfo.getLeftMargin() + "; ");
			}
			if(userPrintInfo.getRightMargin() >= 0)
			{
				out.println(" PageSettor.rightMargin = "+ userPrintInfo.getRightMargin() +"; ");
			}
			else
			{
				out.println(" PageSettor.rightMargin = "+ printSettingInfo.getRightMargin() +"; ");
			}
			if(userPrintInfo.getTopMargin() >= 0)
			{
				out.println(" PageSettor.topMargin = " + userPrintInfo.getTopMargin() + "; ");
			}
			else
			{
				out.println(" PageSettor.topMargin = " + printSettingInfo.getTopMargin() + "; ");
			}
			if(userPrintInfo.getBottomMargin() >= 0)
			{
				out.println(" PageSettor.bottomMargin = " + userPrintInfo.getBottomMargin() + "; ");
			}
			else
			{
				out.println(" PageSettor.bottomMargin = " + printSettingInfo.getBottomMargin() + "; ");
			}
			
			
			out.println(" PageSettor.SetUp(); ");
			if(isPrint)
			{
				out.println("  PageSettor.PrintDocument(true); ");
			}
			out.println(" } ");
			out.println(" </script> \n");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 打印控件输出打印属性 add by wjliu --2007-4-11
	 * 华油服所加,日结科目汇总表、科目对账单,开户行余额汇总查询打印、打印账户余额表、结息汇总表，科目对账单,账户金额查询,这些打印需保持现在的边距，左边距约5左右(这几个写在程序里)
	 * 而其余的打印，若用A4的纸张打印，需要左边距约25，而上边距需要50(这些数据从数据库里取)
	 * @param out
	 * @param sessionMng
	 * @param userPrintInfo 用户设置的一些打印属性，主要是顶空、左空 
	 * 						优先使用用户的设置
	 * 						注意：1、当边距都为0时，需要设置如：userSettingInfo.setBottomMargin(0);
	 * 							  2、页眉页脚不需要显示时，需要设置如：userPrintInfo.setHeader("noShow");
	 * @param strPaperSize 纸张大小 如："A4"
	 * @param lPortrait 1：纵向  2：横向
	 * @param isPrint 是否需要自动弹出打印机选择窗口 
	 */
	private static void HYFshowPrintScriptDefer(JspWriter out,SessionMng sessionMng,PrintSettingInfo userPrintInfo,String strPaperSize,long lPortrait,boolean isPrint) throws Exception
	{
		try 
		{

			PrintSettingBean bean = new PrintSettingBean();
			PrintSettingInfo printSettingInfo = new PrintSettingInfo();
			//从数据库中查询 改办事处、币种、模块设置的打印参数
			printSettingInfo = bean.queryPrintSettingInfo(sessionMng);
			printSettingInfo.setLeftMargin(0);
			printSettingInfo.setHeader("");
			printSettingInfo.setRightMargin(0);
			printSettingInfo.setBottomMargin(0);
			printSettingInfo.setFooter("");
			
			
			//控件注册
			out.println(" <OBJECT ID=\"PageSettor\" \n");
			out.println(" CLASSID=\"CLSID:C8D19F3D-3A3A-458C-89BA-64E6FF51C327\" \n");
			out.println(" CODEBASE=\"/webscript/ISSlib.CAB#version=1,0,0,0\"> \n");
			out.println(" </OBJECT> \n");
			out.println(" <OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb> \n");
			out.println(" </OBJECT> \n");
			//控件设置属性信息
			out.println(" <script defer> ");
			out.println(" function window.onload() ");
			out.println(" { \n");
			out.println(" PageSettor.regist(\"isoftstone\"); ");
			out.println(" PageSettor.setWebBrowser(wb); ");
			out.println(" PageSettor.PaperSize = \"" + strPaperSize + "\"; ");
			out.println(" PageSettor.Orientation = "+ lPortrait +"; ");//1纵向2横向
			
			if(userPrintInfo.getFooter() != null && userPrintInfo.getFooter().length() >0)
			{
				if(userPrintInfo.getFooter().equalsIgnoreCase("noShow"))
				{
					out.println(" PageSettor.Footer = ''; ");
				}
				else
				{
					out.println(" PageSettor.Footer = '"+ userPrintInfo.getFooter() +"'; ");
				}
			}
			else
			{
				out.println(" PageSettor.Footer = '"+ printSettingInfo.getFooter() +"'; ");
			}
			if(userPrintInfo.getHeader() != null && userPrintInfo.getHeader().length() >0)
			{
				if(userPrintInfo.getHeader().equalsIgnoreCase("noShow"))
				{
					out.println(" PageSettor.Header = ''; ");
				}
				else
				{
					out.println(" PageSettor.Header = '"+ userPrintInfo.getHeader() +"'; ");
				}
			}
			else
			{
				out.println(" PageSettor.Header = '"+ printSettingInfo.getHeader() +"'; ");
			}
			if(userPrintInfo.getLeftMargin() >= 0)
			{
				out.println(" PageSettor.LeftMargin =" + userPrintInfo.getLeftMargin() + "; ");
			}
			else
			{
				out.println(" PageSettor.LeftMargin =" + printSettingInfo.getLeftMargin() + "; ");
			}
			if(userPrintInfo.getRightMargin() >= 0)
			{
				out.println(" PageSettor.rightMargin = "+ userPrintInfo.getRightMargin() +"; ");
			}
			else
			{
				out.println(" PageSettor.rightMargin = "+ printSettingInfo.getRightMargin() +"; ");
			}
			if(userPrintInfo.getTopMargin() >= 0)
			{
				out.println(" PageSettor.topMargin = " + userPrintInfo.getTopMargin() + "; ");
			}
			else
			{
				out.println(" PageSettor.topMargin = " + printSettingInfo.getTopMargin() + "; ");
			}
			if(userPrintInfo.getBottomMargin() >= 0)
			{
				out.println(" PageSettor.bottomMargin = " + userPrintInfo.getBottomMargin() + "; ");
			}
			else
			{
				out.println(" PageSettor.bottomMargin = " + printSettingInfo.getBottomMargin() + "; ");
			}
			
			
			out.println(" PageSettor.SetUp(); ");
			if(isPrint)
			{
				out.println("  PageSettor.PrintDocument(true); ");
			}
			out.println(" } ");
			out.println(" </script> \n");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 凭证打印使用 （包括套打、全打）
	 * 1:打印凭证类型  =1 为套打凭证，页边距为0
	 * 2:打印凭证类型 <>1 为全打凭证，页边距从数据库的设置中取得
	 */
	public static void showPrintVoucher(JspWriter out,SessionMng sessionMng,long lVoucherTypeID) throws Exception
	{
		try
		{
			PrintSettingInfo userSettingInfo = new PrintSettingInfo();
			//套打凭证不需要显示页眉页脚
			userSettingInfo.setHeader("noShow");
			userSettingInfo.setFooter("noShow");
			
			if(lVoucherTypeID == 1)//套打
			{
				//由于套打的页面边距和纸张大小都是从套打模板设置中取得
				userSettingInfo.setTopMargin(0);
				userSettingInfo.setBottomMargin(0);
				userSettingInfo.setLeftMargin(0);
				userSettingInfo.setRightMargin(0);
				
				//套打 纸张统一为：A5 横向 跳出打印机对话框
				showPrintScriptDefer(out,sessionMng,userSettingInfo,"A5",2,true);
			}
			else
			{
				//全打凭证 纸张统一为：A5 横向 跳出打印机对话框
				showPrintScriptDefer(out,sessionMng,userSettingInfo,"A5",2,true);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 报表打印
	 * 显示报表需要的css样式表文件 （用于任意纸上的报表打印，页面边距从数据库中取得）
	 * @param out
	 * @param sessionMng
	 * @param strPaperSize
	 * @param lPortrait
	 * @param userSettingInfo
	 *   这个参数是用户自定义的页眉页脚和边距顶距，当这个参数为空的时候将从数据库取得设置参数
	 * @throws Exception
	 */
	public static void showPrintReport(JspWriter out,SessionMng sessionMng,String strPaperSize,long lPortrait,PrintSettingInfo userSettingInfo) throws Exception
	{
		//显示报表需要使用的css样式表
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title></title>\n");
		showCSS(out);
		out.println("</head>\n");
		//设置参数为空的时候，使用默认的设置
		if(userSettingInfo==null){
		    userSettingInfo=new PrintSettingInfo(); 
		}
		//输出 打印控件的主体部分
		//边距都从数据库中取得，纸张大小、方向由用户输入
		showPrintScriptDefer(out,sessionMng,userSettingInfo,strPaperSize,lPortrait,false);
		
		//输出回车打印 空格预览的JavaScript
		out.println(" <script defer> \n");
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
	 * 报表打印
	 * 显示报表需要的css样式表文件 （用于任意纸上的报表打印，页面边距从数据库中取得）
	 * @param out
	 * @param sessionMng
	 * @param strPaperSize
	 * @param lPortrait
	 * @throws Exception
	 */
	public static void showPrintReport(JspWriter out,SessionMng sessionMng,String strPaperSize,long lPortrait) throws Exception
	{
		//显示报表需要使用的css样式表
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title></title>\n");
		showCSS(out);
		out.println("</head>\n");
		
		//输出 打印控件的主体部分
		//边距都从数据库中取得，纸张大小、方向由用户输入
		PrintSettingInfo userSettingInfo = new PrintSettingInfo();
		showPrintScriptDefer(out,sessionMng,userSettingInfo,strPaperSize,lPortrait,false);
		
		//输出回车打印 空格预览的JavaScript
		out.println(" <script defer> \n");
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
	 * 报表打印
	 * 显示报表需要的css样式表文件 （用于任意纸上的报表打印，页面边距从数据库中取得）
	 * @param out
	 * @param sessionMng
	 * @param strPaperSize
	 * @param lPortrait
	 * @throws Exception
	 * @author fhx  2010-6-22
	 */
	public static void showPrintReport2(JspWriter out,SessionMng sessionMng,String strPaperSize,long lPortrait) throws Exception
	{
		//显示报表需要使用的css样式表
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title></title>\n");
		showCSS(out);
		out.println("</head>\n");
		
		//输出 打印控件的主体部分
		//边距都从数据库中取得，纸张大小、方向由用户输入
		PrintSettingInfo userSettingInfo = new PrintSettingInfo();
//		showPrintScriptDefer(out,sessionMng,userSettingInfo,strPaperSize,lPortrait,false);
		
		//输出回车打印 空格预览的JavaScript
		out.println(" <script defer> \n");
		out.println("	function document.onkeydown(DnEvents)\n");
		out.println("	{\n");
		out.println("		k =  window.event.keyCode;\n");
		out.println("		if(k==13)\n");
		out.println("		{\n");
		out.println("			if (confirm(\"是否打印？\"))\n");
		out.println("			{\n");
		out.println("				printit();\n");
		out.println("			}\n");
		out.println("		}\n");
		out.println("		if(k==32)\n");
		out.println("		{\n");
		out.println("			if (confirm(\"是否预览？\"))\n");
		out.println("			{\n");
		out.println("				printpreview();\n");
		out.println("			}\n");
		out.println("		}\n");
		out.println("}	\n");
		out.println("</script>\n");
		out.println("\n");

		out.println("<SCRIPT type='text/javascript'>");
		out.println("var hkey_root,hkey_path,hkey_key;");
		out.println("hkey_path='\\Software\\Microsoft\\Internet Explorer\\PageSetup';");
		out.println("function pagesetup_null(){");
		out.println("try{");
		out.println("var RegWsh = new ActiveXObject('WScript.Shell');");
		out.println("hkey_key='\\header';");
		out.println("RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,'');");
		out.println("hkey_key='\\footer';");
		out.println("RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,'');");
		out.println("}catch(e){}");
		out.println("}");
		out.println("");
		out.println("function pagesetup_default(){");
		out.println("try{");
		out.println("var RegWsh = new ActiveXObject('WScript.Shell');");
		out.println("hkey_key='\\header' ;");
		out.println("RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,'&w&b页码,&p/&P');");
		out.println("hkey_key='\\footer';");
		out.println("RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,'&u&b&d');");
		out.println("}catch(e){}");
		out.println("}");
		out.println("function printsetup(){  ");
		out.println("wb.execwb(8,1);");
		out.println("} ");
		out.println("");		
		out.println("function printpreview(){ ");
		out.println("wb.execwb(7,1);");
		out.println("}");
		out.println("");
		out.println("function printit() { ");
		out.println("wb.execwb(6,1);");
		out.println("} ");
		out.println("</SCRIPT>");
		out.println("");
		out.println("<style media='print'>");
		out.println("<!--");
		out.println(".Noprint{display:none;}");
		out.println(".PageNext{page-break-after:always;}");
		out.println("-->");
		out.println("</style>");
		out.println("");
		out.println("<style type='text/css'>");
		out.println("<!--");
		out.println(".STYLE1 {font-size: 12px}");
		out.println("-->");
		out.println("</style>");
		out.println("");
		out.println("<OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>");
		out.println("");


	}
	
	/**
	 * 报表打印
	 * 显示报表需要的css样式表文件 （用于任意纸上的报表打印，页面边距从数据库中取得）
	 * add by wjliu --2007-4-11
	 * 华油服所加,日结科目汇总表、开户行余额汇总查询打印、打印账户余额表、结息汇总表，这些打印需保持现在的边距，左边距约5左右(这几个写在程序里)
	 * 而其余的打印，若用A4的纸张打印，需要左边距约25，而上边距需要50(这些数据从数据库里取)
	 * @param out
	 * @param sessionMng
	 * @param strPaperSize
	 * @param lPortrait
	 * @throws Exception
	 */
	public static void HYFshowPrintReport(JspWriter out,SessionMng sessionMng,String strPaperSize,long lPortrait) throws Exception
	{
		//显示报表需要使用的css样式表
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title></title>\n");
		showCSS(out);
		out.println("</head>\n");
		
		//输出 打印控件的主体部分
		//边距都从数据库中取得，纸张大小、方向由用户输入
		PrintSettingInfo userSettingInfo = new PrintSettingInfo();
		HYFshowPrintScriptDefer(out,sessionMng,userSettingInfo,strPaperSize,lPortrait,false);
		
		//输出回车打印 空格预览的JavaScript
		out.println(" <script defer> \n");
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
	 * 报表打印
	 * 显示报表需要的css样式表文件 （用于任意纸上的报表打印，页面边距从数据库中取得）
	 * @param out
	 * @param sessionMng
	 * @param strPaperSize
	 * @param lPortrait
	 * @throws Exception
	 */
	public static void showPrintReport(JspWriter out,SessionMng sessionMng,String strPaperSize,long lPortrait,boolean bIsPrint) throws Exception
	{
		//显示报表需要使用的css样式表
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title></title>\n");
		showCSS(out);
		out.println("</head>\n");
		
		//输出 打印控件的主体部分
		//边距都从数据库中取得，纸张大小、方向由用户输入
		PrintSettingInfo userSettingInfo = new PrintSettingInfo();
		showPrintScriptDefer(out,sessionMng,userSettingInfo,strPaperSize,lPortrait,bIsPrint);
		
		//输出回车打印 空格预览的JavaScript
		out.println(" <script defer> \n");
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
		
		showExcelCSS(out);
		 
		out.println("</head>\n");
		}
	
	
	/*
	 * 显示 报表导出excel的CSS样式
	 */
	private static void showExcelCSS(JspWriter out)
	{
		try
		{
			out.println("<meta http-equiv=\"Content-Type\" content=\"application/msexcel; charset=gb2312\">\n");
			out.println("\n");
			out.println("<style type=\"text/css\">\n");
			out.println("<!--\n");
			out.println(".table1 {  border: 1px #000000 solid}\n");
			out.println(".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
			out.println(".td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
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
	
}

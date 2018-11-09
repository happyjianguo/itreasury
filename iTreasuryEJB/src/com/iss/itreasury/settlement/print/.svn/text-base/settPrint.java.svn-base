/*
 * Created on 2005-1-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.print;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.print.bizlogic.IPrint;
import com.iss.itreasury.print.dataentity.PrintSettingInfo;
import com.iss.itreasury.util.SessionMng;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class settPrint  extends IPrint
{

	/*
	 * 显示 报表的CSS样式
	 */
	private static void showCSS(JspWriter out)   
	{
		try
		{
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
			out.println("\n");
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
	

	  private static void showPrintScriptDefer(JspWriter paramJspWriter, String paramString, long paramLong, boolean paramBoolean)
	  {
	    try
	    {
	      paramJspWriter.println(" <OBJECT ID=\"PageSettor\" \n");
	      paramJspWriter.println(" CLASSID=\"CLSID:C8D19F3D-3A3A-458C-89BA-64E6FF51C327\" \n");
	      paramJspWriter.println(" CODEBASE=\"/webscript/ISSlib.CAB#version=1,0,0,0\"> \n");
	      paramJspWriter.println(" </OBJECT> \n");
	      paramJspWriter.println(" <OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb> \n");
	      paramJspWriter.println(" </OBJECT> \n");
	      paramJspWriter.println(" <script defer> ");
	      paramJspWriter.println(" function window.onload() ");
	      paramJspWriter.println(" { \n");
	      paramJspWriter.println(" PageSettor.regist(\"isoftstone\"); ");
	      paramJspWriter.println(" PageSettor.setWebBrowser(wb); ");
	      paramJspWriter.println(" PageSettor.PaperSize = \"" + paramString + "\"; ");
	      paramJspWriter.println(" PageSettor.Orientation = " + paramLong + "; ");
	      paramJspWriter.println(" PageSettor.Footer = ''; ");
	      paramJspWriter.println(" PageSettor.Header = '&b,&p/&P'; ");
	      paramJspWriter.println(" PageSettor.LeftMargin =0.9; ");
	      paramJspWriter.println(" PageSettor.rightMargin = 0.2; ");
	      paramJspWriter.println(" PageSettor.topMargin = 0.8; ");
	      paramJspWriter.println(" PageSettor.bottomMargin = 0.5; ");
	      paramJspWriter.println(" PageSettor.SetUp(); ");
	      if (paramBoolean)
	        paramJspWriter.println("  PageSettor.PrintDocument(true); ");
	      paramJspWriter.println(" } ");
	      paramJspWriter.println("\tfunction document.onkeydown(DnEvents)\n");
	      paramJspWriter.println("\t{\n");
	      paramJspWriter.println("\t\tk =  window.event.keyCode;\n");
	      paramJspWriter.println("\t\tif(k==13)\n");
	      paramJspWriter.println("\t\t{\n");
	      paramJspWriter.println("\t\t\tif (confirm(\"是否打印？\"))\n");
	      paramJspWriter.println("\t\t\t{\n");
	      paramJspWriter.println("\t\t\t\tPageSettor.PrintDocument(true);\n");
	      paramJspWriter.println("\t\t\t}\n");
	      paramJspWriter.println("\t\t}\n");
	      paramJspWriter.println("\t\tif(k==32)\n");
	      paramJspWriter.println("\t\t{\n");
	      paramJspWriter.println("\t\t\tif (confirm(\"是否预览？\"))\n");
	      paramJspWriter.println("\t\t\t{\n");
	      paramJspWriter.println("\t\t\t\tPageSettor.PreView();\n");
	      paramJspWriter.println("\t\t\t}\n");
	      paramJspWriter.println("\t\t}\n");
	      paramJspWriter.println("}\t\n");
	      paramJspWriter.println(" </script> \n");
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace();
	    }
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
//		PrintSettingInfo userSettingInfo = new PrintSettingInfo();
//		showPrintScriptDefer(out,sessionMng,userSettingInfo,strPaperSize,lPortrait,false);
		  showPrintScriptDefer(out,strPaperSize,lPortrait,false);
	  }
	  
	  public static void showPrintReport(JspWriter out,SessionMng sessionMng,String strPaperSize,long lPortrait,PrintSettingInfo userSettingInfo) throws Exception
	{
		  showPrintReport(out,sessionMng,strPaperSize,lPortrait);
	}
	  
	  public static void showPrintReport2(JspWriter out,SessionMng sessionMng,String strPaperSize,long lPortrait) throws Exception
	{
		  showPrintReport(out,sessionMng,strPaperSize,lPortrait);
	}
	  
	  public static void HYFshowPrintReport(JspWriter out,SessionMng sessionMng,String strPaperSize,long lPortrait) throws Exception
	{
		  showPrintReport(out,sessionMng,strPaperSize,lPortrait);
	}
	  
	  public static void showPrintReport(JspWriter out,SessionMng sessionMng,String strPaperSize,long lPortrait,boolean bIsPrint) throws Exception
	{
		//显示报表需要使用的css样式表
		  out.println("<html>\n");
		  out.println("<head>\n");
		  out.println("<title></title>\n");
		  showCSS(out);
		  out.println("</head>\n");
		  showPrintScriptDefer(out,strPaperSize,lPortrait,bIsPrint);
	}
}

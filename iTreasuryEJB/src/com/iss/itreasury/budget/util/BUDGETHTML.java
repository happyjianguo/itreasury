/*
 * Created on 2003-8-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.budget.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.budget.templet.dataentity.DisplayTemplateDetailInfo;
import com.iss.itreasury.budget.templet.dataentity.DisplayTemplateInfo;
import com.iss.itreasury.dataentity.HtmlHeaderInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.SessionMng;
/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BUDGETHTML
{
	private static String beginUsefulBalanceRowNo = "004";
	private static String beginBalanceRowNo = "001";
	private static String formRowNo = "005";
	private static String toRowNo = "006";
	private static String endUsefulBalanceRowNo = "012";
	private static String endBalanceRowNo = "011";
	private static String depositRowNo = "002";
	private static String assureRowNo = "003";
	
	public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle) throws IException, IOException
	{
		String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.BUDGET) + "RemindMsg.jsp";
		String strStatus = "";
		if (sessionMng.isLogin())
		{
			try
			{
				strStatus =
					sessionMng.m_strUserName
						+ "  "
						+ Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)
						+ "  "
						+ Env.getClientName()
						+ "  "
						+ Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)
						+ "  ";
			}
			catch (Exception exp)
			{
				System.out.println(exp.toString());
			}
		}
		//////////////////////// 
		String strProject = "iTreasuryPro";
		//
		HtmlHeaderInfo htmlHeader = new HtmlHeaderInfo();
		htmlHeader.setJspWriter(out);
		htmlHeader.setSessionMng(sessionMng);
		htmlHeader.setTitle(strTitle);
		htmlHeader.setShowMenu(1);
		htmlHeader.setRemindURL(strRemindURL);
		htmlHeader.setStatus(strStatus);
		htmlHeader.setProjectName(strProject);
		htmlHeader.setTitle(strProject);
		// 
		displyHtmlHeader(htmlHeader);
	}
	public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle, String status) throws IException, IOException
	{
		String strRemindURL = "";
		String strStatus = "";
		//		EndDayProcess process = new EndDayProcess();
		strStatus = status == null ? "" : status;
		//////////////////////// 
		String strProject = "iTreasuryPro";
		//
		HtmlHeaderInfo htmlHeader = new HtmlHeaderInfo();
		htmlHeader.setJspWriter(out);
		htmlHeader.setSessionMng(sessionMng);
		htmlHeader.setTitle(strTitle);
		htmlHeader.setShowMenu(1);
		htmlHeader.setRemindURL(strRemindURL);
		htmlHeader.setStatus(strStatus);
		htmlHeader.setProjectName(strProject);
		htmlHeader.setTitle(strProject);
		//
		displyHtmlHeader(htmlHeader);
	}
	public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle, long lShowMenu) throws IException, IOException
	{
		String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.BUDGET) + "RemindMsg.jsp";
		String strStatus = "";
		String strProject = "iTreasuryPro";
		if (sessionMng.isLogin())
		{
			try
			{
				strStatus =
					sessionMng.m_strUserName
						+ "  "
						+ Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)
						+ "  "
						+ Env.getClientName()
						+ "  "
						+ Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)
						+ "  ";
			}
			catch (Exception exp)
			{
				System.out.println(exp.toString());
			}
		}
		//////////////////////// 
		HtmlHeaderInfo htmlHeader = new HtmlHeaderInfo();
		htmlHeader.setJspWriter(out);
		htmlHeader.setSessionMng(sessionMng);
		htmlHeader.setTitle(strTitle);
		htmlHeader.setShowMenu(lShowMenu);
		htmlHeader.setRemindURL(strRemindURL);
		htmlHeader.setStatus(strStatus);
		htmlHeader.setProjectName(strProject);
		htmlHeader.setTitle(strProject);
		//
		displyHtmlHeader(htmlHeader);
	}
	private static void displyHtmlHeader(HtmlHeaderInfo htmlHeader) throws IException, IOException
	{
		htmlHeader.getJspWriter().println("<html>\n");
		htmlHeader.getJspWriter().println("<head>\n");
		htmlHeader.getJspWriter().println("<title>" + htmlHeader.getTitle() + "</title>\n");
		htmlHeader.getJspWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
		htmlHeader.getJspWriter().println("<Script Language=\"Javascript\">\n");
		htmlHeader.getJspWriter().println("<!--\n");
		htmlHeader.getJspWriter().println("function sendandquit()\n");
		htmlHeader.getJspWriter().println("{\n");
		htmlHeader.getJspWriter().println(
			"	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");\n");
		htmlHeader.getJspWriter().println("}\n");
		htmlHeader.getJspWriter().println("function MM_goToURL() { //v3.0\n");
		htmlHeader.getJspWriter().println("  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;\n");
		htmlHeader.getJspWriter().println("  for (i=0; i<(args.length-1); i+=2) eval(args[i]+\".location='\"+args[i+1]+\"'\");\n");
		htmlHeader.getJspWriter().println("}\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("function MM_jumpMenu(targ,selObj,restore){ //v3.0\n");
		htmlHeader.getJspWriter().println("  eval(targ+\".location='\"+selObj.options[selObj.selectedIndex].value+\"'\");\n");
		htmlHeader.getJspWriter().println("  if (restore) selObj.selectedIndex=0;\n");
		htmlHeader.getJspWriter().println("}\n");
		htmlHeader.getJspWriter().println("//-->\n");
		htmlHeader.getJspWriter().println("</Script>\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("\n");

		htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">\n");
		htmlHeader.getJspWriter().println("</head>\n");
		htmlHeader.getJspWriter().println("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
		htmlHeader.getJspWriter().println("<script language=\"JavaScript1.2\" src=\"/itreasury/js/coolmenus4.js\"></script>\n");
		htmlHeader.getJspWriter().println("<script language=\"JavaScript1.2\" src=\"/itreasury/js/cm_addins.js\"></script>\n");
		htmlHeader.getJspWriter().println("<script language=javascript>\n");
		htmlHeader.getJspWriter().println("function showSending() \n");
		htmlHeader.getJspWriter().println("{\n");
		htmlHeader.getJspWriter().println(" gnIsShowSending=1;\n");
		htmlHeader.getJspWriter().println("	sending.style.visibility=\"visible\";\n");
		htmlHeader.getJspWriter().println("	cover.style.visibility=\"visible\";\n");
		htmlHeader.getJspWriter().println("}\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</script>\n");
		htmlHeader.getJspWriter().println(
			"<div id=\"sending\" style=\"position:absolute; top:320; left:20; z-index:10; visibility:hidden\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中, 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>\n");
		htmlHeader.getJspWriter().println(
			"<div id=\"cover\" style=\"position:absolute; top:0; left:0; z-index:9; visibility:hidden\"><TABLE WIDTH=100% height=900 BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><TD align=center><br></td></tr></table></div>\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader.getJspWriter().println("    <td>\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"pagetop\">\n");
		htmlHeader.getJspWriter().println("      <tr>\n");
		htmlHeader.getJspWriter().println(
			"        <td width=\"400\" nowrap class=\"headerLogo\"><img src=\"/itreasury/graphics/logo.gif\"><img src=\"/itreasury/graphics/logo_zjys.gif\"></td>\n");
		htmlHeader.getJspWriter().println("        <td class=\"welcome\">" + htmlHeader.getStatus() + "</td>\n");
		htmlHeader.getJspWriter().println("        <td width=\"187\" align=\"right\" nowrap class=\"headerHelp\"><table border=\"0\" cellpadding=\"4\" cellspacing=\"2\">\n");
		htmlHeader.getJspWriter().println("      <tr>\n");
		htmlHeader.getJspWriter().println("        <td align=\"center\" class=\"sys\">");
		if (htmlHeader.getSessionMng().m_lUserID > 0)
		{
			htmlHeader.getJspWriter().println(
				"          <a href=\""
					+ Env.getInstance().getURL(htmlHeader.getSessionMng().m_lModuleID)
					+ "Logout.jsp?control=view\" class=\"syslink\">退出登录</a> <a href=\""
					+ Env.getInstance().getURL(htmlHeader.getSessionMng().m_lModuleID)
					+ "S520.jsp?control=view\" class=\"syslink\">修改密码</a>");
		}
		htmlHeader.getJspWriter().println("        </td>\n");
		htmlHeader.getJspWriter().println("      </tr>\n");
		htmlHeader.getJspWriter().println("    </table></td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("</table>\n");
		htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader.getJspWriter().println("    <td height=\"24\" class=\"menuMain\">\n");
		htmlHeader.getJspWriter().println("      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		htmlHeader.getJspWriter().println("        <tr>\n");
		htmlHeader.getJspWriter().println("          <td>&nbsp;</td>\n");
		htmlHeader.getJspWriter().println("        </tr>\n");
		htmlHeader.getJspWriter().println("      </table>\n");
		htmlHeader.getJspWriter().println("    </td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader.getJspWriter().println("    <td height=\"2\" class=\"menuBl1\"><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
		htmlHeader.getJspWriter().println("   </tr>\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader.getJspWriter().println("    <td height=\"3\" class=\"menuBl2\"><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("</table>\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader.getJspWriter().println("    <td><img src=\"/itreasury/graphics/space.gif\" width=\"778\" height=\"1\"></td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader.getJspWriter().println("    <td height=\"20\">\n");
		htmlHeader.getJspWriter().println("      <iframe");
		htmlHeader.getJspWriter().println(" border=0 frameborder=0 framespacing=0");
		htmlHeader.getJspWriter().println(" marginheight=0");
		htmlHeader.getJspWriter().println(" marginwidth=0 name=new_date noResize scrolling=no height=\"20\"");
		htmlHeader.getJspWriter().println(" src=\"" + htmlHeader.getRemindURL() + "\" width=\"100%\" vspale=\"0\"></iframe>\n");
		htmlHeader.getJspWriter().println("    </td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("</table>\n");
		//
		htmlHeader.getJspWriter().println("<NOSCRIPT> \n");
		htmlHeader.getJspWriter().println("<TABLE cellSpacing=0 cellPadding=0 width=798 border=0>\n");
		htmlHeader.getJspWriter().println("  <TBODY> \n");
		htmlHeader.getJspWriter().println("  <TR vAlign=center>\n");
		htmlHeader.getJspWriter().println("          <TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\" \n");
		htmlHeader.getJspWriter().println("            width=\"720\"></TD></TR></TBODY></TABLE>\n");
		htmlHeader.getJspWriter().println("<table cellspacing=0 cellpadding=0 width=798 border=0>\n");
		htmlHeader.getJspWriter().println("  <tbody> \n");
		htmlHeader.getJspWriter().println("  <tr valign=center align=middle width=\"718\" bordercolor=\"#666666\"> \n");
		htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("  </tbody>\n");
		htmlHeader.getJspWriter().println("</table>\n");
		htmlHeader.getJspWriter().println("<TABLE cellSpacing=0 cellPadding=0 width=798 border=0>\n");
		htmlHeader.getJspWriter().println("  <TBODY> \n");
		htmlHeader.getJspWriter().println("  <TR>\n");
		htmlHeader.getJspWriter().println("          <TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\" \n");
		htmlHeader.getJspWriter().println("            width=\"720\"></TD></TR></TBODY></TABLE>\n");
		htmlHeader.getJspWriter().println("        </NOSCRIPT> \n");
		htmlHeader.getJspWriter().println("        \n");
		htmlHeader.getJspWriter().println("<p> \n");
		//
		if (htmlHeader.isShowMenu() == Constant.YesOrNo.YES)
		{
			htmlHeader.getJspWriter().println("  <script language=\"JavaScript1.2\">\n");
			htmlHeader.getJspWriter().println(
				"      showMenu(\"" + htmlHeader.getSessionMng().m_strMenu + "\",\"" + Env.getInstance().getURL(htmlHeader.getSessionMng().m_lModuleID) + "\");\n");
			htmlHeader.getJspWriter().println("  </script>\n");
		}
		htmlHeader.getJspWriter().println("      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#999999\">\n");
		htmlHeader.getJspWriter().println("        <tr>\n");
		htmlHeader.getJspWriter().println("          <td><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
		htmlHeader.getJspWriter().println("        </tr>\n");
		htmlHeader.getJspWriter().println("      </table>\n");
		htmlHeader.getJspWriter().println("    </td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("</table>\n");
		htmlHeader.getJspWriter().println("\n\n");
		htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader.getJspWriter().println("    <td width=\"10\">\n");
		htmlHeader.getJspWriter().println("    </td>\n");
		htmlHeader.getJspWriter().println("    <td><br>\n");
	

	}
	/**
	 * 显示一般信息(页面尾部)
	 * 修改BY胡志强(kewen) 2004-12-21
	 * @param out 输出
	 * @param lTypeID 操作类型
	 */
	public static void showHomeEnd(JspWriter out) throws IOException
	{
		showHomeEnd(out, -1);
	}
	/**
	 * 显示一般信息(页面尾部)
	 * 修改BY胡志强(kewen) 2006-04-13
	 * @param out 输出
	 * @param lTypeID 操作类型(1：打印、-1：其它类型)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID) throws IOException
	{
		long lOfficeID = 1;//默认值
		long lCurrencyID = 1;//默认值
		showHomeEnd(out, lTypeID, lOfficeID, lCurrencyID);
	}
	/**
	 * 显示一般信息(页面尾部)
	 * 修改BY kenny 2006-04-08
	 * @param out 输出
	 * @param lTypeID 操作类型(1：打印、-1：其它类型)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID, long lOfficeID, long lCurrencyID) throws IOException
	{
		String[] date = DataFormat.getDateString(Env.getSystemDate(lOfficeID, lCurrencyID)).split("-");
		//打印操作
		if (lTypeID == 1)
		{
			out.println("</body>\n");
			out.println("</html>");
		}
		else
		{
			out.println("    <br></td>\n");
			out.println("  </tr>\n");
			out.println("</table>\n");
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
			out.println("  <tr>\n");
			out.println("    <td class=\"pagefootTl\"><img src=\"/sefc/graphics/space.gif\" width=\"778\" height=\"1\"></td>\n");
			out.println("  </tr>\n");
			out.println("  <tr>\n");
			out.println(
				"    <td height=\"25\" class=\"pagefoot\"><font face=\"Arial, Helvetica, sans-serif\">&copy;</font> 2005-"+date[0]+" 软通动力 版权所有 "
					
					+ " Powered by 软通动力</td>\n");
			out.println("  </tr>\n");
			out.println("</table>\n");
			out.println("</body>\n");
			out.println("</html>");
		}
	}

	/**
	 * 显示一般信息(错误信息，消息等)
	 * 
	 * @param out
	 *            输出
	 * @param sessionMng
	 *            Session
	 * @param exception
	 *            异常
	 * @param request
	 * @param response
	 * @param strTitle
	 *            页面的Title
	 * @param lTypeID
	 *            是否带头
	 */
	public static void showExceptionMessage(
		JspWriter out,
		SessionMng sessionMng,
		IException exception,
		HttpServletRequest request,
		HttpServletResponse response,
		String strTitle,
		long lTypeID)
		throws Exception
	{
		showException(out, sessionMng, exception, request, response, strTitle, lTypeID, "");
	}
	public static void showMessage(
		JspWriter out,
		SessionMng sessionMng,
		HttpServletRequest request,
		HttpServletResponse response,
		String strTitle,
		long lTypeID,
		String strErroCode)
		throws Exception
	{
		showException(out, sessionMng, null, request, response, strTitle, lTypeID, strErroCode);
	}
	public static void showException(
		JspWriter out,
		SessionMng sessionMng,
		IException exception,
		HttpServletRequest request,
		HttpServletResponse response,
		String strTitle,
		long lTypeID,
		String strErroCode)
		throws Exception
	{
		String strErroMessage = "";
		if (strErroCode != null && strErroCode.length() > 0)
		{
			strErroMessage = IExceptionMessage.getExceptionMessage(strErroCode);
		}
		if (exception != null)
		{
			strErroMessage = IExceptionMessage.getExceptionMSG(exception);
		}
		System.out.println("**************IExceptionMessage:" + strErroMessage);
		if (lTypeID == Constant.RecordStatus.VALID)
		{
			showHomeHead(out, sessionMng, strTitle);
		}
		out.println("<TABLE align=center border=0 class=top height=217 width=\"27%\">\n");
		out.println("  <TBODY>\n");
		out.println("  <TR>\n");
		//out.println(" <TD class=FormTitle height=2 width=\"100%\"><B>" +
		// Env.getClientName() + "</B></TD></TR>\n");
		out.println("    <TD class=FormTitle height=2 width=\"100%\"><B></B></TD></TR>\n");
		out.println("  <TR>\n");
		out.println("      <TD height=190 vAlign=bottom width=\"100%\"> \n");
		out.println("        <TABLE align=center height=187 width=\"97%\">\n");
		out.println("        <TBODY>\n");
		out.println("        <TR>\n");
		out.println("          <TD height=40 vAlign=top width=\"96%\">\n");
		out.println("              <TABLE align=center border=1 borderColor=#999999 height=177 \n");
		out.println("            width=\"99%\">\n");
		out.println("                <TBODY> \n");
		out.println("                <TR borderColor=#D7DFE5 vAlign=center> \n");
		out.println("                  <TD height=162 colspan=\"3\" align=\"center\">" + strErroMessage + "</TD>\n");
		out.println("                </TR>\n");
		out.println("                </TBODY> \n");
		out.println("              </TABLE>\n");
		out.println("            </TD></TR></TBODY></TABLE></TD></TR>\n");
		out.println("  <TR>\n");
		out.println("    <TD height=11 vAlign=top width=\"100%\">\n");
		out.println("      <TABLE align=center height=17 width=\"97%\">\n");
		out.println("        <TBODY>\n");
		out.println("        <TR vAlign=center>\n");
		out.println("          <TD colSpan=6 height=40>\n");
		out.println("            <DIV align=center>\n");
		if (strErroMessage == null || strErroMessage.length() <= 0)
		{
			out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"返回\"> \n");
		}
		else
		{
			//out.println(" <INPUT type=\"button\" class=button name=\"goback\"
			// onclick=\"self.location.href=''\" value=\"返回\"> \n");
			//modified by qhzhou 2008-03-06-16
			if(strErroCode != null && strErroCode.length() > 0 && strErroCode.equals("Gen_E002")){
				out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:window.parent.location.href='"+Constant.SDCURL+"';\"  value=\"返回\"> \n");
			}else{
				out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"返回\"> \n");
			}
		}	
		out.println("            </DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>\n");
		out.println("<SCRIPT language=JavaScript>");
		out.println("   goback.focus();");
		out.println("</SCRIPT>");
		if (lTypeID == Constant.RecordStatus.VALID)
		{
			showHomeEnd(out);
		}
	}
	/**
	 * 通用下拉列表显示控件
	 * 
	 * @param out
	 *            输出
	 * @param strControlName
	 *            控件名称
	 * @param strSQL
	 *            数据库查询语句
	 * @param strDisplayField
	 *            显示字段
	 * @param strID
	 *            显示字段对应标识
	 * @param lSelectValue
	 *            被选择项对应值
	 * @param isNeedAll
	 *            是否需要”全部“项
	 * @param strProperty
	 *            下拉列表属性
	 * @param isNeedBlank
	 *            是否需要空白行
	 * @throws Exception
	 */
	public static void showCommonListControl(
		JspWriter out,
		String strControlName,
		String strSQL,
		String strDisplayField,
		String strID,
		long lSelectValue,
		boolean isNeedAll,
		String strProperty,
		boolean isNeedBlank)
		throws Exception
	{
		long lResult = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lTemplateID = 0;
		long lLobID = -1;
		Vector v = new Vector();
		try
		{
			//校验输入的sql语句里是否包含strDisplayField和strID
			if (strSQL.indexOf(strID) == -1 || strSQL.indexOf(strDisplayField) == -1)
			{
				System.out.println("传入的参数不正确！");
				return;
			}
			out.println("<select class=box name=\"" + strControlName + "\"" + strProperty + ">");
			if (isNeedBlank == true)
			{
				out.println("<option value=\"-1\">&nbsp;</option>");
			}
			con = Database.getConnection();
			String strTmp = new String();
			strTmp = strSQL;
			//System.out.println(strTmp);
			ps = con.prepareStatement(strTmp);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Log.print("rs.getLong(strID) = " + rs.getLong(strID));
				Log.print("lSelectValue = " + lSelectValue);
				if (rs.getLong(strID) == lSelectValue)
				{
					Log.print("有selected");
					out.println("<option value=\"" + rs.getLong(strID) + "\" selected>" + rs.getString(strDisplayField) + "</option>");
				}
				else
				{
					out.println("<option value=\"" + rs.getLong(strID) + "\">" + rs.getString(strDisplayField) + "</option>");
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			if (isNeedAll == true)
			{
				if (lSelectValue == 0)
				{
					out.println("<option value=\"0\" selected>全部</option>");
				}
				else
				{
					out.println("<option value=\"0\" >全部</option>");
				}
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			//System.out.println(" can not select OFFICE, because " + e);
			//throw e;
			Log.print("显示下拉列表异常：" + e.toString());
		}
		finally
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
	}
	/**
	 * 通用下拉列表显示控件
	 * 
	 * @param out
	 *            输出
	 * @param strControlName
	 *            控件名称
	 * @param strSQL
	 *            数据库查询语句
	 * @param strDisplayField
	 *            显示字段
	 * @param strID
	 *            显示字段对应标识
	 * @param strSelectValue
	 *            被选择项对应值
	 * @param isNeedAll
	 *            是否需要”全部“项
	 * @param strProperty
	 *            下拉列表属性
	 * @param isNeedBlank
	 *            是否需要空白行
	 * @throws Exception
	 */
	public static void showCommonListControl(
		JspWriter out,
		String strControlName,
		String strSQL,
		String strDisplayField,
		String strID,
		String strSelectValue,
		boolean isNeedAll,
		String strProperty,
		boolean isNeedBlank)
		throws Exception
	{
		long lResult = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lTemplateID = 0;
		long lLobID = -1;
		Vector v = new Vector();
		try
		{
			//校验输入的sql语句里是否包含strDisplayField和strID
			if (strSQL.indexOf(strID) == -1 || strSQL.indexOf(strDisplayField) == -1)
			{
				System.out.println("传入的参数不正确！");
				return;
			}
			out.println("<select class=box name=\"" + strControlName + "\"" + strProperty + ">");
			if (isNeedBlank == true)
			{
				out.println("<option value=\"-1\">&nbsp;</option>");
			}
			con = Database.getConnection();
			String strTmp = new String();
			strTmp = strSQL;
			//System.out.println(strTmp);
			ps = con.prepareStatement(strTmp);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Log.print("rs.getString(strID) = " + rs.getString(strID));
				Log.print("strSelectValue = " + strSelectValue);
				if (rs.getString(strID) != null && rs.getString(strID).equals(strSelectValue))
				{
					Log.print("有selected");
					out.println("<option value=\"" + rs.getString(strID) + "\" selected>" + rs.getString(strDisplayField) + "</option>");
				}
				else
				{
					out.println("<option value=\"" + rs.getString(strID) + "\">" + rs.getString(strDisplayField) + "</option>");
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			if (isNeedAll == true)
			{
				if (strSelectValue != null && strSelectValue.equals("0"))
				{
					out.println("<option value=\"0\" selected>全部</option>");
				}
				else
				{
					out.println("<option value=\"0\" >全部</option>");
				}
			}
			out.println("</select>");
		}
		catch (Exception e)
		{
			//System.out.println(" can not select OFFICE, because " + e);
			//throw e;
			Log.print("显示下拉列表异常：" + e.toString());
		}
		finally
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
	}
	/**
	 * 通用下拉列表显示控件
	 * 
	 * @param out
	 *            输出
	 * @param strControlName
	 *            控件名称
	 * @param strSQL
	 *            数据库查询语句
	 * @param strDisplayField
	 *            显示字段
	 * @param strID
	 *            显示字段对应标识
	 * @param lSelectValue
	 *            被选择项对应值
	 * @param isNeedAll
	 *            是否需要”全部“项
	 * @param strProperty
	 *            下拉列表属性
	 * @throws Exception
	 */
	public static void showCommonListControl(
		JspWriter out,
		String strControlName,
		String strSQL,
		String strDisplayField,
		String strID,
		long lSelectValue,
		boolean isNeedAll,
		String strProperty)
		throws Exception
	{
		showCommonListControl(out, strControlName, strSQL, strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
	}
	/**
	 * 通用查询数据的方法
	 * 
	 * @param strSQL
	 *            查询语句
	 * @param strField
	 *            返回值对应的字段
	 * @return Vector
	 * @throws Exception
	 */
	public static Vector getCommonSelectList(String strSQL, String strField) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector v = new Vector();
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Object oResult = new Object();
				oResult = rs.getObject(strField);
				v.add(oResult);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("发生数据库错误！");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception _ex)
			{
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		return v.size() > 0 ? v : null;
	}
	/**
	 * 校验客户端请求的有效性。 执行操作： －登录校验 －权限校验 －重复请求检查
	 * 
	 * @param out
	 * @param request
	 * @param response
	 * @return boolean
	 */
	public static boolean validateRequest(JspWriter out, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		boolean bResult = true;
		SessionMng sessionMng = null;
		try
		{
			HttpSession session = request.getSession(true);
			sessionMng = (SessionMng) session.getAttribute("sessionMng");
			//登录检测
			if (sessionMng.isLogin() == false)
			{
				showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E002");
				showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			//判断是否有权限
			if (sessionMng.hasRight(request) == false)
			{
				showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E002");
				showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			//处理重复请求
			String strTemp = (String) request.getAttribute("ActionID");
			Long lActionID = new Long(0);
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				try
				{
					lActionID = Long.valueOf(strTemp);
				}
				catch (Exception e)
				{
					Log.print("Request 中的ActionID 无效。");
				}
			}
			Long lTemp = (Long) session.getAttribute("ActionID");
			Log.print("Request 中的ActionID 是 ：" + lActionID);
			Log.print("Session 中的ActionID 是 ：" + lTemp);
			if (lActionID.longValue() != 0)
			{
				if (lTemp != null && lActionID.compareTo(lTemp) <= 0)
				{
					Log.print("重复请求！" + strTemp);
					showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E008");
					showHomeEnd(out);
					out.flush();
					bResult = false;
				}
				else
				{
					session.setAttribute("ActionID", lActionID);
					request.setAttribute("ActionID", String.valueOf(lActionID.longValue() + 1));
				}
			}
		}
		catch (IException ie)
		{
			showException(out, sessionMng, ie, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "");
			showHomeEnd(out);
			out.flush();
			ie.printStackTrace();
			bResult = false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			bResult = false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}
	/**
	 * 显示预算模板树
	 * @param out
	 * @param aryHeadString 表格上面的表头栏位（列名）	 
	 * @param vTemplate 摸板数据信息集
	 * @param lLevelCount 摸板树总共的级数
	 * @param aryLevelStatus 每级的默认状态（1,display;0,hide;例如：想让第一级默认显示，其它隐藏，则数组为{1}。
	 * 											如果想让前三级默认显示，这个树组应该是{1,1,1}
	 * @throws IException
	 */
	public static void showBudgetTemplate(JspWriter out, String[] aryHeadDateString, Vector vTemplate, long lLevelCount, long[] aryLevelStatus) throws IException
	{
		try
		{
			out.println("<!------------------------------------------------------------------------------------------>");
			out.println("					<input type=\"hidden\" name=\"IsNeedConvertToChinese\" value=\"-1\">");
			out.println("					<input type=\"hidden\" name=\"hdnTemplateLevelCount\" value=\""+lLevelCount+"\">");
			out.println("					<table width=\"99%\" border=\"0\" align=\"center\" height=\"70\" class=\"ItemList\">");
			out.println("						<tr id='treeTitle'  style='display:'>");
			out.println("							<td class=\"ItemTitle\" width=\"20%\" rowspan=\"1\">&nbsp;</td>");
			//显示的列的数目
			long lDisplayColumnCount = 1;
			/*for (int i = 0; i < aryColumnStatus.length; i++)
			{
				if (aryColumnStatus[i] > 0)
				{
					lDisplayColumnCount++;
				}
			}*/
			for (int i = 0; i < aryHeadDateString.length; i++)
			{
				out.println("							<input type=\"hidden\" name=\"hdnDate\" value=\"" + aryHeadDateString[i] + "\">");
				out.println("							<td class=\"ItemTitle\" height=\"20\" colspan=\"" + lDisplayColumnCount + "\">");
				out.println("								<div align=\"center\">" + aryHeadDateString[i] + "</div>");
				out.println("							</td>");
			}
			out.println("						</tr>");

			if (vTemplate != null && vTemplate.size() > 0)
			{
				Iterator it = vTemplate.iterator();
				while (it.hasNext())
				{
					DisplayTemplateInfo info = (DisplayTemplateInfo) it.next();
					ArrayList detailInfos = info.getDetailInfos();
					String strDisplay = "none";
					if (aryLevelStatus != null && info.getItemLevel() <= aryLevelStatus.length && aryLevelStatus[(int) info.getItemLevel() - 1] == 1)
					{
						strDisplay = "";
					}
					out.println("						<tr id=\"tree" + info.getItemNo() + "\"  style='display:" + strDisplay + "'>");
					out.println("						<input type=\"hidden\" name=\"hdnLevel" + info.getItemNo() + "\" value=\"" + info.getItemLevel() + "\">");
					out.println("						<input type=\"hidden\" name=\"hdnItemID" + info.getItemNo() + "\" value=\"" + info.getItemID() + "\">");
					out.println("						<input type=\"hidden\" name=\"hdnItemNo\" value=\""+info.getItemNo()+"\">");
					String strPrefix = "";
					for (int i = 1; i < info.getItemLevel(); i++)
					{
						strPrefix = "&nbsp;&nbsp;&nbsp;&nbsp;" + strPrefix;
					}
					out.println("							<td class=\"ItemBody\" nowrap>");
					if (info.getIsLeaf() == 1)
					{//末级结点
						out.println("								" + strPrefix + "<input name=\"button" + info.getItemNo() + "\" type=\"button\" class=\"button\" value=\"-\" onclick=\"showChild('" + info.getItemNo()
								+ "');\">");
					}
					else
					{//非末级结点
						out.println("								" + strPrefix + "<input name=\"button" + info.getItemNo() + "\" type=\"button\" class=\"button\" value=\"+\" onclick=\"showChild('" + info.getItemNo()
								+ "');\">");
					}
					out.println("								" + info.getItemName());
					out.println("							</td>");
					java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

					
					for (int i = 0; i < detailInfos.size(); i++)
					{
						
						if(((DisplayTemplateDetailInfo) detailInfos.get(i)).getDisplayType()>0)       //显示字符串
						{							
							
							//String strPlanAmount = DataFormat.formatDisabledAmount(((TemplateDetailInfo)detailInfos.get(i)).getPlanAmount());
							String strDisplayValue = ((DisplayTemplateDetailInfo) detailInfos.get(i)).getDisplayValue();
							
							out.println("							<td noWrap class=\"ItemBody\" height=\"20\">");
							out.println("								<div align=\"center\">" + strDisplayValue + "</div>    ");
							out.println("								<input name=\"hdValue" + i + "_" + info.getItemNo() + "\" size=\"10\" type=\"hidden\" value=\"" + strDisplayValue + "\" >");
							out.println("							</td>");
						}
						else							//显示金额 
						{
														
							String strReadOnly = ((info.getIsLeaf() != 1 ||  info.getIsReadOnly() == 1) ? "readonly" : "onBlur=\"doCount('" + i + "','" + info.getItemNo() + "');\" onFocus=\"doFocus('"
										+ i + "','" + info.getItemNo() + "');\"");
								//String strPlanAmount = DataFormat.formatDisabledAmount(((TemplateDetailInfo)detailInfos.get(i)).getPlanAmount());
								String strAmount = nf.format(((DisplayTemplateDetailInfo) detailInfos.get(i)).getAmount());
								
								
								double dAmount = ((DisplayTemplateDetailInfo) detailInfos.get(i)).getAmount();
								
								if(((DisplayTemplateDetailInfo) detailInfos.get(i)).getIsEdit()>0)
								{
									/*out.println("							<td class=\"ItemBody\" height=\"20\">");
									out.println("								<div align=\"center\">" + strAmount + "</div>    ");									
									out.println("							</td>");*/
									out.println("							<td class=\"ItemBody\" height=\"20\">");
									out.println("								<input name=\"txtAmount" + i + "_" + info.getItemNo() + "\" size=\"10\" type=\"text\" class=\"tar\" value=\"" + strAmount + "\" readonly" 
											+ ">");
									out.println("								<input name=\"hdnAmount" + i + "_" + info.getItemNo() + "\" type=\"hidden\" value=\"" + dAmount + "\">");
									out.println("								<input name=\"hdnAmountIsChange" + i + "_" + info.getItemNo() + "\" type=\"hidden\" value=\"0\">");
									out.println("							</td>");
									
								}
								else
								{
									out.println("							<td class=\"ItemBody\" height=\"20\">");
									out.println("								<input name=\"txtAmount" + i + "_" + info.getItemNo() + "\" size=\"10\" type=\"text\" class=\"tar\" value=\"" + strAmount + "\" " + strReadOnly
											+ ">");
									out.println("								<input name=\"hdnAmount" + i + "_" + info.getItemNo() + "\" type=\"hidden\" value=\"" + dAmount + "\">");
									out.println("								<input name=\"hdnAmountIsChange" + i + "_" + info.getItemNo() + "\" type=\"hidden\" value=\"0\">");
									out.println("							</td>");
								}
							
						}
						
						
						
					}
					out.println("						</tr>");
				}
			}
			out.println("					</table>");
			out.println("<script language=\"javascript\">");
			if (vTemplate != null && vTemplate.size() > 0)
			{
				out.println("	var arrayItemNo = new Array(" + vTemplate.size() + ");");
				out.println("	var arrayItemNoStatus = new Array(" + vTemplate.size() + ");");
				Iterator it = vTemplate.iterator();
				int index = 0;
				while (it.hasNext())
				{
					DisplayTemplateInfo info = (DisplayTemplateInfo) it.next();
					long lIsDefaultDisplay = -1;
					if (aryLevelStatus != null && info.getItemLevel() <= aryLevelStatus.length && aryLevelStatus[(int) info.getItemLevel() - 1] == 1)
					{
						lIsDefaultDisplay = 1;
					}
					out.println("	arrayItemNo[" + index + "]='" + info.getItemNo() + "';");
					out.println("	arrayItemNoStatus[" + index + "]=" + lIsDefaultDisplay + ";");
					index++;
				}
			}
			out.println("function showChild(treeItemNo)");
			out.println("{");
			out.println("	var lLevel;");
			out.println("	var lLevelCount=document.all.hdnLevelCount.value;");
			out.println("	eval(\"lLevel=document.all.hdnLevel\"+treeItemNo+\".value\");");
			out.println("	if (lLevel < lLevelCount)");
			out.println("	{");
			out.println("		var temp;");
			out.println("		eval(\"temp=document.all.button\"+treeItemNo+\".value\");");
			out.println("		if (temp == '+')");
			out.println("		{");
			out.println("			eval(\"document.all.button\"+treeItemNo+\".value='-'\");");
			out.println("			closeOrOpen(treeItemNo,1);");
			out.println("		}");
			out.println("		else");
			out.println("		{");
			out.println("			eval(\"document.all.button\"+treeItemNo+\".value='+'\");");
			out.println("			closeOrOpen(treeItemNo,-1);");
			out.println("		}");
			out.println("	}");
			out.println("}");
			out.println("");
			out.println("function closeOrOpen(treeItemNo,nType)");
			out.println("{");
			out.println("	for(var i=0;i<arrayItemNo.length;i++)");
			out.println("	{");
			out.println("		if (arrayItemNo[i].length > treeItemNo.length)");
			out.println("		{");
			out.println("			if (nType == 1)");
			out.println("			{");
			out.println("				//展开");
			out.println("				if (arrayItemNo[i].length == (treeItemNo.length + 4) && arrayItemNo[i].indexOf(treeItemNo) == 0)");
			out.println("				{");
			out.println("					eval(\"document.all.tree\"+arrayItemNo[i]+\".style.display=''\");");
			out.println("					arrayItemNoStatus[i] = 0;");
			out.println("				}");
			out.println("			}");
			out.println("			else");
			out.println("			{");
			out.println("				if (arrayItemNoStatus[i] == 0 && arrayItemNo[i].length > treeItemNo.length && arrayItemNo[i].indexOf(treeItemNo) == 0)");
			out.println("				{");
			out.println("					eval(\"document.all.tree\"+arrayItemNo[i]+\".style.display='none'\");");
			out.println("					eval(\"document.all.button\"+arrayItemNo[i]+\".value='+'\");");
			out.println("					arrayItemNoStatus[i] = 1;");
			out.println("				}");
			out.println("			}");
			out.println("		}");
			out.println("	}");
			out.println("}");
			out.println("");
			out.println("function doFocus(i,sItemNo)");
			out.println("{");
			out.println("	adjustAmountForTemplateTree(\"txtAmount\"+i+\"_\"+sItemNo,2);");
			out.println("}");
			out.println("");
			out.println("function doBalance(i)");
			out.println("{");
			out.println("	eval(\"fromPlanObj=document.all.txtAmount\"+i+\"_\"+\""+formRowNo+"\");");
			out.println("	eval(\"toPlanObj=document.all.txtAmount\"+i+\"_\"+\""+toRowNo+"\");");
			out.println("	eval(\"beginPlanObj=document.all.txtAmount\"+i+\"_\"+\""+beginBalanceRowNo+"\");");
			out.println("	eval(\"beginUsefulPlanObj=document.all.txtAmount\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\");");
			out.println("	eval(\"endPlanObj=document.all.txtAmount\"+i+\"_\"+\""+endBalanceRowNo+"\");");
			out.println("	eval(\"endUsefulPlanObj=document.all.txtAmount\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\");");			
			out.println("	eval(\"depositPlanObj=document.all.txtAmount\"+i+\"_\"+\""+depositRowNo+"\");");
			out.println("	eval(\"assurePlanObj=document.all.txtAmount\"+i+\"_\"+\""+assureRowNo+"\");");			
			out.println("");
			out.println("	if (beginPlanObj!=null && depositPlanObj!=null && assurePlanObj!=null)");
			out.println("	{");
			out.println("		var beginPlanAmount;");
			out.println("		eval(\"beginPlanAmount=document.all.txtAmount\"+i+\"_\"+\""+beginBalanceRowNo+"\"+\".value\");");
			out.println("		beginPlanAmount = reverseFormatAmount(beginPlanAmount);");
			out.println("");
			out.println("		var depositPlanAmount;");
			out.println("		eval(\"depositPlanAmount=document.all.txtAmount\"+i+\"_\"+\""+depositRowNo+"\"+\".value\");");
			out.println("		depositPlanAmount = reverseFormatAmount(depositPlanAmount);");
			out.println("");
			out.println("		var assurePlanAmount;");
			out.println("		eval(\"assurePlanAmount=document.all.txtAmount\"+i+\"_\"+\""+assureRowNo+"\"+\".value\");");
			out.println("		assurePlanAmount = reverseFormatAmount(assurePlanAmount);");
			out.println("");
			out.println("		var beginUsefulPlanAmount;");
			out.println("		beginUsefulPlanAmount = parseFloat(beginPlanAmount) - parseFloat(depositPlanAmount) - parseFloat(assurePlanAmount)");
			out.println("");
			out.println("		eval(\"document.all.txtAmount\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = beginUsefulPlanAmount\");");
			out.println("		eval(\"document.all.hdnAmount\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = beginUsefulPlanAmount\");");
			out.println("		eval(\"document.all.hdnAmountIsChange\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = 1\");");
			out.println("");
			out.println("		adjustAmountForTemplateTree(\"txtAmount\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\",1);");
			out.println("");
			out.println("	}");
			out.println("");
			out.println("	if (fromPlanObj==null||toPlanObj==null){return;}");
			out.println("");
			out.println("	var fromPlanAmount;");
			out.println("	var toPlanAmount;");
			out.println("	eval(\"fromPlanAmount=document.all.txtAmount\"+i+\"_\"+\""+formRowNo+"\"+\".value\");");
			out.println("	eval(\"toPlanAmount=document.all.txtAmount\"+i+\"_\"+\""+toRowNo+"\"+\".value\");");
			out.println("	fromPlanAmount = reverseFormatAmount(fromPlanAmount);");
			out.println("	toPlanAmount = reverseFormatAmount(toPlanAmount);");
			out.println("");
			out.println("	if (beginPlanObj!=null && endPlanObj!=null)");
			out.println("	{");
			out.println("		var beginPlanAmount;");
			out.println("		eval(\"beginPlanAmount=document.all.txtAmount\"+i+\"_\"+\""+beginBalanceRowNo+"\"+\".value\");");
			out.println("		beginPlanAmount = reverseFormatAmount(beginPlanAmount);");
			out.println("");
			out.println("		var endPlanAmount;");
			out.println("		endPlanAmount = parseFloat(beginPlanAmount) + parseFloat(fromPlanAmount) - parseFloat(toPlanAmount)");
			out.println("");
			out.println("		eval(\"document.all.txtAmount\"+i+\"_\"+\""+endBalanceRowNo+"\"+\".value = endPlanAmount\");");
			out.println("		eval(\"document.all.hdnAmount\"+i+\"_\"+\""+endBalanceRowNo+"\"+\".value = endPlanAmount\");");
			out.println("		eval(\"document.all.hdnAmountIsChange\"+i+\"_\"+\""+endBalanceRowNo+"\"+\".value = 1\");");
			out.println("");
			out.println("		adjustAmountForTemplateTree(\"txtAmount\"+i+\"_\"+\""+endBalanceRowNo+"\",1);");
			out.println("");
			out.println("	}");
			out.println("");
			out.println("	if (beginUsefulPlanObj!=null && endUsefulPlanObj!=null)");
			out.println("	{");
			out.println("		var beginUsefulPlanAmount;");
			out.println("		eval(\"beginUsefulPlanAmount=document.all.txtAmount\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value\");");
			out.println("		beginUsefulPlanAmount = reverseFormatAmount(beginUsefulPlanAmount);");
			out.println("");
			out.println("		var endUsefulPlanAmount;");
			out.println("		endUsefulPlanAmount = parseFloat(beginUsefulPlanAmount) + parseFloat(fromPlanAmount) - parseFloat(toPlanAmount)");
			out.println("");
			out.println("		eval(\"document.all.txtAmount\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value = endUsefulPlanAmount\");");
			out.println("		eval(\"document.all.hdnAmount\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value = endUsefulPlanAmount\");");
			out.println("		eval(\"document.all.hdnAmountIsChange\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value = 1\");");
			out.println("		adjustAmountForTemplateTree(\"txtAmount\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\",1);");			
			out.println("	}");
			out.println("");
			out.println("}");
			out.println("");
			out.println("function doCount(i,sItemNo)");
			out.println("{");
			out.println("	var hdnAmount;");
			out.println("	var txtAmount;");
			out.println("	eval(\"hdnAmount=document.all.hdnAmount\"+i+\"_\"+sItemNo+\".value\");");
			out.println("	eval(\"txtAmount=document.all.txtAmount\"+i+\"_\"+sItemNo+\".value\");");
			out.println("	if (isFloat(txtAmount) == false || txtAmount == \"\" )");
			out.println("	{");
			out.println("		alert(\"请输入正确的数字！\");");
			out.println("		eval(\"document.all.txtAmount\"+i+\"_\"+sItemNo+\".value=\"+hdnAmount);");
			out.println("		return;");
			out.println("	}");
			out.println("	adjustAmountForTemplateTree(\"txtAmount\"+i+\"_\"+sItemNo,1);");
			out.println("	hdnAmount = reverseFormatAmount(hdnAmount);");
			out.println("	txtAmount = reverseFormatAmount(txtAmount);");
			out.println("");
			out.println("	if (parseFloat(hdnAmount) != parseFloat(txtAmount))");
			out.println("	{");
			out.println("		eval(\"document.all.hdnAmount\"+i+\"_\"+sItemNo+\".value = txtAmount\");");
			out.println("		eval(\"document.all.hdnAmountIsChange\"+i+\"_\"+sItemNo+\".value = 1\");");
			out.println("");
			out.println("		var diffAmount = parseFloat(txtAmount) - parseFloat(hdnAmount);");
			out.println("		");
			out.println("		while(sItemNo.length > 4)");
			out.println("		{");
			out.println("			sItemNo = sItemNo.substring(0,sItemNo.length-4);");
			out.println("			var tempAmount;");
			out.println("			eval(\"tempAmount=document.all.txtAmount\"+i+\"_\"+sItemNo+\".value\");");
			out.println("			tempAmount = parseFloat(reverseFormatAmount(tempAmount))+parseFloat(diffAmount);");
			out.println("			eval(\"document.all.txtAmount\"+i+\"_\"+sItemNo+\".value = tempAmount\");");
			out.println("			eval(\"document.all.hdnAmountIsChange\"+i+\"_\"+sItemNo+\".value = 1\");");
			//out.println("			alert('t');");
			out.println("			adjustAmountForTemplateTree(\"txtAmount\"+i+\"_\"+sItemNo,1);");
			out.println("		}");
			out.println("	}");
			//out.println("	doBalance(i)");
			out.println("}");
			out.println("");
			out.println("</script>");
			out.println("<!------------------------------------------------------------------------------------------>");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println(ex.toString());
		}
	}
	/**
	*
	* @param out
	* @param selName
	* @param strValue
	* @param strName
	* @throws IOException
	*/
   public static void showMultSelect(JspWriter out, String selName, String strValue,
	   String strName)
	   throws
	   IOException
   {
	   String strProperty = "size=\"3\" align=\"left\" style=\"width:150\"";

	   showMultSelect(out, selName, strValue, strName, strProperty);
   }
   /**
	*
	* @param out
	* @param selName
	* @param strValue
	* @param strName
	* @param strProperty
	* @throws IOException
	*/
  public static void showMultSelect(JspWriter out, String selName, String strValue,
	   String strName,
	   String strProperty)
	   throws IOException
  {
	   out.println("<select name=\"" + selName + "\"" + strProperty + ">");
	   if (strValue == null || strName == null || strValue.equals("") || strName.equals("")) {
		   out.println("</select>");
		   return;
	   }

	   String[] sValues = DataFormat.decode2String(strValue);
	   String[] sNames = DataFormat.decode2String(strName);

	   if (sValues.length == sNames.length) {
		   for (int i = 0; i < sValues.length; i++) {
			   out.println("<option value=" + sValues[i] + ">" + sNames[i] +
				   "</option>");
		   }
	   }

	   out.println("</select>");
  }

}
/*
 * Created on 2003-8-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.bill.util;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.dataentity.HtmlHeaderInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.SessionMng;
/**
 * @author yychen
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BILLHTML
{
	public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle) throws IException, IOException
	{
		String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.BILL) + "RemindMsg.jsp";
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
		String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.BILL) + "RemindMsg.jsp";
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
	
	/**
	 * Modify by leiyang 2007/08/31
	 * @param htmlHeader
	 * @throws IException
	 * @throws IOException
	 */
	private static void displyHtmlHeader(HtmlHeaderInfo htmlHeader) throws IException, IOException
	{
		htmlHeader.getJspWriter().println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		htmlHeader.getJspWriter().println("<html>\n");
		htmlHeader.getJspWriter().println("<head>\n");
		htmlHeader.getJspWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
		htmlHeader.getJspWriter().println("<title>" + htmlHeader.getTitle() + "</title>\n");
		htmlHeader.getJspWriter().println("<script Language=\"Javascript\">\n");
		htmlHeader.getJspWriter().println("<!--\n");
		htmlHeader.getJspWriter().println("function sendandquit()\n");
		htmlHeader.getJspWriter().println("{\n");
		htmlHeader.getJspWriter().println(" window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");\n");
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

		htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/jquery-1.3.2.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/util.js\"></script>\n");
        htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">\n");
		htmlHeader.getJspWriter().println("</head>\n");
		htmlHeader.getJspWriter().println("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
		/*htmlHeader.getJspWriter().println("<script language=\"JavaScript1.2\" src=\"/itreasury/js/coolmenus4.js\"></script>\n");
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
			"        <td width=\"400\" nowrap class=\"headerLogo\"><img src=\"/itreasury/graphics/logo.gif\"><img src=\"/itreasury/graphics/logo_pjgl.gif\"></td>\n");
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
		htmlHeader.getJspWriter().println("\n\n");*/
        htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        htmlHeader.getJspWriter().println("<tbody valign=\"top\">");
        htmlHeader.getJspWriter().println("<tr><td height=\"5\"></td></tr>");
        htmlHeader.getJspWriter().println("<tr><td>");
	

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
	 * Modify by leiyang date 2007/08/31
	 * 显示一般信息(页面尾部)
	 * 修改BY kenny 2006-04-08
	 * @param out 输出
	 * @param lTypeID 操作类型(1：打印、-1：其它类型)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID, long lOfficeID, long lCurrencyID) throws IOException
	{
		/*String[] date = DataFormat.getDateString(Env.getSystemDate(lOfficeID, lCurrencyID)).split("-");
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
		}*/
		out.println("<br></td></tr></tbody></table></body></html>");
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
		
		String URL=Constant.SDCURL;
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
				out.println("<INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:window.parent.location.href='"+URL+"';\"  value=\" 确 定 \"> \n");
			}else{
				out.println("<INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"返回\"> \n");
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
	 * 校验客户端请求的有效性。 执行操作： －登陆校验 －权限校验 －重复请求检查
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
				BILLHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E002");
				BILLHTML.showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			//判断是否有权限
			if (sessionMng.hasRight(request) == false)
			{
				BILLHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E002");
				BILLHTML.showHomeEnd(out);
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
					BILLHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E008");
					BILLHTML.showHomeEnd(out);
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
			BILLHTML.showException(out, sessionMng, ie, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "");
			BILLHTML.showHomeEnd(out);
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
     * 显示所有普票据类型下拉列表显示控件
     * 
     * @param out
     *            输出
     * @param strControlName
     *            控件名称
     * @param lSelectValue
     *            被选择项对应值（贷款期限）
     * @param isNeedAll
     *            是否需要”全部“项
     * @param strProperty
     *            下拉列表属性
     * @param isNeedBlank
     *				是否需要空白行
     * @throws Exception
     */
	public static void showBillTypeListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty,boolean isNeedBlank,long lOfficeID,long lCurrencyID) throws Exception
	{
		showBillTypeListCtrl( out,  strControlName,  lSelectValue,  isNeedAll,  strProperty, isNeedBlank ,0,lOfficeID,lCurrencyID); 
	}
	
	/**
     * 显示票据类型下拉列表显示控件
     * 
     * @param out
     *            输出
     * @param strControlName
     *            控件名称
     * @param lSelectValue
     *            被选择项对应值（贷款期限）
     * @param isNeedAll
     *            是否需要”全部“项
     * @param strProperty
     *            下拉列表属性
     * @param isNeedBlank
     *				是否需要空白行
     * @param lOption 
     * 				选项 0:显示全部
     * 					 1:显示空白凭证(除汇票外所有)
     * 					 2:显示空白支票
     * 					 3:汇票
     * @throws Exception
     */
	public static void showBillTypeListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty,boolean isNeedBlank ,long lOption,long lOfficeID,long lCurrencyID) throws Exception
	{
		StringBuffer sbSQL = new StringBuffer();
		//modified by qizhou 2008-03-27 
        //sbSQL.append(" select id,name from bill_billType where statusId= " + Constant.RecordStatus.VALID +" AND OFFICEID="+lOfficeID+" AND CURRENCYID="+lCurrencyID);
		sbSQL.append(" select id,name from bill_billType where statusId= " + Constant.RecordStatus.VALID );
		 if(lOfficeID > 0){
	        	sbSQL.append(" AND OFFICEID="+lOfficeID );
        }
        if(lCurrencyID > 0){
        	sbSQL.append(" AND CURRENCYID="+lCurrencyID);
        }
		if (lOption == 1)
        {
        	sbSQL.append(" and abstractTypeId != " + BILLConstant.BillAbstractType.DRAFT);
        }
        else if (lOption == 2)
        {
        	sbSQL.append(" and abstractTypeId in (" + BILLConstant.BillAbstractType.CHEQUE + "," + BILLConstant.BillAbstractType.CONSIGNPAYVOUCHER + ")");
        }
        else if (lOption == 3)
        {
        	sbSQL.append(" and abstractTypeId in (" + BILLConstant.BillAbstractType.DRAFT + ")");
        }
        sbSQL.append(" order by id");
        showCommonListControl(out,strControlName,sbSQL.toString(),"name","id",lSelectValue,isNeedAll,strProperty,isNeedBlank); 
	}
}
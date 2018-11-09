/*
 * Created on 2003-8-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.securities.util;
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
import com.iss.itreasury.util.ConfigConstant;
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
public class SECHTML
{
	/**
	 * 显示HTML开始的部分 可不显示菜单
	 * @param out
	 * @param sessionMng
	 * @param strTitle
	 * @param lShowMenu
	 * @throws IException
	 * @throws IOException
	 */
	public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle, long lShowMenu) throws IException, IOException
    {

		//zpli modify 2005-09-27
		String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.SECURITIES) + "securities/msg/RemindMsg.jsp";
        //String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.SECURITIES) + "msg/RemindMsg.jsp";
        String strStatus = "";
        if (sessionMng.isLogin())
        {
            try
            {
                strStatus = sessionMng.m_strUserName + "  " + Env.getSystemDateString() + "  " + Env.getClientName() + "  "
                        + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID);
            }
            catch (Exception exp)
            {
                ;
            }
        }
        String strProject = "iTreasuryPro";
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
        displyHtmlHeader(htmlHeader,lShowMenu);

    }
	
	
	/*
	public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle, long lShowMenu) throws IException
	{
		String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.SECURITIES) + "securities/msg/RemindMsg.jsp";
		String strStatus = "";
		EndDayProcess process = new EndDayProcess();
		if (sessionMng.isLogin())
		{
			try
			{
				strStatus =
					sessionMng.m_strUserName
						+ "  "
						+ DataFormat.getDateString(Env.getSecuritiesSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID))
						+ "  "
						+ Env.getClientName()
						+ "  "
						+ Constant.CurrencyType.getName(sessionMng.m_lCurrencyID);
						//+ "  "
						//+ SECConstant.SystemStatus.getName(process.getSystemStatusID(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID));
			}
			catch (Exception exp)
			{
				;
			}
		}
		try
		{
			out.println("<html>\n");
			out.println("<head>\n");
			out.println("<title>" + strTitle + "</title>\n");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
			out.println("<Script Language=\"Javascript\">\n");
			out.println("<!--\n");
			out.println("function sendandquit()\n");
			out.println("{\n");
			out.println("	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");\n");
			out.println("}\n");
			out.println("function MM_goToURL() { //v3.0\n");
			out.println("  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;\n");
			out.println("  for (i=0; i<(args.length-1); i+=2) eval(args[i]+\".location='\"+args[i+1]+\"'\");\n");
			out.println("}\n");
			out.println("\n");
			out.println("function MM_jumpMenu(targ,selObj,restore){ //v3.0\n");
			out.println("  eval(targ+\".location='\"+selObj.options[selObj.selectedIndex].value+\"'\");\n");
			out.println("  if (restore) selObj.selectedIndex=0;\n");
			out.println("}\n");
			out.println("//-->\n");
			out.println("</Script>\n");
			out.println("\n");
			out.println("\n");
			out.println("<link rel=\"stylesheet\" href=\"/websec/css/template.css\" type=\"text/css\">\n");
			out.println("</head>\n");
			out.println("<body bgcolor=\"#f3f3f3\" text=\"#000000\">\n");
			out.println("<script language=javascript>\n");
			out.println("function showSending() \n");
			out.println("{\n");
			out.println(" gnIsShowSending=1;\n");
			out.println("	sending.style.visibility=\"visible\";\n");
			out.println("	cover.style.visibility=\"visible\";\n");
			out.println("}\n");
			out.println("</script>\n");
			out.println(
				"<div id=\"sending\" style=\"position:absolute; top:320; left:20; z-index:10; visibility:hidden\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行, 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>\n");
			out.println("<div id=\"cover\" style=\"position:absolute; top:0; left:0; z-index:9; visibility:hidden\"><TABLE WIDTH=100% height=900 BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><TD align=center><br></td></tr></table></div>\n");
			out.println("\n");
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
			out.println("  <tr>\n");
			out.println("    <td width=\"24%\"><img src=\"/websec/image/CNPC_Logo.jpg\" ></td>\n");
			out.println("    <td width=\"76%\" align=\"right\"><img src=\"/websec/image/capital_logo.jpg\" ></td>\n");
			out.println("  </tr>\n");
			out.println("<tr>\n");
			out.println("<td background=\"/websec/image/topright.gif\" height=\"24\">&nbsp;</td>\n");
			out.println("<td background=\"/websec/image/topright.gif\">&nbsp;</td>\n");
			out.println("</tr>\n");
			out.println("</table>\n");
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"20\">\n");
			out.println("<tr>\n");
			out.println("<td>\n");
			out.println("<iframe \n");
			out.println("      border=0 frameborder=0 framespacing=0 \n");
			out.println(" marginheight=0 \n");
			out.println("      marginwidth=0 name=new_date noResize scrolling=no height=\"20\"\n");
			out.println("      src=\"" + strRemindURL + "\" width=\"100%\" vspale=\"0\"></iframe>\n");
			out.println("</td>\n");
			out.println("</tr>\n");
			out.println("</table>\n");
			//out.println("<hr size=\"2\" noshade color=\"#FF6633\">\n");
			out.println("<NOSCRIPT> \n");
			out.println("<TABLE cellSpacing=0 cellPadding=0 width=798 border=0>\n");
			out.println("  <TBODY> \n");
			out.println("  <TR vAlign=center>\n");
			out.println("          <TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\" \n");
			out.println("            width=\"720\"></TD></TR></TBODY></TABLE>\n");
			out.println("<table cellspacing=0 cellpadding=0 width=798 border=0>\n");
			out.println("  <tbody> \n");
			out.println("  <tr valign=center align=middle width=\"718\" bordercolor=\"#666666\"> \n");
			out.println("    <td width=90 bgcolor=white height=20><font \n");
			out.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
			out.println("\n");
			out.println("</font></td>\n");
			out.println("    <td width=90 bgcolor=white height=20><font \n");
			out.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
			out.println("\n");
			out.println("</font></td>\n");
			out.println("    <td width=90 bgcolor=white height=20><font \n");
			out.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
			out.println("\n");
			out.println("</font></td>\n");
			out.println("    <td width=90 bgcolor=white height=20><font \n");
			out.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
			out.println("\n");
			out.println("</font></td>\n");
			out.println("    <td width=90 bgcolor=white height=20><font \n");
			out.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
			out.println("\n");
			out.println("</font></td>\n");
			out.println("    <td width=90 bgcolor=white height=20><font \n");
			out.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
			out.println("\n");
			out.println("</font></td>\n");
			out.println("    <td width=90 bgcolor=white height=20><font \n");
			out.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
			out.println("\n");
			out.println("</font></td>\n");
			out.println("    <td width=90 bgcolor=white height=20><font \n");
			out.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
			out.println("\n");
			out.println("</font></td>\n");
			out.println("  </tr>\n");
			out.println("  </tbody>\n");
			out.println("</table>\n");
			out.println("<TABLE cellSpacing=0 cellPadding=0 width=798 border=0>\n");
			out.println("  <TBODY> \n");
			out.println("  <TR>\n");
			out.println("          <TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\" \n");
			out.println("            width=\"720\"></TD></TR></TBODY></TABLE>\n");
			out.println("        </NOSCRIPT> \n");
			out.println("        \n");
			out.println("<p> \n");
			out.println("  <script language=JavaScript1.2 src=\"/websec/js/DropDownData.js\" \n");
			out.println("      type=text/javascript></script>\n");
			
			if (lShowMenu == Constant.YesOrNo.YES)
			{
				out.println("  <script language=\"JavaScript1.2\">\n");
				out.println(" window.status=\"" + strStatus + "\"");
				out.println("      showMenu(\"" + sessionMng.m_strMenu + "\",\"" + Env.getInstance().getURL(sessionMng.m_lModuleID) + "\");\n");
				out.println("  </script>\n");
			}
			
			out.println("\n");
			out.println("</p>\n");
			out.println("<table border=\"0\" width=\"100%\" height=\"20\">\n");
			out.println(" \n");
			out.println("	<tr>\n");
			out.println("\n");
			out.println("     	\n");
			out.println("    <td height=\"20\"></td>\n");
			out.println("\n");
			out.println("	</tr>\n");
			out.println("\n");
			out.println("</table>\n");
			out.println("\n");
		}
		catch (Exception e)
		{
			;
		}
	}*/
	
	/**
	 * Modify by leiyang date 2007/08/31
	 */
	public static void showHomeEnd(JspWriter out) throws IOException
	{
		//out.println("</body>");
		//out.println("</html>");
		out.println("<br></td></tr></tbody></table></body></html>");
        //Modify by jiangqi 2011-03-30
		//out.println("<script language=javascript>try{ document.all.sending.style.visibility=\"hidden\"; }catch(e){}</script>");  
	}
	/**
		  * 显示一般信息(错误信息，消息等)
		  * @param out 输出
		  * @param sessionMng Session
		  * @param exception 异常 
		  * @param request  
		  * @param response   
		  * @param strTitle 页面的Title
		  * @param lTypeID 是否带头
		  */
	public static void showExceptionMessage(JspWriter out, SessionMng sessionMng, IException exception, HttpServletRequest request, HttpServletResponse response, String strTitle, long lTypeID) throws Exception
	{
		showException(out, sessionMng, exception, request, response, strTitle, lTypeID, "");
	}
	public static void showMessage(JspWriter out, SessionMng sessionMng, HttpServletRequest request, HttpServletResponse response, String strTitle, long lTypeID, String strErroCode) throws Exception
	{
		showException(out, sessionMng, null, request, response, strTitle, lTypeID, strErroCode);
	}
	public static void showException(JspWriter out, SessionMng sessionMng, IException exception, HttpServletRequest request, HttpServletResponse response, String strTitle, long lTypeID, String strErroCode) throws Exception
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
			showHomeHead(out, sessionMng, strTitle, Constant.YesOrNo.NO);
		}
		out.println("<TABLE align=center border=0 class=top height=217 width=\"27%\">\n");
		out.println("  <TBODY>\n");
		out.println("  <TR>\n");
		//out.println("    <TD class=FormTitle height=2 width=\"100%\"><B>" + Env.getClientName() + "</B></TD></TR>\n");
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
			//out.println("			 <INPUT type=\"button\" class=button name=\"goback\"  onclick=\"self.location.href=''\" value=\"返回\"> \n");
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
	 * @param out 输出
	 * @param strControlName 控件名称
	 * @param strSQL 数据库查询语句
	 * @param strDisplayField 显示字段
	 * @param strID 显示字段对应标识
	 * @param lSelectValue 被选择项对应值
	 * @param isNeedAll 是否需要”全部“项
	 * @param strProperty 下拉列表属性
	 * @param isNeedBlank 是否需要空白行
	 * @throws Exception
	 */
	public static void showCommonListControl(JspWriter out, String strControlName, String strSQL, String strDisplayField, String strID, long lSelectValue, boolean isNeedAll, String strProperty, boolean isNeedBlank) throws Exception
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
	 * @param out 输出
	 * @param strControlName 控件名称
	 * @param strSQL 数据库查询语句
	 * @param strDisplayField 显示字段
	 * @param strID 显示字段对应标识
	 * @param strSelectValue 被选择项对应值
	 * @param isNeedAll 是否需要”全部“项
	 * @param strProperty 下拉列表属性
	 * @param isNeedBlank 是否需要空白行
	 * @throws Exception
	 */
	public static void showCommonListControl(JspWriter out, String strControlName, String strSQL, String strDisplayField, String strID, String strSelectValue, boolean isNeedAll, String strProperty, boolean isNeedBlank) throws Exception
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
	 * @param out 输出
	 * @param strControlName 控件名称
	 * @param strSQL 数据库查询语句
	 * @param strDisplayField 显示字段
	 * @param strID 显示字段对应标识
	 * @param lSelectValue 被选择项对应值
	 * @param isNeedAll 是否需要”全部“项
	 * @param strProperty 下拉列表属性
	 * @throws Exception
	 */
	public static void showCommonListControl(JspWriter out, String strControlName, String strSQL, String strDisplayField, String strID, long lSelectValue, boolean isNeedAll, String strProperty) throws Exception
	{
		showCommonListControl(out, strControlName, strSQL, strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
	}
	/**
	 * 校验客户端请求的有效性。
	 * 执行操作：
	 * －登录校验
	 * －权限校验
	 * －重复请求检查
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
                SECHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E002");
                SECHTML.showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			//判断是否有权限
			if (sessionMng.hasRight(request) == false)
			{
                SECHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E003");
                SECHTML.showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			//处理重复请求
			String strTemp = (String) request.getAttribute("ActionValidateCode");
			
			Long lActionID = new Long(0);
			if (strTemp != null)
			{
				lActionID = Long.valueOf(strTemp);
			}
			Long lTemp = (Long) session.getAttribute("ActionValidateCode");
			
			Log.print("Request 中的ActionValidateCode 是 ："+ lActionID);
			Log.print("Session 中的ActionValidateCode 是 ："+ lTemp);
			if (lActionID.longValue() != 0)
			{
				if (lTemp != null && lActionID.compareTo(lTemp) <= 0)
				{
					Log.print("重复请求！" + strTemp);
                    SECHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E008");
                    SECHTML.showHomeEnd(out);
					out.flush();
					bResult = false;
				}
				else
				{
					session.setAttribute("ActionValidateCode", lActionID);
					request.setAttribute("ActionValidateCode",String.valueOf(lActionID.longValue()+1));
					
				}
			}
		}
		catch (IException ie)
		{
            SECHTML.showException(out, sessionMng, ie, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "");
            SECHTML.showHomeEnd(out);
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
	 * 通用查询数据的方法
	 * @param strSQL 查询语句
	 * @param strField 返回值对应的字段
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
	   
	   
	   /**
	    * Modify by leiyang date 2007/08/31
	    * @param htmlHeader
	    * @param lShowMenu
	    * @throws IException
	    * @throws IOException
	    */
	   private static void displyHtmlHeader(HtmlHeaderInfo htmlHeader, long lShowMenu) throws IException, IOException {
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
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/jquery-aop-1.3.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/Doaop.js\"></script>\n");
	        htmlHeader.getJspWriter().println("\n");
			htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">\n");
			htmlHeader.getJspWriter().println("</head>\n");
			htmlHeader.getJspWriter().println("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
			//Modify by jiangqi 2011-03-30
            //htmlHeader.getJspWriter().println("<div id=\"sending\" style=\"position:absolute; top:80; left:20; z-index:200; visibility:visible\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中, 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>");         
            //htmlHeader.getJspWriter().println("<script language=javascript> function showSending(){ gnIsShowSending=1;        sending.style.visibility=\"visible\";for (var i=0;i<document.all.length;i++){if((document.all[i].type==\"button\")||(document.all[i].type==\"submit\")||(document.all[i].type==\"reset\")){  document.all[i].disabled = true; }}}</script>");
			
            htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
	        htmlHeader.getJspWriter().println("<tbody valign=\"top\">");
	        htmlHeader.getJspWriter().println("<tr><td height=\"5\"></td></tr>");
	        htmlHeader.getJspWriter().println("<tr><td>");

	}
	   
    /**
     * Add by leiyang date 2007/09/14
     * @param out
     * @throws IException
     * @throws IOException
     */
    public static void showHomeIframeHead(JspWriter out, int height) throws IOException{
    	out.println("<html>\n");
    	out.println("<head>\n");
    	out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
    	out.println("<script language=\"javascript\" src=\"/webloan/js/Control.js\"></script>");
    	out.println("<script language=\"javascript\" src=\"/webloan/js/Check.js\"></script>");
    	out.println("<script language=\"javascript\" src=\"/itreasury/js/jquery-1.3.2.js\"></script>");
    	out.println("<script language=\"javascript\" src=\"/itreasury/js/util.js\"></script>");
    	out.println("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">");
    	out.println("</head>");
    	out.println("<body onload=\"iframeAutoFit("+ height +")\" bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">");
    	
    	out.println("<div id=\"sending\" style=\"position:absolute; top:320; z-index:501; display:none\"><table width=\"100%\"><tr><td align=\"center\">");
    	out.println("<table bgcolor=\"#ff9900\" width=\"380\" height=\"70\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\"><tr><td bgcolor=\"#eeeeee\" align=\"center\">正在执行中, 请稍候...</td></tr></table>");
    	out.println("</td></tr></table></div>");
    	// modify by jiangqi 2011-04-01
    	//out.println("<div id=\"sending\" style=\"position:absolute; top:80; left:20; z-index:200; display:inline\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中, 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>");         
        //out.println("<script language=javascript> function showSending(){ gnIsShowSending=1;        sending.style.display=\"none\";for (var i=0;i<document.all.length;i++){if((document.all[i].type==\"button\")||(document.all[i].type==\"submit\")||(document.all[i].type==\"reset\")){  document.all[i].disabled = true; }}}</script>");
  
    }

    public static void showHomeIframeEnd(JspWriter out) throws IOException{
		out.println("</html>");
		//Modify by jiangqi 2011-03-30
		//out.println("<script language=javascript>try{ document.all.sending.style.display=\"none\"; }catch(e){}</script>");				
    }
}

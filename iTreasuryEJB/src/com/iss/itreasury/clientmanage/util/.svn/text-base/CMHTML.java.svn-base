/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 * 
 * 总体功能说明：项目里需要的页面显示函数
 * 
 * 使用注意事项： 1 2
 * 
 * 作者：liuguang
 * 
 * 更改人员：
 *  
 */
package com.iss.itreasury.clientmanage.util;

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
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.SessionMng;

public class CMHTML {

	/**
	 * 显示HTML开始的部分 可不显示菜单
	 * 
	 * @param out
	 * @param lUserID
	 *            用户标识
	 * 
	 */
	public static void showHomeHead(JspWriter out, SessionMng sessionMng,
			String strTitle, long lShowMenu) throws IException, IOException {
		EndDayProcess process = new EndDayProcess();
		String strRemindURL = Env.getInstance()
				.getURL(Constant.ModuleType.LOAN)
				+ "msg/RemindMsg.jsp";
		String strStatus = "";
		if (sessionMng.isLogin()) {
			try

			{

					// strStatus = sessionMng.m_strUserName + " " +
					// Env.getSystemDateString() + " " +
					// Env.getOfficeName(sessionMng.m_lOfficeID)+ " "
					// +
					// Constant.CurrencyType.getName(sessionMng.m_lCurrencyID);
					strStatus = sessionMng.m_strUserName
							+ "  "
							+ DataFormat.getChineseDateString(Env
									.getSystemDateTime(sessionMng.m_lOfficeID,
											sessionMng.m_lCurrencyID))
							+ "  "
							+ Env.getOfficeName(sessionMng.m_lOfficeID)
							+ Constant.CurrencyType
									.getName(sessionMng.m_lCurrencyID) + "<br>";
					if (sessionMng.dLastLoginTime != null
							&& !sessionMng.dLastLoginTime.equals("")) {
						strStatus += "您的上次登录时间 "
								+ DataFormat
										.getChineseTimeString(sessionMng.dLastLoginTime);
					}

			} catch (Exception exp) {
				;
			}
		}
		String strProject = "iTreasuryPro";
		// //////////////////////
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
		displyHtmlHeader(htmlHeader, lShowMenu);

	}

	/**
	 * 显示一般信息(页面尾部) 修改BY胡志强(kewen) 2004-12-21
	 * 
	 * @param out
	 *            输出
	 * @param lTypeID
	 *            操作类型
	 */
	public static void showHomeEnd(JspWriter out) throws IOException {
		showHomeEnd(out, -1);
	}

	/**
	 * 显示一般信息(页面尾部) 修改BY胡志强(kewen) 2006-04-13
	 * 
	 * @param out
	 *            输出
	 * @param lTypeID
	 *            操作类型(1：打印、-1：其它类型)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID)
			throws IOException {
		long lOfficeID = 1;// 默认值
		long lCurrencyID = 1;// 默认值
		showHomeEnd(out, lTypeID, lOfficeID, lCurrencyID);
	}

	/**
	 * 显示一般信息(页面尾部) 修改BY kenny 2006-04-08
	 * 
	 * @param out
	 *            输出
	 * @param lTypeID
	 *            操作类型(1：打印、-1：其它类型)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID, long lOfficeID,
			long lCurrencyID) throws IOException {
		out.println("<br></td></tr></tbody></table></body></html>");
        //modify by jiangqi 2011-03-30
		//out.println("<script language=javascript>try{ document.all.sending.style.visibility=\"hidden\"; }catch(e){}</script>");     
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
	public static void showExceptionMessage(JspWriter out,
			SessionMng sessionMng, IException exception,
			HttpServletRequest request, HttpServletResponse response,
			String strTitle, long lTypeID) throws Exception {
		showException(out, sessionMng, exception, request, response, strTitle,
				lTypeID, "");

	}

	public static void showMessage(JspWriter out, SessionMng sessionMng,
			HttpServletRequest request, HttpServletResponse response,
			String strTitle, long lTypeID, String strErroCode) throws Exception {
		showException(out, sessionMng, null, request, response, strTitle,
				lTypeID, strErroCode);
	}

	public static void showException(JspWriter out, SessionMng sessionMng,
			IException exception, HttpServletRequest request,
			HttpServletResponse response, String strTitle, long lTypeID,
			String strErroCode) throws Exception {
		String strErroMessage = "";
		if (strErroCode != null && strErroCode.length() > 0) {
			strErroMessage = IExceptionMessage.getExceptionMessage(strErroCode);
		}
		if (exception != null) {
			strErroMessage = IExceptionMessage.getExceptionMSG(exception);
		}
		System.out.println("**************IExceptionMessage:" + strErroMessage);
		if (lTypeID == Constant.RecordStatus.VALID) {
			showHomeHead(out, sessionMng, strTitle, Constant.YesOrNo.NO);
		}
		out
				.println("<TABLE align=center border=0 class=top height=217 width=\"27%\">\n");
		out.println("  <TBODY>\n");
		out.println("  <TR>\n");
		// out.println(" <TD class=FormTitle height=2 width=\"100%\"><B>" +
		// Env.getClientName() + "</B></TD></TR>\n");
		out
				.println("    <TD class=FormTitle height=2 width=\"100%\"><B></B></TD></TR>\n");
		out.println("  <TR>\n");
		out.println("      <TD height=190 vAlign=bottom width=\"100%\"> \n");
		out.println("        <TABLE align=center height=187 width=\"97%\">\n");
		out.println("        <TBODY>\n");
		out.println("        <TR>\n");
		out.println("          <TD height=40 vAlign=top width=\"96%\">\n");
		out
				.println("              <TABLE align=center border=1 borderColor=#999999 height=177 \n");
		out.println("            width=\"99%\">\n");
		out.println("                <TBODY> \n");
		out
				.println("                <TR borderColor=#D7DFE5 vAlign=center> \n");
		out
				.println("                  <TD height=162 colspan=\"3\" align=\"center\">"
						+ strErroMessage + "</TD>\n");
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
			if (strErroMessage == null || strErroMessage.length() <= 0) {
				out
						.println("            <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"返回\"> \n");
			} else {
				// out.println(" <INPUT type=\"button\" class=button
				// name=\"goback\"
				// onclick=\"self.location.href=''\" value=\"返回\"> \n");
				//modified by qhzhou 2008-03-06-16
				if(strErroCode != null && strErroCode.length() > 0 && strErroCode.equals("Gen_E002")){
					out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:window.parent.location.href='"+Constant.SDCURL+"';\"  value=\"返回\"> \n");
				}else{
					out.println("            <INPUT type=\"button\" class=button name=\"goback\"  onclick=\"javascript:history.back(-1)\" value=\"返回\"> \n");
			
				}
			}
			out.println("            </DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>\n");
			out.println("<SCRIPT language=JavaScript>");
			out.println("   goback.focus();");
			out.println("</SCRIPT>");
		if (lTypeID == Constant.RecordStatus.VALID) {
			showHomeEnd(out);
		}
	}

	/**
	 * 校验客户端请求的有效性。 执行操作： －登录校验 －权限校验 －重复请求检查
	 * 
	 * @param out
	 * @param request
	 * @param response
	 * @return boolean
	 */
	public static boolean validateRequest(JspWriter out,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean bResult = true;
		SessionMng sessionMng = null;
		try {
			HttpSession session = request.getSession(true);
			sessionMng = (SessionMng) session.getAttribute("sessionMng");
			// 登录检测
			if (sessionMng.isLogin() == false) {
				CMHTML.showMessage(out, sessionMng, request, response, Env
						.getClientName(), Constant.RecordStatus.INVALID,
						"Gen_E002");
				CMHTML.showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			// 判断是否有权限
			else if (sessionMng.hasRight(request) == false) {
				CMHTML.showMessage(out, sessionMng, request, response, Env
						.getClientName(), Constant.RecordStatus.INVALID,
						"Gen_E003");
				CMHTML.showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			// 处理重复请求
			String strTemp = (String) request
					.getAttribute("ActionValidateCode");

			Long lActionID = new Long(0);
			if (strTemp != null) {
				lActionID = Long.valueOf(strTemp);
			}
			Long lTemp = (Long) session.getAttribute("ActionValidateCode");

			Log.print("Request 中的ActionValidateCode 是 ：" + lActionID);
			Log.print("Session 中的ActionValidateCode 是 ：" + lTemp);
			if (lActionID.longValue() != 0) {
				if (lTemp != null && lActionID.compareTo(lTemp) <= 0) {
					Log.print("重复请求！" + strTemp);
					CMHTML.showMessage(out, sessionMng, request, response, Env
							.getClientName(), Constant.RecordStatus.INVALID,
							"Gen_E008");
					CMHTML.showHomeEnd(out);
					out.flush();
					bResult = false;
				} else {
					session.setAttribute("ActionValidateCode", lActionID);
					request.setAttribute("ActionValidateCode", String
							.valueOf(lActionID.longValue() + 1));

				}
			}
		} catch (IException ie) {
			CMHTML.showException(out, sessionMng, ie, request, response, Env
					.getClientName(), Constant.RecordStatus.INVALID, "");
			CMHTML.showHomeEnd(out);
			out.flush();
			ie.printStackTrace();
			bResult = false;
		} catch (IOException e) {
			e.printStackTrace();
			bResult = false;
		} catch (Exception e) {
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	private static void displyHtmlHeader(HtmlHeaderInfo htmlHeader,
			long lShowMenu) throws IException, IOException {
		htmlHeader
				.getJspWriter()
				.println(
						"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		htmlHeader.getJspWriter().println("<html>");
		htmlHeader.getJspWriter().println("<head>");
		htmlHeader
				.getJspWriter()
				.println(
						"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
		htmlHeader.getJspWriter().println(
				"<title>" + htmlHeader.getTitle() + "</title>\n");
		htmlHeader.getJspWriter().println("<Script Language=\"Javascript\">\n");
		htmlHeader.getJspWriter().println("<!--\n");
		htmlHeader.getJspWriter().println("function sendandquit()\n");
		htmlHeader.getJspWriter().println("{\n");
		htmlHeader
				.getJspWriter()
				.println(
						"	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");\n");
		htmlHeader.getJspWriter().println("}\n");
		htmlHeader.getJspWriter().println("function MM_goToURL() { //v3.0\n");
		htmlHeader
				.getJspWriter()
				.println(
						"  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;\n");
		htmlHeader
				.getJspWriter()
				.println(
						"  for (i=0; i<(args.length-1); i+=2) eval(args[i]+\".location='\"+args[i+1]+\"'\");\n");
		htmlHeader.getJspWriter().println("}\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println(
				"function MM_jumpMenu(targ,selObj,restore){ //v3.0\n");
		htmlHeader
				.getJspWriter()
				.println(
						"  eval(targ+\".location='\"+selObj.options[selObj.selectedIndex].value+\"'\");\n");
		htmlHeader.getJspWriter().println(
				"  if (restore) selObj.selectedIndex=0;\n");
		htmlHeader.getJspWriter().println("}\n");
		htmlHeader.getJspWriter().println("//-->\n");
		htmlHeader.getJspWriter().println("</Script>\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("\n");
		// Modify by leiyang date 2007/07/23
		// htmlHeader.getJspWriter().println("<script language=\"javascript\"
		// src=\"/itreasury/js/coolmenus4.js\"></script>\n");
		// htmlHeader.getJspWriter().println("<script language=\"javascript\"
		// src=\"/itreasury/js/cm_addins.js\"></script>\n");
		htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/jquery-1.3.2.js\"></script>\n");
		htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/util.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/jquery-aop-1.3.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/Doaop.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language = \"javascript\" src=\"/itreasury/js/taglib.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language = \"javascript\" src=\"/webcm/js/MagnifierSQL.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language = \"javascript\" src=\"/webcm/js/cmCheck.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language = \"javascript\" src=\"/webcm/js/Control.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language = \"javascript\" src=\"/webcm/js/CheckInput.js\"></script>\n");
        htmlHeader.getJspWriter().println("<script language = \"javascript\" src=\"/webcm/js/Check.js\"></script>\n");
		htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">\n");
		htmlHeader.getJspWriter().println("</head>\n");
		htmlHeader.getJspWriter().println("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
        //modify by jiangqi 2011-03-30
		//htmlHeader.getJspWriter().println("<div id=\"sending\" style=\"position:absolute; top:80; left:20; z-index:200; visibility:visible\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中, 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>");         
        //htmlHeader.getJspWriter().println("<script language=javascript> function showSending(){ gnIsShowSending=1;        sending.style.visibility=\"visible\";for (var i=0;i<document.all.length;i++){if((document.all[i].type==\"button\")||(document.all[i].type==\"submit\")||(document.all[i].type==\"reset\")){  document.all[i].disabled = true; }}}</script>");
		// Modify by leiyang date 2007/07/23
		htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		htmlHeader.getJspWriter().println("<tbody valign=\"top\">");
		htmlHeader.getJspWriter().println("<tr><td height=\"5\"></td></tr>");
		htmlHeader.getJspWriter().println("<tr><td>");
	}

	/**
	 * Modify by leiyang date 2007/07/23
	 * 
	 * @param out
	 * @throws IException
	 * @throws IOException
	 */
	public static void showHomeIframeHead(JspWriter out, int height)
			throws IOException {
		out.println("<html>\n");
		out.println("<head>\n");
		out
				.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
		out
				.println("<script language=\"javascript\" src=\"/webloan/js/Control.js\"></script>");
		out
				.println("<script language=\"javascript\" src=\"/webloan/js/Check.js\"></script>");
		out
				.println("<script language=\"javascript\" src=\"/itreasury/js/jquery-1.3.2.js\"></script>");
		out
				.println("<script language=\"javascript\" src=\"/itreasury/js/util.js\"></script>");
		out
				.println("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">");
		out.println("</head>");
		out
				.println("<body onload=\"iframeAutoFit("
						+ height
						+ ")\" bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">");

		out
				.println("<div id=\"sending\" style=\"position:absolute; top:320; z-index:501; display:none\"><table width=\"100%\"><tr><td align=\"center\">");
		out
				.println("<table bgcolor=\"#ff9900\" width=\"380\" height=\"70\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\"><tr><td bgcolor=\"#eeeeee\" align=\"center\">正在执行中, 请稍候...</td></tr></table>");
		out.println("</td></tr></table></div>");
	}
}
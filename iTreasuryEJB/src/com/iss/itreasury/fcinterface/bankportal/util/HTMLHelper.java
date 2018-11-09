/*
 * Created on 2003-8-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.fcinterface.bankportal.constant.BooleanValue;
import com.iss.itreasury.fcinterface.bankportal.constant.RecordStatus;
import com.iss.itreasury.fcinterface.bankportal.setting.currency.dao.CurrencyDAO;
import com.iss.itreasury.fcinterface.bankportal.setting.currency.dataentity.CurrencyInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

/**
 * Html工具类<br>
 * 提供与html相关的基本工具类，以及html页面校验逻辑
 * 
 * @author mxzhou
 */
public class HTMLHelper {
	/** 日志对象 */
	private static Logger log = new Logger(HTMLHelper.class);
	
	private static String headerStr1 = null;
	private static String headerStr2 = null;
	private static String endStr = null;
	static
	{
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("<html><head><title></title>");
		strBuf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
		strBuf.append("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\"></head>\n");
		strBuf.append("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">");
		strBuf.append("<div id=\"sending\" style=\"position:absolute; top:80; left:20; z-index:200; visibility:visible\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中, 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>");
		headerStr1 = strBuf.toString();
		strBuf = null;
		strBuf = new StringBuffer();
		strBuf.append("<Script Language=\"Javascript\"><!--");
		strBuf.append("function sendandquit(){");
		strBuf.append("	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");");
		strBuf.append("}");
		strBuf.append("function MM_goToURL() { //v3.0");
		strBuf.append("  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;");
		strBuf.append("  for (i=0; i<(args.length-1); i+=2) eval(args[i]+\".location='\"+args[i+1]+\"'\");");
		strBuf.append("}");
		strBuf.append("function MM_jumpMenu(targ,selObj,restore){ //v3.0");
		strBuf.append("  eval(targ+\".location='\"+selObj.options[selObj.selectedIndex].value+\"'\");");
		strBuf.append("  if (restore) selObj.selectedIndex=0;");
		strBuf.append("}");
		strBuf.append("//-->");     
		strBuf.append("</Script>");
//		strBuf.append("<script language=\"JavaScript1.2\" src=\"/webbp/js/coolmenus.js\"></script>");
//		strBuf.append("<script language=\"JavaScript1.2\" src=\"/itreasury/js/cm_addins.js\"></script>");
//		strBuf.append("<script language=javascript>function showSending(){");
//		strBuf.append("gnIsShowSending=1;");
//		strBuf.append("sending.style.visibility=\"visible\";");
//		strBuf.append("window.event.returnvalue = false;");
//		strBuf.append("}</script>");
		strBuf.append("<script language=javascript>");
		strBuf.append("function showSending(){");
		strBuf.append(" gnIsShowSending=1;");
		strBuf.append("	sending.style.visibility=\"visible\";");
		strBuf.append("for (var i=0;i<document.all.length;i++){");
//		strBuf.append("if ((document.all[i].type==\"text\")||(document.all[i].type==\"textarea\")){");
//		strBuf.append("  document.all[i].realonly = true; ");
//		strBuf.append("}else if((document.all[i].type==\"button\")||(document.all[i].type==\"submit\")||(document.all[i].type==\"reset\")){");
		strBuf.append("if((document.all[i].type==\"button\")||(document.all[i].type==\"submit\")||(document.all[i].type==\"reset\")){");
		strBuf.append("  document.all[i].disabled = true; ");
		strBuf.append("}}}</script>");
		strBuf.append("<div id=MainDiv style='width:100%;height:100%'><table id=MainTable width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"10\" height=\"10\"></td><td align=left valign=top></td></tr><tr><td width=\"10\"></td><td align=left valign=top>");//</td><td align=left valign=top><br>
		headerStr2 = strBuf.toString();
		strBuf = null;
		endStr = "<br></td></tr></table></div></body></html><script language=javascript>try{ document.all.sending.style.visibility=\"hidden\"; }catch(e){}</script>";		
	}

	/**
	 * 显示文件头
	 * 
	 * @param out
	 * @param sessionMng
	 * @param strTitle
	 */
	public static void showHomeHead(JspWriter out, SessionMng sessionMng,
			String strTitle) {
		showHomeHead(out, sessionMng, strTitle, null, BooleanValue.TRUE);
	}
	public static void showHomeHead(JspWriter out, SessionMng sessionMng,
			String strTitle, long lShowMenu) {
		showHomeHead(out, sessionMng, strTitle, null, lShowMenu);
	}
	public static void showHomeHead(JspWriter out, SessionMng sessionMng,
			String strTitle, String status) {
		showHomeHead(out, sessionMng, strTitle, status, BooleanValue.TRUE);
	}
	public static void showHomeHead(JspWriter out, SessionMng sessionMng,String strTitle, String status, long lShowMenu) {
		try{
//			StringBuffer sb = new StringBuffer();
//			sb.append("<html><head><title></title>");
//			sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
//			sb.append("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\"></head>\n");
//			sb.append("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">");
//			sb.append("<div id=\"sending\" style=\"position:absolute; top:80; left:20; z-index:200; visibility:visible\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中, 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>");			
			out.print(headerStr1);
			out.flush();
		
//			sb = new StringBuffer();
//			sb.append("<Script Language=\"Javascript\"><!--");
//			sb.append("function sendandquit(){");
//			sb.append("	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");");
//			sb.append("}");
//			sb.append("function MM_goToURL() { //v3.0");
//			sb.append("  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;");
//			sb.append("  for (i=0; i<(args.length-1); i+=2) eval(args[i]+\".location='\"+args[i+1]+\"'\");");
//			sb.append("}");
//			sb.append("function MM_jumpMenu(targ,selObj,restore){ //v3.0");
//			sb.append("  eval(targ+\".location='\"+selObj.options[selObj.selectedIndex].value+\"'\");");
//			sb.append("  if (restore) selObj.selectedIndex=0;");
//			sb.append("}");
//			sb.append("//-->");     
//			sb.append("</Script>");
//			sb.append("<script language=\"JavaScript1.2\" src=\"/webbp/js/coolmenus.js\"></script>");
//			sb.append("<script language=\"JavaScript1.2\" src=\"/itreasury/js/cm_addins.js\"></script>");
//			sb.append("<script language=javascript>function showSending(){");
//			sb.append("gnIsShowSending=1;");
//			sb.append("sending.style.visibility=\"visible\";");
//			//sb.append("document.onkeydown=event.returnValue=false;");
//			sb.append("}</script>");
//			sb.append("<div id=MainDiv style='width:100%;height:100%'><table id=MainTable width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"10\"></td><td align=left valign=top><br>");
			out.print(headerStr2);
			out.flush();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 显示一般信息(页面尾部)
	 * 
	 * @param out
	 *            输出
	 */
	public static void showHomeEnd(JspWriter out) throws IOException {
		showHomeEnd(out, -1);
	}

	/**
	 * 显示一般信息(页面尾部)
	 * 
	 * @param out
	 *            输出
	 * @param lTypeID
	 *            操作类型(1：打印、-1：其它类型)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID) throws IOException {
		out.print(endStr);
		out.flush();
	}
	public static void initShow(JspWriter out,SessionMng sessionMng,String strTitle, String status, long lShowMenu) throws IOException, SystemException{		
		String strStatus = "";
		if (status == null && sessionMng.isLogin()) {
			try {
//				strStatus = sessionMng.userName + "  " + DataFormat.formatDate(Env.getSystemDateTime(),DataFormat.DT_YYYYMMDD) + "  " + Env.getEnvConfigItem(Env.G_CLIENT_NAME);
				strStatus = sessionMng.userName + "  " + DataFormat.formatDate(Env.getSystemDateTime(),DataFormat.DT_YYYYMMDD) + "  " + sessionMng.officeName;
			} catch (Exception exp) {System.out.println(exp.toString());}
		} else if (status != null) {strStatus = status;}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><title>" + strTitle + "</title>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
		sb.append("<Script Language=\"Javascript\"><!--");
		sb.append("function sendandquit(){");
		sb.append("	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");");
		sb.append("}");
		sb.append("function MM_goToURL() { //v3.0");
		sb.append("  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;");
		sb.append("  for (i=0; i<(args.length-1); i+=2) eval(args[i]+\".location='\"+args[i+1]+\"'\");");
		sb.append("}");
		sb.append("function MM_jumpMenu(targ,selObj,restore){ //v3.0");
		sb.append("  eval(targ+\".location='\"+selObj.options[selObj.selectedIndex].value+\"'\");");
		sb.append("  if (restore) selObj.selectedIndex=0;");
		sb.append("}");
		sb.append("//-->");
		sb.append("</Script>");
		sb.append("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">");
		sb.append("</head><body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" style=\"OVERFLOW:scroll;OVERFLOW-X:hidden;OVERFLOW-Y:hidden\">");
		sb.append("<script language=\"JavaScript1.2\" src=\"/webbp/js/coolmenus.js\"></script>");
		sb.append("<script language=\"JavaScript1.2\" src=\"/itreasury/js/cm_addins.js\"></script>");
//		sb.append("<script language=javascript>");
//		sb.append("function showSending(){");
//		sb.append(" gnIsShowSending=1;");
//		sb.append("	sending.style.visibility=\"visible\";");
//		sb.append("	cover.style.visibility=\"visible\";");
//		sb.append("for (var i=0;i<document.all.length;i++){");
//		sb.append("if ((document.all[i].type==\"text\")||(document.all[i].type==\"textarea\")){");
//		sb.append("  document.all[i].realonly = true; ");
//		sb.append("}else if((document.all[i].type==\"button\")||(document.all[i].type==\"submit\")||(document.all[i].type==\"reset\")){");
//		sb.append("  document.all[i].disabled = true; ");
//		sb.append("}}}</script>");
//		sb.append("<div id=\"sending\" style=\"position:absolute; top:320; left:20; z-index:10; visibility:hidden\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中, 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>");
//		sb.append("<div id=\"cover\" style=\"position:absolute; top:0; left:0; z-index:9; visibility:hidden\"><TABLE WIDTH=100% height=0 BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><TD align=center><br></td></tr></table></div>");
		sb.append("<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("<tr><td>");
		sb.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"pagetop\">");
		sb.append("<tr><td width=\"400\" nowrap class=\"headerLogo\"><img src=\"/itreasury/graphics/logo.gif\"><img src=\"/itreasury/graphics/logo_bankportal.gif\"></td>");
		sb.append("    <td class=\"welcome\">" + strStatus +"</td><td width=\"187\" align=\"right\" nowrap class=\"headerHelp\">");
		sb.append("<table border=\"0\" cellpadding=\"4\" cellspacing=\"2\">");
		sb.append("<tr><td align=\"center\" class=\"sys\">");
		if (sessionMng.userID > 0 && lShowMenu == BooleanValue.TRUE) 
		sb.append("<a href=\"" + Env.getEnvConfigItem(Env.G_BANKPORTAL_URL) + "/Logout.jsp\" target=\"DataArea\" target=\"DataArea\" class=\"syslink\">退出登录</a> <a href=\"" + Env.getEnvConfigItem(Env.G_BANKPORTAL_URL) + "/ChangePWD.jsp\" target=\"DataArea\" class=\"syslink\">修改密码</a>");
		sb.append("</td></tr></table>");
		sb.append("</td></tr></table>");
		
		sb.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("<tr><td height=\"24\" class=\"menuMain\">");
		sb.append("    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td>&nbsp;</td></tr></table>");
		sb.append("</td></tr>");
		sb.append("<tr><td height=\"2\" class=\"menuBl1\"><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td></tr>");
		sb.append("<tr><td height=\"3\" class=\"menuBl2\"><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td></tr>");
		sb.append("</table>");
		
		sb.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
//		sb.append("<tr><td><img src=\"/itreasury/graphics/space.gif\" width=\"778\" height=\"1\"></td></tr>");
		sb.append("<tr><td height=\"20\">");
		sb.append("      <iframe border=0 frameborder=0 framespacing=0 marginheight=0 marginwidth=0 name=new_date noResize scrolling=no height=\"20\" src=\"\" width=\"100%\" vspale=\"0\"></iframe>");
		sb.append("</td></tr>");
		sb.append("</table>");
		
		sb.append("<NOSCRIPT> ");
		sb.append("<TABLE cellSpacing=0 cellPadding=0 width=798 border=0><TBODY><TR vAlign=center><TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\" width=\"720\"></TD></TR></TBODY></TABLE>");
		sb.append("<table cellspacing=0 cellpadding=0 width=798 border=0><tbody><tr valign=center align=middle width=\"718\" bordercolor=\"#666666\"> ");
		sb.append("<td width=90 bgcolor=white height=20><font style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??</font></td>");
		sb.append("<td width=90 bgcolor=white height=20><font  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??</font></td>");
		sb.append("<td width=90 bgcolor=white height=20><font  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??</font></td>");
		sb.append("<td width=90 bgcolor=white height=20><font  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??</font></td>");
		sb.append("<td width=90 bgcolor=white height=20><font  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??</font></td>");
		sb.append("<td width=90 bgcolor=white height=20><font  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??</font></td>");
		sb.append("<td width=90 bgcolor=white height=20><font  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??</font></td>");
		sb.append("<td width=90 bgcolor=white height=20><font  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??</font></td>");
		sb.append("</tr></tbody></table>");
		sb.append("<TABLE cellSpacing=0 cellPadding=0 width=798 border=0><TBODY><TR><TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\"   width=\"720\"></TD></TR></TBODY></TABLE>");
		sb.append("</NOSCRIPT> ");
		if (lShowMenu == BooleanValue.TRUE) {
			sb.append("<script language=\"JavaScript1.2\">");
			sb.append("showMenu(\"" + sessionMng.menu + "\",\"" + Env.getEnvConfigItem(Env.G_BANKPORTAL_URL) + "\");");
			sb.append("</script>");
		}
//		sb.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td height=\"2\"><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td></tr></table>");
		sb.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#999999\"><tr><td><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td></tr></table>");

		//Message
		sb.append("</td></tr><tr><td width=\"100%\" height=\"100%\" align=center valign=middle>");
		sb.append("<iframe name=DataArea border=0 frameborder=0 framespacing=0 marginheight=0 marginwidth=0  noResize width=\"100%\" height=\"100%\"></iframe>");
		
		//END
		sb.append("</td></tr>");
		sb.append("<tr><td class=\"pagefootTl\"><img src=\"/itreasury/graphics/space.gif\" width=\"778\" height=\"1\"></td></tr>");
		sb.append("<tr><td height=1 class=\"pagefoot\"><font face=\"Arial, Helvetica, sans-serif\">&copy;</font> 2005-"+DataFormat.formatDate(Env.getSystemDateTime(),DataFormat.DT_YYYY) +" 版权所有 " + Env.getEnvConfigItem(Env.COPYRIGHT) + "</td></tr>");
		//sb.append("<tr><td height=1 class=\"pagefoot\"><font face=\"Arial, Helvetica, sans-serif\">&copy;</font> 2005-"+DataFormat.formatDate(Env.getSystemDateTime(),DataFormat.DT_YYYY) +" 版权所有 软通动力" + "</td></tr>");
		sb.append("</table></body></html>");
		out.println(sb.toString());
		
	}
	


	/**
	 * 显示页面消息
	 * 
	 * @param out
	 * @param sessionMng
	 * @param request
	 * @param response
	 * @param strTitle
	 * @param lTypeID
	 * @param strErroCode
	 * @param strErroMessage
	 * @throws Exception
	 */
	public static void showMessage(JspWriter out, SessionMng sessionMng,
			HttpServletRequest request, HttpServletResponse response,
			String strTitle, long lTypeID, String strErroCode,
			String strErroMessage, boolean flag) throws Exception {

		log.info("**************IExceptionMessage:" + strErroMessage);
		if (lTypeID == BooleanValue.TRUE) showHomeHead(out, sessionMng, strTitle);
		StringBuffer sb = new StringBuffer();
		sb.append("<TABLE align=center border=0 class=top height=217 width=\"27%\"><TBODY>");
//		sb.append("<TR><TD class=FormTitle height=2 width=\"100%\"><B>" + Env.getEnvConfigItem(Env.G_CLIENT_NAME) + "</B></TD></TR>\n");
		sb.append("<TR><TD class=FormTitle height=2 width=\"100%\"><B>" + sessionMng.officeName + "</B></TD></TR>\n");
		sb.append("<TR><TD height=190 vAlign=bottom width=\"100%\"> \n");
		sb.append("    <TABLE align=center height=187 width=\"97%\"><TBODY>\n");
		sb.append("    <TR><TD height=40 vAlign=top width=\"96%\">\n");
		sb.append("        <TABLE align=center border=1 borderColor=#999999 height=177  width=\"99%\"><TBODY>");
		sb.append("        <TR borderColor=#D7DFE5 vAlign=center> \n");
		sb.append("            <TD height=162 colspan=\"3\" align=\"center\">" + strErroMessage + "</TD>\n");
		sb.append("        </TR></TBODY></TABLE>");
		sb.append("    </TD></TR></TBODY></TABLE>");
		sb.append("</TD></TR><TR><TD height=11 vAlign=top width=\"100%\">\n");
		sb.append("    <TABLE align=center height=17 width=\"97%\"><TBODY>\n");
		sb.append("    <TR vAlign=center><TD colSpan=6 height=40><DIV align=center>\n");
		if (strErroMessage == null || strErroMessage.length() <= 0) {
			if (!flag)
				sb.append("<INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"返回\"> \n");
			else 
			{
				String strUrl = Env.getEnvConfigItem(Env.G_LOGIN_URL);
				if(strUrl == null)
				{
					strUrl = Env.getEnvConfigItem(Env.G_BANKPORTAL_URL)+"/Login.jsp";
				}
				sb.append("<INPUT type=\"button\" class=button name=\"goback\"   onclick=\"top.location.href='" + strUrl + "'\"  value=\"返回\"> \n");
			}
		} else {
			if (!flag) 
				sb.append("<INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"返回\"> \n");
			else
			{
				String strUrl = Env.getEnvConfigItem(Env.G_LOGIN_URL);
				if(strUrl == null)
				{
					strUrl = Env.getEnvConfigItem(Env.G_BANKPORTAL_URL)+"/Login.jsp";
				}
				sb.append("<INPUT type=\"button\" class=button name=\"goback\"   onclick=\"top.location.href='" + strUrl + "'\"  value=\"返回\"> \n");
			}
		}
		sb.append("    </DIV></TD></TR></TBODY></TABLE>");
		sb.append("</TD></TR></TBODY></TABLE>\n");
		sb.append("<SCRIPT language=JavaScript>goback.focus();</SCRIPT>");
		out.println(sb.toString());
		if (lTypeID == BooleanValue.TRUE) showHomeEnd(out);
	}
	/**
	 * 显示通用下拉列表
	 * 
	 * @param out
	 *            输出
	 * @param strControlName
	 *            控件名称
	 * @param lArrayID
	 *            id数组
	 * @param strArrayName
	 *            显示名称数组
	 * @param lSelectValue
	 *            被选择项对应值
	 * @param isNeedAll
	 *            是否需要”全部“项
	 * @param strProperty
	 *            下拉列表属性
	 * @param isNeedBlank
	 *            是否需要空白行
	 */
	public static void showCommonSelectListControl(JspWriter out,
			String strControlName, long[] lArrayID, String[] strArrayName,
			long lSelectValue, boolean isNeedAll, String strProperty,
			boolean isNeedBlank) {
		try {
			out.println("<select name=\"" + strControlName + "\" "
					+ strProperty + ">");
			if (isNeedBlank == true) {
				if (lSelectValue == -1) {
					out.println("<option value='-1' selected>&nbsp;</option>");
				} else {
					out.println("<option value='-1'>&nbsp;</option>");
				}
			}
			for (int i = 0; i < lArrayID.length; i++) {
				// log.debug("lArrayID[i] = " + lArrayID[i]);
				// log.debug("lSelectValue = " + lSelectValue);
				if (lArrayID[i] == lSelectValue) {
					out.println("<option value='" + lArrayID[i]
							+ "' selected >" + strArrayName[i] + "</option>");
				} else {
					out.println("<option value='" + lArrayID[i] + "'>"
							+ strArrayName[i] + "</option>");
				}
			}
			if (isNeedAll == true) {
				if (lSelectValue == 0) {
					out.println("<option value='0' selected>全部</option>");
				} else {
					out.println("<option value='0'>全部</option>");
				}
			}
			out.println("</select>");
		} catch (Exception ex) {
			log.error("显示下拉列表出现异常：" + ex.toString());
		}
	}
	
	public static void showCommonSelectListControlSel(JspWriter out,
			String strControlName, long[] lArrayID, String[] strArrayName,
			long lSelectValue, boolean isNeedAll, String strProperty,
			boolean isNeedBlank) {
		try {
			out.println("<select name=\"" + strControlName + "\" "
					+ strProperty + ">");
			if (isNeedBlank == true) {
				if (lSelectValue == -1) {
					out.println("<option value='-1' selected>--请选择--</option>");
				} else {
					out.println("<option value='-1'>--请选择--</option>");
				}
			}
			for (int i = 0; i < lArrayID.length; i++) {
				// log.debug("lArrayID[i] = " + lArrayID[i]);
				// log.debug("lSelectValue = " + lSelectValue);
				if (lArrayID[i] == lSelectValue) {
					out.println("<option value='" + lArrayID[i]
							+ "' selected >" + strArrayName[i] + "</option>");
				} else {
					out.println("<option value='" + lArrayID[i] + "'>"
							+ strArrayName[i] + "</option>");
				}
			}
			if (isNeedAll == true) {
				if (lSelectValue == 0) {
					out.println("<option value='0' selected>全部</option>");
				} else {
					out.println("<option value='0'>全部</option>");
				}
			}
			out.println("</select>");
		} catch (Exception ex) {
			log.error("显示下拉列表出现异常：" + ex.toString());
		}
	}

	/**
	 * 显示通用下拉列表
	 * 
	 * @param out
	 *            输出
	 * @param strControlName
	 *            控件名称
	 * @param lArrayID(String)
	 *            id数组
	 * @param strArrayName
	 *            显示名称数组
	 * @param lSelectValue(String)
	 *            被选择项对应值
	 * @param isNeedAll
	 *            是否需要”全部“项
	 * @param strProperty
	 *            下拉列表属性
	 * @param isNeedBlank
	 *            是否需要空白行
	 */
	public static void showCommonSelectListControls(JspWriter out,
			String strControlName, String[] lArrayID, String[] strArrayName,
			String lSelectValue, boolean isNeedAll, String strProperty,
			boolean isNeedBlank) {
		try {
			out.println("<select name=\"" + strControlName + "\" "
					+ strProperty + ">");
			if (isNeedBlank == true) {
				if (lSelectValue.equals("-1")) {
					out.println("<option value='-1' selected>&nbsp;</option>");
				} else {
					out.println("<option value='-1'>&nbsp;</option>");
				}
			}
			for (int i = 0; i < lArrayID.length; i++) {				
				if (lArrayID[i].equals(lSelectValue)){
					out.println("<option value='" + lArrayID[i]
							+ "' selected >" + strArrayName[i] + "</option>");
				} else {
					out.println("<option value='" + lArrayID[i] + "'>"
							+ strArrayName[i] + "</option>");
				}
			}
			if (isNeedAll == true) {
				if (lSelectValue.equals("0")) {
					out.println("<option value='0' selected>全部</option>");
				} else {
					out.println("<option value='0'>全部</option>");
				}
			}
			out.println("</select>");
		} catch (Exception ex) {
			log.error("显示下拉列表出现异常：" + ex.toString());
		}
	}
	
	/**
	 * 校验客户端请求的有效性。 执行操作： －登陆校验 －权限校验 －重复请求检查
	 * 
	 * @param out
	 * @param request
	 * @param response
	 * @return boolean
	 */
	public static boolean validateRequest(JspWriter out,HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean bResult = true;
		SessionMng sessionMng = null;
		try {
			HttpSession session = request.getSession(true);
			sessionMng = (SessionMng) session.getAttribute("sessionMng");
			// 登录检测
			if (sessionMng.isLogin() == false) {
				HTMLHelper.showMessage(out, sessionMng, request, response, Env.getEnvConfigItem(Env.G_CLIENT_NAME),BooleanValue.FALSE, "Gen_E002", "对不起，您没有登录。", true);
				HTMLHelper.showHomeEnd(out);
				out.flush();
				bResult = false;
			}else if (sessionMng.hasRight(request) == false) {// 判断是否有权限
				HTMLHelper.showMessage(out, sessionMng, request, response, Env.getEnvConfigItem(Env.G_CLIENT_NAME),BooleanValue.FALSE, "Gen_E003", "对不起，您没有操作这个功能的权限。",false);
				HTMLHelper.showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			
			// 处理重复请求
			String strTemp = (String) request.getAttribute("ActionID");
			Long lActionID = new Long(0);
			if (strTemp != null && strTemp.trim().length() > 0) {
				try {lActionID = Long.valueOf(strTemp);} catch (Exception e) {log.debug("Request 中的ActionID 无效。");}
			} else {
				strTemp = (String) request.getParameter("ActionID");
				if (strTemp != null && strTemp.trim().length() > 0) {
					try {lActionID = Long.valueOf(strTemp);} catch (Exception e) {log.debug("Request 中的ActionID 无效。");}
				}
			}
			Long lTemp = (Long) session.getAttribute("ActionID");
			log.debug("Request 中的ActionID 是 ：" + lActionID);
			log.debug("Session 中的ActionID 是 ：" + lTemp);
			if (lActionID.longValue() != 0) {
				if (lTemp != null && lActionID.compareTo(lTemp) <= 0) {
					log.debug("重复请求！" + strTemp);
					HTMLHelper.showMessage(out, sessionMng, request, response,Env.getEnvConfigItem(Env.G_CLIENT_NAME),BooleanValue.FALSE, "Gen_E008", "重复请求。", false);
					HTMLHelper.showHomeEnd(out);
					out.flush();
					bResult = false;
				} else {
					session.setAttribute("ActionID", lActionID);
					request.setAttribute("ActionID", String.valueOf(lActionID.longValue() + 1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	/**
	 * 显示币种下拉列表
	 * 
	 * @param out
	 * @param strControlName, *
	 *            控件名称
	 * @param nType，控件类型（0，显示全部；）
	 * @param lSelectValue
	 * @param isNeedAll，是否需要”全部项“
	 * @param isNeedBlank
	 *            是否需要空白行
	 * @param strProperty
	 */
	public static final void showCurrencyTypeList(JspWriter out,
			String strControlName, int nType, long lSelectValue,
			boolean isNeedAll, boolean isNeedBlank, String strProperty) {
		long[] lArrayID = null;
		String[] strArrayName = null;
		try {
			switch (nType) {
			case 0: {
				CurrencyDAO bs_CurrencyDAO = (CurrencyDAO) DAOFactory
						.getDAOImpl(CurrencyDAO.class, null);

				CurrencyInfo condition = new CurrencyInfo();
				condition.setRdStatus(RecordStatus.VALID);
				Collection col = bs_CurrencyDAO
						.findByCondition(condition, null);
				if (col != null && col.size() > 0) {
					lArrayID = new long[col.size()];
					strArrayName = new String[col.size()];
					CurrencyInfo[] infos = (CurrencyInfo[]) col
							.toArray(new CurrencyInfo[0]);
					for (int i = 0; i < infos.length; i++) {

						strArrayName[i] = infos[i].getName_zh();
						lArrayID[i] = infos[i].getId();
					}

				}

				break;
			}
			}

			HTMLHelper.showCommonSelectListControl(out, strControlName,
					lArrayID, strArrayName, lSelectValue, isNeedAll,
					strProperty, isNeedBlank);
		} catch (Exception ex) {
			log.error(ex.toString());
		}
	}

	/**
	 * 显示报表的表头
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
	 */
	public static void showCPFPrintHead(JspWriter out, boolean bPortrait,
			String strPaperSize, String strPrinter, int nNo) throws IOException {
		String strPortrait = "";
		if (bPortrait) {
			strPortrait = "true";
		} else {
			strPortrait = "false";
		}
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title></title>\n");
		out
				.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
		out.println("\n");
		out
				.println("<link rel=\"stylesheet\" href=\"/graphics/template.css\" type=\"text/css\">\n");
		out.println("<style type=\"text/css\">\n");
		out.println("<!--\n");
		out.println(".table1 {  border: 1px #000000 solid}\n");
		out
				.println(".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out
				.println(".td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out
				.println(".td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out
				.println(".td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out
				.println(".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}\n");
		out
				.println(".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out
				.println(".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out
				.println(".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out
				.println(".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out
				.println(".td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}\n");
		out
				.println(".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out
				.println(".small-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out
				.println(".small-td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out
				.println(".small-td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out
				.println(".small-td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out
				.println(".small-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 9px}\n");
		out
				.println(".small-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out
				.println(".small-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out
				.println(".small-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out
				.println(".small-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out
				.println(".small-td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 9px}\n");
		out
				.println(".small-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out
				.println(".td-none {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out.println("-->\n");
		out.println("</style>\n");
		out.println("</head>\n");
		long lPortrait = -1;
		if(bPortrait)
		{
			lPortrait = 2;
		}
		else
		{
			lPortrait = 1;
		}
		showPrintScriptDefer(out,strPaperSize,lPortrait,false);		
	}

	/**
	 * 打印控件输出打印属性
	 * @param out	 
	 * @param strPaperSize 纸张大小 如："A4"
	 * @param lPortrait 1：纵向  2：横向
	 * @param isPrint 是否需要自动弹出打印机选择窗口
	 */
	private static void showPrintScriptDefer(JspWriter out,String strPaperSize,long lPortrait,boolean isPrint)
	{
		try
		{			
			//控件注册
			out.println(" <OBJECT ID=\"PageSettor\" \n");
			out.println(" CLASSID=\"CLSID:C8D19F3D-3A3A-458C-89BA-64E6FF51C327\" \n");
			out.println(" CODEBASE=\"/webbp/Library/ISSlib.CAB#version=1,0,0,0\"> \n");
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
			
			out.println(" PageSettor.Footer = ''; ");			
			out.println(" PageSettor.Header = '&b,&p/&P'; ");			
			out.println(" PageSettor.LeftMargin =0.9; ");			
			out.println(" PageSettor.rightMargin = 0.2; ");			
			out.println(" PageSettor.topMargin = 0.8; ");
			out.println(" PageSettor.bottomMargin = 0.5; ");			
			
			out.println(" PageSettor.SetUp(); ");
			if(isPrint)
			{
				out.println("  PageSettor.PrintDocument(true); ");
			}
			out.println(" } ");
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
			out.println(" </script> \n");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
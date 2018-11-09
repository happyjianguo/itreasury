/*
 * Created on 2003-8-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.configtool.util;

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
public class CONFIGHTML {
	public static void showHomeHead(JspWriter out, String strTitle) throws IException, IOException {
		displyHtmlHeader(out, strTitle);
	}

	private static void displyHtmlHeader(JspWriter out, String strTitle) throws IException, IOException {
		out.println("<html>\n");
		out.println("<head>\n");
		out.println("<title>" + strTitle + "</title>\n");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
		out.println("<Script Language=\"Javascript\">\n");
		out.println("<!--\n");
		/*out.println("function sendandquit()\n");
		out.println("{\n");
		out.println("	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,\n");
		out.println("toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");\n");
		out.println("}\n");*/
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
    	//Modify by leiyang date 2007/07/24
		out.println("<script language=\"javascript\" src=\"/itreasury/js/coolmenus4.js\"></script>\n");
		out.println("<script language=\"javascript\" src=\"/itreasury/js/cm_addins.js\"></script>\n");
		out.println("<script language=\"javascript\" src=\"/itreasury/js/jquery-1.3.2.js\"></script>\n");
		out.println("<script language=\"javascript\" src=\"/itreasury/js/util.js\"></script>\n");
		out.println("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">\n");
		out.println("</head>\n");
		out.println("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
		out.println("\n");
		out.println("<div id=\"sending\" style=\"position:absolute; top:320; z-index:501; display:none\"><table width=\"100%\"><tr><td align=\"center\">");
		out.println("<table bgcolor=\"#ff9900\" width=\"380\" height=\"70\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\"><tr><td bgcolor=\"#eeeeee\" align=\"center\">����ִ����, ���Ժ�...</td></tr></table>");
		out.println("</td></tr></table></div>");

		out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		out.println("  <tr>\n");
		out
				.println("    <td>\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"pagetop\">\n");
		out.println("      <tr>\n");
		out.println("        <td class=\"welcome\">" + "</td>\n");
		out
				.println("        <td width=\"187\" align=\"right\" nowrap class=\"headerHelp\"><table border=\"0\" cellpadding=\"4\" cellspacing=\"2\">\n");
		out.println("      <tr>\n");
		out.println("        <td align=\"center\" class=\"sys\">");
		out.println("        </td>\n");
		out.println("      </tr>\n");
		out.println("    </table></td>\n");
		out.println("  </tr>\n");
		out.println("</table>\n");
		out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		out.println("  <tr>\n");
		out.println("    <td height=\"24\" class=\"menuMain\">\n");
		out.println("      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		out.println("        <tr>\n");
		out.println("          <td>&nbsp;</td>\n");
		out.println("        </tr>\n");
		out.println("      </table>\n");
		out.println("    </td>\n");
		out.println("  </tr>\n");
		out.println("  <tr>\n");
		out
				.println("    <td height=\"2\" class=\"menuBl1\"><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
		out.println("   </tr>\n");
		out.println("  <tr>\n");
		out
				.println("    <td height=\"3\" class=\"menuBl2\"><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
		out.println("  </tr>\n");
		out.println("</table>\n");
		out.println("\n");
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
		out
				.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		out.println("\n");
		out.println("</font></td>\n");
		out.println("    <td width=90 bgcolor=white height=20><font \n");
		out
				.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		out.println("\n");
		out.println("</font></td>\n");
		out.println("    <td width=90 bgcolor=white height=20><font \n");
		out
				.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		out.println("\n");
		out.println("</font></td>\n");
		out.println("    <td width=90 bgcolor=white height=20><font \n");
		out
				.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		out.println("\n");
		out.println("</font></td>\n");
		out.println("    <td width=90 bgcolor=white height=20><font \n");
		out
				.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		out.println("\n");
		out.println("</font></td>\n");
		out.println("    <td width=90 bgcolor=white height=20><font \n");
		out
				.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		out.println("\n");
		out.println("</font></td>\n");
		out.println("    <td width=90 bgcolor=white height=20><font \n");
		out
				.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		out.println("\n");
		out.println("</font></td>\n");
		out.println("    <td width=90 bgcolor=white height=20><font \n");
		out
				.println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
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
		out.println("      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#999999\">\n");
		out.println("        <tr>\n");
		out.println("          <td><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
		out.println("        </tr>\n");
		out.println("      </table>\n");
		out.println("    </td>\n");
		out.println("  </tr>\n");
		out.println("</table>\n");
		out.println("\n\n");
		//Modify by leiyang date 2007/07/24
		out.println("<table height=\"525\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		out.println("  <tbody valign=\"top\">\n");
		out.println("  <tr>\n");
		out.println("    <td width=\"10\">\n");
		out.println("    </td>\n");
		out.println("    <td><br>\n");
	}

	/**
	 * ��ʾһ����Ϣ(ҳ��β��)
	 * �޸�BY��־ǿ(kewen) 2004-12-21
	 * @param out ���
	 * @param lTypeID ��������
	 */
	public static void showHomeEnd(JspWriter out) throws IOException
	{
		showHomeEnd(out, -1);
	}
	/**
	 * ��ʾһ����Ϣ(ҳ��β��)
	 * �޸�BY��־ǿ(kewen) 2006-04-13
	 * @param out ���
	 * @param lTypeID ��������(1����ӡ��-1����������)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID) throws IOException
	{
		long lOfficeID = 1;//Ĭ��ֵ
		long lCurrencyID = 1;//Ĭ��ֵ
		showHomeEnd(out, lTypeID, lOfficeID, lCurrencyID);
	}
	/**
	 * ��ʾһ����Ϣ(ҳ��β��)
	 * �޸�BY kenny 2006-04-08
	 * @param out ���
	 * @param lTypeID ��������(1����ӡ��-1����������)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID, long lOfficeID, long lCurrencyID) throws IOException
	{
		String[] date = DataFormat.getDateString(Env.getSystemDate(lOfficeID, lCurrencyID)).split("-");
		//��ӡ����
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
				"    <td height=\"25\" class=\"pagefoot\"><font face=\"Arial, Helvetica, sans-serif\">&copy;</font> 2005-"+date[0]+" ��ͨ���� ��Ȩ���� "
					
					+ " Powered by ��ͨ����</td>\n");
			out.println("  </tr>\n");
			out.println("</table>\n");
			out.println("</body>\n");
			out.println("</html>");
		}
	}

	/**
	 * ��ʾһ����Ϣ(������Ϣ����Ϣ��)
	 * 
	 * @param out
	 *            ���
	 * @param sessionMng
	 *            Session
	 * @param exception
	 *            �쳣
	 * @param request
	 * @param response
	 * @param strTitle
	 *            ҳ���Title
	 * @param lTypeID
	 *            �Ƿ��ͷ
	 */
	public static void showExceptionMessage(JspWriter out, IException exception, HttpServletRequest request,
			HttpServletResponse response, String strTitle, long lTypeID) throws Exception {
		showException(out, exception, request, response, strTitle, lTypeID, "");
	}

	public static void showMessage(JspWriter out, HttpServletRequest request, HttpServletResponse response, String strTitle,
			long lTypeID, String strErroCode) throws Exception {
		showException(out, null, request, response, strTitle, lTypeID, strErroCode);
	}

	public static void showException(JspWriter out, IException exception, HttpServletRequest request,
			HttpServletResponse response, String strTitle, long lTypeID, String strErroCode) throws Exception {
		String strErroMessage = "";
		if (strErroCode != null && strErroCode.length() > 0) {
			strErroMessage = IExceptionMessage.getExceptionMessage(strErroCode);
		}
		if (exception != null) {
			strErroMessage = IExceptionMessage.getExceptionMSG(exception);
		}
		
		if (lTypeID == Constant.RecordStatus.VALID) {
			showHomeHead(out, strTitle);
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
		if (strErroMessage == null || strErroMessage.length() <= 0) {
			out
					.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"����\"> \n");
		} else {
			//out.println(" <INPUT type=\"button\" class=button name=\"goback\"
			// onclick=\"self.location.href=''\" value=\"����\"> \n");
			//modified by qhzhou 2008-03-06-16
			if(strErroCode != null && strErroCode.length() > 0 && strErroCode.equals("Gen_E002")){
				out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:window.parent.location.href='"+Constant.SDCURL+"';\"  value=\"����\"> \n");
			}else{
				out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"����\"> \n");
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
	 * ͨ�������б���ʾ�ؼ�
	 * 
	 * @param out
	 *            ���
	 * @param strControlName
	 *            �ؼ�����
	 * @param strSQL
	 *            ���ݿ��ѯ���
	 * @param strDisplayField
	 *            ��ʾ�ֶ�
	 * @param strID
	 *            ��ʾ�ֶζ�Ӧ��ʶ
	 * @param lSelectValue
	 *            ��ѡ�����Ӧֵ
	 * @param isNeedAll
	 *            �Ƿ���Ҫ��ȫ������
	 * @param strProperty
	 *            �����б�����
	 * @param isNeedBlank
	 *            �Ƿ���Ҫ�հ���
	 * @throws Exception
	 */
	public static void showCommonListControl(JspWriter out, String strControlName, String strSQL, String strDisplayField,
			String strID, long lSelectValue, boolean isNeedAll, String strProperty, boolean isNeedBlank) throws Exception {
		long lResult = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lTemplateID = 0;
		long lLobID = -1;
		Vector v = new Vector();
		try {
			//У�������sql������Ƿ����strDisplayField��strID
			if (strSQL.indexOf(strID) == -1 || strSQL.indexOf(strDisplayField) == -1) {
				System.out.println("����Ĳ�������ȷ��");
				return;
			}
			out.println("<select class=box name=\"" + strControlName + "\"" + strProperty + ">");
			if (isNeedBlank == true) {
				out.println("<option value=\"-1\">&nbsp;</option>");
			}
			con = Database.getConnection();
			String strTmp = new String();
			strTmp = strSQL;
			//System.out.println(strTmp);
			ps = con.prepareStatement(strTmp);
			rs = ps.executeQuery();
			while (rs.next()) {
				Log.print("rs.getLong(strID) = " + rs.getLong(strID));
				Log.print("lSelectValue = " + lSelectValue);
				if (rs.getLong(strID) == lSelectValue) {
					Log.print("��selected");
					out.println("<option value=\"" + rs.getLong(strID) + "\" selected>" + rs.getString(strDisplayField)
							+ "</option>");
				} else {
					out.println("<option value=\"" + rs.getLong(strID) + "\">" + rs.getString(strDisplayField) + "</option>");
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			if (isNeedAll == true) {
				if (lSelectValue == 0) {
					out.println("<option value=\"0\" selected>ȫ��</option>");
				} else {
					out.println("<option value=\"0\" >ȫ��</option>");
				}
			}
			out.println("</select>");
		} catch (Exception e) {
			//System.out.println(" can not select OFFICE, because " + e);
			//throw e;
			Log.print("��ʾ�����б��쳣��" + e.toString());
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		}
	}

	/**
	 * ͨ�������б���ʾ�ؼ�
	 * 
	 * @param out
	 *            ���
	 * @param strControlName
	 *            �ؼ�����
	 * @param strSQL
	 *            ���ݿ��ѯ���
	 * @param strDisplayField
	 *            ��ʾ�ֶ�
	 * @param strID
	 *            ��ʾ�ֶζ�Ӧ��ʶ
	 * @param strSelectValue
	 *            ��ѡ�����Ӧֵ
	 * @param isNeedAll
	 *            �Ƿ���Ҫ��ȫ������
	 * @param strProperty
	 *            �����б�����
	 * @param isNeedBlank
	 *            �Ƿ���Ҫ�հ���
	 * @throws Exception
	 */
	public static void showCommonListControl(JspWriter out, String strControlName, String strSQL, String strDisplayField,
			String strID, String strSelectValue, boolean isNeedAll, String strProperty, boolean isNeedBlank) throws Exception {
		long lResult = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lTemplateID = 0;
		long lLobID = -1;
		Vector v = new Vector();
		try {
			//У�������sql������Ƿ����strDisplayField��strID
			if (strSQL.indexOf(strID) == -1 || strSQL.indexOf(strDisplayField) == -1) {
				System.out.println("����Ĳ�������ȷ��");
				return;
			}
			out.println("<select class=box name=\"" + strControlName + "\"" + strProperty + ">");
			if (isNeedBlank == true) {
				out.println("<option value=\"-1\">&nbsp;</option>");
			}
			con = Database.getConnection();
			String strTmp = new String();
			strTmp = strSQL;
			//System.out.println(strTmp);
			ps = con.prepareStatement(strTmp);
			rs = ps.executeQuery();
			while (rs.next()) {
				Log.print("rs.getString(strID) = " + rs.getString(strID));
				Log.print("strSelectValue = " + strSelectValue);
				if (rs.getString(strID) != null && rs.getString(strID).equals(strSelectValue)) {
					Log.print("��selected");
					out.println("<option value=\"" + rs.getString(strID) + "\" selected>" + rs.getString(strDisplayField)
							+ "</option>");
				} else {
					out.println("<option value=\"" + rs.getString(strID) + "\">" + rs.getString(strDisplayField) + "</option>");
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			if (isNeedAll == true) {
				if (strSelectValue != null && strSelectValue.equals("0")) {
					out.println("<option value=\"0\" selected>ȫ��</option>");
				} else {
					out.println("<option value=\"0\" >ȫ��</option>");
				}
			}
			out.println("</select>");
		} catch (Exception e) {
			//System.out.println(" can not select OFFICE, because " + e);
			//throw e;
			Log.print("��ʾ�����б��쳣��" + e.toString());
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		}
	}

	/**
	 * ͨ�������б���ʾ�ؼ�
	 * 
	 * @param out
	 *            ���
	 * @param strControlName
	 *            �ؼ�����
	 * @param strSQL
	 *            ���ݿ��ѯ���
	 * @param strDisplayField
	 *            ��ʾ�ֶ�
	 * @param strID
	 *            ��ʾ�ֶζ�Ӧ��ʶ
	 * @param lSelectValue
	 *            ��ѡ�����Ӧֵ
	 * @param isNeedAll
	 *            �Ƿ���Ҫ��ȫ������
	 * @param strProperty
	 *            �����б�����
	 * @throws Exception
	 */
	public static void showCommonListControl(JspWriter out, String strControlName, String strSQL, String strDisplayField,
			String strID, long lSelectValue, boolean isNeedAll, String strProperty) throws Exception {
		showCommonListControl(out, strControlName, strSQL, strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
	}

	/**
	 * ��ʾ���ڴ�����������б���ʾ�ؼ�
	 * 
	 * @param out
	 *            ���
	 * @param strControlName
	 *            �ؼ�����
	 * @param lSelectValue
	 *            ��ѡ�����Ӧֵ�����ڴ�����ޣ�
	 * @param isNeedAll
	 *            �Ƿ���Ҫ��ȫ������
	 * @param strProperty
	 *            �����б�����
	 * @throws Exception
	 */
	public static void showFixedDepositMonthListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll,
			String strProperty) throws Exception {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL
				.append(" select distinct nFixedDepositMonthID as DepositMonthID,(nFixedDepositMonthID || '����') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed");
		sbSQL.append(" where nStatusID=1 and nAccountTypeID=2 and nFixedDepositMonthID < 10000");
		sbSQL.append(" union");
		sbSQL
				.append(" select distinct nFixedDepositMonthID as DepositMonthID,((nFixedDepositMonthID-10000) || '��') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed");
		sbSQL.append(" where nStatusID=1 and nAccountTypeID=2 and nFixedDepositMonthID>10000");
		String strDisplayField = "DepositMonthDesc";
		String strID = "DepositMonthID";
		showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll,
				strProperty, true);
	}

	/**
	 * ͨ�ò�ѯ���ݵķ���
	 * 
	 * @param strSQL
	 *            ��ѯ���
	 * @param strField
	 *            ����ֵ��Ӧ���ֶ�
	 * @return Vector
	 * @throws Exception
	 */
	public static Vector getCommonSelectList(String strSQL, String strField) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector v = new Vector();
		try {
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				Object oResult = new Object();
				oResult = rs.getObject(strField);
				v.add(oResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("�������ݿ����");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception _ex) {
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return v.size() > 0 ? v : null;
	}

	/**
	 * У��ͻ����������Ч�ԡ� ִ�в����� ����¼У�� ��Ȩ��У�� ���ظ�������
	 * 
	 * @param out
	 * @param request
	 * @param response
	 * @return boolean
	 */
	public static boolean validateRequest(JspWriter out, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean bResult = true;
		SessionMng sessionMng = null;
		try {
			HttpSession session = request.getSession(true);
			sessionMng = (SessionMng) session.getAttribute("sessionMng");
			//��¼���
			if (sessionMng.isLogin() == false) {
				CONFIGHTML.showMessage(out, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E002");
				CONFIGHTML.showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			//�ж��Ƿ���Ȩ��
			if (sessionMng.hasRight(request) == false) {
				CONFIGHTML.showMessage(out, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E002");
				CONFIGHTML.showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			//�����ظ�����
			String strTemp = (String) request.getAttribute("ActionID");

			Long lActionID = new Long(0);
			if (strTemp != null && strTemp.trim().length() > 0) {
				try {
					lActionID = Long.valueOf(strTemp);
				} catch (Exception e) {
					Log.print("Request �е�ActionID ��Ч��");
				}
			}
			Long lTemp = (Long) session.getAttribute("ActionID");

			Log.print("Request �е�ActionID �� ��" + lActionID);
			Log.print("Session �е�ActionID �� ��" + lTemp);
			if (lActionID.longValue() != 0) {
				if (lTemp != null && lActionID.compareTo(lTemp) <= 0) {
					Log.print("�ظ�����" + strTemp);
					CONFIGHTML
							.showMessage(out, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E008");
					CONFIGHTML.showHomeEnd(out);
					out.flush();
					bResult = false;
				} else {
					session.setAttribute("ActionID", lActionID);
					request.setAttribute("ActionID", String.valueOf(lActionID.longValue() + 1));

				}
			}
		} catch (IException ie) {
			CONFIGHTML.showException(out, ie, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "");
			CONFIGHTML.showHomeEnd(out);
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
}
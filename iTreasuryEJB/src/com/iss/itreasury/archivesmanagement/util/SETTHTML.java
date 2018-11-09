/*
 * Created on 2003-8-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.archivesmanagement.util;

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
public class SETTHTML
{
    public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle) throws IException,IOException
    {
        String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.SETTLEMENT) + "settlement/msg/RemindMsg.jsp";
        String strStatus = "";
        EndDayProcess process = new EndDayProcess();
        if (sessionMng.isLogin())
        {
            try
            {
//            		 strStatus = sessionMng.m_strUserName + "  " + Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID) + "  "
//                     + Env.getOfficeName(sessionMng.m_lOfficeID) + "  " + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID) + "  "
//                     + SETTConstant.SystemStatus.getName(process.getSystemStatusID(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID));
            		 strStatus = sessionMng.m_strUserName + "  " 
            		 + DataFormat.getChineseDateString(Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)) + "  "
            		 + Env.getOfficeName(sessionMng.m_lOfficeID)+ " "
                     + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID) + "  " 
                     + SETTConstant.SystemStatus.getName(process.getSystemStatusID(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)) +  "<br>";  
                 	if(sessionMng.dLastLoginTime != null && !sessionMng.dLastLoginTime.equals("")){
                		strStatus += "�����ϴε�¼ʱ�� "+ DataFormat.getChineseTimeString(sessionMng.dLastLoginTime) ;
                	}
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
        String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.SETTLEMENT) + "settlement/msg/RemindMsg.jsp";
        String strStatus = "";
        EndDayProcess process = new EndDayProcess();
        String strProject = "iTreasuryPro";
        if (sessionMng.isLogin())
        {
            try
            {
                		 strStatus = sessionMng.m_strUserName + "  " + Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID) + "  "
                         + Env.getClientName() + "  " + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID) + "  "
                         + SETTConstant.SystemStatus.getName(process.getSystemStatusID(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)) + "<br>";
                     	 if(sessionMng.dLastLoginTime != null && !sessionMng.dLastLoginTime.equals("")){
                    		 strStatus += "�����ϴε�¼ʱ�� "+ DataFormat.getChineseTimeString(sessionMng.dLastLoginTime) ;
                    	 }
                
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
    
    public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle, long lShowMenu, long lShowRemind) throws IException, IOException
    {
        String strRemindURL = "";
        if (lShowRemind == Constant.YesOrNo.YES)
        {
            strRemindURL = Env.getInstance().getURL(Constant.ModuleType.SETTLEMENT) + "settlement/msg/RemindMsg.jsp";
        }
        else
        {
            strRemindURL = "";
        }
        String strStatus = "";
        EndDayProcess process = new EndDayProcess();
        String strProject = "iTreasuryPro";
        if (sessionMng.isLogin())
        {
            try
            {
                		 strStatus = sessionMng.m_strUserName + "  " + Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID) + "  "
                         + Env.getClientName() + "  " + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID) + "  "
                         + SETTConstant.SystemStatus.getName(process.getSystemStatusID(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)) + "<br>";
                     	 if(sessionMng.dLastLoginTime != null && !sessionMng.dLastLoginTime.equals("")){
                    		 strStatus += "�����ϴε�¼ʱ�� "+ DataFormat.getChineseTimeString(sessionMng.dLastLoginTime) ;
                    	 }
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
    	htmlHeader.getJspWriter().println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        htmlHeader.getJspWriter().println("<html>");
        htmlHeader.getJspWriter().println("<head>");
        htmlHeader.getJspWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
        htmlHeader.getJspWriter().println("<title>" + htmlHeader.getTitle() + "</title>\n");
        htmlHeader.getJspWriter().println("<Script Language=\"Javascript\">\n");
        htmlHeader.getJspWriter().println("<!--\n");
        htmlHeader.getJspWriter().println("function sendandquit()\n");
        htmlHeader.getJspWriter().println("{\n");
        htmlHeader.getJspWriter().println("	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");\n");
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
        //modify by jiangqi 2011-03-30
        //htmlHeader.getJspWriter().println("<div id=\"sending\" style=\"position:absolute; top:80; left:20; z-index:200; visibility:visible\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>����ִ����, ���Ժ�...</td></tr></table></td><td width=30%></td></tr></table></div>");	 
        //htmlHeader.getJspWriter().println("<script language=javascript> function showSending(){ gnIsShowSending=1;	sending.style.visibility=\"visible\";for (var i=0;i<document.all.length;i++){if((document.all[i].type==\"button\")||(document.all[i].type==\"submit\")||(document.all[i].type==\"reset\")){  document.all[i].disabled = true; }}}</script>");
        
        htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        htmlHeader.getJspWriter().println("<tbody valign=\"top\">");
        htmlHeader.getJspWriter().println("<tr><td height=\"5\"></td></tr>");
        htmlHeader.getJspWriter().println("<tr><td>");
    }
    
    
    /**
     * Modify by leiyang date 2007/07/20
     * @param out
     * @throws IException
     * @throws IOException
     */
    public static void showHomeIframeHead(JspWriter out, int height) throws IOException{
    	out.println("<html>\n");
    	out.println("<head>\n");
    	out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
    	out.println("<script language=\"javascript\" src=\"/websett/js/Control.js\"></script>");
    	out.println("<script language=\"javascript\" src=\"/websett/js/SettCheck.js\"></script>");
    	out.println("<script language=\"javascript\" src=\"/websett/js/MagnifierSQL.js\"></script>");
    	out.println("<script language=\"javascript\" src=\"/websett/js/Check.js\"></script>");
    	out.println("<script language=\"javascript\" src=\"/itreasury/js/jquery-1.3.2.js\"></script>");
    	out.println("<script language=\"javascript\" src=\"/itreasury/js/util.js\"></script>");
    	out.println("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">");
    	out.println("</head>");
    	out.println("<body onload=\"iframeAutoFit("+ height +")\" bgcolor=\"#EAEAEA\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">");
    	
    	/*out.println("<div id=\"sending\" style=\"position:absolute; top:320; z-index:501; display:none\"><table width=\"100%\"><tr><td align=\"center\">");
    	out.println("<table bgcolor=\"#ff9900\" width=\"380\" height=\"70\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\"><tr><td bgcolor=\"#eeeeee\" align=\"center\">����ִ����, ���Ժ�...</td></tr></table>");
    	out.println("</td></tr></table></div>");*/
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
		//String[] date = DataFormat.getDateString(Env.getSystemDate(lOfficeID, lCurrencyID)).split("-");
		//��ӡ����
		//if (lTypeID == 1)
		//{
		//	out.println("</body>\n");
		//	out.println("</html>");
		//}
		//else
		//{
			//out.println("    <br></td>\n");
			//out.println("  </tr>\n");
			//out.println("</table>\n");
			/*out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
			out.println("  <tr>\n");
			out.println("    <td class=\"pagefootTl\"><img src=\"/itreasury/graphics/space.gif\" width=\"778\" height=\"1\"></td>\n");
			out.println("  </tr>\n");
			out.println("  <tr>\n");
			out.println(
				"    <td height=\"25\" class=\"pagefoot\"><font face=\"Arial, Helvetica, sans-serif\">&copy;</font> 2005-"+date[0]+" ��ͨ���� ��Ȩ���� "
					
					+ "</td>\n");
			out.println("  </tr>\n");
			out.println("</table>\n");*/
			out.println("<br></td></tr></tbody></table></body></html>");
			//modify by jiangqi 2011-03-30
			//out.println("<script language=javascript>try{ document.all.sending.style.visibility=\"hidden\"; }catch(e){}</script>");			
		//}
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
    public static void showExceptionMessage(JspWriter out, SessionMng sessionMng, IException exception, HttpServletRequest request,
            HttpServletResponse response, String strTitle, long lTypeID) throws Exception
    {
        showException(out, sessionMng, exception, request, response, strTitle, lTypeID, "");
    }

    public static void showMessage(JspWriter out, SessionMng sessionMng, HttpServletRequest request, HttpServletResponse response, String strTitle,
            long lTypeID, String strErroCode) throws Exception
    {
        showException(out, sessionMng, null, request, response, strTitle, lTypeID, strErroCode);
    }

    public static void showException(JspWriter out, SessionMng sessionMng, IException exception, HttpServletRequest request, HttpServletResponse response,
            String strTitle, long lTypeID, String strErroCode) throws Exception
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
	        if (strErroMessage == null || strErroMessage.length() <= 0){
	            out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"����\"> \n");
	        }else{
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
        if (lTypeID == Constant.RecordStatus.VALID)
        {
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
    public static void showCommonListControl(JspWriter out, String strControlName, String strSQL, String strDisplayField, String strID, long lSelectValue,
            boolean isNeedAll, String strProperty, boolean isNeedBlank) throws Exception
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
            //У�������sql������Ƿ����strDisplayField��strID
            if (strSQL.indexOf(strID) == -1 || strSQL.indexOf(strDisplayField) == -1)
            {
                System.out.println("����Ĳ�������ȷ��");
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
                    Log.print("��selected");
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
                    out.println("<option value=\"0\" selected>ȫ��</option>");
                }
                else
                {
                    out.println("<option value=\"0\" >ȫ��</option>");
                }
            }
            out.println("</select>");
        }
        catch (Exception e)
        {
            //System.out.println(" can not select OFFICE, because " + e);
            //throw e;
            Log.print("��ʾ�����б��쳣��" + e.toString());
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

    public static void showCommonListControl1(JspWriter out, String strControlName, String strSQL, String strDisplayField, String strID, long lSelectValue,
            boolean isNeedAll, String strProperty, boolean isNeedBlank) throws Exception
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
            //У�������sql������Ƿ����strDisplayField��strID
            if (strSQL.indexOf(strID) == -1 || strSQL.indexOf(strDisplayField) == -1)
            {
                System.out.println("����Ĳ�������ȷ��");
                return;
            }
            out.println("<select class=box name=\"" + strControlName + "\"" + strProperty + " onchange=\"onChanged(this)\">");
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
                    Log.print("��selected");
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
                    out.println("<option value=\"0\" selected>ȫ��</option>");
                }
                else
                {
                    out.println("<option value=\"0\" >ȫ��</option>");
                }
            }
            out.println("</select>");
        }
        catch (Exception e)
        {
            //System.out.println(" can not select OFFICE, because " + e);
            //throw e;
            Log.print("��ʾ�����б��쳣��" + e.toString());
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
    public static void showCommonListControl(JspWriter out, String strControlName, String strSQL, String strDisplayField, String strID, String strSelectValue,
            boolean isNeedAll, String strProperty, boolean isNeedBlank) throws Exception
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
            //У�������sql������Ƿ����strDisplayField��strID
            if (strSQL.indexOf(strID) == -1 || strSQL.indexOf(strDisplayField) == -1)
            {
                System.out.println("����Ĳ�������ȷ��");
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
                    Log.print("��selected");
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
                    out.println("<option value=\"0\" selected>ȫ��</option>");
                }
                else
                {
                    out.println("<option value=\"0\" >ȫ��</option>");
                }
            }
            out.println("</select>");
        }
        catch (Exception e)
        {
            //System.out.println(" can not select OFFICE, because " + e);
            //throw e;
            Log.print("��ʾ�����б��쳣��" + e.toString());
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
    public static void showCommonListControl(JspWriter out, String strControlName, String strSQL, String strDisplayField, String strID, long lSelectValue,
            boolean isNeedAll, String strProperty) throws Exception
    {
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
    public static void showFixedDepositMonthListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty,long lOfficeID,long lCurrencyID)
            throws Exception
    {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,(nFixedDepositMonthID || '����') as DepositMonthDesc");
        sbSQL.append(" from Sett_SubAccountType_Fixed a,sett_accountType b");
        sbSQL.append(" where a.nStatusID=1 and nAccountTypeID=b.id and b.nAccountGroupID = " + SETTConstant.AccountGroupType.FIXED + " and nFixedDepositMonthID < 10000 and nOfficeID = " + lOfficeID + " and nCurrencyID=" + lCurrencyID);
        sbSQL.append(" union");
        sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,((nFixedDepositMonthID-10000) || '��') as DepositMonthDesc");
        sbSQL.append(" from Sett_SubAccountType_Fixed a,sett_accountType b");
        sbSQL.append(" where a.nStatusID=1 and nAccountTypeID=b.id and b.nAccountGroupID = " + SETTConstant.AccountGroupType.FIXED + " and nFixedDepositMonthID>10000 and nOfficeID = " + lOfficeID + " and nCurrencyID=" + lCurrencyID
        		+" and b.OfficeID = " + lOfficeID + " and b.CurrencyID=" + lCurrencyID);
        String strDisplayField = "DepositMonthDesc";
        String strID = "DepositMonthID";
        showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
    }

    public static void showFixedDepositMonthListCtrl1(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty,long lOfficeID,long lCurrencyID)
    throws Exception
		{
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,(nFixedDepositMonthID || '����') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed a,sett_accountType b");
		sbSQL.append(" where a.nStatusID=1 and nAccountTypeID=b.id and b.nAccountGroupID = " + SETTConstant.AccountGroupType.FIXED + " and nFixedDepositMonthID < 10000 and nOfficeID = " + lOfficeID + " and nCurrencyID=" + lCurrencyID);
		sbSQL.append(" union");
		sbSQL.append(" select distinct nFixedDepositMonthID as DepositMonthID,((nFixedDepositMonthID-10000) || '��') as DepositMonthDesc");
		sbSQL.append(" from Sett_SubAccountType_Fixed a,sett_accountType b");
		sbSQL.append(" where a.nStatusID=1 and nAccountTypeID=b.id and b.nAccountGroupID = " + SETTConstant.AccountGroupType.FIXED + " and nFixedDepositMonthID>10000 and nOfficeID = " + lOfficeID + " and nCurrencyID=" + lCurrencyID);
		String strDisplayField = "DepositMonthDesc";
		String strID = "DepositMonthID";
		showCommonListControl1(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
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
    public static void showNotifyDepositMonthListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty,long lOfficeID,long lCurrencyID)
            throws Exception
    {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" select distinct a.nFixedDepositMonthID as DepositMonthID,(a.nFixedDepositMonthID || '����') as DepositMonthDesc");
        sbSQL.append(" from Sett_SubAccountType_Fixed a,sett_accountType b");
        sbSQL.append(" where a.nStatusID=1 and b.nStatusID=1 and a.nAccountTypeID=b.id and b.nAccountGroupID = " + SETTConstant.AccountGroupType.NOTIFY + " and a.nFixedDepositMonthID < 10000");
        sbSQL.append(" and a.nOfficeID = " + lOfficeID + " and a.nCurrencyID = " + lCurrencyID);
        sbSQL.append(" and b.OfficeID = " + lOfficeID + " and b.CurrencyID = " + lCurrencyID);
        sbSQL.append(" union");
        sbSQL.append(" select distinct a.nFixedDepositMonthID as DepositMonthID,((a.nFixedDepositMonthID-10000) || '��') as DepositMonthDesc");
        sbSQL.append(" from Sett_SubAccountType_Fixed a,sett_accountType b");
        sbSQL.append(" where a.nStatusID=1 and b.nStatusID=1 and a.nAccountTypeID=b.id and b.nAccountGroupID = " + SETTConstant.AccountGroupType.NOTIFY + " and a.nFixedDepositMonthID>10000");
        sbSQL.append(" and a.nOfficeID = " + lOfficeID + " and a.nCurrencyID = " + lCurrencyID);
        sbSQL.append(" and b.OfficeID = " + lOfficeID + " and b.CurrencyID = " + lCurrencyID);
        String strDisplayField = "DepositMonthDesc";
        String strID = "DepositMonthID";
        showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
    }

    /**
     * ��ʾ�������������б���ʾ�ؼ�
     * 
     * @param out
     *            ���
     * @param strControlName
     *            �ؼ�����
     * @param lSelectValue
     *            ��ѡ�����Ӧֵ���������ޣ�
     * @param isNeedAll
     *            �Ƿ���Ҫ��ȫ������
     * @param strProperty
     *            �����б�����
     * @throws Exception
     */
    public static void showLoanTermListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty,long lOfficeID,long lCurrencyID) throws Exception
    {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" select distinct nIntervalNum as id,(nIntervalNum || '����') as sDesc");
        sbSQL.append(" from loan_Contractform");
        sbSQL.append(" where nIntervalNum>0  ");
        sbSQL.append(" and nOfficeID = " + lOfficeID + " and nCurrencyID=" + lCurrencyID);
		sbSQL.append(" order by nIntervalNum");
        String strDisplayField = "sDesc";
        String strID = "id";
        showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
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
            throw new Exception("�������ݿ����");
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
                System.out.println("�ر����ݿ�����ʱ�������ݿ����");
            }
        }
        return v.size() > 0 ? v : null;
    }

    /**
     * У��ͻ����������Ч�ԡ� ִ�в����� ����¼У�� ��Ȩ��У�� ���ظ�������
     * 
     * �޸���ʷ��mzh_fu 2007-03-15���ĳ�ֻ�����û���¼�ɹ�������£��Ž���Ȩ��У�顢�ظ������飬���£� 
	 *     if (sessionMng.isLogin() == false){
	 *     		//��ʾδ��¼
	 *     }else{
	 *     		//Ȩ��У��
	 *          //�ظ�������
	 *     } 
	 *     
	 *       
     * @param out
     * @param request
     * @param response
     * @return boolean
     *  
     */
    public static boolean validateRequest(JspWriter out, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        boolean bResult = true;
        SessionMng sessionMng = null;
        try
        {
            HttpSession session = request.getSession(true);
            sessionMng = (SessionMng) session.getAttribute("sessionMng");
            //��¼���
            if (sessionMng.isLogin() == false)
            {
                SETTHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E002");
                SETTHTML.showHomeEnd(out);
                out.flush();
                bResult = false;
                
            }else{ // mzh_fu 2007-03-15 added "else"           	
            	
	            //�ж��Ƿ���Ȩ��
	            if (sessionMng.hasRight(request) == false)
	            {
	                SETTHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E002");
	                SETTHTML.showHomeEnd(out);
	                out.flush();
	                bResult = false;
	            }
	            //�����ظ�����
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
	                    Log.print("Request �е�ActionID ��Ч��");
	                }
	            }
	            Long lTemp = (Long) session.getAttribute("ActionID");
	
	            Log.print("Request �е�ActionID �� ��" + lActionID);
	            Log.print("Session �е�ActionID �� ��" + lTemp);
	            if (lActionID.longValue() != 0)
	            {
	                if (lTemp != null && lActionID.compareTo(lTemp) <= 0)
	                {
	                    Log.print("�ظ�����" + strTemp);
	                    SETTHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E008");
	                    SETTHTML.showHomeEnd(out);
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
        }
        catch (IException ie)
        {
            SETTHTML.showException(out, sessionMng, ie, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "");
            SETTHTML.showHomeEnd(out);
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
     * ��ʾ��ҵ���������б���ʾ�ؼ�
     * 
     * @param out
     *            ���
     * @param strControlName
     *            �ؼ�����
     * @param lSelectValue
     *            ��ѡ�����Ӧֵ
     * @param isNeedAll
     *            �Ƿ���Ҫ��ȫ������
     * @param strProperty
     *            �����б�����
     * @throws Exception
     */
    public static void showEnterpriseTypeListCtrl(JspWriter out,long lOfficeID, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty)
    		throws Exception
    {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" select  id, sname ");
        sbSQL.append(" from SETT_ENTERPRICETYPE ");
        sbSQL.append(" where  nstatusid=" + Constant.RecordStatus.VALID);
        sbSQL.append(" and nOfficeID=" + lOfficeID);
        sbSQL.append(" order by id");
        String strDisplayField = "sname";
        String strID = "id";
        showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
    }

    /**
     * ��ʾ�ͻ����������б���ʾ�ؼ�
     * 
     * @param out
     *            ���
     * @param strControlName
     *            �ؼ�����
     * @param lSelectValue
     *            ��ѡ�����Ӧֵ���������ޣ�
     * @param isNeedAll
     *            �Ƿ���Ҫ��ȫ������
     * @param strProperty
     *            �����б�����
     * @throws Exception
     */
    public static void showClientTypeListCtrl(JspWriter out,long lOfficeID, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty) throws Exception
    {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" select  id, sname ");
        sbSQL.append(" from sett_clienttype ");
        sbSQL.append(" where  nstatusid=" + Constant.RecordStatus.VALID);
        sbSQL.append(" and nOfficeID=" + lOfficeID);
        sbSQL.append(" order by id");
        String strDisplayField = "sname";
        String strID = "id";
        showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
    }
    /**
     * ��ʾ��չ���������б���ʾ�ؼ�
     * 
     * @param out
     *            ���
     * @param ExtendAttributeTypeID
     * 			  ��չ���Է���:SETTConstant.ExtendAttribute.Attribute1
     * 							SETTConstant.ExtendAttribute.Attribute2
     * 							SETTConstant.ExtendAttribute.Attribute3
     * 							SETTConstant.ExtendAttribute.Attribute4
     * @param lOfficeID
     * 			  ���´�
     * @param strControlName
     *            �ؼ�����
     * @param lSelectValue
     *            ��ѡ�����Ӧֵ
     * @param isNeedAll
     *            �Ƿ���Ҫ��ȫ������
     * @param strProperty
     *            �����б�����
     * @param isNeedBlank
     * 			  �Ƿ���Ҫ�հ���
     * @throws Exception
     */
    public static void showExtendAttributeListCtrl(JspWriter out,long ExtendAttributeTypeID,long lOfficeID, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty,boolean isNeedBlank) throws Exception
    {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" select id,name from Client_ExtendAttribute where AttributeID="+ ExtendAttributeTypeID);
        sbSQL.append(" and StatusID=" + Constant.RecordStatus.VALID);
       // sbSQL.append(" and nOfficeID=" + lOfficeID);
        sbSQL.append(" order by id");
        String strDisplayField = "name";
        String strID = "id";
        showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, isNeedBlank);
    }

    /**
     * ��ʾ��ҵ���������б���ʾ�ؼ�
     * 
     * @param out
     *            ���
     * @param strControlName
     *            �ؼ�����
     * @param lSelectValue
     *            ��ѡ�����Ӧֵ���������ޣ�
     * @param isNeedAll
     *            �Ƿ���Ҫ��ȫ������
     * @param strProperty
     *            �����б�����
     * @throws Exception
     */
    public static void showIndustryTypeListCtrl(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, String strProperty)
            throws Exception
    {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" select  id, sname ");
        sbSQL.append(" from sett_IndustryType ");
        sbSQL.append(" where  nstatusid=" + Constant.RecordStatus.VALID);
        sbSQL.append(" order by id");
        String strDisplayField = "sname";
        String strID = "id";
        showCommonListControl(out, strControlName, sbSQL.toString(), strDisplayField, strID, lSelectValue, isNeedAll, strProperty, true);
    }
    
    /**
	 * ��ʾ����ı�ͷ
	 * @param out
	 * @param bPortrait �Ƿ����
	 * @param strPapaerSize ֽ�Ŵ�С
	 * @param strPrinter ��ӡ������ - ����
	 * @param nNo ������ - ����
	 */
	public static void showCPFPrintHead(JspWriter out, boolean bPortrait, String strPaperSize, String strPrinter, int nNo) throws IOException
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
		out.println("<link rel=\"stylesheet\" href=\"/graphics/template.css\" type=\"text/css\">\n");
		out.println("<style type=\"text/css\">\n");
		out.println("<!--\n");
		out.println(".table1 {  border: 1px #000000 solid}\n");
		out.println(
			".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out.println(
			".td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out.println(
			".td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out.println(
			".td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out.println(
			".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}\n");
		out.println(
			".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out.println(
			".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out.println(
			".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out.println(
			".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out.println(
			".td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}\n");
		out.println(
			".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}\n");
		out.println(
			".small-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out.println(
			".small-td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out.println(
			".small-td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out.println(
			".small-td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out.println(
			".small-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 9px}\n");
		out.println(
			".small-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out.println(
			".small-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out.println(
			".small-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out.println(
			".small-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		out.println(
			".small-td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 9px}\n");
		out.println(
			".small-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 9px}\n");
		
		out.println("-->\n");
		out.println("</style>\n");
		out.println("</head>\n");
		out.println("<!-- MeadCo Security Manager -->\n");
		out.println("<object viewastext style=\"display:none\"\n");
		out.println("	classid=\"clsid:5445be81-b796-11d2-b931-002018654e2e\"\n");
		out.println("	codebase=\"/webpb/Library/smsx.cab#Version=6,1,429,14\">\n");
		out.println("	<param name=\"GUID\" value=\"{E74B584D-C36B-11D6-A31F-0020ED1AFD98}\">\n");
		out.println("	<param name=\"Path\" value=\"/graphics/sxlic.mlf\">\n");
		out.println("	<param name=\"Revision\" value=\"2\">\n");
		out.println("</object>\n");
		out.println("\n");
		out.println("<!-- MeadCo ScriptX -->\n");
		out.println("<object viewastext id=\"factory\" style=\"display:none\"\n");
		out.println("	classid=\"clsid:1663ed61-23eb-11d2-b92f-008048fdd814\"\n");
		out.println("	codebase=\"/webpb/Library/smsx.cab#Version=6,1,429,14\">\n");
		out.println("</object>\n");
		out.println("<script defer>\n");
		out.println("	function window.onload()\n");
		out.println("	{\n");
		out.println("		factory.printing.header = \"&b,&p/&P\"\n");
		out.println("		factory.printing.footer = \"\"\n");
		out.println("		factory.printing.leftMargin = 0.9\n");
		out.println("		factory.printing.topMargin = 0.8\n");
		out.println("		factory.printing.rightMargin = 0.2\n");
		out.println("		factory.printing.bottomMargin = 0.5\n");
		out.println("		factory.printing.portrait=" + strPortrait + ";//����\n");
		out.println("		factory.printing.paperSize=\"" + strPaperSize + "\";\n");
		out.println("	}\n");
		out.println("	function document.onkeydown(DnEvents)\n");
		out.println("	{\n");
		out.println("		k =  window.event.keyCode;\n");
		out.println("		if(k==13)\n");
		out.println("		{\n");
		out.println("			if (confirm(\"�Ƿ��ӡ��\"))\n");
		out.println("			{\n");
		out.println("				//factory.printing.printer=\"\";����д��ӡ��������\n");
		out.println("				factory.printing.Print(true);\n");
		out.println("			}\n");
		out.println("		}\n");
		out.println("		if(k==32)\n");
		out.println("		{\n");
		out.println("			if (confirm(\"�Ƿ�Ԥ����\"))\n");
		out.println("			{\n");
		out.println("				//factory.printing.printer=\"\";����д��ӡ��������\n");
		out.println("				factory.printing.Preview();\n");
		out.println("			}\n");
		out.println("		}\n");
		out.println("}	\n");
		out.println("</script>\n");
		out.println("\n");
	} 

}
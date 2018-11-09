/*
 * OBHtml.java
 * 
 * Created on 2002年12月8日
 */
package com.iss.itreasury.ebank.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.dataentity.HtmlHeaderInfo;
import com.iss.itreasury.ebank.obpaynotice.dao.OBPayNoticeDao;
import com.iss.itreasury.ebank.obpaynotice.dataentity.ShowGrantLoanInfo;
import com.iss.itreasury.ebank.obprint.dataentity.OBPrintInfo;
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.IPrint;
import com.iss.itreasury.util.Log;

public class OBHtml extends IPrint
{
	/**
     * 校验客户端请求的有效性。 执行操作： －登录校验 －权限校验 －重复请求检查
     * 
     * @param out
     * @param request
     * @param response
     * @return boolean
     */
	private static String menu = null;
	private static String[] menus = null;     
	private static String[] menuName = null;
	private static String[] menuURL = null;
    
	public static boolean validateRequest(JspWriter out, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        boolean bResult = true;
        SessionOB sessionMng = null;
        try
        {
            HttpSession session = request.getSession(true);
            sessionMng = (SessionOB) session.getAttribute("sessionMng");
                		            
            //判断是否登录
            if (sessionMng.isLogin() == false)
            {
            	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
	    		out.flush();
	    		bResult = false;
            }
            
            //判断是否有权限
    		if (sessionMng.hasRight(request) == false)
    		{
    			OBHtml.showCommonMessage(out, sessionMng, "","", 2, "Gen_E003");
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
                    OBHtml.showCommonMessage(out, sessionMng, "","", 2, "Gen_E008");
        			//FOBHtml.showForeignOBHomeEnd(out,sessionMng);
                    //out.flush();
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
            OBHtml.showCommonMessage(out, sessionMng, "","", 2, "Gen_E001");
			//FOBHtml.showForeignOBHomeEnd(out,sessionMng);
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
     * 显示代码控件
     * 
     * @param strFieldName
     *            域的名称
     * @param lCodeTypeID
     *            代码类型
     * @param lCodeID
     *            代码，如果>0表示选中这个控件
     * @param strFieldDesc
     *            域描述
     */
    public static void showCodeControl(JspWriter out, String strFieldName, long lCodeTypeID, long lCodeID, String strFieldDesc, long lDescID) throws Exception
    {
        /*
         * try { if (lCodeTypeID == Notes.CODETYPE_RECORD_FIXEDDEPOSITMONTH ||
         * lCodeTypeID == Notes1.CODETYPE_RECORD_LOANMONTH) { //定期存款期限
         * out.println(" <select class=box name=\"" + strFieldName + "\" " +
         * strFieldDesc + ">"); out.println(" <option value=\"-1\"> </option>");
         * Collection collection = Common.getCodeInfo(lCodeTypeID, lDescID); if
         * (collection != null) { Iterator iteratorCode = collection.iterator();
         * collection = null; CommonIDNameDesc cind = null; for (int nIndex = 0;
         * iteratorCode.hasNext(); nIndex++) { cind = (CommonIDNameDesc)
         * iteratorCode.next(); String strSelected = ""; if (cind.m_lID ==
         * lCodeID) { strSelected = "selected"; } String strTTT = ""; if
         * (cind.m_strName != null) { if (Long.parseLong(cind.m_strName) <
         * 10000) { strTTT = cind.m_strName + "月"; } else { strTTT =
         * String.valueOf(Long.parseLong(cind.m_strName) - 10000) + "天"; } } if
         * (strTTT == null || strTTT.equals("")) { strTTT = "0"; } out.println("
         * <option value=\"" + String.valueOf(cind.m_lID) + "\"" + strSelected +
         * ">" + strTTT + " </option>"); } } out.println(" </select>"); } else {
         * out.println(" <select class=box name=\"" + strFieldName + "\" " +
         * strFieldDesc + ">"); out.println(" <option value=\"-1\"> </option>");
         * Collection collection = Common.getCodeInfo(lCodeTypeID, lDescID); if
         * (collection != null) { Iterator iteratorCode = collection.iterator();
         * collection = null; CommonIDNameDesc cind = null; for (int nIndex = 0;
         * iteratorCode.hasNext(); nIndex++) { cind = (CommonIDNameDesc)
         * iteratorCode.next(); String strSelected = ""; if (cind.getID() ==
         * lCodeID) { strSelected = "selected"; } out.println(" <option
         * value=\"" + String.valueOf(cind.getID()) + "\"" + strSelected + ">" +
         * cind.getName() + " </option>"); } } out.println(" </select>"); } }
         * catch (Exception exp) { throw exp; }
         */

    }

    /**
     * 显示HTML开始的部分
     * 
     * @param out
     * @param sessionOB
     *            用户session
     * @param lModule
     *            当前模块标示
     * @param strTitle
     *            页面显示名称
     * @param lShowMenu
     *            是否显示菜单, <>2 显示菜单 =2 不显示菜单
     *  
     */
    public static void showOBHomeHead(JspWriter out, SessionOB sessionOB, String strTitle, long lShowMenu) throws IException, IOException
    {
        try
        {
            String strStatus = "";
            String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.EBANK) + "msg/RemindMsg.jsp";
            if (sessionOB.isLogin())
            {
                try
                {
                    // java.sql.Timestamp(new java.util.Date().getTime()));
//                    strStatus = sessionOB.m_strUserName + "  " + Env.getSystemDateString() + "  " + Env.getOfficeName(sessionOB.m_lOfficeID) + "  "
//                            + Constant.CurrencyType.getName(sessionOB.m_lCurrencyID);
                	
                		  strStatus = Env.getClientName(sessionOB.m_lClientID) + " " + sessionOB.m_strUserName + "  " + DataFormat.getChineseTimeString(Env.getSystemDateTime()) + "  " 
		                  + Constant.CurrencyType.getName(sessionOB.m_lCurrencyID) + "<br>";
                		  if(sessionOB.dLastLoginTime != null && !sessionOB.dLastLoginTime.equals("")){	  
                			  strStatus += "您的上次登录时间 "+ DataFormat.getChineseTimeString(sessionOB.dLastLoginTime);
                		  }
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
            htmlHeader.setEBank_SessionMng(sessionOB);
            htmlHeader.setSubTitle(strTitle);
            htmlHeader.setShowMenu(lShowMenu);
            htmlHeader.setRemindURL(strRemindURL);
            htmlHeader.setStatus(strStatus);
            htmlHeader.setProjectName(strProject);
            htmlHeader.setTitle(strProject);
            
            displyHtmlHeader(htmlHeader);
            /*if(lShowMenu == 2) {
            	displyHtmlHeaderForOther(htmlHeader);
        	}
        	else {
        		displyHtmlHeader(htmlHeader);
        	}*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
    public static void showOBHomeHeadForYQ(JspWriter out, SessionOB sessionOB, String strTitle, long lShowMenu) throws IException, IOException
    {
        try
        {
            String strStatus = "";
            String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.EBANK) + "msg/RemindMsg.jsp";
            if (sessionOB.isLogin())
            {
                try
                {
                    // java.sql.Timestamp(new java.util.Date().getTime()));
//                    strStatus = sessionOB.m_strUserName + "  " + Env.getSystemDateString() + "  " + Env.getOfficeName(sessionOB.m_lOfficeID) + "  "
//                            + Constant.CurrencyType.getName(sessionOB.m_lCurrencyID);
                  strStatus = Env.getClientName(sessionOB.m_lClientID) + " " + sessionOB.m_strUserName + "  " + DataFormat.getChineseTimeString(Env.getSystemDateTime()) + "  " 
                  + Constant.CurrencyType.getName(sessionOB.m_lCurrencyID) + "<br>";
                  if(sessionOB.dLastLoginTime != null && !sessionOB.dLastLoginTime.equals("")){
                	  strStatus += "您的上次登录时间 " + DataFormat.getChineseTimeString(sessionOB.dLastLoginTime);
                  }
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
            htmlHeader.setEBank_SessionMng(sessionOB);
            htmlHeader.setSubTitle(strTitle);
            htmlHeader.setShowMenu(lShowMenu);
            htmlHeader.setRemindURL(strRemindURL);
            htmlHeader.setStatus(strStatus);
            htmlHeader.setProjectName(strProject);
            htmlHeader.setTitle(strProject);
            //
            displyHtmlHeader(htmlHeader);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
	/**
	 * Create my leiyang date 2008/11/17
	 * @param out
	 * @param strTitle
	 * @return
	 */
	public HtmlHeaderInfo getHtmlHeaderInfo(JspWriter out, SessionOB sessionOB, String strTitle){
		HtmlHeaderInfo htmlHeader = null;
        try{
            String strStatus = "";
            String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.EBANK) + "msg/RemindMsg.jsp";
            if (sessionOB.isLogin())
            {
                try
                {
                  strStatus = Env.getClientName(sessionOB.m_lClientID) + " " + sessionOB.m_strUserName + "  " + DataFormat.getChineseTimeString(Env.getSystemDateTime()) + "  " + Constant.CurrencyType.getName(sessionOB.m_lCurrencyID) + "<br>";
                  if(sessionOB.dLastLoginTime != null && !sessionOB.dLastLoginTime.equals("")){
                	  strStatus += "您的上次登陆时间 " + DataFormat.getChineseTimeString(sessionOB.dLastLoginTime)+"<br>";
                  }
                  EndDayProcess process = new EndDayProcess();
                  strStatus+="结算开机日："+Env.getSystemDateString(sessionOB.m_lOfficeID, sessionOB.m_lCurrencyID)+"&nbsp;&nbsp;&nbsp;&nbsp;"+SETTConstant.SystemStatus.getName(process.getSystemStatusID(sessionOB.m_lOfficeID, sessionOB.m_lCurrencyID));
                }
                catch (Exception exp) {
                	exp.printStackTrace();
                }
            }
	        String strProject = "iTreasuryPro";
	        
	        htmlHeader = new HtmlHeaderInfo();
            htmlHeader.setJspWriter(out);
            htmlHeader.setEBank_SessionMng(sessionOB);
            htmlHeader.setSubTitle(strTitle);
            htmlHeader.setShowMenu(Constant.ShowMenu.YES);
            htmlHeader.setRemindURL(strRemindURL);
            htmlHeader.setStatus(strStatus);
            htmlHeader.setProjectName(strProject);
            htmlHeader.setTitle(strProject);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return htmlHeader;
	}

    /**
     * 显示HTML结束的部分
     * 
     * modify by kenny (2006-04-13)
     * @param out
     *  
     */
    public static void showOBHomeEnd(JspWriter out) throws IOException
    {
    	long lOfficeID = 1;//默认值
    	long lCurrencyID = 1;//默认值
    	showOBHomeEnd(out, lOfficeID, lCurrencyID);      
    }

    /**
     * 显示HTML结束的部分
     * 
     * modify by kenny (2006-04-13)
     * @param out
     *  
     */
    public static void showOBHomeEnd(JspWriter out, long lOfficeID, long lCurrencyID) throws IOException
    {
    	//String[] date = DataFormat.getDateString(Env.getSystemDate(lOfficeID, lCurrencyID)).split("-");
        /*out.println("    <br></td>\n");
        out.println("  </tr>\n");
        out.println("</table>\n");
        out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
        out.println("  <tr>\n");
        //out.println("    <td class=\"pagefootTl\"><img src=\"/itreasury/graphics/space.gif\" width=\"778\" height=\"1\"></td>\n");
        out.println("  </tr>\n");
        out.println("  <tr>\n");
        //out.println("    <td height=\"25\" class=\"pagefoot\"><font face=\"Arial, Helvetica, sans-serif\">&copy;</font> 2005-"+date[0]+" 软通动力 版权所有</td>\n");
        out.println("  </tr>\n");
        out.println("</table>\n");
        out.println("</body>\n");
        out.println("</html>");*/
    	
    	//out.println("<td width=\"5\">&nbsp;</td>");
		out.println("</td></tr></tbody></table></body></html>");
        //Modify by jiangqi 2011-03-30
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
     * @param strTitle
     *            页面的Title
     * @param lTypeID
     *            是否带头: 1-带头,2-不带头
     * @param strErroCode
     *            错误码
     */
    public static void showMessage(JspWriter out, SessionOB sessionMng, Exception exception, String strTitle, String strReturn, long lTypeID,
            String strErroCode) throws Exception
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

        if (lTypeID == 1)
        {
            showOBHomeHead(out, sessionMng, strTitle, 1);
        }

        out.println("<TABLE align=center border=0 class=top height=217 width=\"27%\">\n");
        out.println("  <TBODY>\n");
        out.println("  <TR ");
        out.println("> \n");
        out.println("    <TD class=FormTitle height=2 width=\"100%\"><B>" + Env.getClientName() + "</B></TD></TR>\n");
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
    	if(strErroCode != null && strErroCode.length() > 0 && strErroCode.equals("Gen_E002")){
			out.println("        <INPUT type=\"button\" class=button name=\"goback\" onclick=\"javascript:window.parent.location.href='"+Constant.SDCURL+"';\"  value=\"返回\"> \n");
		}else{
			if (strReturn == null || strReturn.equals("")){
				out.println("        <INPUT type=\"button\" class=button name=\"goback\" onclick=\"javascript:history.back(-1)\"  value=\"返回\"> \n");
			}
        	else{
        		out.println("		<INPUT type=\"button\" class=button name=\"goback\"  onclick=\"self.location.href='" + strReturn + "'\" value=\"返回\"> \n");
        	}
		}
        out.println("            </DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>\n");
        out.println("<SCRIPT language=JavaScript>");
        out.println("   goback.focus();");
        out.println("</SCRIPT>");

        if (lTypeID == 1)
        {
            showOBHomeEnd(out);
        }
    }

    public static void showCommonMessage(JspWriter out, SessionOB sessionMng, String strTitle, String strReturn, long lTypeID, String strErroCode)
            throws Exception
    {
        showMessage(out, sessionMng, null, strTitle, strReturn, lTypeID, strErroCode);
    }

    public static void showExceptionMessage(JspWriter out, SessionOB sessionMng, Exception exception, String strTitle, String strReturn, long lTypeID)
            throws Exception
    {
        showMessage(out, sessionMng, exception, strTitle, strReturn, lTypeID, "");
    }

    /**
     * 显示货币符号
     * 
     * @param out
     * @param lCurrencyID
     *            Created by Linsg,2002-03-28
     */
    public static void showCurrencyIcon(JspWriter out, long lCurrencyID) throws Exception
    {
        try
        {
            switch ((int) lCurrencyID)
            {
                case (int) Constant.CurrencyType.RMB:
                    out.print("￥");
                    break;
                case (int) Constant.CurrencyType.USD:
                    out.print("$");
                    break;
                case (int) Constant.CurrencyType.UKP:
                    out.print("&pound;");
                    break;
                case (int) Constant.CurrencyType.ED:
                    out.print("E");
                    break;
                default:
                    out.print("￥");
            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 得到TD的宽度
     * 
     * @param nNumber
     *            字数
     * @return 返回TD的宽度
     */
    public static long getTDWidth(int nNumber)
    {
        return 12 * nNumber;
    }

    /**
     * 得到字体
     * 
     * @param strData
     *            字符串
     * @param nNumber
     *            允许字数（双字节，单字节算0.5个）
     * @return 返回正确的class
     */
    public static String getDataFont(String strData, String strClass, int nNumber)
    {
        if (strData == null)
        {
            strData = "";
        }
        //得到单字节数
        byte[] byTemp = strData.getBytes();
        int nLength = byTemp.length;
        String strReturn = "";
        if (nLength > nNumber * 2)
        {
            strReturn = "small-" + strClass;
        }
        else
        {
            strReturn = strClass;
        }
        return strReturn;
    }

    /**
     * 得到行数（内容所占用的行数） add by rxie
     * 
     * @param strData
     *            字符串
     * @param nNumber
     *            允许字数（双字节，单字节算0.5个）
     * @return 返回行数
     */
    public static int getDataLine(String strData, int nNumber)
    {
        int lReturn = -1;
        if (strData == null)
        {
            strData = "";
        }
        //得到单字节数
        byte[] byTemp = strData.getBytes();
        int nLength = byTemp.length;
        nNumber = nNumber * 2;
        if (nLength % nNumber == 0 && nLength / nNumber != 0) //处理字符数等于行宽的情况
        {
            lReturn = nLength / nNumber - 1;
        }
        else
        {
            lReturn = nLength / nNumber;
        }
        return lReturn;
    }

    /**
     * 输出翻页控件 add by hyzeng
     * 
     * @param out
     * @param sFormName
     *            翻页控件使用的表单名称
     * @param sFormAction
     *            翻页控件使用的表单提交到的页面
     * @param inResultSize
     *            记录总数
     * @param inInitPageNo
     *            页号
     * @param inPageSize
     *            每页的记录数
     * @param lOrderParam
     *            排序的字段
     * @param lDesc
     *            升序或降序
     */
    public static void showTurnPageControl(JspWriter out, String sFormName, String sFormAction, long inResultSize, long inInitPageNo, long inPageSize,
            long lOrderParam, long lDesc) throws Exception
    {
        long inPageCount = 0;
        //计算总页数
        if (inResultSize > inPageSize)
        {
            inPageCount = inResultSize / inPageSize;
            if ((inResultSize % inPageSize) != 0)
            {
                inPageCount++;
            }
        }
        else if (inResultSize > 0 && inResultSize <= inPageSize)
        {
            inPageCount = 1;
        }
        else
        {
            inPageCount = 0;
        }

        StringBuffer sbTurnPageControl = new StringBuffer();
        
        sbTurnPageControl.append("<TABLE width=100% border=0 cellspacing=3 cellpadding=0 class=SearchBar> \n");
        sbTurnPageControl.append("<form name=" + sFormName + " action=" + sFormAction + " method=post> \n");
        sbTurnPageControl.append("<TR> \n");
        sbTurnPageControl.append("<TD width=952 align=right> \n");
        sbTurnPageControl.append("<b> 第 \n");
        sbTurnPageControl.append("<input type=text name=txtPageNo maxlength=3 size=3 class=SearchBar_Page value=" + inInitPageNo
                + " onkeydown='Javascript:k = window.event.keyCode;if (k == 13){return turnPage(1);}'> \n");
        sbTurnPageControl.append("页 / 共 " + inPageCount + " 页 \n");
        sbTurnPageControl.append("<input type=submit name=SUBMIT1 value=go class=SearchBar_Btn onclick='Javascript:return turnPage(1);'> \n");
        sbTurnPageControl.append("<input type=submit name=SUBMIT2 value='|&lt;' class=SearchBar_Btn onclick='Javascript:return turnPage(2);'> \n");
        sbTurnPageControl.append("<input type=submit name=SUBMIT3 value='&lt;' class=SearchBar_Btn onclick='Javascript:return turnPage(3);'> \n");
        sbTurnPageControl.append("<input type=submit name=SUBMIT4 value='&gt;' class=SearchBar_Btn onclick='Javascript:return turnPage(4);'> \n");
        sbTurnPageControl.append("<input type=submit name=SUBMIT5 value='&gt;|' class=SearchBar_Btn onclick='Javascript:return turnPage(5);'> \n");
        sbTurnPageControl.append("<input type=hidden name=lPageCount value=" + inPageCount + "> \n");
        sbTurnPageControl.append("<input type=hidden name=lOrderParam value=" + lOrderParam + "> \n");
        sbTurnPageControl.append("<input type=hidden name=lDesc value=" + lDesc + "> \n");
        sbTurnPageControl.append("</b> \n");
        sbTurnPageControl.append("</TD> \n");
        sbTurnPageControl.append("</TR> \n");
        sbTurnPageControl.append("</form> \n");
        sbTurnPageControl.append("</TABLE> \n");

        sbTurnPageControl.append("<script language=javascript>\n");
        sbTurnPageControl.append("function turnPage(clicked){ \n");
        sbTurnPageControl.append("var iPage;var sSubmit;var iAllPage; \n");
        sbTurnPageControl.append("iAllPage = parseInt(" + sFormName + ".lPageCount.value); \n");
        sbTurnPageControl.append("if (!InputValid(" + sFormName + ".txtPageNo, 1, 'int', 0, 1, 10, '页数'))return false; \n");
        sbTurnPageControl.append("iPage = parseInt(" + sFormName + ".txtPageNo.value); \n");
        sbTurnPageControl.append("if (iAllPage < 1){ return false;} \n");
        sbTurnPageControl.append("if ((iPage < 1) || (iPage > iAllPage)){alert(\"没有此页\");"+sFormName+".txtPageNo.value=1;"+"return false;} \n");
        sbTurnPageControl.append("switch (clicked){ \n");
        sbTurnPageControl.append("case 1 : \n");
        sbTurnPageControl.append("if ((iPage == '') || (iPage < 0) || (iPage > iAllPage)){return false;} \n");
        sbTurnPageControl.append("else{" + sFormName + ".txtPageNo.value = iPage;}break; \n");
        sbTurnPageControl.append("case 2 : \n");
        sbTurnPageControl.append("if (iPage <= 1){return false;} \n");
        sbTurnPageControl.append("else{" + sFormName + ".txtPageNo.value = 1;}break; \n");
        sbTurnPageControl.append("case 3 : \n");
        sbTurnPageControl.append("if (iPage > 1){" + sFormName + ".txtPageNo.value = iPage - 1;} \n");
        sbTurnPageControl.append("else{return false;}break; \n");
        sbTurnPageControl.append("case 4 : \n");
        sbTurnPageControl.append("if (iPage < iAllPage){" + sFormName + ".txtPageNo.value = iPage + 1;} \n");
        sbTurnPageControl.append("else{return false;}break; \n");
        sbTurnPageControl.append("case 5 : \n");
        sbTurnPageControl.append("if (iPage >= iAllPage){return false;} \n");
        sbTurnPageControl.append("else{" + sFormName + ".txtPageNo.value = iAllPage;}break; \n");
        sbTurnPageControl.append("}showSending(); \n");
        sbTurnPageControl.append("return true;} \n");
        sbTurnPageControl.append(" \n");
        sbTurnPageControl.append("function go(frm, lDescPara){ \n");
        sbTurnPageControl.append("var iAllPage = parseInt(" + sFormName + ".lPageCount.value); \n");
        sbTurnPageControl.append("if (iAllPage < 1){return;} \n");
        sbTurnPageControl.append("if (frm.lOrderParam.value == lDescPara){ \n");
        sbTurnPageControl.append("if (frm.lDesc.value == " + Constant.PageControl.CODE_ASCORDESC_ASC + "){ \n");
        sbTurnPageControl.append("frm.lDesc.value = " + Constant.PageControl.CODE_ASCORDESC_DESC + "; \n");
        sbTurnPageControl.append("}else{ \n");
        sbTurnPageControl.append("frm.lDesc.value = " + Constant.PageControl.CODE_ASCORDESC_ASC + ";}} \n");
        sbTurnPageControl.append("frm.lOrderParam.value = lDescPara; \n");
        sbTurnPageControl.append("frmSubmit(frm); \n");
        sbTurnPageControl.append("} \n");
        sbTurnPageControl.append(" \n");
        sbTurnPageControl.append("function frmSubmit(frm) \n");
        sbTurnPageControl.append("{ showSending(); \n");
        sbTurnPageControl.append("frm.submit(); \n");
        sbTurnPageControl.append("} \n");
        sbTurnPageControl.append(" \n");
        sbTurnPageControl.append("</script>");

        out.println(sbTurnPageControl.toString());
    }

    public static void showWithDraw1(OBPrintInfo info, JspWriter out) throws Exception
    {
        try
        {
            //out.println(" <html> " +
            //" <head> " );
            ////noShowPrintHeadAndFooter(out);
            out
                    .print("<Script Language=\"JavaScript\"> document.write(' "
                            + " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
                            + " <link rel=\"stylesheet\" href=\"/websett/template.css\" type=\"text/css\"> "
                            + " <style type=\"text/css\"> "
                            + " <!-- "
                            + " .In1-table1 {  border: 2px #000000 solid} "
                            + " .In1-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px} "
                            + " .In1-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px} "
                            + " .In1-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px} "
                            + " .In1-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
                            + " .In1-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px} "
                            + " .In1-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
                            + " .In1-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
                            + " .In1-td-top {  border-color: black black #000000; border-style: solid; border-top-width: 2px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px} "
                            + " --> "
                            + " </style> "
                            + "	<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
                            + "<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
                            + "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD  width=\"50\">&nbsp;	</TD><TD width=\"310\">&nbsp;	</TD><TD width=\"70\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
                            + "<TR> 	" + "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;	</TD> "
                            + "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">" + Env.getClientName()
                            + "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
                            + "      存款支取凭证</font></font></strong> 　</strong></TD>" + "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");
            out
                    .println("</TD>"
                            + "</TR> "
                            + "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
                            + "</TABLE> 	<TABLE width=\"592\">  "
                            + "<TR>		<TD width=\"177\"> </TD> 	"
                            + "	<TD align=\"center\" width=\"198\">"
                            + info.getYear()
                            + " 年 "
                            + info.getMonth()
                            + " 月 "
                            + info.getDay()
                            + " 日 "
                            + "</TD> 	"
                            + "	<TD width=\"210\" align=\"right\">第 "
                            + info.getTransNo()
                            + " 号</TD> "
                            + "</TR> "
                            + "	</TABLE>"
                            + " <table><tr><td>"
                            + " <TABLE width=\"590\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\">"
                            + "   <TR>      <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">付<BR>       款<BR>       人</FONT></B> </TD>"
                            + "     <TD align=\"center\" width=\"70\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>      </TD>"
                            + "     <TD align=\"left\"  colspan=\"4\" nowrap class=\"In1-td-rightbottom\" width=\"180\">"
                            + info.getPayAccountName()
                            + "&nbsp;  </TD>   "
                            + "  <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">收<BR>       款<BR>       人</FONT></B> </TD>"
                            + "     <TD align=\"center\" width=\"70\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>      </TD>"
                            + "     <TD align=\"left\" colspan=\"4\" nowrap class=\"In1-td-bottom\" width=\"180\">"
                            + info.getReceiveAccountName()
                            + "&nbsp; </TD>  "
                            + " </TR> "
                            + "  <TR>    <TD align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>      </TD>  "
                            + "   <TD colspan=\"4\" align=\"left\" nowrap class=\"In1-td-rightbottom\">"
                            + info.getPayAccountNo()
                            + "&nbsp;</TD> "
                            + "    <TD align=\"center\"  class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>      </TD>    "
                            + " <TD colspan=\"4\" align=\"left\" nowrap class=\"In1-td-bottom\">"
                            + info.getReceiveAccountNo()
                            + "&nbsp;</TD>   "
                            + "</TR>"
                            + "   <TR>   <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">开户银行</font></b>      </td>  "
                            + "   <td  nowrap colspan=\"4\" class=\"In1-td-rightbottom\">"
                            + info.getPayBankName()
                            + "&nbsp;</td>  "
                            + "   <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">汇入地点</font></b>      </td>  "
                            + "   <td width=\"50\" nowrap colspan =\"2\" class=\"In1-td-rightbottom\">" + info.getReceiveRemitInAddress() + "&nbsp;</td>     "
                            + "<td width=\"45\" nowrap class=\"In1-td-rightbottom\"><b><font face=\"楷体_GB2312\">汇入行<br>       名称</font></b></td>    "
                            + " <td width=\"104\" nowrap class=\"In1-td-bottom\">" + info.getReceiveBankName() + "&nbsp;</td> " + "  </TR>  " + " <TR>    "
                            + "  <TD colspan=\"2\" align=\"center\" class=\"In1-td-topbottom2right\" height=\"30\" nowrap><B><FONT face=\"楷体_GB2312\">"
                            + info.getCurrencyName() + "<BR>       (大写)</FONT></B> </TD>    "
                            + " <TD colspan=\"6\" class=\"In1-td-topbottom2right\" nowrap><B>" + info.getChineseAmount() + "&nbsp;</B> </TD>    "
                            + " <TD colspan=\"4\" align=\"right\" nowrap class=\"In1-td-topbottom2\"><B>" + info.getAmount() + "&nbsp; </B>      </TD> "
                            + "  </TR>  " + " <TR>    " + "  <TD align=\"center\" class=\"In1-td-rightbottom\" colspan=\"2\"><b> 摘　　要 </b>&nbsp;</TD>    "
                            + " <TD colspan=\"12\" class=\"In1-td-bottom\" >" + info.getAbstract() + "&nbsp; </TD>   " + "</TR>  " + "<TR>    "
                            + "   <TD colspan=\"14\" ><B>以上款项已在你单位账下付讫</B>&nbsp; </TD>  " + " </TR> " + " </TABLE> " + "<TABLE width=\"30\" border=\"0\"> 	"
                            + "	<TR> 	" + "		<TD height=\"160\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>记<BR>账<BR>凭<BR>证</FONT> </TD> "
                            + "		</TR> 	</TABLE>" + " </td></tr></table>" + "<br>" + "<table width=\"590\"><TR align=\"right\">     "
                            + "   <TD  colspan=\"3\" align=\"right\">&nbsp;  客户网上银行 </TD>     "
                            + "<TD width=\"360\" height=\"22\" colspan=\"11\"  nowrap>[录入] &nbsp;" + info.getOBInputUserName() + "&nbsp;[复核]&nbsp;"
                            + info.getOBCheckUserName() + "&nbsp; [签认]&nbsp;" + info.getOBSignUserName() + " </TD>  " + " </TR>   " + "<TR align=\"right\">   "
                            + "   <TD  colspan=\"3\" align=\"right\">&nbsp;  " + Env.getClientName() + " </TD>    "
                            + " <TD colspan=\"11\" height=\"22\"  nowrap>[录入]&nbsp;" + info.getInputUserName() + "&nbsp;[复核]&nbsp;" + info.getCheckUserName()
                            + " </TD>   " + "</TR>" + "</table>" + " </BODY> " + " '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }

    public static void showWithDraw2(OBPrintInfo info, JspWriter out) throws Exception
    {
        try
        {
            //out.println(" <html> " +
            //" <head> " );
            ////noShowPrintHeadAndFooter(out);
            out
                    .print(" <Script Language=\"JavaScript\"> document.write(' "
                            + " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
                            + " <link rel=\"stylesheet\" href=\"/websett/template.css\" type=\"text/css\"> "
                            + " <style type=\"text/css\"> "
                            + " <!-- "
                            + " .In1-table1 {  border: 2px #000000 solid} "
                            + " .In1-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px} "
                            + " .In1-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px} "
                            + " .In1-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px} "
                            + " .In1-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
                            + " .In1-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px} "
                            + " .In1-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
                            + " .In1-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
                            + " .In1-td-top {  border-color: black black #000000; border-style: solid; border-top-width: 2px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px} "
                            + " --> "
                            + " </style> "
                            + "	<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
                            + "<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
                            + "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD  width=\"50\">&nbsp;	</TD><TD width=\"310\">&nbsp;	</TD><TD width=\"70\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
                            + "<TR> 	" + "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;	</TD> "
                            + "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">" + Env.getClientName()
                            + "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
                            + "      存款支取凭证</font></font></strong> 　</strong></TD>" + "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");
            out
                    .println("</TD>"
                            + "</TR> "
                            + "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
                            + "</TABLE> 	<TABLE width=\"592\">  "
                            + "<TR>		<TD width=\"177\"> </TD> 	"
                            + "	<TD align=\"center\" width=\"198\">"
                            + info.getYear()
                            + " 年 "
                            + info.getMonth()
                            + " 月 "
                            + info.getDay()
                            + " 日 "
                            + "</TD> 	"
                            + "	<TD width=\"210\" align=\"right\">第 "
                            + info.getTransNo()
                            + " 号</TD> "
                            + "</TR> "
                            + "	</TABLE>"
                            + " <table><tr><td>"
                            + " <TABLE width=\"590\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\">"
                            + "   <TR>      <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">付<BR>       款<BR>       人</FONT></B> </TD>"
                            + "     <TD align=\"center\" width=\"70\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>      </TD>"
                            + "     <TD align=\"left\"  colspan=\"4\" nowrap class=\"In1-td-rightbottom\" width=\"180\">"
                            + info.getPayAccountName()
                            + "&nbsp;  </TD>   "
                            + "  <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">收<BR>       款<BR>       人</FONT></B> </TD>"
                            + "     <TD align=\"center\" width=\"70\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>      </TD>"
                            + "     <TD align=\"left\" colspan=\"4\" nowrap class=\"In1-td-bottom\" width=\"180\">"
                            + info.getReceiveAccountName()
                            + "&nbsp; </TD>  "
                            + " </TR> "
                            + "  <TR>    <TD align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>      </TD>  "
                            + "   <TD colspan=\"4\" align=\"left\" nowrap class=\"In1-td-rightbottom\">"
                            + info.getPayAccountNo()
                            + "&nbsp;</TD> "
                            + "    <TD align=\"center\"  class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>      </TD>    "
                            + " <TD colspan=\"4\" align=\"left\" nowrap class=\"In1-td-bottom\">"
                            + info.getReceiveAccountNo()
                            + "&nbsp;</TD>   "
                            + "</TR>"
                            + "   <TR>   <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">开户银行</font></b>      </td>  "
                            + "   <td  nowrap colspan=\"4\" class=\"In1-td-rightbottom\">"
                            + info.getPayBankName()
                            + "&nbsp;</td>  "
                            + "   <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">汇入地点</font></b>      </td>  "
                            + "   <td width=\"50\" nowrap colspan =\"2\" class=\"In1-td-rightbottom\">" + info.getReceiveRemitInAddress() + "&nbsp;</td>     "
                            + "<td width=\"45\" nowrap class=\"In1-td-rightbottom\"><b><font face=\"楷体_GB2312\">汇入行<br>       名称</font></b></td>    "
                            + " <td width=\"104\" nowrap class=\"In1-td-bottom\">" + info.getReceiveBankName() + "&nbsp;</td> " + "  </TR>  " + " <TR>    "
                            + "  <TD colspan=\"2\" align=\"center\" class=\"In1-td-topbottom2right\" height=\"30\" nowrap><B><FONT face=\"楷体_GB2312\">"
                            + info.getCurrencyName() + "<BR>       (大写)</FONT></B> </TD>    "
                            + " <TD colspan=\"6\" class=\"In1-td-topbottom2right\" nowrap><B>" + info.getChineseAmount() + "&nbsp;</B> </TD>    "
                            + " <TD colspan=\"4\" align=\"right\" nowrap class=\"In1-td-topbottom2\"><B>" + info.getAmount() + "&nbsp; </B>      </TD> "
                            + "  </TR>  " + " <TR>    " + "  <TD align=\"center\" class=\"In1-td-rightbottom\" colspan=\"2\"><b> 摘　　要 </b>&nbsp;</TD>    "
                            + " <TD colspan=\"12\" class=\"In1-td-bottom\" >" + info.getAbstract() + "&nbsp; </TD>   " + "</TR>  " + "<TR>    "
                            + "   <TD colspan=\"14\" ><B>以上款项已在你单位账下付讫</B>&nbsp; </TD>  " + " </TR> " + " </TABLE> " + "<TABLE width=\"30\" border=\"0\"> 	"
                            + "	<TR> 	" + "		<TD height=\"160\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>回<BR>单</FONT> </TD> " + "		</TR> 	</TABLE>"
                            + " </td></tr></table>" + "<br>" + "<table width=\"590\"><TR align=\"right\">     "
                            + "   <TD  colspan=\"3\" align=\"right\">&nbsp;  客户网上银行 </TD>     "
                            + "<TD width=\"360\" height=\"22\" colspan=\"11\"  nowrap>[录入] &nbsp;" + info.getOBInputUserName() + "&nbsp;[复核]&nbsp;"
                            + info.getOBCheckUserName() + "&nbsp; [签认]&nbsp;" + info.getOBSignUserName() + " </TD>  " + " </TR>   " + "<TR align=\"right\">   "
                            + "   <TD  colspan=\"3\" align=\"right\">&nbsp;  " + Env.getClientName() + " </TD>    "
                            + " <TD colspan=\"11\" height=\"22\"  nowrap>[录入]&nbsp;" + info.getInputUserName() + "&nbsp;[复核]&nbsp;" + info.getCheckUserName()
                            + " </TD>   " + "</TR>" + "</table>" + " </BODY> " + "  '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }

    public static void showGrantLoan(JspWriter out, ShowGrantLoanInfo info, int i) throws Exception
    {
        try
        {
            OBPayNoticeDao.showGrantLoan(out, info, i);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new IException("Gen_E001");
        }
    }

    public static void noShowPrintHeadAndFooterPayNotice(JspWriter out, long lTypeID, long lOfficeID) throws Exception
    {
        OBPayNoticeDao.noShowPrintHeadAndFooter(out, lTypeID, lOfficeID);
    }


    private static void displyHtmlHeader(HtmlHeaderInfo htmlHeader) throws IException, IOException
    {
        try
        { 
	       	htmlHeader.getJspWriter().println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
	        htmlHeader.getJspWriter().println("<html>");
	        htmlHeader.getJspWriter().println("<head>");
	        htmlHeader.getJspWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\">");
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
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/itreasury/js/jquery-1.4.2.min.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/itreasury/js/jquery-aop-1.3.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/itreasury/js/Doaop.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/itreasury/js/util.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/itreasury/js/Popup.js\"></script>\n");
	        
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/webob/js/Check.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/webob/js/Control.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/webob/js/MagnifierSQL.js\"></script>\n");
	        
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/webob/js/date-picker.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/itreasury/js/wpCalendar.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/itreasury/js/flexigrid.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/itreasury/js/flexigridEncapsulation.ebank.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/itreasury/js/suggest.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/itreasury/js/jquery-ui-1.7.2.custom.min.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script type=\"text/javascript\" src=\"/safety/js/fgVilidate.js\"></script>\n");
	        
	        htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/webob/css/style.css\" type=\"text/css\">\n");
	        htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/Popup.css\" type=\"text/css\">\n");
	        htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/jquery-ui-1.7.2.custom.css\" type=\"text/css\">\n");
	        htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/suggest.css\" type=\"text/css\">\n");
	        htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/flexigrid.css\" type=\"text/css\">\n");
	        htmlHeader.getJspWriter().println("\n");
	        htmlHeader.getJspWriter().println("</head>\n");
	        htmlHeader.getJspWriter().println("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
	        //Modify by jiangqi 2011-03-30
            //htmlHeader.getJspWriter().println("<div id=\"sending\" style=\"position:absolute; top:80; left:20; z-index:200; visibility:visible\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中, 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>");                     
            //htmlHeader.getJspWriter().println("<script language=javascript> function showSending(){ gnIsShowSending=1;        sending.style.visibility=\"visible\";for (var i=0;i<document.all.length;i++){if((document.all[i].type==\"button\")||(document.all[i].type==\"submit\")||(document.all[i].type==\"reset\")){  document.all[i].disabled = true; }}}</script>");
	        
            htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
	        htmlHeader.getJspWriter().println("<tbody valign=\"top\">");
	        //htmlHeader.getJspWriter().println("<tr><td height=\"5\" colspan=\"3\"></td></tr>");
	        htmlHeader.getJspWriter().println("<tr>");
	        //htmlHeader.getJspWriter().println("<td width=\"5\">&nbsp;</td>");
	        htmlHeader.getJspWriter().println("<td width=\"*%\">");
        	
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
    /**
     * 获取从数据库里取出菜单的名称和URL
     */
   public static void getInterCeptMenu(HtmlHeaderInfo htmlHeader){
 	   menu = htmlHeader.getEBank_SessionMng().m_strMenu;
 	   menus  = menu.split("::");
 	   menuName = menus[0].split(";;");
 	   menuURL = new String[menuName.length];
 	   String[] urls = menus[3].split(";;");
 	   for(int i=0;i<urls.length;i++){
 		   menuURL[i] = urls[i];
 	   }
   }
   
   
   /**
    * added by boxu 20007/11/06 打印等待
    * @param out
    * @throws IException
    * @throws IOException
    */
   	public static void ShowSendingHtml(JspWriter out) throws IException, IOException {
   		out.println("<script language=javascript>\n");
   		out.println("function showSending(printnum) \n");
   		out.println("{\n");
   		out.println(" gnIsShowSending=1;\n");
   		out.println(" sending.style.visibility=\"visible\";\n");
   		out.println(" cover.style.visibility=\"visible\";\n");
   		out.println(" var message=document.getElementById(\"showmessage\");\n");
   		out.println(" message.innerHTML=\"正在执行中, 打印第\"+ printnum +\"张...\";\n");
   		out.println("}\n");
   		out.println("\n");
      	out.println("</script>\n");
      	out.println("<div id=\"sending\" style=\"position:absolute; top:320; left:20; z-index:10; visibility:hidden\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td id=\"showmessage\" bgcolor=#eeeeee align=center>正在执行中, 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>\n");
      	out.println("<div id=\"cover\" style=\"position:absolute; top:0; left:0; z-index:9; visibility:hidden\"><TABLE WIDTH=100% height=900 BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><TD align=center><br></td></tr></table></div>\n");
      	out.println("\n");
   	}
   
}
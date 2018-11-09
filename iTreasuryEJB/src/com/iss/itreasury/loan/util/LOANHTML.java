/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 * 
 * 总体功能说明：项目里需要的页面显示函数
 * 
 * 使用注意事项： 1 2
 * 
 * 作者：yfan
 * 
 * 更改人员：
 *  
 */
package com.iss.itreasury.loan.util;

import java.io.IOException;
import java.rmi.RemoteException;
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

public class LOANHTML {

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
				// + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID);
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
		showCommonDeferScript(out);
		
	}
	//call this method at the end of HTML with out call the method showHomeEnd if you want to use
	public static void showCommonDeferScript(JspWriter out) throws IOException {
		   //defer
	   out.println("  <script defer type=\"text/javascript\">");
        
	   out.println("	$('table[list]').addClass(\"list_table\");");
	   out.println("	$('table[list]').css(\"border-collapse\",\"collapse\");");
	   out.println("	$('table[list]').css(\"border-spacing\",\"0\");");
	   out.println("	$('table[list]').css(\"min-width\",\"100%\");");
	   

       out.println(" 	$('table[list]').find(\"tr:eq(0)\").addClass(\"itemtitle\");");
       out.println(" 	$('table[list]').find(\"tr:eq(0)\").css(\"text-align\",\"center\");");
       out.println(" 	$('table[list]').find(\"td\").css('white-space','nowrap')");
       out.println(" 	$('table[list]').find(\"tr\").each(function(){");
       out.println("		if(this.sectionRowIndex%2!=0){");
   	   out.println("				$(this).css(\"background-color\",\"#fff\");");
       out.println("				$(this).mouseleave( function(){");
   	   out.println("		$(this).css(\"background-color\",\"#fff\");");
	   out.println(" 				});");
	   out.println(" 		}else{");
	   out.println(" 				$(this).css(\"background-color\",\"\");");
	   out.println(" 				$(this).mouseleave( function(){");
	   out.println(" 					$(this).css(\"background-color\",\"\");");
	   out.println(" 				});");
	   out.println(" 		}");
	   out.println("	       $(this).hover(");
		   out.println("		       function(){");
		   out.println("		       	$(this).css(\"background-color\",\"#d5effc\") ;");
		   out.println("		       },");
		  out.println("		       function(){");
       out.println("		if(this.sectionRowIndex%2!=0){");
	   out.println("		       	$(this).css(\"background-color\",\"#fff\");}else{");
	   out.println(" 				$(this).css(\"background-color\",\"\");");
	   out.println("		      } }");
		   out.println("	       );");
		   out.println("	 	});");
		   out.println("	$('input[rate]').each(function(){ $(this).parent().append('%');});" );
		   out.println("	$('input[rate]').css('text-align','right')");

		   out.println("</script>");
		
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
					.println(" <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"返回\"> \n");
		} else {
			if (strErroCode != null && strErroCode.length() > 0
					&& strErroCode.equals("Gen_E002")) {
				out
						.println(" <INPUT type=\"button\" class=button name=\"goback\" onclick=\"javascript:window.parent.location.href='"
								+ Constant.SDCURL + "';\"  value=\"返回\"> \n");
			} else {
				out
						.println(" <INPUT type=\"button\" class=button name=\"goback\" onclick=\"javascript:history.back(-1)\" value=\"返回\"> \n");
			}
		}
		out
				.println(" </DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>\n");
		out.println("<SCRIPT language=JavaScript>");
		out.println("   goback.focus();");
		out.println("</SCRIPT>");
		if (lTypeID == Constant.RecordStatus.VALID) {
			showHomeEnd(out);
		}
	}

	/*
	 * 显示下拉列表 @param out 输出 @param strFieldName 控件的名称 @param strFieldName
	 * 下一个控件的名称 @param lValue 初始值 @param lListType 下拉列表类型
	 */
	public static void ShowList(JspWriter out, String strFieldName,
			String strNextFieldName, long lValue, long lListType,
			long lOfficeID, long lCurrencyID) throws RemoteException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		switch ((int) lListType) {
		case (int) LOANConstant.ListType.LOANCLIENTTYPE:
			strSQL = " select id, sName from LOAN_CLIENTTYPE where nStatusID = "
					+ Constant.RecordStatus.VALID + "order by sCode ";
			break;
		case (int) LOANConstant.ListType.SETTCLIENTTYPE:
			strSQL = " select id, sName from SETT_CLIENTTYPE where nStatusID = "
					+ Constant.RecordStatus.VALID + "order by sCode ";
			break;
		}
		try {
			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			out.println("<select class=\"box\" name=\"" + strFieldName
					+ "\" onfocus=\"nextfield='" + strNextFieldName + "'\">");
			out.println("<option value=\"-1\"></option>");
			while (rs != null && rs.next()) {
				if (lValue == rs.getLong(1)) {
					out.println("<option value=\"" + rs.getLong(1)
							+ "\" selected>" + rs.getString(2) + "</option>");
				} else {
					out.println("<option value=\"" + rs.getLong(1) + "\">"
							+ rs.getString(2) + "</option>");
				}
			}
			out.println("</select>");
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
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
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new RemoteException(ex.getMessage());
			}
		}
	}

	/*
	 * 显示下拉列表 @param out 输出 @param strFieldName 控件的名称 @param strFieldName
	 * 下一个控件的名称 @param lValue 初始值 @param lListType 下拉列表类型
	 */
	public static void ShowList(JspWriter out, String strFieldName,
			String strNextFieldName, long lValue, long lListType,
			boolean isDisable, long lOfficeID, long lCurrencyID)
			throws RemoteException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		switch ((int) lListType) {
		case (int) LOANConstant.ListType.LOANCLIENTTYPE:
			strSQL = " select id, sName from LOAN_CLIENTTYPE where nStatusID = "
					+ Constant.RecordStatus.VALID + "order by sCode ";
			break;
		case (int) LOANConstant.ListType.SETTCLIENTTYPE:
			strSQL = " select id, sName from SETT_CLIENTTYPE where nStatusID = "
					+ Constant.RecordStatus.VALID + "order by sCode ";
			break;
		}
		try {
			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (isDisable)
				out.println("<select class=\"box\" name=\"" + strFieldName
						+ "\" onfocus=\"nextfield='" + strNextFieldName
						+ "'\" disabled>");
			else
				out.println("<select class=\"box\" name=\"" + strFieldName
						+ "\" onfocus=\"nextfield='" + strNextFieldName
						+ "'\">");
			out.println("<option value=\"-1\"></option>");
			while (rs != null && rs.next()) {
				if (lValue == rs.getLong(1)) {
					out.println("<option value=\"" + rs.getLong(1)
							+ "\" selected>" + rs.getString(2) + "</option>");
				} else {
					out.println("<option value=\"" + rs.getLong(1) + "\">"
							+ rs.getString(2) + "</option>");
				}
			}
			out.println("</select>");
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
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
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				throw new RemoteException(ex.getMessage());
			}
		}
	}

	/**
	 * 按地区分类下拉列表显示控件(带nextfield)
	 * 
	 * @param out
	 *            输出
	 * @param strFieldName
	 *            域的名称
	 * @param lData
	 *            数据
	 * @param strDesc
	 *            其他属性
	 * 
	 */
	public static void showAreaTypeListControl(JspWriter out,
			String strFieldName, long lData, String strDesc) throws Exception {
		try {
			out.println("<select " + strDesc + " class=\"select\" name=\""
					+ strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");

			String strSelected = "";
			long[] lTemp = LOANConstant.AreaType.getAllCode();
			for (int i = 0; i < lTemp.length; i++) {
				if (lTemp[i] == lData) {
					strSelected = "selected";
				} else {
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected
						+ ">" + LOANConstant.AreaType.getName(lTemp[i])
						+ "</option>");
				strSelected = "";
			}
			out.println("</select>");
		} catch (Exception e) {
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 按行业分类1下拉列表显示控件(带nextfield)
	 * 
	 * @param out
	 *            输出
	 * @param strFieldName
	 *            域的名称
	 * @param lData
	 *            数据
	 * @param strDesc
	 *            其他属性
	 * 
	 */
	public static void showIndustryType1ListControl(JspWriter out,
			String strFieldName, long lData, String strDesc) throws Exception {
		try {
			out.println("<select " + strDesc + " class=\"select\" name=\""
					+ strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");

			String strSelected = "";
			long[] lTemp = LOANConstant.IndustryType1.getAllCode();
			for (int i = 0; i < lTemp.length; i++) {
				if (lTemp[i] == lData) {
					strSelected = "selected";
				} else {
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected
						+ ">" + LOANConstant.IndustryType1.getName(lTemp[i])
						+ "</option>");
				strSelected = "";
			}
			out.println("</select>");
		} catch (Exception e) {
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 按行业分类2下拉列表显示控件(带nextfield)
	 * 
	 * @param out
	 *            输出
	 * @param strFieldName
	 *            域的名称
	 * @param lData
	 *            数据
	 * @param strDesc
	 *            其他属性
	 * 
	 */
	public static void showIndustryType2ListControl(JspWriter out,
			String strFieldName, long lData, String strDesc) throws Exception {
		try {
			out.println("<select " + strDesc + " class=\"select\" name=\""
					+ strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");

			String strSelected = "";
			long[] lTemp = LOANConstant.IndustryType2.getAllCode();
			for (int i = 0; i < lTemp.length; i++) {
				if (lTemp[i] == lData) {
					strSelected = "selected";
				} else {
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected
						+ ">" + LOANConstant.IndustryType2.getName(lTemp[i])
						+ "</option>");
				strSelected = "";
			}
			out.println("</select>");
		} catch (Exception e) {
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 按行业分类3下拉列表显示控件(带nextfield)
	 * 
	 * @param out
	 *            输出
	 * @param strFieldName
	 *            域的名称
	 * @param lData
	 *            数据
	 * @param strDesc
	 *            其他属性
	 * 
	 */
	public static void showIndustryType3ListControl(JspWriter out,
			String strFieldName, long lData, String strDesc) throws Exception {
		try {
			out.println("<select " + strDesc + " class=\"select\" name=\""
					+ strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");

			String strSelected = "";
			long[] lTemp = LOANConstant.IndustryType3.getAllCode();
			for (int i = 0; i < lTemp.length; i++) {
				if (lTemp[i] == lData) {
					strSelected = "selected";
				} else {
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected
						+ ">" + LOANConstant.IndustryType3.getName(lTemp[i])
						+ "</option>");
				strSelected = "";
			}
			out.println("</select>");
		} catch (Exception e) {
			throw new IException("Gen_E001");
		}
	}

	public static void showTypeListControl(JspWriter out, String strFieldName,
			long officeId, long currencyId, long typeId, long lData,
			String strDesc) throws Exception {
		try {
			out.println("<select " + strDesc + " class=select name=\""
					+ strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");

			String strSelected = "";
			long[] lTemp = LOANConstant.AreaType
					.getOfficeIdAndCurrencyIdAllCode(officeId, currencyId,
							typeId);
			for (int i = 0; i < lTemp.length; i++) {
				if (lTemp[i] == lData) {
					strSelected = "selected";
				} else {
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected
						+ ">" + LOANConstant.AreaType.getName(lTemp[i])
						+ "</option>");
				strSelected = "";
			}
			out.println("</select>");
		} catch (Exception e) {
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 合同状态下拉列表显示控件(带nextfield)
	 * 
	 * @param out
	 *            输出
	 * @param strFieldName
	 *            域的名称
	 * @param lData
	 *            数据
	 * @param strDesc
	 *            其他属性
	 * 
	 */
	public static void showContractStatusList(JspWriter out,
			String strFieldName, long lData, String strDesc) throws Exception {
		try {
			out.println("<select " + strDesc + " class=box name=\""
					+ strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");

			String strSelected = "";
			long[] lTemp = LOANConstant.ContractStatus.getAllCode();
			for (int i = 0; i < lTemp.length; i++) {
				if (lTemp[i] == lData) {
					strSelected = "selected";
				} else {
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected
						+ ">" + LOANConstant.ContractStatus.getName(lTemp[i])
						+ "</option>");
				strSelected = "";
			}
			out.println("</select>");
		} catch (Exception e) {
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 合同风险状态下拉列表显示控件(带nextfield)
	 * 
	 * @param out
	 *            输出
	 * @param strFieldName
	 *            域的名称
	 * @param lData
	 *            数据
	 * @param strDesc
	 *            其他属性
	 * 
	 */
	public static void showContractRiskStatusList(JspWriter out,
			String strFieldName, long lData, String strDesc) throws Exception {
		try {
			out.println("<select " + strDesc + " class=box name=\""
					+ strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");

			String strSelected = "";
			long[] lTemp = LOANConstant.VentureLevel.getAllCode();
			for (int i = 0; i < lTemp.length; i++) {
				if (lTemp[i] == lData) {
					strSelected = "selected";
				} else {
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected
						+ ">" + LOANConstant.VentureLevel.getName(lTemp[i])
						+ "</option>");
				strSelected = "";
			}
			out.println("</select>");
		} catch (Exception e) {
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 放款通知单状态下拉列表显示控件(带nextfield)
	 * 
	 * @param out
	 *            输出
	 * @param strFieldName
	 *            域的名称
	 * @param lData
	 *            数据
	 * @param strDesc
	 *            其他属性
	 * 
	 */
	public static void showPayNoticeStatusList(JspWriter out,
			String strFieldName, long lData, String strDesc) throws Exception {
		try {
			out.println("<select " + strDesc + " class=box name=\""
					+ strFieldName + "\">");
			out.println("<option value=\"-1\"></option>");

			String strSelected = "";
			long[] lTemp = LOANConstant.LoanPayNoticeStatus.getAllCode();
			for (int i = 0; i < lTemp.length; i++) {
				if (lTemp[i] == lData) {
					strSelected = "selected";
				} else {
					strSelected = "";
				}
				out.println("<option value=\"" + lTemp[i] + "\"" + strSelected
						+ ">"
						+ LOANConstant.LoanPayNoticeStatus.getName(lTemp[i])
						+ "</option>");
				strSelected = "";
			}
			out.println("</select>");
		} catch (Exception e) {
			throw new IException("Gen_E001");
		}
	}

	/**
	 * 显示企业类型下拉列表显示控件
	 * 
	 * @param out
	 *            输出
	 * @param strControlName
	 *            控件名称
	 * @param lSelectValue
	 *            被选择项对应值
	 * @param isNeedAll
	 *            是否需要”全部“项
	 * @param strProperty
	 *            下拉列表属性
	 * @throws Exception
	 */
	public static void showEnterpriseTypeListCtrl(JspWriter out,
			long lOfficeID, String strControlName, long lSelectValue,
			boolean isNeedAll, String strProperty) throws Exception {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select  id, sname ");
		sbSQL.append(" from SETT_ENTERPRICETYPE ");
		sbSQL.append(" where  nstatusid=" + Constant.RecordStatus.VALID);
		sbSQL.append(" order by id");
		String strDisplayField = "sname";
		String strID = "id";
		showCommonListControl(out, strControlName, sbSQL.toString(),
				strDisplayField, strID, lSelectValue, isNeedAll, strProperty,
				true);
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
	public static void showCommonListControl(JspWriter out,
			String strControlName, String strSQL, String strDisplayField,
			String strID, long lSelectValue, boolean isNeedAll,
			String strProperty, boolean isNeedBlank) throws Exception {
		long lResult = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lTemplateID = 0;
		long lLobID = -1;
		Vector v = new Vector();
		try {
			// 校验输入的sql语句里是否包含strDisplayField和strID
			if (strSQL.indexOf(strID) == -1
					|| strSQL.indexOf(strDisplayField) == -1) {
				System.out.println("传入的参数不正确！");
				return;
			}

			out.println("<select class=box name=\"" + strControlName + "\""
					+ strProperty + ">");
			if (isNeedBlank == true) {
				out.println("<option value=\"-1\">&nbsp;</option>");
			}
			con = Database.getConnection();
			String strTmp = new String();
			strTmp = strSQL;
			// System.out.println(strTmp);
			ps = con.prepareStatement(strTmp);
			rs = ps.executeQuery();
			String strSelected = "";
			while (rs.next()) {
				if (rs.getLong(strID) == lSelectValue) {
					strSelected = "selected";
				}
				out.println("<option value=\"" + rs.getLong(strID) + "\""
						+ strSelected + ">" + rs.getString(strDisplayField)
						+ "</option>");
				strSelected = "";
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			if (isNeedAll == true) {
				if (lSelectValue == 0) {
					out.println("<option value=\"0\" selected>全部</option>");
				} else {
					out.println("<option value=\"0\" >全部</option>");
				}
			}
			out.println("</select>");
		} catch (Exception e) {
			// System.out.println(" can not select OFFICE, because " + e);
			// throw e;
			System.out.println(e.toString());
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
				LOANHTML.showMessage(out, sessionMng, request, response, Env
						.getClientName(), Constant.RecordStatus.INVALID,
						"Gen_E002");
				LOANHTML.showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			// 判断是否有权限
			if (sessionMng.hasRight(request) == false) {
				LOANHTML.showMessage(out, sessionMng, request, response, Env
						.getClientName(), Constant.RecordStatus.INVALID,
						"Gen_E002");
				LOANHTML.showHomeEnd(out);
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
					LOANHTML.showMessage(out, sessionMng, request, response,
							Env.getClientName(), Constant.RecordStatus.INVALID,
							"Gen_E008");
					LOANHTML.showHomeEnd(out);
					out.flush();
					bResult = false;
				} else {
					session.setAttribute("ActionValidateCode", lActionID);
					request.setAttribute("ActionValidateCode", String
							.valueOf(lActionID.longValue() + 1));

				}
			}
		} catch (IException ie) {
			LOANHTML.showException(out, sessionMng, ie, request, response, Env
					.getClientName(), Constant.RecordStatus.INVALID, "");
			LOANHTML.showHomeEnd(out);
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
	public static Vector getCommonSelectList(String strSQL, String strField)
			throws Exception {
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
			throw new Exception("发生数据库错误！");
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
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		return v.size() > 0 ? v : null;
	}

	public static void main(String[] args) {
		try {
			SessionMng sessionMng = null;
			javax.servlet.jsp.JspWriter out = null;
			javax.servlet.http.HttpServletRequest request = null;
			javax.servlet.http.HttpServletResponse response = null;
			LOANHTML.showMessage(out, sessionMng, request, response, "登录",
					Constant.RecordStatus.VALID, "Gen_E002");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void displyHtmlHeader(HtmlHeaderInfo htmlHeader,long lShowMenu) throws IException, IOException
    {
    	htmlHeader.getJspWriter().println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        htmlHeader.getJspWriter().println("<html>");
        htmlHeader.getJspWriter().println("<head>");
        htmlHeader.getJspWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
        htmlHeader.getJspWriter().println("<title>" + htmlHeader.getTitle() + "</title>\n");
        // 20121129 IE内核限制 王春蕾 start
        htmlHeader.getJspWriter().println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE8\" > ");
		        htmlHeader.getJspWriter().println(  "<script type=\"text/javascript\">");
		        htmlHeader.getJspWriter().println("	var ua = window.navigator.userAgent;");
		        htmlHeader.getJspWriter().println("var msie = ua.indexOf(\"MSIE \") ; ");
		        htmlHeader.getJspWriter().println("	var ver = parseInt(ua.substring(msie + 5, ua.indexOf(\".\", msie)));");
		        htmlHeader.getJspWriter().println("	if(msie==-1 ||ver<8){"); 
		        htmlHeader.getJspWriter().println("	alert(\"系统不支持此版本浏览器，建议使用IE8以上（包含IE8）的版本\");");
		        htmlHeader.getJspWriter().println("	window.opener = null; ");
		     // htmlHeader.getJspWriter().println(" window.parent.open('',
				// '_self', '')");
		        htmlHeader.getJspWriter().println("	window.parent.close();  "); 
		        htmlHeader.getJspWriter().println("}</script>");
		// end
		    
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

        	// Modify by leiyang date 2007/07/19
	        // htmlHeader.getJspWriter().println("<script
			// language=\"javascript\"
			// src=\"/itreasury/js/coolmenus4.js\"></script>\n");
	        // htmlHeader.getJspWriter().println("<script
			// language=\"javascript\"
			// src=\"/itreasury/js/cm_addins.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/jquery-1.4.2.min.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/jquery-aop-1.3.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/Doaop.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/util.js\"></script>\n");
	        
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/wpCalendar.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/flexigrid.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/flexigridEncapsulation.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/suggest.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/jquery-ui-1.7.2.custom.min.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"javascript\" src=\"/itreasury/js/pdfobject.js\"></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"JavaScript\" src=\"/jQueryEasyUI/json2.js\" ></script>\n");
	       
	        htmlHeader.getJspWriter().println("<script language=\"JavaScript\" src=\"/webloan/js/Control.js\" ></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"JavaScript\" src=\"/webloan/js/Check.js\" ></script>\n");
	        


	        htmlHeader.getJspWriter().println("<script language=\"JavaScript\" src=\"/webloan/js/MagnifierSQL.js\" ></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"JavaScript\" src=\"/safety/js/fgVilidate.js\" ></script>\n");
	        htmlHeader.getJspWriter().println("<script language=\"JavaScript\" src=\"/webscript/taglib.js\" ></script>\n");
	        
	        htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/webloan/css/style.css\" type=\"text/css\">\n");
	        htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/webloan/css/approve.css\" type=\"text/css\">");

	        
	        htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/jquery-ui-1.7.2.custom.css\" type=\"text/css\">\n");
	        htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/suggest.css\" type=\"text/css\">\n");
	        htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/flexigrid.css\" type=\"text/css\">\n");
	        
	     
	        htmlHeader.getJspWriter().println("</head>\n");
	        htmlHeader.getJspWriter().println("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
	        // Modify by jiangqi 2011-03-30
	        // htmlHeader.getJspWriter().println("<div id=\"sending\"
			// style=\"position:absolute; top:80; left:20; z-index:200;
			// visibility:visible\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0
			// CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE
			// WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td
			// bgcolor=#eeeeee align=center>正在执行中,
			// 请稍候...</td></tr></table></td><td
			// width=30%></td></tr></table></div>");
	        // htmlHeader.getJspWriter().println("<script language=javascript>
			// function showSending(){ gnIsShowSending=1;
			// sending.style.visibility=\"visible\";for (var
			// i=0;i<document.all.length;i++){if((document.all[i].type==\"button\")||(document.all[i].type==\"submit\")||(document.all[i].type==\"reset\")){
			// document.all[i].disabled = true; }}}</script>");
	        
	        htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
	        htmlHeader.getJspWriter().println("<tbody valign=\"top\">");
	        // htmlHeader.getJspWriter().println("<tr><td
			// height=\"5\"></td></tr>");
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
				.println("<script language=\"javascript\" src=\"/itreasury/js/jquery-1.4.2.min.js\"></script>\n");
		out
				.println("<script language=\"javascript\" src=\"/itreasury/js/jquery-aop-1.3.js\"></script>\n");
		out
				.println("<script language=\"javascript\" src=\"/itreasury/js/Doaop.js\"></script>\n");
		out
				.println("<script language=\"javascript\" src=\"/itreasury/js/util.js\"></script>\n");

		out
				.println("<script language=\"javascript\" src=\"/itreasury/js/wpCalendar.js\"></script>\n");
		out
				.println("<script language=\"javascript\" src=\"/itreasury/js/flexigrid.js\"></script>\n");
		out
				.println("<script language=\"javascript\" src=\"/itreasury/js/flexigridEncapsulation.js\"></script>\n");
		out
				.println("<script language=\"javascript\" src=\"/itreasury/js/suggest.js\"></script>\n");
		out
				.println("<script language=\"javascript\" src=\"/itreasury/js/jquery-ui-1.7.2.custom.min.js\"></script>\n");
		out
				.println("<script language=\"javascript\" src=\"/itreasury/js/pdfobject.js\"></script>\n");
		out
				.println("<script language=\"JavaScript\" src=\"/jQueryEasyUI/json2.js\" ></script>\n");

		out
				.println("<script language=\"JavaScript\" src=\"/webloan/js/Control.js\" ></script>\n");
		out
				.println("<script language=\"JavaScript\" src=\"/webloan/js/Check.js\" ></script>\n");

		out
				.println("<script language=\"JavaScript\" src=\"/webloan/js/MagnifierSQL.js\" ></script>\n");
		out
				.println("<script language=\"JavaScript\" src=\"/safety/js/fgVilidate.js\" ></script>\n");
		out
				.println("<script language=\"JavaScript\" src=\"/webscript/taglib.js\" ></script>\n");

		out
				.println("<link rel=\"stylesheet\" href=\"/webloan/css/style.css\" type=\"text/css\">\n");

		out
				.println("<link rel=\"stylesheet\" href=\"/itreasury/css/jquery-ui-1.7.2.custom.css\" type=\"text/css\">\n");
		out
				.println("<link rel=\"stylesheet\" href=\"/itreasury/css/suggest.css\" type=\"text/css\">\n");
		out
				.println("<link rel=\"stylesheet\" href=\"/itreasury/css/flexigrid.css\" type=\"text/css\">\n");

		out.println("</head>");
		out
				.println("<body onload=\"iframeAutoFit("
						+ height
						+ ")\" bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">");

		// out.println("<div id=\"sending\" style=\"position:absolute; top:320;
		// z-index:501; display:none\"><table width=\"100%\"><tr><td
		// align=\"center\">");
		// out.println("<table bgcolor=\"#ff9900\" width=\"380\" height=\"70\"
		// border=\"0\" cellspacing=\"2\" cellpadding=\"0\"><tr><td
		// bgcolor=\"#eeeeee\" align=\"center\">正在执行中,
		// 请稍候...</td></tr></table>");
		// out.println("</td></tr></table></div>");
		// 2011-04-01 by jiangqi
		// out.println("<div id=\"sending\" style=\"position:absolute; top:80;
		// left:20; z-index:200; display:inline\"><TABLE WIDTH=100% BORDER=0
		// CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD
		// bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2
		// CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中,
		// 请稍候...</td></tr></table></td><td
		// width=30%></td></tr></table></div>");
		// out.println("<script language=javascript> function showSending(){
		// gnIsShowSending=1; sending.style.display=\"none\";for (var
		// i=0;i<document.all.length;i++){if((document.all[i].type==\"button\")||(document.all[i].type==\"submit\")||(document.all[i].type==\"reset\")){
		// document.all[i].disabled = true; }}}</script>");
	}

	public static void showHomeIframeEnd(JspWriter out) throws IOException {
		out.println("</html>");
		// Modify by jiangqi 2011-03-30
		// out.println("<script language=javascript>try{
		// document.all.sending.style.display=\"none\"; }catch(e){}</script>");
	}
	/**
	 * added by mzh_fu 20007/06/23
	 * 
	 * @param out
	 * @throws IException
	 * @throws IOException
	 */
	/*
	 * public static void displyShowSendingHtml(JspWriter out) throws
	 * IException, IOException{ out.println("<script language=javascript>\n");
	 * out.println("function showSending() \n"); out.println("{\n");
	 * out.println(" gnIsShowSending=1;\n"); out.println("
	 * sending.style.display=\"block\";\n"); out.println("
	 * cover.style.display=\"block\";\n"); out.println("}\n");
	 * out.println("\n"); out.println("</script>\n"); out.println("<div
	 * id=\"sending\" style=\"position:absolute; top:320; left:20; z-index:10;
	 * display:none\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE
	 * WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中,
	 * 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>\n");
	 * out.println("<div id=\"cover\" style=\"position:absolute; top:0; left:0;
	 * z-index:9; display:none\"><TABLE WIDTH=100% height=900 BORDER=0
	 * CELLSPACING=0 CELLPADDING=0><TR><TD align=center><br></td></tr></table></div>\n");
	 * out.println("\n"); }
	 */
	/**
	 * added by mzh_fu 20007/06/23 解决IFrame分页用
	 * 
	 * @param out
	 * @throws IException
	 * @throws IOException
	 */
	/*
	 * public static void displyShowSendingHtml(JspWriter out,long topHeight)
	 * throws IException, IOException{ out.println("<script
	 * language=javascript>\n"); out.println("function showSending() \n");
	 * out.println("{\n"); out.println(" gnIsShowSending=1;\n"); out.println("
	 * sending.style.display=\"block\";\n"); out.println("
	 * cover.style.display=\"block\";\n"); out.println("
	 * sending.style.top=document.body.scrollTop+"+topHeight+";\n");
	 * out.println("}\n"); out.println("\n"); out.println("</script>\n");
	 * out.println("<div id=\"sending\" style=\"position:absolute; top:0;
	 * left:20; z-index:10; display:none\"><TABLE WIDTH=100% BORDER=0
	 * CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE
	 * WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>正在执行中,
	 * 请稍候...</td></tr></table></td><td width=30%></td></tr></table></div>\n");
	 * out.println("<div id=\"cover\" style=\"position:absolute; top:0; left:0;
	 * z-index:9; display:none\"><TABLE WIDTH=100% height=900 BORDER=0
	 * CELLSPACING=0 CELLPADDING=0><TR><TD align=center><br></td></tr></table></div>\n");
	 * out.println("\n"); }
	 */
}
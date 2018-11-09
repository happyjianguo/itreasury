/*
* Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
*
* 凭证打印模板
*
* 使用注意事项：
* 1
* 2
*
* 作者: rxie
*
 */
package com.iss.itreasury.settlement.print;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo;
import com.iss.itreasury.settlement.print.templateinfo.BankShowCcbTransVoucherInfo;
import com.iss.itreasury.settlement.print.templateinfo.PrintOptionInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowDepositInterestInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowFixedDepositOpenInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowInInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowNoticeOpenInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowPayInterestInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowSpecialTransInfo;
import com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionResultInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.SETTHTML;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IPrint;
public class NHCW_IPrintTemplate extends IPrint
{
	public static final long ISPRINT = -1000; //是否打印收放或者付方凭证标示 为-1000时不打印
	/**
	 * 显示进账单
	 * 第一联
	 */
	public static void showIn1(ShowInInfo info, JspWriter out) throws Exception
	{
		try
		{
			//out.println(" <html> " +
			//" <head> " );
			////noShowPrintHeadAndFooter(out);
			out.print(
				"<Script Language=\"JavaScript\"> document.write(' "
					+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD  width=\"50\">&nbsp;	</TD><TD width=\"310\">&nbsp;	</TD><TD width=\"70\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "<TR> 	"
					+ "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;	</TD> "
					+ "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "      进&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;单</font></font></strong> 　</strong></TD>"
					+ "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");

				//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"</TD>"
					+ "</TR> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "	</TABLE> "
					+ "	<TABLE width=\"600\"> "
					+ " <TR> "
					+ "		<TD width=\"10%\"> "
					+ "		</TD> "
					+ "		<TD align=\"right\" width=\"50%\">"
                    + " 打印日期: "
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ " </TD> "
					+ "	<TD width=\"40%\" align=\"right\">交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "	</TD> "
					+ "</TR> "
					+ "	</TABLE>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">付<BR> "
					+ "      款<BR> "
					+ "      人</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>  "
					+ "    </TD> "
					+ "    <TD  align=\"left\" class=\"In1-td-rightbottom\" width=\"199\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountName())
					+ "&nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">收<BR> "
					+ "      款<BR> "
					+ "      人</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"In1-td-bottom\" width=\"199\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountName())
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountNo())
					+ "&nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-bottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountNo())
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">开户银行</font></b>  "
					+ "    </td> "
					+ "    <td class=\"In1-td-rightbottom\" align=\"left\">"
					+ DataFormat.formatStringForPrint(info.getPayBankName())
					+ "&nbsp;</td> "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">开户银行</font></b>  "
					+ "    </td> "
					+ "    <td class=\"In1-td-bottom\" align=\"left\">"
					+ DataFormat.formatStringForPrint(info.getReceiveBankName())
					+ "&nbsp;</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"2\" align=\"center\" class=\"In1-td-topbottom2right\" height=\"30\" nowrap><B><FONT face=\"楷体_GB2312\">"
					+ DataFormat.formatStringForPrint(info.getCurrencyName())
					+ "<BR> "
					+ "      (大写)</FONT></B> </TD> "
					+ "    <TD colspan=\"3\" class=\"In1-td-topbottom2right\" ><B>&nbsp;"
					+ info.getChineseAmount()
					+ "</B> </TD> "
					+ "    <TD align=\"right\" nowrap class=\"In1-td-topbottom2\"><B>&nbsp;"
					+ info.getAmount()
					+ "</B> </TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"2\" align=\"center\" height=\"22\" class=\"In1-td-right\" nowrap><B><FONT face=\"楷体_GB2312\">摘要</FONT></B>  "
					+ "    </TD> "
					+ "    <TD colspan=\"4\" >&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAbstract())
					+ " </TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"160\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>回<BR>单</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "<Table width=\"600  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" nowrap>&nbsp; </TD>"
					+ "    <TD nowrap>[记账人]&nbsp; "
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ " &nbsp;[复核人]&nbsp; "
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp; </TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</BODY> "
					+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 显示进账单
	 * 第二联
	 */
	public static void showIn2(ShowInInfo info, JspWriter out) throws Exception
	{
		try
		{
			//out.println(" <html> " +
			//" <head> " );
			////noShowPrintHeadAndFooter(out);
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD  width=\"50\">&nbsp;	</TD><TD width=\"310\">&nbsp;	</TD><TD width=\"70\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "<TR> 	"
					+ "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;	</TD> "
					+ "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "       进&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;单</font></font></strong> 　</strong></TD>"
					+ "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"</TD>"
					+ "</TR> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "	</TABLE> "
					+ "	<TABLE width=\"600\"> "
					+ " <TR> "
					+ "		<TD width=\"10%\"> "
					+ "		</TD> "
					+ "		<TD align=\"right\" width=\"50%\">"
                    + " 打印日期: "
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ " </TD> "
					+ "	<TD width=\"40%\" align=\"right\">交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "	</TD> "
					+ "</TR> "
					+ "	</TABLE>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">付<BR> "
					+ "      款<BR> "
					+ "      人</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"In1-td-rightbottom\" width=\"199\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountName())
					+ "&nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">收<BR> "
					+ "      款<BR> "
					+ "      人</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"In1-td-bottom\" width=\"199\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountName())
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"In1-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountNo())
					+ "&nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"In1-td-bottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountNo())
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">开户银行</font></b>  "
					+ "    </td> "
					+ "    <td class=\"In1-td-rightbottom\" align=\"left\">"
					+ DataFormat.formatStringForPrint( info.getPayBankName())
					+ "&nbsp;</td> "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">开户银行</font></b>  "
					+ "    </td> "
					+ "    <td class=\"In1-td-bottom\" align=\"left\">"
					+ DataFormat.formatStringForPrint(info.getReceiveBankName())
					+ "&nbsp;</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"2\" align=\"center\" class=\"In1-td-topbottom2right\" height=\"30\" nowrap><B><FONT face=\"楷体_GB2312\">"
					+ DataFormat.formatStringForPrint(info.getCurrencyName())
					+ "<BR> "
					+ "      (大写)</FONT></B> </TD> "
					+ "    <TD colspan=\"3\" class=\"In1-td-topbottom2right\" ><B>&nbsp;"
					+ info.getChineseAmount()
					+ "</B> </TD> "
					+ "    <TD align=\"right\" nowrap class=\"In1-td-topbottom2\"><B>&nbsp;"
					+ info.getAmount()
					+ "</B> </TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"2\" align=\"center\" height=\"22\" class=\"In1-td-right\" nowrap><B><FONT face=\"楷体_GB2312\">摘要</FONT></B>  "
					+ "    </TD> "
					+ "    <TD colspan=\"4\" >&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAbstract())
					+ " </TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "</TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"160\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>记<BR>账<BR>凭<BR>证</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					 
					+ "<Table width=\"600  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\"  nowrap>&nbsp; </TD>"
					+ "    <TD  nowrap>[记账人]&nbsp; "
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ " &nbsp;[复核人]&nbsp; "
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp; </TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</BODY> "
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * 通知存款开户证实书
	 * 第三联
	 */
	public static void showOpenAccountNotice3(ShowNoticeOpenInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "<td width=\"400\"><div align=\"center\"><strong><font style=\"font-size:16px\">&nbsp;&nbsp;&nbsp;"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "通知存款开户证实书&nbsp;</font></font></strong></strong></div></td>");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">存单号："
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</div><td></tr></table>"
					+ "</td></tr>"
					+ "</table>"
					+ "<table width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "  <tr>"
					+ "    <td width=\"600\" valign=\"top\">"
					+ "<table width=\"100%\" border=\"1\" bordercolordark=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#666666\">"
					+ "        <tr>"
					+ "          <td valign=\"top\"><br>"
					+ "<div align=\"center\">"
					+ "              <p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;名：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;&nbsp;号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;起&nbsp;息&nbsp;日：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getStartInterestDate())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;金&nbsp;&nbsp;&nbsp;&nbsp;额："+Constant.CurrencyType.getName(info.getCurrencyID())+"（大写）&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（小写）&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;品&nbsp;&nbsp;&nbsp;&nbsp;种："
					+ info.getType()
					+ "                <br><br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "              备注：<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;本证实书仅对存款人开户证实，不得作为质押的权利凭证。<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;本存单一式三份，开户单位持一份，财务公司持二份。<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">第<br>三<br>"
					+ "      联<br>"
					+ "      <br>"
					+ "      交<br>"
					+ "      存<br>"
					+ "      款<br>"
					+ "      单<br>"
					+ "      位</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[记账人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[复核人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[部门经理]&nbsp;"
					+ "      </div></td>"
					+ "  </tr>"
					+ "</table><br>"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "<div align=\"center\"> "
					+ " &nbsp;"
					+ "        <div align=\"left\"><strong></strong></div>"
					+ "      </div></td>"
					+ "    <td width=\"50%\" align=\"center\">"
					+ " "
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ " "
					+ "   </td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "   </td>"
					+ "    <td width=\"50%\" valign=\"top\" align=\"center\"><br>"
					+ info.getDateOpenAccount()
					+ "   </td>"
					+ "  </tr>"
					+ "</table>"
                    
                    
                    
                    
                    
                    +"   <table width=\"630\" border=\"0\">  "  
                    +"   <tr>     "  
                    +"  <td width=\"100%\" height=\"12\"><div align=\"left\">-------------------------------------------------------------------------------------------------</div></td>  "  
                    +"  </tr>" 
                    +"  </table>" 
                    +" <table width=\"630\" border=\"0\">"  
                    +"  <tr> "  
                    +"    <td height=\"24\"><div align=\"center\"><font style=\"font-size:20px\"><b>人民币单位通知存款支取通知</b></font></div></td>" 
                    +"  </tr>"  
                    +"  <tr> "  
                    +"    <td height=\"24\"> <div align=\"left\">"  
                    +DataFormat.formatStringForPrint( Env.getClientName())
                    +"  </div></td>" 
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\"> "
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +" 我单位（名称）__________________________预于_____年__月__日从我通知存款账户（账号<u>"
                    + DataFormat.formatStringForPrint(info.getAccountNo())
                    +"</u>）</td>"  
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\">支取人民币（大写）______________________________元（小写：__________________元），请贵公司在设定日</td>" 
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\">期内将该支取款项连同利息转入以下指定之账户。</td>" 
                    +"  </tr>" 
                    +"  <tr>"  
                    +"&nbsp;&nbsp;"
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"账号：</td>" 
                    +"  </tr>" 
                    +"  <tr>"  
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"开户银行：</td>"  
                    +"  </tr>" 
                    +"  <tr>"  
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"特此通知</td>"  
                    +"  </tr>" 
                    +"  <tr>" 
                    +"    <td height=\"34\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;单位预留印鉴：</div></td>"
                    +"  </tr>" 
                    +"  <tr>" 
                    +"    <td height=\"24\"><b>注：此通知书一联，连同定期存款证实书提交财务公司营业部。</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_____年__月__日</td>"  
                    +"  </tr>" 
                    +"  <tr>"  
                    +"    <td height=\"24\"></td>"  
                    +"  </tr>" 
                    +"</table>"  
                    
                    
                    
                    
                    
					+ "<p></TD> </TR> </TABLE> </p>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 *通知存款开户证实书
	 * 第二联
	 */
	public static void showOpenAccountNotice2(ShowNoticeOpenInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "<td width=\"400\"><div align=\"center\"><strong><font style=\"font-size:16px\">&nbsp;&nbsp;&nbsp;"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "通知存款开户证实书&nbsp;</font></font></strong></strong></div></td>");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				      "  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">存单号："
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</div><td></tr></table>"
					+ "</td></tr>"
					+ "</table>"
					+ "<table width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "  <tr>"
					+ "    <td width=\"600\" valign=\"top\">"
					+ "<table width=\"100%\" border=\"1\" bordercolordark=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#666666\">"
					+ "        <tr>"
					+ "          <td valign=\"top\"><br>"
					+ "<div align=\"center\">"
					+ "              <p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;名：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;&nbsp;号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;起&nbsp;息&nbsp;日：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getStartInterestDate())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;金&nbsp;&nbsp;&nbsp;&nbsp;额："+ Constant.CurrencyType.getName(info.getCurrencyID()) +"（大写）&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（小写）&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;品&nbsp;&nbsp;&nbsp;&nbsp;种："
					+ info.getType()
					+ "                <br><br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "              备注：<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;本证实书仅对存款人开户证实，不得作为质押的权利凭证。<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;本存单一式三份，开户单位持一份，财务公司持二份。<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">第<br>二<br>"
					+ "      联<br>"
					+ "      <br>"
					+ "      业<br>"
					+ "      务<br>"
					+ "      留<br>"
					+ "      存</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[记账人];"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[复核人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[部门经理]&nbsp;"
					+ "      </div></td>"
					+ "  </tr>"
					+ "</table><br>"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "<div align=\"center\"> "
					+ " &nbsp;"
					+ "        <div align=\"left\"><strong></strong></div>"
					+ "      </div></td>"
					+ "    <td width=\"50%\" align=\"center\">"
					+ " "
					+DataFormat.formatStringForPrint( Env.getClientName())
					+ " "
					+ "   </td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "   </td>"
					+ "    <td width=\"50%\" valign=\"top\" align=\"center\"><br>"
					+ DataFormat.formatStringForPrint(info.getDateOpenAccount())
					+ "   </td>"
					+ "  </tr>"
					+ "</table>"
					+ "<p></TD> </TR> </TABLE> </p>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 *通知存款开户证实书
	 * 第一联
	 */
	public static void showOpenAccountNotice1(ShowNoticeOpenInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "<td width=\"400\"><div align=\"center\"><strong><font style=\"font-size:16px\">&nbsp;&nbsp;&nbsp;"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "通知存款开户证实书&nbsp;</font></font></strong></strong></div></td>");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				    "  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">存单号："
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</div><td></tr></table>"
					+ "</td></tr>"
					+ "</table>"
					+ "<table width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "  <tr>"
					+ "    <td width=\"600\" valign=\"top\">"
					+ "<table width=\"100%\" border=\"1\" bordercolordark=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#666666\">"
					+ "        <tr>"
					+ "          <td valign=\"top\"><br>"
					+ "<div align=\"center\">"
					+ "              <p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;名：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;&nbsp;号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;起&nbsp;息&nbsp;日：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getStartInterestDate())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;金&nbsp;&nbsp;&nbsp;&nbsp;额："+Constant.CurrencyType.getName(info.getCurrencyID())+"（大写）&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（小写）&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;品&nbsp;&nbsp;&nbsp;&nbsp;种："
					+ info.getType()
					+ "                <br><br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "              备注：<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;本证实书仅对存款人开户证实，不得作为质押的权利凭证。<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;本存单一式三份，开户单位持一份，财务公司持二份。<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">第<br>一<br>"
					+ "      联<br>"
					+ "      <br>"
					+ "      记<br>"
					+ "      账<br>"
					+ "      凭<br>"
					+ "      证</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[记账人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[复核人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[部门经理]&nbsp;"
					+ "      </div></td>"
					+ "  </tr>"
					+ "</table><br>"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "<div align=\"center\"> "
					+ " &nbsp;"
					+ "        <div align=\"left\"><strong></strong></div>"
					+ "      </div></td>"
					+ "    <td width=\"50%\" align=\"center\">"
					+ " "
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ " "
					+ "   </td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "   </td>"
					+ "    <td width=\"50%\" valign=\"top\" align=\"center\"><br>"
					+ DataFormat.formatStringForPrint(info.getDateOpenAccount())
					+ "   </td>"
					+ "  </tr>"
					+ "</table>"
					+ "<p></TD> </TR> </TABLE> </p>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 定期存款开户证实书
	 * 第一联
	 */
	public static void showFixOpenAccountNotice1(ShowFixedDepositOpenInfo info, JspWriter out) throws Exception
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "<td width=\"400\"><div align=\"center\"><strong><font style=\"font-size:16px\">&nbsp;&nbsp;&nbsp;"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "定期存款开户证实书&nbsp;</font></font></strong></strong></div></td>");
					
			//sb.append("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			sb.append(
				    "  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">存单号："
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</div><td></tr></table>"
					+ "</td></tr>"
					+ "</table>"
					+ "<table width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "  <tr>"
					+ "    <td width=\"600\" valign=\"top\">"
					+ "<table width=\"100%\" border=\"1\" bordercolordark=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#666666\">"
					+ "        <tr>"
					+ "          <td valign=\"top\"><br>"
					+ "<div align=\"center\">"
					+ "<p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;名：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;&nbsp;号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;起&nbsp;息&nbsp;日：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getDateOpenAccount()));
			/*  TOCONFIG—TODELETE  */
			/*
					if(Env.getProjectName().compareToIgnoreCase(Constant.ProjectName.SEFC) == 0)
					{
						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
								+ "到&nbsp;期&nbsp;日：&nbsp;"
								+ info.getEndDate());
					}
					*/
			
					sb.append(
					"<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;金&nbsp;&nbsp;&nbsp;&nbsp;额："+ info.getCurrencyName() +"（大写）&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（小写）&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;期&nbsp;&nbsp;&nbsp;&nbsp;限："
					+ info.getInterval());
					/*  TOCONFIG—TODELETE  */
					/*
					if(Env.getProjectName().compareToIgnoreCase(Constant.ProjectName.SEFC) == 0)
					{
						sb.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;摘&nbsp;&nbsp;&nbsp;&nbsp;要：&nbsp;"
						+ info.getAbstract());
					}
					*/
					sb.append(
					 "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;利&nbsp;&nbsp;&nbsp;&nbsp;率："
					+ info.getRate()
					+ "％（年息）<br>"
					+ "                <br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;备注：<br>"
					+ "          &nbsp;&nbsp;&nbsp;&nbsp;本证实书仅对存款人开户证实，不得作为质押的权利凭证。<br>"
					+ "          &nbsp;&nbsp;&nbsp;&nbsp;本存单一式三份，开户单位持一份，财务公司持二份。<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">第<br>一<br>"
					+ "      联<br>"
					+ "      <br>"
					+ "      记<br>"
					+ "      账<br>"
					+ "      凭<br>"
					+ "      证</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[记账人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[复核人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[部门经理]&nbsp;"
					+ "      </div></td>"
					+ "  </tr>"
					+ "</table><br>"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "<div align=\"center\"> "
					+ "&nbsp;"
					+ "        <div align=\"left\"><strong></strong></div>"
					+ "      </div></td>"
					+ "    <td width=\"50%\" align=\"center\">"
					+ " "
					+DataFormat.formatStringForPrint(Env.getClientName())
					+ " "
					+ "   </td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "   </td>"
					+ "    <td width=\"50%\" valign=\"top\" align=\"center\"><br>"
					+ DataFormat.formatStringForPrint(info.getDateOpenAccount())
					+ "   </td>"
					+ "  </tr>"
					+ "</table>"
					+ "<p></TD> </TR> </TABLE> </p>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
					out.println(sb.toString());
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 定期存款开户证实书
	 * 第二联
	 */
	public static void showFixOpenAccountNotice2(ShowFixedDepositOpenInfo info, JspWriter out) throws Exception
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "<td width=\"400\"><div align=\"center\"><strong><font style=\"font-size:16px\">&nbsp;&nbsp;&nbsp;"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "定期存款开户证实书&nbsp;</font></font></strong></strong></div></td>");
			//sb.append("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			sb.append(
				 "  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">存单号："
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</div><td></tr></table>"
					+ "</td></tr>"
					+ "</table>"
					+ "<table width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "  <tr>"
					+ "    <td width=\"600\" valign=\"top\">"
					+ "<table width=\"100%\" border=\"1\" bordercolordark=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#666666\">"
					+ "        <tr>"
					+ "          <td valign=\"top\"><br>"
					+ "<div align=\"center\">"
					+ "              <p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;名：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;&nbsp;号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;起&nbsp;息&nbsp;日：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getDateOpenAccount()));
			/*  TOCONFIG—TODELETE  */
			/*
					if(Env.getProjectName().compareToIgnoreCase(Constant.ProjectName.SEFC) == 0)
					{
						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
								+ "到&nbsp;期&nbsp;日：&nbsp;"
								+ info.getEndDate());
					}
					*/
					sb.append(
					 "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;金&nbsp;&nbsp;&nbsp;&nbsp;额："+ info.getCurrencyName() +"（大写）&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（小写）&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;期&nbsp;&nbsp;&nbsp;&nbsp;限："
					+ info.getInterval());
					/*  TOCONFIG—TODELETE  */
					/*
					if(Env.getProjectName().compareToIgnoreCase(Constant.ProjectName.SEFC) == 0)
					{
						sb.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;摘&nbsp;&nbsp;&nbsp;&nbsp;要：&nbsp;"
						+ info.getAbstract());
					}
					*/
					sb.append(
					 "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;利&nbsp;&nbsp;&nbsp;&nbsp;率："
					+ info.getRate()
					+ "％（年息）<br>"
					+ "                <br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "              备注：<br>"
					+ "           &nbsp;&nbsp;&nbsp;&nbsp;本证实书仅对存款人开户证实，不得作为质押的权利凭证。<br>"
					+ "           &nbsp;&nbsp;&nbsp;&nbsp;本存单一式三份，开户单位持一份，财务公司持二份。<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">第<br>二<br>"
					+ "      联<br>"
					+ "      <br>"
					+ "      业<br>"
					+ "      务<br>"
					+ "      留<br>"
					+ "      存</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[记账人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[复核人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[部门经理]&nbsp;"
					+ "      </div></td>"
					+ "  </tr>"
					+ "</table><br>"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "<div align=\"center\"> "
					+ "&nbsp;"
					+ "        <div align=\"left\"><strong></strong></div>"
					+ "      </div></td>"
					+ "    <td width=\"50%\" align=\"center\">"
					+ " "
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ " "
					+ "   </td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "   </td>"
					+ "    <td width=\"50%\" valign=\"top\" align=\"center\"><br>"
					+ DataFormat.formatStringForPrint(info.getDateOpenAccount())
					+ "   </td>"
					+ "  </tr>"
					+ "</table>"
					+ "<p></TD> </TR> </TABLE> </p>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
					out.println(sb.toString());
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 定期存款开户证实书
	 * 第三联
	 */
	public static void showFixOpenAccountNotice3(ShowFixedDepositOpenInfo info, JspWriter out) throws Exception
	{
		StringBuffer sb = new StringBuffer();
       
		try
		{
			sb.append(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "<td width=\"400\"><div align=\"right\"><strong><font style=\"font-size:16px\">&nbsp;&nbsp;&nbsp;"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "定期存款开户证实书&nbsp;</font></font></strong></strong></div></td>");
					
			//sb.append("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			sb.append(
				    "  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">存单号："
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</div><td></tr></table>"
					+ "</td></tr>"
					+ "</table>"
					+ "<table width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "  <tr>"
					+ "    <td width=\"600\" valign=\"top\">"
					+ "<table width=\"100%\" border=\"1\" bordercolordark=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#666666\">"
					+ "        <tr>"
					+ "          <td valign=\"top\"><br>"
					+ "<div align=\"center\">"
					+ "              <p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;名：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;&nbsp;号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;起&nbsp;息&nbsp;日：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getDateOpenAccount()));
			
					sb.append(
					 "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;金&nbsp;&nbsp;&nbsp;&nbsp;额："+ info.getCurrencyName() +"（大写）&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（小写）&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;期&nbsp;&nbsp;&nbsp;&nbsp;限："
					+ info.getInterval());
					
					sb.append(
					 "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;利&nbsp;&nbsp;&nbsp;&nbsp;率："
					+ info.getRate()
					+ "％（年息）<br>"
					+ "                <br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "              备注：<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;本证实书仅对存款人开户证实，不得作为质押的权利凭证。<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;本存单一式三份，开户单位持一份，财务公司持二份。<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">第<br>三<br>"
					+ "      联<br>"
					+ "      <br>"
					+ "      交<br>"
					+ "      存<br>"
					+ "      款<br>"
					+ "      单<br>"
					+ "      位</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[记账人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[复核人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[部门经理]&nbsp;"
					+ "      </div></td>"
					+ "  </tr>"
					+ "</table><br>"
					+ "<table width=\"630\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "<div align=\"center\"> "
					+ "&nbsp;"
					+ "        <div align=\"left\"><strong></strong></div>"
					+ "      </div></td>"
					+ "    <td width=\"50%\" align=\"center\">"
					+ " "
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ " "
					+ "   </td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td width=\"50%\" height=\"24\">"
					+ "   </td>"
					+ "    <td width=\"50%\" valign=\"top\"align=\"center\"><br>"
					+ DataFormat.formatStringForPrint(info.getDateOpenAccount())
					+ "   </td>"
					+ "  </tr>"
					+ "</table>"
                    
                    
                    
                    
                    +"   <table width=\"630\" border=\"0\">  "  
                    +"   <tr>     "  
                    +"  <td width=\"100%\" height=\"12\"><div align=\"left\">-------------------------------------------------------------------------------------------------</div></td>  "  
                    +"  </tr>" 
                    +"  </table>" 
                    +" <table width=\"630\" border=\"0\">"  
                    +"  <tr> "  
                    +"    <td height=\"24\"><div align=\"center\"><font style=\"font-size:20px\"><b>人民币单位定期存款支取通知</b></font></div></td>" 
                    +"  </tr>"  
                    +"  <tr> "  
                    +"    <td height=\"24\"> <div align=\"left\">"  
                    + DataFormat.formatStringForPrint(Env.getClientName())
                    +" ： </div></td>" 
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\"> "
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +" 我单位（名称）__________________________预于_____年__月__日从我定期存款账户（账号<u>"
                    + DataFormat.formatStringForPrint(info.getAccountNo())
                    +"</u>）</td>"  
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\">支取人民币（大写）______________________________元（小写：__________________元），请贵公司在设定日</td>" 
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\">期内将该支取款项连同利息转入以下指定之账户。</td>" 
                    +"  </tr>" 
                    +"  <tr>"  
                    +"&nbsp;&nbsp;"
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"账号：</td>" 
                    +"  </tr>" 
                    +"  <tr>"  
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"开户银行：</td>"  
                    +"  </tr>" 
                    +"  <tr>"  
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"特此通知</td>"  
                    +"  </tr>" 
                    +"  <tr>" 
                    +"    <td height=\"34\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;单位预留印鉴：</div></td>"
                    +"  </tr>" 
                    +"  <tr>" 
                    +"    <td height=\"24\"><b>注：此通知书一联，连同定期存款证实书提交财务公司营业部。</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_____年__月__日</td>"  
                    +"  </tr>" 
                    +"  <tr>"  
                    +"    <td height=\"24\"></td>"  
                    +"  </tr>" 
                    +"</table>"  
                    
                     
                    
                    
                    
                    
					+ "<p></TD> </TR> </TABLE> </p>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
					out.println(sb.toString());
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
	}
	/**
	 * 显示特种转账借方传票
	 *
	 */
	public static void showDebtor(ShowSpecialTransInfo info, JspWriter out) throws Exception
	{
		try
		{
			// out.print(" <html> " +
			//"	<head> " );
			////noShowPrintHeadAndFooter(out);
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ "	<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
					+ "	<style type=\"text/css\"> "
					+ "	<!-- "
					+ "	.Debtor-table1 {  border: 2px #000000 solid} "
					+ "	.Debtor-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px} "
					+ "	.Debtor-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px} "
					+ "	.Debtor-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px} "
					+ "	.Debtor-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
					+ "	.Debtor-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px} "
					+ "	.Debtor-td-rightbottom-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 3px; border-left-width: 0px} "
					+ "	.Debtor-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
					+ "	.Debtor-td-rightbottomright {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px} "
					+ "	.Debtor-td-rightbottom-left {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
					+ "	.Debtor-td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 2px} "
					+ " .Debtor-td-rightbottom2left {  border-color: black black #000000; border-style: solid; border-top-width:1px; border-right-width: 1px; border-bottom-width: 3px; border-left-width: 1px}"
					+ " .Debtor-td-rightbottomtop {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
					+ "	.Debtor-td-rightbottom-right {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 3px; border-left-width: 2px} "
					+ "	.Debtor-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
					+ " .Debtor-td-top {  border-color: black black #000000; border-style: solid; border-top-width: 2px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px} "
					+ "	--> "
					+ "	</style> "
					+ "<BODY bgcolor=\"#FFFFFF\" text=\"#000000\"> "
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD  width=\"50\">&nbsp;	</TD><TD width=\"310\">&nbsp;	</TD><TD width=\"70\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "<TR> 	"
					+ "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;	</TD> "
					+ "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "      特种转账借方传票</font></font></strong> 　</strong></TD>"
					+ "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"</TD>"
					+ "</TR> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"Debtor-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "	</TABLE><TABLE width=\"600\">	<TR> "
					+ "			<TD width=\"10%\">&nbsp;	 "
					+ "			</TD> "
					+ "			<TD align=\"right\" height=\"18\" width=\"51%\">	"
                    + " 打印日期: "
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "	</TD> "
					+ "		<TD align=\"right\" width=\"39%\">交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "		</TD>  "
					+ "		</TR> "
					+ "	</TABLE> "
					+ " <TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"Debtor-table1\"> "
					+ "  <TR>  "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"Debtor-td-rightbottom2left\">	 "
					+ "      付<BR> "
					+ "      款<BR> "
					+ "      单<BR> "
					+ "      位 </TD> "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"Debtor-td-rightbottomtop\">	 "
					+ "      全　　称 </TD> "
					+ "    <TD align=\"left\" width=\"214\" class=\"Debtor-td-rightbottomtop\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountName())
					+ "&nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"Debtor-td-rightbottomleft\">	 "
					+ "      收<BR> "
					+ "      款<BR> "
					+ "      单<BR> "
					+ "      位 </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"Debtor-td-rightbottom\"> 全　　称 </TD> "
					+ "    <TD align=\"left\" width=\"214\" class=\"Debtor-td-rightbottomright\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountName())
					+ " &nbsp; </TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Debtor-td-rightbottom\">	 "
					+ "      账　　号</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Debtor-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountNo())
					+ "    &nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"12%\" class=\"Debtor-td-rightbottom\"> 账　　号</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Debtor-td-bottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountNo())
					+ "    &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR> "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Debtor-td-rightbottom-bottom\">	 "
					+ "      开户银行 </TD> "
					+ "    <TD align=\"left\" class=\"Debtor-td-rightbottom-bottom\">"
					+ DataFormat.formatStringForPrint(info.getPayBankName())
					+ " &nbsp;</TD> "
					+ "   <TD align=\"center\" width=\"12%\" class=\"Debtor-td-rightbottom\"> 开户银行 </TD> "
					+ "   <TD align=\"left\" class=\"Debtor-td-bottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveBankName())
					+ " &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" class=\"Debtor-td-rightbottom\"> 金<BR> "
					+ "      额 </TD> "
					+ "    <TD align=\"center\" class=\"Debtor-td-rightbottom\">"
					+ info.getCurrencyName()
					+ "<BR> "
					+ "      (大写) </TD> "
					+ "    <TD colspan=\"3\" class=\"Debtor-td-rightbottom\" width=\"33%\"><B>&nbsp;"
					+ info.getChineseAmount()
					+ "</B>  "
					+ "    </TD> "
					+ "    <TD align=\"right\" width=\"33%\" class=\"Debtor-td-rightbottomright\"><B>&nbsp;"
					+ info.getAmount()
					+ "</B>  "
					+ "    </TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" height=\"60\" class=\"Debtor-td-rightbottom\"><BR> "
					+ "      转<BR> "
					+ "      账<BR> "
					+ "      原<BR> "
					+ "      因<BR> &nbsp; </TD> "
					+ "    <TD align=\"right\" colspan=\"5\" height=\"50\" valign=\"top\" class=\"Debtor-td-bottom\"><TABLE width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"100%\"> "
					+ "        <TR>  "
					+ "          <TD>&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAbstract())
					+ " </TD> "
					+ "        </TR> "
					+ "     </TABLE>  "
					+ "</TABLE> "
					+ "<br>"
					+ "<Table width=\"630\">"
					+ "<TR>"
					+ "<td align=\"right\">[记账人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;[复核人]&nbsp; "
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ " &nbsp;&nbsp;</td>"
					+ "</tr>"
					+ "</Table>"
					+ "</TABLE> "
					+ "</BODY> "
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 显示特种转账贷方传票
	 */
	public static void showCredit(ShowSpecialTransInfo info, JspWriter out) throws Exception
	{
		try
		{
			// out.print(" <html> " +
			// "	<head> " );
			////noShowPrintHeadAndFooter(out);
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ "	<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
					+ "	<style type=\"text/css\"> "
					+ "	<!-- "
					+ "	.Credit-table1 {  border: 2px #000000 solid} "
					+ "	.Credit-td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px} "
					+ "	.Credit-td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px} "
					+ "	.Credit-td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px} "
					+ "	.Credit-td-rightbottom-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 3px; border-left-width: 0px} "
					+ "	.Credit-td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
					+ " .Credit-td-rightbottomtop {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
					+ "	.Credit-td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px} "
					+ "	.Credit-td-rightbottom-right {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px} "
					+ "	.Credit-td-rightbottom-left {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
					+ "	.Credit-td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
					+ "	.Credit-td-rightbottombottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px} "
					+ "	.Credit-td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 3px; border-left-width: 2px} "
					+ "	.Credit-td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px} "
					+ " .Credit-td-top {  border-color: black black #000000; border-style: solid; border-top-width: 2px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px} "
					+ "	--> "
					+ "	</style> "
					+ "<BODY bgcolor=\"#FFFFFF\" text=\"#000000\"> "
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD  width=\"50\">&nbsp;	</TD><TD width=\"310\">&nbsp;	</TD><TD width=\"70\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "<TR> 	"
					+ "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;	</TD> "
					+ "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "      特种转账贷方传票</font></font></strong> 　</strong></TD>"
					+ "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"</TD>"
					+ "</TR> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"Credit-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "	</TABLE><TABLE width=\"600\">	<TR> "
					+ "			<TD width=\"10%\">&nbsp;	 "
					+ "			</TD> "
					+ "			<TD align=\"right\" height=\"18\" width=\"51%\">	"
                    + " 打印日期: "
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "	</TD> "
					+ "		<TD align=\"right\" width=\"39%\">交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "		</TD>  "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"Credit-table1\"> "
					+ "  <TR>  "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"Credit-td-rightbottom\"> 付<BR> "
					+ "      款<BR> "
					+ "      单<BR> "
					+ "      位 </TD> "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"Credit-td-rightbottom\">	 "
					+ "      全　　称 </TD> "
					+ "    <TD  align=\"left\" width=\214\" class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountName())
					+ " &nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"Credit-td-rightbottomleft\">	 "
					+ "      收<BR> "
					+ "      款<BR> "
					+ "      单<BR> "
					+ "      位 </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"Credit-td-rightbottomtop\"> 全　　称 </TD> "
					+ "    <TD align=\"left\" width=\"214\" class=\"Credit-td-rightbottomtop\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountName())
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Credit-td-rightbottom\">	 "
					+ "      账　　号</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountNo())
					+ "    &nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"12%\" class=\"Credit-td-rightbottom\"> 账　　号</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountNo())
					+ "    &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Credit-td-rightbottom\"> "
					+ "      开户银行 </TD> "
					+ "    <TD class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayBankName())
					+ "&nbsp; </TD> "
					+ "    <TD align=\"center\" width=\"12%\" class=\"Credit-td-rightbottom-bottom\"> 开户银行  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"Credit-td-rightbottom-bottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveBankName())
					+ " &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" class=\"Credit-td-rightbottom\"> 金<BR> "
					+ "      额 </TD> "
					+ "    <TD align=\"center\" class=\"Credit-td-rightbottom\">"
					+ info.getCurrencyName()
					+ "<BR> "
					+ "      (大写) </TD> "
					+ "    <TD colspan=\"3\" class=\"Credit-td-rightbottom\" width=\"33%\"><B>&nbsp;"
					+ info.getChineseAmount()
					+ "</B>  "
					+ "    </TD> "
					+ "    <TD align=\"right\" width=\"33%\" class=\"Credit-td-rightbottom-right\"><B>&nbsp;"
					+ info.getAmount()
					+ "</B>  "
					+ "    </TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" height=\"60\" class=\"Credit-td-rightbottom\"><BR> "
					+ "      转<BR> "
					+ "      账<BR> "
					+ "      原<BR> "
					+ "      因<BR> &nbsp; </TD> "
					+ "    <TD align=\"right\" colspan=\"5\" height=\"50\" valign=\"top\" class=\" Credit-td-bottom\"><TABLE width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"84%\">"
					+ "        <TR>"
					+ "          <TD height=\"16\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAbstract())
					+ "</TD>"
					+ "        </TR>"
					+ "      </TABLE>"
					+ "      <br> </TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "<br>"
					+ "<Table width=\"630\">"
					+ "<TR>"
					+ "<td align=\"right\">[记账人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;[复核人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ " &nbsp;&nbsp;</td>"
					+ "</tr>"
					+ "</Table>"
					+ "</BODY>"
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}

	
	/**
	 *全打模版；利息通知单
	 * 自营支取
	 * 自营贷款利息通知单第一联：收款贷方凭证
	 */
	public static void showTrustPayInterestNotice(ShowPayInterestInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ "<meta    http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
					+ "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
					+ "<style type=\"text/css\">"
					+ "<!--"
					+ ".table1 {  border: 2px solid #000000}"
					+ ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
					+ ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
					+ ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ "-->"
					+ "</style>"
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "	<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
					+ "		<TR>"
					+ "		<TD>"
					+ "	<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
					+ "		<TR>"
					+ "			<TD width=\"70\">&nbsp;	"
					+ "			</TD>"
					+ "			<TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"贷款利息通知单</font></b></font>"
					+ "			</TD>"
					+ "			<TD width=\"90\">");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"</TD>"
					+ "		</TR>"
					+ "	</TABLE><BR>"
					+ "	<TABLE width=\"600\" border=\"0\">"
					+ "		<TR>"
					+ "			<TD width=\"100%\" align=\"center\">"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td>	"
					+ "</tr>	"
					+ "<tr>	"
					+ "<td>	"
					+ "<table width=\"630\" border=\"0\">"
					+ "<tr><td>"
					+ "<TABLE width=\"600\" border=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"50%\" align=\"left\">借款单位名称："
					+ DataFormat.formatStringForPrint(info.getClientName())
					+ " </TD>"
					+ "    <TD width=\"50%\" align=\"right\">账号类型："
					+ DataFormat.formatStringForPrint(info.getAccountType())
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD>交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</TD>"
					+ "    <TD align=\"right\">&nbsp;&nbsp;账号："
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "        <tr align=\"center\"> "
					+ "          <td class=\"td-rightbottom\" width=\"80\" align=\"center\">类型</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">起息日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">终止日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">天数</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">本金</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\"align=\"center\">利率（%）</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\"> 利息 </td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">正常利息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestDay())
					+ "&nbsp;</td>"
					+ "          <td width=\"110\" colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">复利</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestDay())
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">逾期罚息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestDay())
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getTotalInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">担保费</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeDay())
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFee())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "              <tr> "
					+ "                <td> 上列利息，已照收你单位第（"
					+ DataFormat.formatStringForPrint(info.getInterestAccountNo())
					+ "）号账户。 </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应账户号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCurrentAccountNo())
					+ "  ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应合同号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getContractNo())
					+ " ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应放款通知单号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getLoanBillNo())
					+ "） </td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">转账日期：&nbsp; "
					+ DataFormat.formatStringForPrint(info.getTransAccountDate())
					+ " </td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> （盖章） </td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>收<BR>款<BR>贷<BR>方<BR>凭<BR>证</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;[复核人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>	"
					+ "</tr>"
					+ "  </Table>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * Method showTrustPayInterestNotice2.
	 * 自营贷款利息通知单第二联：信审部收款通知
	 * @param info
	 * @param out
	 * @throws Exception
	 */
	public static void showTrustPayInterestNotice2(ShowPayInterestInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ "<meta    http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
					+ "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
					+ "<style type=\"text/css\">"
					+ "<!--"
					+ ".table1 {  border: 2px solid #000000}"
					+ ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
					+ ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
					+ ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ "-->"
					+ "</style>"
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "	<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
					+ "		<TR>"
					+ "		<TD>"
					+ "	<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
					+ "		<TR>"
					+ "			<TD width=\"70\">&nbsp;	"
					+ "			</TD>"
					+ "			<TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"贷款利息通知单</font></b></font>"
					+ "			</TD>"
					+ "			<TD width=\"90\">");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"</TD>"
					+ "		</TR>"
					+ "	</TABLE><BR>"
					+ "	<TABLE width=\"600\" border=\"0\">"
					+ "		<TR>"
					+ "			<TD width=\"100%\" align=\"center\">"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td>"
					+ "</tr>"
					+ "		<TR>"
					+ "		<TD>"
					+ "<table width=\"630\" border=\"0\"><tr><td>"
					+ "<TABLE width=\"600\" border=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"50%\" align=\"left\"> 借款单位名称："
					+ DataFormat.formatStringForPrint(info.getClientName())
					+ " </TD>"
					+ "    <TD width=\"50%\" align=\"right\">账号类型："
					+ DataFormat.formatStringForPrint(info.getAccountType())
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD>交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</TD>"
					+ "    <TD align=\"right\">&nbsp;&nbsp;账号："
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "        <tr align=\"center\"> "
					+ "          <td class=\"td-rightbottom\" width=\"80\" align=\"center\">类型</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">起息日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">终止日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">天数</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">本金</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\"align=\"center\">利率（%）</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\">利息</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">正常利息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestDay())
					+ "&nbsp;</td>"
					+ "          <td width=\"110\" colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">复利</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestDay())
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">逾期罚息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestDay())
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getTotalInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">担保费</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeDay())
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint( info.getAssureFeeRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFee())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "              <tr> "
					+ "                <td> 上列利息，已照收你单位第（"
					+ DataFormat.formatStringForPrint(info.getInterestAccountNo())
					+ "）号账户。 </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应账户号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCurrentAccountNo())
					+ "  ）</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应合同号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getContractNo())
					+ " ）</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应放款通知单号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getLoanBillNo())
					+ "）</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">转账日期：&nbsp; "
					+ DataFormat.formatStringForPrint(info.getTransAccountDate())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> （盖章） </td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>信<BR>审<BR>部<BR>收<BR>款<BR>通<BR>知</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;[复核人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "  </Table>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * Method showTrustPayInterestNotice3.
	 * 自营贷款利息通知单第三联：业务部收款通知
	 * @param info
	 * @param out
	 * @throws Exception
	 */
	public static void showTrustPayInterestNotice3(ShowPayInterestInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ "<meta    http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
					+ "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
					+ "<style type=\"text/css\">"
					+ "<!--"
					+ ".table1 {  border: 2px solid #000000}"
					+ ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
					+ ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
					+ ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ "-->"
					+ "</style>"
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "	<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
					+ "		<TR>"
					+ "		<TD>"
					+ "	<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
					+ "		<TR>"
					+ "			<TD width=\"70\">&nbsp;	"
					+ "			</TD>"
					+ "			<TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
					+ DataFormat.formatStringForPrint(Env.getClientName())
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"贷款利息通知单</font></b></font>"
					+ "			</TD>"
					+ "			<TD width=\"90\">");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"</TD>"
					+ "		</TR>"
					+ "	</TABLE><BR>"
					+ "	<TABLE width=\"600\" border=\"0\">"
					+ "		<TR>"
					+ "			<TD width=\"100%\" align=\"center\">"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "			</TD>"
					+ "		</TR>"
					+ "		<TR>"
					+ "		<TD>"
					+ "<table width=\"630\" border=\"0\"><tr><td>"
					+ "<TABLE width=\"600\" border=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"50%\" align=\"left\">借款单位名称："
					+ DataFormat.formatStringForPrint(info.getClientName())
					+ " </TD>"
					+ "    <TD width=\"50%\" align=\"right\">账号类型："
					+ DataFormat.formatStringForPrint(info.getAccountType())
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD>交易编号："
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</TD>"
					+ "    <TD align=\"right\">&nbsp;&nbsp;账号："
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "        <tr align=\"center\"> "
					+ "          <td class=\"td-rightbottom\" width=\"80\" align=\"center\">类型</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">起息日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">终止日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">天数</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">本金</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\"align=\"center\">利率（%）</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\"> 利息 </td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">正常利息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestDay())
					+ "&nbsp;</td>"
					+ "          <td width=\"110\" colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterestRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getNormalInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">复利</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestDay())
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterestRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCompoundInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">逾期罚息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestDay())
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterestRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getOverInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getTotalInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">担保费</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeDateStart())
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeDateEnd())
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeDay())
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeAmount())
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFeeRate())
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAssureFee())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "              <tr> "
					+ "                <td> 上列利息，已照收你单位第（"
					+ DataFormat.formatStringForPrint(info.getInterestAccountNo())
					+ "）号账户。 </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应账户号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCurrentAccountNo())
					+ "  ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应合同号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getContractNo())
					+ " ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应放款通知单号：&nbsp;"
					+ DataFormat.formatStringForPrint(info.getLoanBillNo())
					+ "） </td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">转账日期：&nbsp; "
					+ DataFormat.formatStringForPrint(info.getTransAccountDate())
					+ " </td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> （盖章） </td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>三<BR>联<BR><BR>业<BR>务<BR>部<BR>收<BR>款<BR>通<BR>知</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;[复核人]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
     * Method showTrustPayInterestNotice3.
     * 自营贷款利息通知单第四联：付款借方凭证
     * @param info
     * @param out
     * @throws Exception
     */
    public static void showTrustPayInterestNotice4(ShowPayInterestInfo info, JspWriter out) throws Exception
    {
        try
        {
            out.print(
                " <Script Language=\"JavaScript\"> document.write(' "
                    + "<meta    http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
                    + "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
                    + "<style type=\"text/css\">"
                    + "<!--"
                    + ".table1 {  border: 2px solid #000000}"
                    + ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
                    + ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
                    + ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + "-->"
                    + "</style>"
                    + "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
                    + " <TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
                    + "     <TR>"
                    + "     <TD>"
                    + " <TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
                    + "     <TR>"
                    + "         <TD width=\"70\">&nbsp; "
                    + "         </TD>"
                    + "         <TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
                    + DataFormat.formatStringForPrint(Env.getClientName())
                    + "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"贷款利息通知单</font></b></font>"
                    + "         </TD>"
                    + "         <TD width=\"90\">");
            //out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
            out.println(
                "</TD>"
                    + "     </TR>"
                    + " </TABLE><BR>"
                    + " <TABLE width=\"600\" border=\"0\">"
                    + "     <TR>"
                    + "         <TD width=\"100%\" align=\"center\">"
                    + info.getYear()
                    + " 年 "
                    + info.getMonth()
                    + " 月 "
                    + info.getDay()
                    + " 日"
                    + "         </TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "         </TD>"
                    + "     </TR>"
                    + "     <TR>"
                    + "     <TD>"
                    + "<table width=\"630\" border=\"0\"><tr><td>"
                    + "<TABLE width=\"600\" border=\"0\">"
                    + "  <TR> "
                    + "    <TD width=\"50%\" align=\"left\">借款单位名称："
                    + DataFormat.formatStringForPrint(info.getClientName())
                    + " </TD>"
                    + "    <TD width=\"50%\" align=\"right\">账号类型："
                    + DataFormat.formatStringForPrint(info.getAccountType())
                    + " </TD>"
                    + "  </TR>"
                    + "  <TR>"
                    + "    <TD>交易编号："
                    + DataFormat.formatStringForPrint(info.getTransNo())
                    + "</TD>"
                    + "    <TD align=\"right\">&nbsp;&nbsp;账号："
                    + DataFormat.formatStringForPrint(info.getAccountNo())
                    + "</TD>"
                    + "  </TR>"
                    + "</TABLE>"
                    + " <TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
                    + "     <TR>"
                    + "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                    + "        <tr align=\"center\"> "
                    + "          <td class=\"td-rightbottom\" width=\"80\" align=\"center\">类型</td>"
                    + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">起息日期</td>"
                    + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">终止日期</td>"
                    + "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">天数</td>"
                    + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">本金</td>"
                    + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\"align=\"center\">利率（%）</td>"
                    + "          <td class=\"td-bottom\" width=\"90\" align=\"center\"> 利息 </td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">正常利息</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterestDateStart())
                    + "&nbsp;</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterestDateEnd())
                    + "&nbsp;</td>"
                    + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterestDay())
                    + "&nbsp;</td>"
                    + "          <td width=\"110\" colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterestAmount())
                    + "</td>"
                    + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterestRate())
                    + "&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterest())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">复利</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterestDateStart())
                    + "&nbsp;</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterestDateEnd())
                    + "&nbsp;</td>"
                    + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterestDay())
                    + "&nbsp;</td>"
                    + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterestAmount())
                    + "</td>"
                    + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterestRate())
                    + "&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterest())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">逾期罚息</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterestDateStart())
                    + "&nbsp;</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterestDateEnd())
                    + "&nbsp;</td>"
                    + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterestDay())
                    + "&nbsp;</td>"
                    + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterestAmount())
                    + "</td>"
                    + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterestRate())
                    + "&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterest())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
                    + "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getTotalInterest())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">担保费</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFeeDateStart())
                    + "&nbsp;</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFeeDateEnd())
                    + "&nbsp;</td>"
                    + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFeeDay())
                    + "&nbsp;</td>"
                    + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFeeAmount())
                    + "</td>"
                    + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFeeRate())
                    + "&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFee())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
                    + "              <tr> "
                    + "                <td> 上列利息，已照收你单位第（"
                    + DataFormat.formatStringForPrint(info.getInterestAccountNo())
                    + "）号账户。 </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> （对应账户号：&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCurrentAccountNo())
                    + "  ） </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> （对应合同号：&nbsp;"
                    + DataFormat.formatStringForPrint(info.getContractNo())
                    + " ） </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> （对应放款通知单号：&nbsp;"
                    + DataFormat.formatStringForPrint(info.getLoanBillNo())
                    + "） </td>"
                    + "              </tr>"
                    + "            </table></td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"3\">转账日期：&nbsp; "
                    + DataFormat.formatStringForPrint(info.getTransAccountDate())
                    + " </td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> （盖章） </td>"
                    + "        </tr>"
                    + "      </table></TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "</td><td>"
                    + " <TABLE width=\"30\" border=\"0\"> "
                    + "     <TR> "
                    + "         <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>四<BR>联<BR><BR>付<BR>款<BR>借<BR>方<BR>凭<BR>证</FONT> "
                    + "         </TD> "
                    + "     </TR> "
                    + " </TABLE> "
                    + "</td></tr></table>"
                    + "<br>"
                    + "<Table width=\"630  \" border=\"0\">"
                    + "  <TR align=\"right\"> "
                    + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
                    + "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
                    + DataFormat.formatStringForPrint(info.getInputUserName())
                    + "&nbsp;[复核人]&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCheckUserName())
                    + "</TD>"
                    + "  </TR>"
                    + "  </Table>"
                    + "         </TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "</BODY>"
                    + ""
                    + "  '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }
    /**
     * Method showTrustPayInterestNotice3.
     * 自营贷款利息通知单第五联：付款人付款通知
     * @param info
     * @param out
     * @throws Exception
     */
    public static void showTrustPayInterestNotice5(ShowPayInterestInfo info, JspWriter out) throws Exception
    {
        try
        {
            out.print(
                " <Script Language=\"JavaScript\"> document.write(' "
                    + "<meta    http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
                    + "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
                    + "<style type=\"text/css\">"
                    + "<!--"
                    + ".table1 {  border: 2px solid #000000}"
                    + ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
                    + ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
                    + ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + "-->"
                    + "</style>"
                    + "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
                    + " <TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
                    + "     <TR>"
                    + "     <TD>"
                    + " <TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
                    + "     <TR>"
                    + "         <TD width=\"70\">&nbsp; "
                    + "         </TD>"
                    + "         <TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
                    + DataFormat.formatStringForPrint(Env.getClientName())
                    + "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"贷款利息通知单</font></b></font>"
                    + "         </TD>"
                    + "         <TD width=\"90\">");
            //out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
            out.println(
                "</TD>"
                    + "     </TR>"
                    + " </TABLE><BR>"
                    + " <TABLE width=\"600\" border=\"0\">"
                    + "     <TR>"
                    + "         <TD width=\"100%\" align=\"center\">"
                    + info.getYear()
                    + " 年 "
                    + info.getMonth()
                    + " 月 "
                    + info.getDay()
                    + " 日"
                    + "         </TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "         </TD>"
                    + "     </TR>"
                    + "     <TR>"
                    + "     <TD>"
                    + "<table width=\"630\" border=\"0\"><tr><td>"
                    + "<TABLE width=\"600\" border=\"0\">"
                    + "  <TR> "
                    + "    <TD width=\"50%\" align=\"left\">借款单位名称："
                    + DataFormat.formatStringForPrint(info.getClientName())
                    + " </TD>"
                    + "    <TD width=\"50%\" align=\"right\">账号类型："
                    + DataFormat.formatStringForPrint(info.getAccountType())
                    + " </TD>"
                    + "  </TR>"
                    + "  <TR>"
                    + "    <TD>交易编号："
                    + DataFormat.formatStringForPrint(info.getTransNo())
                    + "</TD>"
                    + "    <TD align=\"right\">&nbsp;&nbsp;账号："
                    + DataFormat.formatStringForPrint(info.getAccountNo())
                    + "</TD>"
                    + "  </TR>"
                    + "</TABLE>"
                    + " <TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
                    + "     <TR>"
                    + "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                    + "        <tr align=\"center\"> "
                    + "          <td class=\"td-rightbottom\" width=\"80\" align=\"center\">类型</td>"
                    + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">起息日期</td>"
                    + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">终止日期</td>"
                    + "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">天数</td>"
                    + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">本金</td>"
                    + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\"align=\"center\">利率（%）</td>"
                    + "          <td class=\"td-bottom\" width=\"90\" align=\"center\"> 利息 </td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">正常利息</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterestDateStart())
                    + "&nbsp;</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterestDateEnd())
                    + "&nbsp;</td>"
                    + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterestDay())
                    + "&nbsp;</td>"
                    + "          <td width=\"110\" colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterestAmount())
                    + "</td>"
                    + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterestRate())
                    + "&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getNormalInterest())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">复利</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterestDateStart())
                    + "&nbsp;</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterestDateEnd())
                    + "&nbsp;</td>"
                    + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterestDay())
                    + "&nbsp;</td>"
                    + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterestAmount())
                    + "</td>"
                    + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterestRate())
                    + "&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCompoundInterest())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">逾期罚息</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterestDateStart())
                    + "&nbsp;</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterestDateEnd())
                    + "&nbsp;</td>"
                    + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterestDay())
                    + "&nbsp;</td>"
                    + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterestAmount())
                    + "</td>"
                    + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterestRate())
                    + "&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getOverInterest())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
                    + "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getTotalInterest())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">担保费</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFeeDateStart())
                    + "&nbsp;</td>"
                    + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFeeDateEnd())
                    + "&nbsp;</td>"
                    + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFeeDay())
                    + "&nbsp;</td>"
                    + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFeeAmount())
                    + "</td>"
                    + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFeeRate())
                    + "&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getAssureFee())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
                    + "              <tr> "
                    + "                <td> 上列利息，已照收你单位第（"
                    + DataFormat.formatStringForPrint(info.getInterestAccountNo())
                    + "）号账户。 </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> （对应账户号：&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCurrentAccountNo())
                    + "  ） </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> （对应合同号：&nbsp;"
                    + DataFormat.formatStringForPrint(info.getContractNo())
                    + " ） </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> （对应放款通知单号：&nbsp;"
                    + DataFormat.formatStringForPrint(info.getLoanBillNo())
                    + "） </td>"
                    + "              </tr>"
                    + "            </table></td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"3\">转账日期：&nbsp; "
                    + DataFormat.formatStringForPrint(info.getTransAccountDate())
                    + " </td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> （盖章） </td>"
                    + "        </tr>"
                    + "      </table></TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "</td><td>"
                    + " <TABLE width=\"30\" border=\"0\"> "
                    + "     <TR> "
                    + "         <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>五<BR>联<BR><BR>付<BR>款<BR>人<BR>付<BR>款<BR>通<BR>知</FONT> "
                    + "         </TD> "
                    + "     </TR> "
                    + " </TABLE> "
                    + "</td></tr></table>"
                    + "<br>"
                    + "<Table width=\"630  \" border=\"0\">"
                    + "  <TR align=\"right\"> "
                    + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
                    + "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
                    + DataFormat.formatStringForPrint(info.getInputUserName())
                    + "&nbsp;[复核人]&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCheckUserName())
                    + "</TD>"
                    + "  </TR>"
                    + "  </Table>"
                    + "         </TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "</BODY>"
                    + ""
                    + "  '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }
	/**
	 *全打模版；利息通知单
	 * 委托支取
	 * 委托贷款利息通知单
     * 第一联 收款贷方凭证
	 */
	public static void showConsignPayInterestNotice(ShowPayInterestInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
					+ "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
					+ "<style type=\"text/css\">"
					+ "<!--"
					+ ".table1 {  border: 2px solid #000000}"
					+ ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
					+ ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
					+ ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ "-->"
					+ "</style>"
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
					+ "		<TR>"
					+ "<td>"
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
					+ "		<TR>"
					+ "			<TD width=\"70\">&nbsp;	"
					+ "			</TD>"
					+ "			<TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"贷款利息通知单</font></b></font>"
					+ "			</TD>"
					+ "			<TD width=\"90\">");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"</TD>"
					+ "		</TR>"
					+ "	</TABLE><BR>"
					+ "	<TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
					+ "		<TR>"
					+ "			<TD width=\"100%\" align=\"center\">"
					+ " 打印日期: "
                    + info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "			</TD>"
					+ "		</TR>"
					+ "		<TR>"
					+ "<td valign=\"top\">"
					+ "<table width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"><tr><td valign=\"top\">"
					+ "<TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"46%\" align=\"left\">借款单位名称："
					+ info.getClientName()
					+ " </TD>"
					+ "    <TD width=\"33%\" align=\"right\">委托单位名称："
					+ info.getConsignClientName()
					+ " </TD>"
					+ "    <TD width=\"20%\" align=\"right\">账号类型："
					+ info.getAccountType()
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD width=\"46%\" align=\"left\">交易编号："
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
					+ "    <TD width=\"20%\" align=\"right\">账号："
					+ info.getAccountNo()
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD > <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "        <tr align=\"center\"> "
					+ "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">类型</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">起息日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">终息日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">天数</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">本金</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">利率（%）</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\">利息</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">正常利息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\"  class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getNormalInterestAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getNormalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">复利</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getCompoundInterestAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getCompoundInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">逾期罚息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\" align=\"center\">&nbsp;"
					+ info.getOverInterestDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getOverInterestAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getOverInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getTotalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">手续费</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\">&nbsp;"
					+ info.getCommissionFeeDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCommissionFeeDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCommissionFeeDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getCommissionFeeAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCommissionFeeRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getCommissionFee()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "              <tr> "
					+ "                <td> 上列利息，已照收你单位第("
					+ info.getInterestAccountNo()
					+ ")号账户。 </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应账户号：&nbsp;"
					+ info.getCurrentAccountNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应合同号：&nbsp;"
					+ info.getContractNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应借据号：&nbsp;"
					+ info.getLoanBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应存单号：&nbsp;"
					+ info.getDepositBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">转账日期：&nbsp;"
					+ info.getTransAccountDate()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(盖章)</td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>收<BR>款<BR>贷<BR>方<BR>凭<BR>证</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核人]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 *全打模版；利息通知单
	 * 委托支取
	 * 委托贷款利息通知单
     * 第二联 信贷部收款通知
	 */
	public static void showConsignPayInterestNotice2(ShowPayInterestInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
					+ "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
					+ "<style type=\"text/css\">"
					+ "<!--"
					+ ".table1 {  border: 2px solid #000000}"
					+ ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
					+ ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
					+ ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ "-->"
					+ "</style>"
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
					+ "		<TR>"
					+ "<td>"
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
					+ "		<TR>"
					+ "			<TD width=\"70\">&nbsp;	"
					+ "			</TD>"
					+ "			<TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"贷款利息通知单</font></b></font>"
					+ "			</TD>"
					+ "			<TD width=\"90\">");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"</TD>"
					+ "		</TR>"
					+ "	</TABLE><BR>"
					+ "	<TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
					+ "		<TR>"
					+ "			<TD width=\"100%\" align=\"center\">"
					+ " 打印日期: "
                    + info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "			</TD>"
					+ "		</TR>"
					+ "		<TR>"
					+ "			<TD valign=\"top\">"
					+ "<table width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"><tr><td valign=\"top\">"
					+ "<TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"46%\" align=\"left\">借款单位名称："
					+ info.getClientName()
					+ " </TD>"
					+ "    <TD width=\"33%\" align=\"right\">委托单位名称："
					+ info.getConsignClientName()
					+ " </TD>"
					+ "    <TD width=\"20%\" align=\"right\">账号类型："
					+ info.getAccountType()
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD width=\"46%\" align=\"left\">交易编号："
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
					+ "    <TD width=\"20%\" align=\"right\">账号："
					+ info.getAccountNo()
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD > <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "        <tr align=\"center\"> "
					+ "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">类型</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">起息日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">终息日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">天数</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">本金</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">利率（%）</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\">利息</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">正常利息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\"  class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getNormalInterestAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getNormalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">复利</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getCompoundInterestAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getCompoundInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">逾期罚息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\" align=\"center\">&nbsp;"
					+ info.getOverInterestDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getOverInterestAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getOverInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getTotalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">手续费</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\">&nbsp;"
					+ info.getCommissionFeeDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCommissionFeeDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCommissionFeeDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getCommissionFeeAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCommissionFeeRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getCommissionFee()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "              <tr> "
					+ "                <td> 上列利息，已照收你单位第("
					+ info.getInterestAccountNo()
					+ ")号账户。 </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应账户号：&nbsp;"
					+ info.getCurrentAccountNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应合同号：&nbsp;"
					+ info.getContractNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应借据号：&nbsp;"
					+ info.getLoanBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应存单号：&nbsp;"
					+ info.getDepositBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">转账日期：&nbsp;"
					+ info.getTransAccountDate()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(盖章)</td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>信<BR>贷<BR>部<BR>收<BR>款<BR>通<BR>知</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核人]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 全打模版；利息通知单
	 * 委托支取
	 * 委托贷款利息通知单
     * 第三联 业务部收款通知
	 */
	public static void showConsignPayInterestNotice3(ShowPayInterestInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
					+ "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
					+ "<style type=\"text/css\">"
					+ "<!--"
					+ ".table1 {  border: 2px solid #000000}"
					+ ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
					+ ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
					+ ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ "-->"
					+ "</style>"
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
					+ "		<TR>"
					+ "<td>"
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
					+ "		<TR>"
					+ "			<TD width=\"70\">&nbsp;	"
					+ "			</TD>"
					+ "			<TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"贷款利息通知单</font></b></font>"
					+ "			</TD>"
					+ "			<TD width=\"90\">");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"</TD>"
					+ "		</TR>"
					+ "	</TABLE><BR>"
					+ "	<TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
					+ "		<TR>"
					+ "			<TD width=\"100%\" align=\"center\">"
					+ " 打印日期: "
                    + info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "			</TD>"
					+ "		</TR>"
					+ "			<TR>"
					+ "		<TD valign=\"top\">"
					+ "<table width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"><tr><td valign=\"top\">"
					+ "<TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"46%\" align=\"left\">借款单位名称："
					+ info.getClientName()
					+ " </TD>"
					+ "    <TD width=\"33%\" align=\"right\">委托单位名称："
					+ info.getConsignClientName()
					+ " </TD>"
					+ "    <TD width=\"20%\" align=\"right\">账号类型："
					+ info.getAccountType()
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD width=\"46%\" align=\"left\">交易编号："
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
					+ "    <TD width=\"20%\" align=\"right\">账号："
					+ info.getAccountNo()
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD > <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "        <tr align=\"center\"> "
					+ "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">类型</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">起息日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">终息日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">天数</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">本金</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">利率（%）</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\">利息</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">正常利息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\"  class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getNormalInterestAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getNormalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">复利</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getCompoundInterestAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getCompoundInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">逾期罚息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\" align=\"center\">&nbsp;"
					+ info.getOverInterestDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getOverInterestAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getOverInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getTotalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">手续费</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\">&nbsp;"
					+ info.getCommissionFeeDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCommissionFeeDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCommissionFeeDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getCommissionFeeAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCommissionFeeRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getCommissionFee()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "              <tr> "
					+ "                <td> 上列利息，已照收你单位第("
					+ info.getInterestAccountNo()
					+ ")号账户。 </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应账户号：&nbsp;"
					+ info.getCurrentAccountNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应合同号：&nbsp;"
					+ info.getContractNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应借据号：&nbsp;"
					+ info.getLoanBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应存单号：&nbsp;"
					+ info.getDepositBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">转账日期：&nbsp;"
					+ info.getTransAccountDate()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(盖章)</td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>三<BR>联<BR><BR>业<BR>务<BR>部<BR>收<BR>款<BR>通<BR>知</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核人]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 *全打模版；利息通知单
	 * 委托支取
	 * 委托贷款利息通知单
     * 第四联 付款借方凭证
	 */
public static void showConsignPayInterestNotice4(ShowPayInterestInfo info, JspWriter out) throws Exception
{
	try
	{
		out.print(
			" <Script Language=\"JavaScript\"> document.write(' "
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
				+ "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
				+ "<style type=\"text/css\">"
				+ "<!--"
				+ ".table1 {  border: 2px solid #000000}"
				+ ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
				+ ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
				+ ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
				+ ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
				+ ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
				+ ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
				+ ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
				+ "-->"
				+ "</style>"
				+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
				+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
				+ "		<TR>"
				+ "<td>"
				+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
				+ "		<TR>"
				+ "			<TD width=\"70\">&nbsp;	"
				+ "			</TD>"
				+ "			<TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
				+ Env.getClientName()
				+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"贷款利息通知单</font></b></font>"
				+ "			</TD>"
				+ "			<TD width=\"90\">");
		//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
		out.println(
			"</TD>"
				+ "		</TR>"
				+ "	</TABLE><BR>"
				+ "	<TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
				+ "		<TR>"
				+ "			<TD width=\"100%\" align=\"center\">"
				+ " 打印日期: "
                + info.getYear()
				+ " 年 "
				+ info.getMonth()
				+ " 月 "
				+ info.getDay()
				+ " 日"
				+ "			</TD>"
				+ "		</TR>"
				+ "	</TABLE>"
				+ "			</TD>"
				+ "		</TR>"
				+ "			<TR>"
				+ "		<TD valign=\"top\">"
				+ "<table width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"><tr><td valign=\"top\">"
				+ "<TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
				+ "  <TR> "
				+ "    <TD width=\"46%\" align=\"left\">借款单位名称："
				+ info.getClientName()
				+ " </TD>"
				+ "    <TD width=\"33%\" align=\"right\">委托单位名称："
				+ info.getConsignClientName()
				+ " </TD>"
				+ "    <TD width=\"20%\" align=\"right\">账号类型："
				+ info.getAccountType()
				+ " </TD>"
				+ "  </TR>"
				+ "  <TR>"
				+ "    <TD width=\"46%\" align=\"left\">交易编号："
				+ info.getTransNo()
				+ "</TD>"
				+ "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
				+ "    <TD width=\"20%\" align=\"right\">账号："
				+ info.getAccountNo()
				+ "</TD>"
				+ "  </TR>"
				+ "</TABLE>"
				+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
				+ "		<TR>"
				+ "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\">"
				+ "		<TR>"
				+ "    <TD > <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
				+ "        <tr align=\"center\"> "
				+ "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">类型</td>"
				+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">起息日期</td>"
				+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">终息日期</td>"
				+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">天数</td>"
				+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">本金</td>"
				+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">利率（%）</td>"
				+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\">利息</td>"
				+ "        </tr>"
				+ "        <tr align=\"center\"> "
				+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">正常利息</td>"
				+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getNormalInterestDateStart()
				+ "&nbsp;</td>"
				+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getNormalInterestDateEnd()
				+ "&nbsp;</td>"
				+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getNormalInterestDay()
				+ "&nbsp;</td>"
				+ "          <td colspan=\"2\" width=\"110\"  class=\"td-rightbottom\" align=\"right\">&nbsp;"
				+ info.getNormalInterestAmount()
				+ "</td>"
				+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getNormalInterestRate()
				+ "&nbsp;</td>"
				+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
				+ info.getNormalInterest()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr align=\"center\"> "
				+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">复利</td>"
				+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getCompoundInterestDateStart()
				+ "&nbsp;</td>"
				+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getCompoundInterestDateEnd()
				+ "&nbsp;</td>"
				+ "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getCompoundInterestDay()
				+ "&nbsp;</td>"
				+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
				+ info.getCompoundInterestAmount()
				+ "</td>"
				+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getCompoundInterestRate()
				+ "&nbsp;</td>"
				+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
				+ info.getCompoundInterest()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr align=\"center\"> "
				+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">逾期罚息</td>"
				+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getOverInterestDateStart()
				+ "&nbsp;</td>"
				+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getOverInterestDateEnd()
				+ "&nbsp;</td>"
				+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\" align=\"center\">&nbsp;"
				+ info.getOverInterestDay()
				+ "&nbsp;</td>"
				+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
				+ info.getOverInterestAmount()
				+ "</td>"
				+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getOverInterestRate()
				+ "&nbsp;</td>"
				+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
				+ info.getOverInterest()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr align=\"center\"> "
				+ "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
				+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
				+ "&nbsp;</td>"
				+ "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
				+ info.getTotalInterest()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr align=\"center\"> "
				+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">手续费</td>"
				+ "          <td width=\"105\" class=\"td-rightbottom\">&nbsp;"
				+ info.getCommissionFeeDateStart()
				+ "&nbsp;</td>"
				+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getCommissionFeeDateEnd()
				+ "&nbsp;</td>"
				+ "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getCommissionFeeDay()
				+ "&nbsp;</td>"
				+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
				+ info.getCommissionFeeAmount()
				+ "</td>"
				+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
				+ info.getCommissionFeeRate()
				+ "&nbsp;</td>"
				+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
				+ info.getCommissionFee()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr> "
				+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
				+ "              <tr> "
				+ "                <td> 上列利息，已照收你单位第("
				+ info.getInterestAccountNo()
				+ ")号账户。 </td>"
				+ "              </tr>"
				+ "              <tr> "
				+ "                <td>(对应账户号：&nbsp;"
				+ info.getCurrentAccountNo()
				+ ")</td>"
				+ "              </tr>"
				+ "              <tr> "
				+ "                <td>(对应合同号：&nbsp;"
				+ info.getContractNo()
				+ ")</td>"
				+ "              </tr>"
				+ "              <tr> "
				+ "                <td>(对应借据号：&nbsp;"
				+ info.getLoanBillNo()
				+ ")</td>"
				+ "              </tr>"
				+ "              <tr> "
				+ "                <td>(对应存单号：&nbsp;"
				+ info.getDepositBillNo()
				+ ")</td>"
				+ "              </tr>"
				+ "            </table></td>"
				+ "        </tr>"
				+ "        <tr> "
				+ "          <td colspan=\"3\">转账日期：&nbsp;"
				+ info.getTransAccountDate()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr> "
				+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(盖章)</td>"
				+ "        </tr>"
				+ "      </table></TD>"
				+ "		</TR>"
				+ "	</TABLE>"
				+ "</td><td>"
				+ "	<TABLE width=\"30\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"> "
				+ "		<TR> "
				+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>四<BR>联<BR><BR>付<BR>款<BR>借<BR>方<BR>凭<BR>证</FONT> "
				+ "			</TD> "
				+ "		</TR> "
				+ "	</TABLE> "
				+ "</td></tr></table>"
				+ "<br>"
				+ "<Table width=\"630  \" border=\"0\">"
				+ "  <TR align=\"right\"> "
				+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
				+ "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
				+ info.getInputUserName()
				+ "&nbsp;[复核人]&nbsp;"
				+ info.getCheckUserName()
				+ "</TD>"
				+ "  </TR>"
				+ "  </Table>"
				+ "			</TD>"
				+ "		</TR>"
				+ "	</TABLE>"
				+ "</BODY>"
				+ ""
				+ "  '); </SCRIPT>  ");
	}
	catch (Exception e)
	{
	}
}

/**
 * 全打模版；利息通知单
 * 委托支取
 * 委托贷款利息通知单
 * 第六联 付款单位留存
 */
public static void showConsignPayInterestNotice6(ShowPayInterestInfo info, JspWriter out) throws Exception
{
    try
    {
        out.print(
            " <Script Language=\"JavaScript\"> document.write(' "
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
                + "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
                + "<style type=\"text/css\">"
                + "<!--"
                + ".table1 {  border: 2px solid #000000}"
                + ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
                + ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
                + ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
                + ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                + ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
                + ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
                + ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                + "-->"
                + "</style>"
                + "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
                + " <TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
                + "     <TR>"
                + "<td>"
                + " <TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
                + "     <TR>"
                + "         <TD width=\"70\">&nbsp; "
                + "         </TD>"
                + "         <TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
                + Env.getClientName()
                + "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"贷款利息通知单</font></b></font>"
                + "         </TD>"
                + "         <TD width=\"90\">");
        //out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
        out.println(
            "</TD>"
                + "     </TR>"
                + " </TABLE><BR>"
                + " <TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
                + "     <TR>"
                + "         <TD width=\"100%\" align=\"center\">"
                + " 打印日期: "
                + info.getYear()
                + " 年 "
                + info.getMonth()
                + " 月 "
                + info.getDay()
                + " 日"
                + "         </TD>"
                + "     </TR>"
                + " </TABLE>"
                + "         </TD>"
                + "     </TR>"
                + "         <TR>"
                + "     <TD valign=\"top\">"
                + "<table width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"><tr><td valign=\"top\">"
                + "<TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
                + "  <TR> "
                + "    <TD width=\"46%\" align=\"left\">借款单位名称："
                + info.getClientName()
                + " </TD>"
                + "    <TD width=\"33%\" align=\"right\">委托单位名称："
                + info.getConsignClientName()
                + " </TD>"
                + "    <TD width=\"20%\" align=\"right\">账号类型："
                + info.getAccountType()
                + " </TD>"
                + "  </TR>"
                + "  <TR>"
                + "    <TD width=\"46%\" align=\"left\">交易编号："
                + info.getTransNo()
                + "</TD>"
                + "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
                + "    <TD width=\"20%\" align=\"right\">账号："
                + info.getAccountNo()
                + "</TD>"
                + "  </TR>"
                + "</TABLE>"
                + " <TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
                + "     <TR>"
                + "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\">"
                + "     <TR>"
                + "    <TD > <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                + "        <tr align=\"center\"> "
                + "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">类型</td>"
                + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">起息日期</td>"
                + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">终息日期</td>"
                + "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">天数</td>"
                + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">本金</td>"
                + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">利率（%）</td>"
                + "          <td class=\"td-bottom\" width=\"90\" align=\"center\">利息</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">正常利息</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getNormalInterestDateStart()
                + "&nbsp;</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getNormalInterestDateEnd()
                + "&nbsp;</td>"
                + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getNormalInterestDay()
                + "&nbsp;</td>"
                + "          <td colspan=\"2\" width=\"110\"  class=\"td-rightbottom\" align=\"right\">&nbsp;"
                + info.getNormalInterestAmount()
                + "</td>"
                + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getNormalInterestRate()
                + "&nbsp;</td>"
                + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getNormalInterest()
                + "</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">复利</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCompoundInterestDateStart()
                + "&nbsp;</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCompoundInterestDateEnd()
                + "&nbsp;</td>"
                + "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCompoundInterestDay()
                + "&nbsp;</td>"
                + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                + info.getCompoundInterestAmount()
                + "</td>"
                + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCompoundInterestRate()
                + "&nbsp;</td>"
                + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getCompoundInterest()
                + "</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">逾期罚息</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getOverInterestDateStart()
                + "&nbsp;</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getOverInterestDateEnd()
                + "&nbsp;</td>"
                + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\" align=\"center\">&nbsp;"
                + info.getOverInterestDay()
                + "&nbsp;</td>"
                + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                + info.getOverInterestAmount()
                + "</td>"
                + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getOverInterestRate()
                + "&nbsp;</td>"
                + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getOverInterest()
                + "</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
                + "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
                + "&nbsp;</td>"
                + "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getTotalInterest()
                + "</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">手续费</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\">&nbsp;"
                + info.getCommissionFeeDateStart()
                + "&nbsp;</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCommissionFeeDateEnd()
                + "&nbsp;</td>"
                + "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCommissionFeeDay()
                + "&nbsp;</td>"
                + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                + info.getCommissionFeeAmount()
                + "</td>"
                + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCommissionFeeRate()
                + "&nbsp;</td>"
                + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getCommissionFee()
                + "</td>"
                + "        </tr>"
                + "        <tr> "
                + "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
                + "              <tr> "
                + "                <td> 上列利息，已照收你单位第("
                + info.getInterestAccountNo()
                + ")号账户。 </td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(对应账户号：&nbsp;"
                + info.getCurrentAccountNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(对应合同号：&nbsp;"
                + info.getContractNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(对应借据号：&nbsp;"
                + info.getLoanBillNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(对应存单号：&nbsp;"
                + info.getDepositBillNo()
                + ")</td>"
                + "              </tr>"
                + "            </table></td>"
                + "        </tr>"
                + "        <tr> "
                + "          <td colspan=\"3\">转账日期：&nbsp;"
                + info.getTransAccountDate()
                + "</td>"
                + "        </tr>"
                + "        <tr> "
                + "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(盖章)</td>"
                + "        </tr>"
                + "      </table></TD>"
                + "     </TR>"
                + " </TABLE>"
                + "</td><td>"
                + " <TABLE width=\"30\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"> "
                + "     <TR> "
                + "         <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>六<BR>联<BR><BR>付<BR>款<BR>单<BR>位<BR>留<BR>存</FONT> "
                + "         </TD> "
                + "     </TR> "
                + " </TABLE> "
                + "</td></tr></table>"
                + "<br>"
                + "<Table width=\"630  \" border=\"0\">"
                + "  <TR align=\"right\"> "
                + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
                + "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
                + info.getInputUserName()
                + "&nbsp;[复核人]&nbsp;"
                + info.getCheckUserName()
                + "</TD>"
                + "  </TR>"
                + "  </Table>"
                + "         </TD>"
                + "     </TR>"
                + " </TABLE>"
                + "</BODY>"
                + ""
                + "  '); </SCRIPT>  ");
    }
    catch (Exception e)
    {
    }
}
/**
     *全打模版；利息通知单
     * 委托支取
     * 委托贷款利息通知单
     * 第四联 付款人付款通知
     */
public static void showConsignPayInterestNotice5(ShowPayInterestInfo info, JspWriter out) throws Exception
{
    try
    {
        out.print(
            " <Script Language=\"JavaScript\"> document.write(' "
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
                + "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
                + "<style type=\"text/css\">"
                + "<!--"
                + ".table1 {  border: 2px solid #000000}"
                + ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
                + ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
                + ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
                + ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                + ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
                + ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
                + ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                + "-->"
                + "</style>"
                + "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
                + " <TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
                + "     <TR>"
                + "<td>"
                + " <TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
                + "     <TR>"
                + "         <TD width=\"70\">&nbsp; "
                + "         </TD>"
                + "         <TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
                + Env.getClientName()
                + "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"贷款利息通知单</font></b></font>"
                + "         </TD>"
                + "         <TD width=\"90\">");
        //out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
        out.println(
            "</TD>"
                + "     </TR>"
                + " </TABLE><BR>"
                + " <TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
                + "     <TR>"
                + "         <TD width=\"100%\" align=\"center\">"
                + " 打印日期: "
                + info.getYear()
                + " 年 "
                + info.getMonth()
                + " 月 "
                + info.getDay()
                + " 日"
                + "         </TD>"
                + "     </TR>"
                + " </TABLE>"
                + "         </TD>"
                + "     </TR>"
                + "         <TR>"
                + "     <TD valign=\"top\">"
                + "<table width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"><tr><td valign=\"top\">"
                + "<TABLE width=\"630\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\">"
                + "  <TR> "
                + "    <TD width=\"46%\" align=\"left\">借款单位名称："
                + info.getClientName()
                + " </TD>"
                + "    <TD width=\"33%\" align=\"right\">委托单位名称："
                + info.getConsignClientName()
                + " </TD>"
                + "    <TD width=\"20%\" align=\"right\">账号类型："
                + info.getAccountType()
                + " </TD>"
                + "  </TR>"
                + "  <TR>"
                + "    <TD width=\"46%\" align=\"left\">交易编号："
                + info.getTransNo()
                + "</TD>"
                + "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
                + "    <TD width=\"20%\" align=\"right\">账号："
                + info.getAccountNo()
                + "</TD>"
                + "  </TR>"
                + "</TABLE>"
                + " <TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
                + "     <TR>"
                + "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\">"
                + "     <TR>"
                + "    <TD > <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                + "        <tr align=\"center\"> "
                + "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">类型</td>"
                + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">起息日期</td>"
                + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">终息日期</td>"
                + "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">天数</td>"
                + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">本金</td>"
                + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">利率（%）</td>"
                + "          <td class=\"td-bottom\" width=\"90\" align=\"center\">利息</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">正常利息</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getNormalInterestDateStart()
                + "&nbsp;</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getNormalInterestDateEnd()
                + "&nbsp;</td>"
                + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getNormalInterestDay()
                + "&nbsp;</td>"
                + "          <td colspan=\"2\" width=\"110\"  class=\"td-rightbottom\" align=\"right\">&nbsp;"
                + info.getNormalInterestAmount()
                + "</td>"
                + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getNormalInterestRate()
                + "&nbsp;</td>"
                + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getNormalInterest()
                + "</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">复利</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCompoundInterestDateStart()
                + "&nbsp;</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCompoundInterestDateEnd()
                + "&nbsp;</td>"
                + "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCompoundInterestDay()
                + "&nbsp;</td>"
                + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                + info.getCompoundInterestAmount()
                + "</td>"
                + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCompoundInterestRate()
                + "&nbsp;</td>"
                + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getCompoundInterest()
                + "</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">逾期罚息</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getOverInterestDateStart()
                + "&nbsp;</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getOverInterestDateEnd()
                + "&nbsp;</td>"
                + "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\" align=\"center\">&nbsp;"
                + info.getOverInterestDay()
                + "&nbsp;</td>"
                + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                + info.getOverInterestAmount()
                + "</td>"
                + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getOverInterestRate()
                + "&nbsp;</td>"
                + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getOverInterest()
                + "</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
                + "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
                + "&nbsp;</td>"
                + "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getTotalInterest()
                + "</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">手续费</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\">&nbsp;"
                + info.getCommissionFeeDateStart()
                + "&nbsp;</td>"
                + "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCommissionFeeDateEnd()
                + "&nbsp;</td>"
                + "          <td width=\"75\"  class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCommissionFeeDay()
                + "&nbsp;</td>"
                + "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                + info.getCommissionFeeAmount()
                + "</td>"
                + "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                + info.getCommissionFeeRate()
                + "&nbsp;</td>"
                + "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getCommissionFee()
                + "</td>"
                + "        </tr>"
                + "        <tr> "
                + "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
                + "              <tr> "
                + "                <td> 上列利息，已照收你单位第("
                + info.getInterestAccountNo()
                + ")号账户。 </td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(对应账户号：&nbsp;"
                + info.getCurrentAccountNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(对应合同号：&nbsp;"
                + info.getContractNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(对应借据号：&nbsp;"
                + info.getLoanBillNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(对应存单号：&nbsp;"
                + info.getDepositBillNo()
                + ")</td>"
                + "              </tr>"
                + "            </table></td>"
                + "        </tr>"
                + "        <tr> "
                + "          <td colspan=\"3\">转账日期：&nbsp;"
                + info.getTransAccountDate()
                + "</td>"
                + "        </tr>"
                + "        <tr> "
                + "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(盖章)</td>"
                + "        </tr>"
                + "      </table></TD>"
                + "     </TR>"
                + " </TABLE>"
                + "</td><td>"
                + " <TABLE width=\"30\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"> "
                + "     <TR> "
                + "         <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>五<BR>联<BR><BR>付<BR>款<BR>人<BR>付<BR>款<BR>通<BR>知</FONT> "
                + "         </TD> "
                + "     </TR> "
                + " </TABLE> "
                + "</td></tr></table>"
                + "<br>"
                + "<Table width=\"630  \" border=\"0\">"
                + "  <TR align=\"right\"> "
                + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
                + "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
                + info.getInputUserName()
                + "&nbsp;[复核人]&nbsp;"
                + info.getCheckUserName()
                + "</TD>"
                + "  </TR>"
                + "  </Table>"
                + "         </TD>"
                + "     </TR>"
                + " </TABLE>"
                + "</BODY>"
                + ""
                + "  '); </SCRIPT>  ");
    }
    catch (Exception e)
    {
    }
}
	/**
	 * 全打模版；利息通知单
	 * 活期
     * 第一联 回单
	 */
	public static void showCurrentPayInterestNotice(ShowPayInterestInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
					+ "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
					+ "<style type=\"text/css\">"
					+ "<!--"
					+ ".table1 {  border: 2px solid #000000}"
					+ ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
					+ ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
					+ ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ "-->"
					+ "</style>"
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "	<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
					+ "		<TR>"
					+ "			<TD width=\"90\">&nbsp;	"
					+ "			</TD>"
					+ "			<TD width=\"370\" height=\"2\" align=\"center\" class=\"td-bottom2\"><b><font size =\"+1.5\">"
					+ Env.getClientName()
					+ "</font><br><font style=\"font-size:20px\">"
					+ info.getCurrencyName()
					+ "活期利息通知单</font></b>"
					+ "			</TD>"
					+ "			<TD width=\"90\">");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"</TD>"
					+ "		</TR>"
					+ "	</TABLE><BR>"
					+ "	<TABLE width=\"600\" border=\"0\">"
					+ "		<TR>"
					+ "			<TD width=\"100%\" align=\"center\">"
					+ " 打印日期: "
                    + info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE><BR>"
					+ "	"
					+ "<TABLE width=\"600\" border=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"50%\" align=\"left\">账户名称："
					+ info.getAccountName()
					+ "    <TD width=\"50%\" align=\"right\">账号类型："
					+ info.getAccountType()
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD width=\"50%\" align=\"left\">交易编号："
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD width=\"50%\" align=\"right\">账号："
					+ info.getAccountNo()
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD height=\"24\"> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "        <tr align=\"center\"> "
					+ "          <td class=\"td-rightbottom\" width=\"77\" align=\"center\">类型</td>"
					+ "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">起息日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">终息日期</td>"
					+ "          <td class=\"td-rightbottom\" width=\"39\" align=\"center\">天数</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"130\" align=\"center\">平均余额</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">利率(%)</td>"
					+ "          <td class=\"td-bottom\" width=\"123\" align=\"center\">利息</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">活期利息</td>"
					+ "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCurrentInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCurrentInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"39\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCurrentInterestDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"130\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getCurrentInterestAmount()
					+ "</td>"
					+ "          <td width=\"81\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCurrentInterestRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"123\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getCurrentInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">协定利息</td>"
					+ "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAccordInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAccordInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"39\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAccordInterestDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getAccordInterestAmount()
					+ "</td>"
					+ "          <td width=\"81\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAccordInterestRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"123\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getAccordInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">利息合计</td>"
					+ "          <td width=\"85\" class=\"td-rightbottom\" colspan=\"6\" align=\"right\">&nbsp;"
					+ info.getTotalInterestChinese()
					+ "</td>"
					+ "          <td width=\"123\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getTotalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "              <tr> "
					+ "                <td> 上列利息，已照付你单位第("
					+ info.getInterestAccountNo()
					+ ")号账户。 </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应账户号：&nbsp;"
					+ info.getCurrentAccountNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应合同号：&nbsp;"
					+ info.getContractNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应借据号：&nbsp;"
					+ info.getLoanBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(对应存单号：&nbsp;"
					+ info.getDepositBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">转账日期：&nbsp;"
					+ info.getTransAccountDate()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(盖章)</td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp;</TD>"
					+ "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核人]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</BODY>"
					+ ""
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
     * 全打模版；利息通知单
     * 活期
     * 第二联 记账凭证
     */
    public static void showCurrentPayInterestNotice2(ShowPayInterestInfo info, JspWriter out) throws Exception
    {
        try
        {
            out.print(
                " <Script Language=\"JavaScript\"> document.write(' "
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
                    + "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
                    + "<style type=\"text/css\">"
                    + "<!--"
                    + ".table1 {  border: 2px solid #000000}"
                    + ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
                    + ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
                    + ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + "-->"
                    + "</style>"
                    + "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
                    + " <TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                    + "     <TR>"
                    + "         <TD width=\"90\">&nbsp; "
                    + "         </TD>"
                    + "         <TD width=\"370\" height=\"2\" align=\"center\" class=\"td-bottom2\"><b><font size =\"+1.5\">"
                    + Env.getClientName()
                    + "</font><br><font style=\"font-size:20px\">"
                    + info.getCurrencyName()
                    + "活期利息通知单</font></b>"
                    + "         </TD>"
                    + "         <TD width=\"90\">");
            //out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
            out.println(
                "</TD>"
                    + "     </TR>"
                    + " </TABLE><BR>"
                    + " <TABLE width=\"600\" border=\"0\">"
                    + "     <TR>"
                    + "         <TD width=\"100%\" align=\"center\">"
                    + " 打印日期: "
                    + info.getYear()
                    + " 年 "
                    + info.getMonth()
                    + " 月 "
                    + info.getDay()
                    + " 日"
                    + "         </TD>"
                    + "     </TR>"
                    + " </TABLE><BR>"
                    + " "
                    + "<TABLE width=\"600\" border=\"0\">"
                    + "  <TR> "
                    + "    <TD width=\"50%\" align=\"left\">账户名称："
                    + info.getAccountName()
                    + "    <TD width=\"50%\" align=\"right\">账号类型："
                    + info.getAccountType()
                    + " </TD>"
                    + "  </TR>"
                    + "  <TR>"
                    + "    <TD width=\"50%\" align=\"left\">交易编号："
                    + info.getTransNo()
                    + "</TD>"
                    + "    <TD width=\"50%\" align=\"right\">账号："
                    + info.getAccountNo()
                    + "</TD>"
                    + "  </TR>"
                    + "</TABLE>"
                    + " <TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
                    + "     <TR>"
                    + "    <TD height=\"24\"> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                    + "        <tr align=\"center\"> "
                    + "          <td class=\"td-rightbottom\" width=\"77\" align=\"center\">类型</td>"
                    + "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">起息日期</td>"
                    + "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">终息日期</td>"
                    + "          <td class=\"td-rightbottom\" width=\"39\" align=\"center\">天数</td>"
                    + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"130\" align=\"center\">平均余额</td>"
                    + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">利率(%)</td>"
                    + "          <td class=\"td-bottom\" width=\"123\" align=\"center\">利息</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">活期利息</td>"
                    + "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getCurrentInterestDateStart()
                    + "&nbsp;</td>"
                    + "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getCurrentInterestDateEnd()
                    + "&nbsp;</td>"
                    + "          <td width=\"39\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getCurrentInterestDay()
                    + "&nbsp;</td>"
                    + "          <td colspan=\"2\" width=\"130\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + info.getCurrentInterestAmount()
                    + "</td>"
                    + "          <td width=\"81\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getCurrentInterestRate()
                    + "&nbsp;</td>"
                    + "          <td width=\"123\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + info.getCurrentInterest()
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">协定利息</td>"
                    + "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getAccordInterestDateStart()
                    + "&nbsp;</td>"
                    + "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getAccordInterestDateEnd()
                    + "&nbsp;</td>"
                    + "          <td width=\"39\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getAccordInterestDay()
                    + "&nbsp;</td>"
                    + "          <td colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + info.getAccordInterestAmount()
                    + "</td>"
                    + "          <td width=\"81\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getAccordInterestRate()
                    + "&nbsp;</td>"
                    + "          <td width=\"123\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + info.getAccordInterest()
                    + "</td>"
                    + "        </tr>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">利息合计</td>"
                    + "          <td width=\"85\" class=\"td-rightbottom\" colspan=\"6\" align=\"right\">&nbsp;"
                    + info.getTotalInterestChinese()
                    + "</td>"
                    + "          <td width=\"123\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + info.getTotalInterest()
                    + "</td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
                    + "              <tr> "
                    + "                <td> 上列利息，已照付你单位第("
                    + info.getInterestAccountNo()
                    + ")号账户。 </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(对应账户号：&nbsp;"
                    + info.getCurrentAccountNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(对应合同号：&nbsp;"
                    + info.getContractNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(对应借据号：&nbsp;"
                    + info.getLoanBillNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(对应存单号：&nbsp;"
                    + info.getDepositBillNo()
                    + ")</td>"
                    + "              </tr>"
                    + "            </table></td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"3\">转账日期：&nbsp;"
                    + info.getTransAccountDate()
                    + "</td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(盖章)</td>"
                    + "        </tr>"
                    + "      </table></TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "<br>"
                    + "<Table width=\"630  \" border=\"0\">"
                    + "  <TR align=\"right\"> "
                    + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp;</TD>"
                    + "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
                    + info.getInputUserName()
                    + "&nbsp;[复核人]&nbsp;"
                    + info.getCheckUserName()
                    + "</TD>"
                    + "  </TR>"
                    + "  </Table>"
                    + "</BODY>"
                    + ""
                    + "  '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }
    
    /**
     * 全打模版；利息通知单
     * 活期
     * 第三联 利息凭证
     */
    public static void showCurrentPayInterestNotice3(ShowPayInterestInfo info, JspWriter out) throws Exception
    {
        try
        {
            out.print(
                " <Script Language=\"JavaScript\"> document.write(' "
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
                    + "<link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\">"
                    + "<style type=\"text/css\">"
                    + "<!--"
                    + ".table1 {  border: 2px solid #000000}"
                    + ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
                    + ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
                    + ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + "-->"
                    + "</style>"
                    + "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\">"
                    + " <TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                    + "     <TR>"
                    + "         <TD width=\"90\">&nbsp; "
                    + "         </TD>"
                    + "         <TD width=\"370\" height=\"2\" align=\"center\" class=\"td-bottom2\"><b><font size =\"+1.5\">"
                    + Env.getClientName()
                    + "</font><br><font style=\"font-size:20px\">"
                    + info.getCurrencyName()
                    + "活期利息通知单</font></b>"
                    + "         </TD>"
                    + "         <TD width=\"90\">");
            //out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
            out.println(
                "</TD>"
                    + "     </TR>"
                    + " </TABLE><BR>"
                    + " <TABLE width=\"600\" border=\"0\">"
                    + "     <TR>"
                    + "         <TD width=\"100%\" align=\"center\">"
                    + " 打印日期: "
                    + info.getYear()
                    + " 年 "
                    + info.getMonth()
                    + " 月 "
                    + info.getDay()
                    + " 日"
                    + "         </TD>"
                    + "     </TR>"
                    + " </TABLE><BR>"
                    + " "
                    + "<TABLE width=\"600\" border=\"0\">"
                    + "  <TR> "
                    + "    <TD width=\"50%\" align=\"left\">账户名称："
                    + info.getAccountName()
                    + "    <TD width=\"50%\" align=\"right\">账号类型："
                    + info.getAccountType()
                    + " </TD>"
                    + "  </TR>"
                    + "  <TR>"
                    + "    <TD width=\"50%\" align=\"left\">交易编号："
                    + info.getTransNo()
                    + "</TD>"
                    + "    <TD width=\"50%\" align=\"right\">账号："
                    + info.getAccountNo()
                    + "</TD>"
                    + "  </TR>"
                    + "</TABLE>"
                    + " <TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
                    + "     <TR>"
                    + "    <TD height=\"24\"> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                    + "        <tr align=\"center\"> "
                    + "          <td class=\"td-rightbottom\" width=\"77\" align=\"center\">类型</td>"
                    + "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">起息日期</td>"
                    + "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">终息日期</td>"
                    + "          <td class=\"td-rightbottom\" width=\"39\" align=\"center\">天数</td>"
                    + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"130\" align=\"center\">平均余额</td>"
                    + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">利率(%)</td>"
                    + "          <td class=\"td-bottom\" width=\"123\" align=\"center\">利息</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">活期利息</td>"
                    + "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getCurrentInterestDateStart()
                    + "&nbsp;</td>"
                    + "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getCurrentInterestDateEnd()
                    + "&nbsp;</td>"
                    + "          <td width=\"39\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getCurrentInterestDay()
                    + "&nbsp;</td>"
                    + "          <td colspan=\"2\" width=\"130\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + info.getCurrentInterestAmount()
                    + "</td>"
                    + "          <td width=\"81\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getCurrentInterestRate()
                    + "&nbsp;</td>"
                    + "          <td width=\"123\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + info.getCurrentInterest()
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">协定利息</td>"
                    + "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getAccordInterestDateStart()
                    + "&nbsp;</td>"
                    + "          <td width=\"85\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getAccordInterestDateEnd()
                    + "&nbsp;</td>"
                    + "          <td width=\"39\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getAccordInterestDay()
                    + "&nbsp;</td>"
                    + "          <td colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
                    + info.getAccordInterestAmount()
                    + "</td>"
                    + "          <td width=\"81\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
                    + info.getAccordInterestRate()
                    + "&nbsp;</td>"
                    + "          <td width=\"123\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + info.getAccordInterest()
                    + "</td>"
                    + "        </tr>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">利息合计</td>"
                    + "          <td width=\"85\" class=\"td-rightbottom\" colspan=\"6\" align=\"right\">&nbsp;"
                    + info.getTotalInterestChinese()
                    + "</td>"
                    + "          <td width=\"123\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
                    + info.getTotalInterest()
                    + "</td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
                    + "              <tr> "
                    + "                <td> 上列利息，已照付你单位第("
                    + info.getInterestAccountNo()
                    + ")号账户。 </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(对应账户号：&nbsp;"
                    + info.getCurrentAccountNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(对应合同号：&nbsp;"
                    + info.getContractNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(对应借据号：&nbsp;"
                    + info.getLoanBillNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(对应存单号：&nbsp;"
                    + info.getDepositBillNo()
                    + ")</td>"
                    + "              </tr>"
                    + "            </table></td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"3\">转账日期：&nbsp;"
                    + info.getTransAccountDate()
                    + "</td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(盖章)</td>"
                    + "        </tr>"
                    + "      </table></TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "<br>"
                    + "<Table width=\"630  \" border=\"0\">"
                    + "  <TR align=\"right\"> "
                    + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp;</TD>"
                    + "    <TD height=\"22\" nowrap>[记账人]&nbsp;"
                    + info.getInputUserName()
                    + "&nbsp;[复核人]&nbsp;"
                    + info.getCheckUserName()
                    + "</TD>"
                    + "  </TR>"
                    + "  </Table>"
                    + "</BODY>"
                    + ""
                    + "  '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }

	/**
	 * 存款利息计付通知单
	 * 第一联 利息回单
	 * throws Exception
	 */
	public static void showDepositInterest1(ShowDepositInterestInfo info, JspWriter out) throws Exception
	{
		try
		{
			//  +"<html>"
			// +"<head>"
			////noShowPrintHeadAndFooter(out
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
					+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
					+ "<link rel=\"stylesheet\" href=\"../library/template.css\" type=\"text/css\">"
					+ "<style type=\"text/css\">"
					+ "<!--"
					+ ".table1 {  border: 2px solid #000000}"
					+ ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
					+ ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
					+ ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
					+ ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
					+ ".td-lefttopright {  border-color: black black #000000; border-style: solid; border-top-width: 3px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 3px}"
					+ ".td-topright {  border-color: black black #000000; border-style: solid; border-top-width: 3px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px}"
					+ ".f9 {  font-size: 10px}"
					+ "-->"
					+ "</style>"
					+ "<body text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "<table width=\"640\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
					+ "  "
					+ "<tr> "
					+ "    "
					+ "<td width=\"150\">&nbsp;</td>"
					+ "<td width=\"300\" align=\"center\"><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><br>"
					+ "<FONT face=\"Arial Black\"><font style=\"font-size:20px\">存款利息计付通知单</font></FONT></strong></td>"
					+ "<td width=\"180\">");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
					"</TD>"
					+ "</tr>"
					+ "<br>"
					+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"640\">"
					+ "  "
					+ "<tr>"
					+ "<td width=\"185\" align=\"left\"></td>"
					+ "<td width=\"35\" align=\"right\">&nbsp;</td>"
					+ "<td width=\"152\"> "
					+ "</td>"
					+ "<td width=\"74\">&nbsp;</td>"
					+ "<td width=\"184\" align=\"right\">"
					+ "&nbsp;</td>"
					+ "<td width=\"10\" align=\"right\">&nbsp;</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td width=\"185\" align=\"left\"></td>"
					
					+ "    "
					+ "<td width=\"230\" colspan=\"2\"> "
					+ "			<font style=\"font-size:13px\">"
					+ " 打印日期: "
                    + info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日</font></td>"
					+ "<td width=\"254\" align=\"center\" colspan=\"3\" ><font style=\"font-size:13px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易编号："
					+ info.getTransNo()
					+ "</font></td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td width=\"200\" align=\"left\" nowrap><font style=\"font-size:13px\">单位名称：<U>"
					+ info.getClientName()
					+ "</U></font></td>"
					+ "<td width=\"35\" align=\"right\">&nbsp;</td>"
					+ " "
					+ "    "
					+ "<td width=\"152\"> "
					+ "      "
					+ "<font style=\"font-size:13px\">账号：<U>"
					+ info.getAccountNo()
					+ "</U></font></td>"
					+ "<td width=\"74\">&nbsp;</td>"
					+ "<td width=\"170\" align=\"right\"><font style=\"font-size:13px\">存款种类：<U>"
					+ info.getDepositTypeName()
					+ "</U></font></td>"
					+ "<td width=\"10\" align=\"right\">&nbsp;</td>"
					+ "</tr>"
					+ "</table>"
					+ "<table width=\"640\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "  "
					+ "<tr>"
					+ "    "
					+ "<td>"
					+ "      "
					+ "<table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "<tr>"
					+ "<td class=\"td-rightbottom\" colspan=\"12\" align=\"center\"><font style=\"font-size:13px\">本金</font></td>"
					+ "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">起息日期</font></td>"
					+ "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">截至日期</font></td>"
					+ "<td class=\"td-rightbottom\" width=\"6%\" align=\"center\"><font style=\"font-size:13px\">天数</font></td>"
					+ "<td class=\"td-rightbottom\" width=\"5%\" align=\"center\"><font style=\"font-size:13px\">利率%</font></td>"
					+ "<td class=\"td-bottom\" width=\"8%\" colspan=\"10\" align=\"center\"><font style=\"font-size:13px\">利息</font></td>"
					+ "</tr>"
					+ "        "
                    
                    
				+ "<tr>" //显示本金<!--------1---------!>
			    +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
				+info.getAmount1()
				+ "</font></td>"
                //显示其息日期
			    +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getBeginYear1()
				+ "</font></td>"
				+ "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getBeginMonth1()
				+ "</font></td>"
				+ "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getBeginDay1()
				+ "</font></td>" //显示终息日期
				+"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getOverYear1()
				+ "</font></td>"
				+ "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getOverMonth1()
				+ "</font></td>"
				+ "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getOverDay1()
				+ "</font></td>" //天数
				+"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber1() + "</font></td>" //利率
				+"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate1() + "</td>" //利息
				+"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getInterest1()
				+ "</font></td>"
				+ "</tr>"
                
				+ "        "
				+ "        "
				+ "<tr>" //显示本金<!--------2---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount2()
                + "</font></td>"
                //显示其息日期
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay2()
                + "</font></td>" //显示终息日期
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay2()
                + "</font></td>" //天数
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber2() + "</font></td>" //利率
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate2() + "</td>" //利息
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                +info.getInterest2()
                + "</font></td>"
                + "</tr>"
				+ "        "
				+ "        "
				+ "<tr>" //显示本金<!--------3---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount3()
                + "</font></td>"
                //显示其息日期
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay3()
                + "</font></td>" //显示终息日期
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay3()
                + "</font></td>" //天数
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber3() + "</font></td>" //利率
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate3() + "</td>" //利息
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest3()
                + "</font></td>"
                + "</tr>"
				+ "        "
				+ "        "
				+ "<tr>" //显示本金<!--------4---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount4()
                + "</font></td>"
                //显示其息日期
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay4()
                + "</font></td>" //显示终息日期
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay4()
                + "</font></td>" //天数
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber4() + "</font></td>" //利率
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate4() + "</td>" //利息
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest4()
                + "</font></td>"
                + "</tr>"
				+ "        "
				+ "<tr>"
				+ "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
				+ "<td class=\"td-bottom\" align=\"center\">&nbsp;</td>"
				+ "</tr>"
				+ "        "
				+ "<tr>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\" align=\"center\">&nbsp;</td>"
                + "</tr>"
                
                
				+ "        "
				+ "<tr>"
				+ "<td class=\"td-rightbottom\" colspan=\"20\"><font style=\"font-size:13px\">合计（大写）："
				+ info.getTotalInterestChinese()
				+ "</font></td>"
				+ "<td class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
				+"￥"+ info.getTotalInterest()
				+ "</font></td>"
				+ "</tr>"
				+ "        "
				+ "<tr>"
				+ "<td colspan=\"44\">"
				+ "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				+ "<tr>"
				+ "<td width=\"50%\"><font style=\"font-size:13px\">备注：存单号："
				+ info.getDepositBillNo()
				+ "</font></td>"
				+ "<td width=\"50%\">&nbsp;"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td width=\"100%\" colspan=\"2\"><font style=\"font-size:13px\">上列利息，已照付你单位第（"
				+ info.getInterestAccountNo()
				+ "）号账户</font></td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "      "
				+ "</table>"
				+ "</td>"
				+ "<td width=\"20\" align=\"center\">第<br>"
				+ "一<br>"
				+ "联<br>"
				+ "<br>"
				+ "利<br>"
				+ "息<br>"
				+ "回<br>"
				+ "单<br>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</body>"
				+ "  ' ); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 *全打模版；存款利息计付通知单
	 * 第二联 记账凭证
	 * throws Exception
	 */
	public static void showDepositInterest2(ShowDepositInterestInfo info, JspWriter out) throws Exception
	{
		try
		{
            //  +"<html>"
            // +"<head>"
            ////noShowPrintHeadAndFooter(out
            out.print(
                " <Script Language=\"JavaScript\"> document.write(' "
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
                    + "<link rel=\"stylesheet\" href=\"../library/template.css\" type=\"text/css\">"
                    + "<style type=\"text/css\">"
                    + "<!--"
                    + ".table1 {  border: 2px solid #000000}"
                    + ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
                    + ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
                    + ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-lefttopright {  border-color: black black #000000; border-style: solid; border-top-width: 3px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 3px}"
                    + ".td-topright {  border-color: black black #000000; border-style: solid; border-top-width: 3px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px}"
                    + ".f9 {  font-size: 10px}"
                    + "-->"
                    + "</style>"
                    + "<body text=\"#000000\" bgcolor=\"#FFFFFF\">"
                    + "<table width=\"640\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                    + "  "
                    + "<tr> "
                    + "    "
                    + "<td width=\"150\">&nbsp;</td>"
                    + "<td width=\"300\" align=\"center\"><strong><font style=\"font-size:16px\">"
                    + Env.getClientName()
                    + "</font><br>"
                    + "<FONT face=\"Arial Black\"><font style=\"font-size:20px\">存款利息计付通知单</font></FONT></strong></td>"
                    + "<td width=\"180\">");
            //out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
            out.println(
                    "</TD>"
                    + "</tr>"
                    + "<br>"
                    + "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"640\">"
                    + "  "
                    + "<tr>"
                    + "<td width=\"185\" align=\"left\"></td>"
                    + "<td width=\"35\" align=\"right\">&nbsp;</td>"
                    + "<td width=\"152\"> "
                    + "</td>"
                    + "<td width=\"74\">&nbsp;</td>"
                    + "<td width=\"184\" align=\"right\">"
                    + "&nbsp;</td>"
                    + "<td width=\"10\" align=\"right\">&nbsp;</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td width=\"185\" align=\"left\"></td>"
                   
                    + " "
                    + "    "
                    + "<td width=\"230\" colspan=\"2\"> "
                    + "         <font style=\"font-size:13px\">"
                    + " 打印日期: "
                    + info.getYear()
                    + " 年 "
                    + info.getMonth()
                    + " 月 "
                    + info.getDay()
                    + " 日</font></td>"
                    + "<td width=\"254\" align=\"center\" colspan=\"3\" ><font style=\"font-size:13px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易编号："
                    + info.getTransNo()
                    + "</font></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td width=\"200\" align=\"left\" nowrap><font style=\"font-size:13px\">单位名称：<U>"
                    + info.getClientName()
                    + "</U></font></td>"
                    + "<td width=\"35\" align=\"right\">&nbsp;</td>"
                    + " "
                    + "    "
                    + "<td width=\"152\"> "
                    + "      "
                    + "<font style=\"font-size:13px\">账号：<U>"
                    + info.getAccountNo()
                    + "</U></font></td>"
                    + "<td width=\"74\">&nbsp;</td>"
                    + "<td width=\"170\" align=\"right\"><font style=\"font-size:13px\">存款种类：<U>"
                    + info.getDepositTypeName()
                    + "</U></font></td>"
                    + "<td width=\"10\" align=\"right\">&nbsp;</td>"
                    + "</tr>"
                    + "</table>"
                    + "<table width=\"640\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
                    + "  "
                    + "<tr>"
                    + "    "
                    + "<td>"
                    + "      "
                    + "<table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                    + "<tr>"
                    + "<td class=\"td-rightbottom\" colspan=\"12\" align=\"center\"><font style=\"font-size:13px\">本金</font></td>"
                    + "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">起息日期</font></td>"
                    + "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">截至日期</font></td>"
                    + "<td class=\"td-rightbottom\" width=\"6%\"  align=\"center\"><font style=\"font-size:13px\">天数</font></td>"
                    + "<td class=\"td-rightbottom\"  width=\"5%\" align=\"center\"><font style=\"font-size:13px\">利率%</font></td>"
                    + "<td class=\"td-bottom\" width=\"8%\" colspan=\"10\" align=\"center\"><font style=\"font-size:13px\">利息</font></td>"
                    + "</tr>"
                    + "        "
                 + "<tr>" //显示本金<!--------1---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                
                +info.getAmount1()
                + "</font></td>"
                //显示其息日期
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear1()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth1()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay1()
                + "</font></td>" //显示终息日期
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear1()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth1()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay1()
                + "</font></td>" //天数
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber1() + "</font></td>" //利率
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate1() + "</td>" //利息
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest1()
                + "</font></td>"
                + "</tr>"
                
                + "        "
                + "        "
                + "<tr>" //显示本金<!--------2---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount2()
                + "</font></td>"
                //显示其息日期
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay2()
                + "</font></td>" //显示终息日期
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay2()
                + "</font></td>" //天数
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber2() + "</font></td>" //利率
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate2() + "</td>" //利息
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                +info.getInterest2()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "        "
                + "<tr>" //显示本金<!--------3---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount3()
                + "</font></td>"
                //显示其息日期
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay3()
                + "</font></td>" //显示终息日期
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay3()
                + "</font></td>" //天数
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber3() + "</font></td>" //利率
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate3() + "</td>" //利息
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest3()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "        "
                + "<tr>" //显示本金<!--------4---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount4()
                + "</font></td>"
                //显示其息日期
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay4()
                + "</font></td>" //显示终息日期
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay4()
                + "</font></td>" //天数
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber4() + "</font></td>" //利率
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate4() + "</td>" //利息
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest4()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "<tr>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\" align=\"center\">&nbsp;</td>"
                + "</tr>"
                + "        "
                + "<tr>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\" align=\"center\">&nbsp;</td>"
                + "</tr>"
                
                
                + "        "
                + "<tr>"
                + "<td class=\"td-rightbottom\" colspan=\"20\"><font style=\"font-size:13px\">合计（大写）："
                + info.getTotalInterestChinese()
                + "</font></td>"
                + "<td class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                +"￥"+ info.getTotalInterest()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "<tr>"
                + "<td colspan=\"44\">"
                + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                + "<tr>"
                + "<td width=\"50%\"><font style=\"font-size:13px\">备注：存单号："
                + info.getDepositBillNo()
                + "</font></td>"
                + "<td width=\"50%\">&nbsp;"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td width=\"100%\" colspan=\"2\"><font style=\"font-size:13px\">上列利息，已照付你单位第（"
                + info.getInterestAccountNo()
                + "）号账户</font></td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "      "
                + "</table>"
                + "</td>"
                + "<td width=\"20\" align=\"center\">第<br>"
                + "二<br>"
                + "联<br>"
                + "<br>"
                + "记<br>"
                + "账<br>"
                + "凭<br>"
                + "证<br>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</body>"
                + "  ' ); </SCRIPT>  ");
        }
		catch (Exception e)
		{
		}
	}
    /**
     *全打模版；存款利息计付通知单
     * 第三联 利息凭证
     * throws Exception
     */
    public static void showDepositInterest3(ShowDepositInterestInfo info, JspWriter out) throws Exception
    {
        try
        {
            //  +"<html>"
            // +"<head>"
            ////noShowPrintHeadAndFooter(out
            out.print(
                " <Script Language=\"JavaScript\"> document.write(' "
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">"
                    + "<link rel=\"stylesheet\" href=\"../library/template.css\" type=\"text/css\">"
                    + "<style type=\"text/css\">"
                    + "<!--"
                    + ".table1 {  border: 2px solid #000000}"
                    + ".td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}"
                    + ".td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}"
                    + ".td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}"
                    + ".td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}"
                    + ".td-lefttopright {  border-color: black black #000000; border-style: solid; border-top-width: 3px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 3px}"
                    + ".td-topright {  border-color: black black #000000; border-style: solid; border-top-width: 3px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px}"
                    + ".f9 {  font-size: 10px}"
                    + "-->"
                    + "</style>"
                    + "<body text=\"#000000\" bgcolor=\"#FFFFFF\">"
                    + "<table width=\"640\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                    + "  "
                    + "<tr> "
                    + "    "
                    + "<td width=\"150\">&nbsp;</td>"
                    + "<td width=\"300\" align=\"center\"><strong><font style=\"font-size:16px\">"
                    + Env.getClientName()
                    + "</font><br>"
                    + "<FONT face=\"Arial Black\"><font style=\"font-size:20px\">存款利息计付通知单</font></FONT></strong></td>"
                    + "<td width=\"180\">");
            //out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
            out.println(
                    "</TD>"
                    + "</tr>"
                    + "<br>"
                    + "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"640\">"
                    + "  "
                    + "<tr>"
                    + "<td width=\"185\" align=\"left\"></td>"
                    + "<td width=\"35\" align=\"right\">&nbsp;</td>"
                    + "<td width=\"152\"> "
                    + "</td>"
                    + "<td width=\"74\">&nbsp;</td>"
                    + "<td width=\"184\" align=\"right\">"
                    + "&nbsp;</td>"
                    + "<td width=\"10\" align=\"right\">&nbsp;</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td width=\"185\" align=\"left\"></td>"
                   
                    + " "
                    + "    "
                    + "<td width=\"230\" colspan=\"2\"> "
                    + "         <font style=\"font-size:13px\">"
                    + " 打印日期: "
                    + info.getYear()
                    + " 年 "
                    + info.getMonth()
                    + " 月 "
                    + info.getDay()
                    + " 日</font></td>"
                    + "<td width=\"254\" align=\"center\" colspan=\"3\" ><font style=\"font-size:13px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易编号："
                    + info.getTransNo()
                    + "</font></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td width=\"200\" align=\"left\" nowrap><font style=\"font-size:13px\">单位名称：<U>"
                    + info.getClientName()
                    + "</U></font></td>"
                    + "<td width=\"35\" align=\"right\">&nbsp;</td>"
                    + " "
                    + "    "
                    + "<td width=\"152\"> "
                    + "      "
                    + "<font style=\"font-size:13px\">账号：<U>"
                    + info.getAccountNo()
                    + "</U></font></td>"
                    + "<td width=\"74\">&nbsp;</td>"
                    + "<td width=\"170\" align=\"right\"><font style=\"font-size:13px\">存款种类：<U>"
                    + info.getDepositTypeName()
                    + "</U></font></td>"
                    + "<td width=\"10\" align=\"right\">&nbsp;</td>"
                    + "</tr>"
                    + "</table>"
                    + "<table width=\"640\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
                    + "  "
                    + "<tr>"
                    + "    "
                    + "<td>"
                    + "      "
                    + "<table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                    + "<tr>"
                    + "<td class=\"td-rightbottom\" colspan=\"12\" align=\"center\"><font style=\"font-size:13px\">本金</font></td>"
                    + "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">起息日期</font></td>"
                    + "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">截至日期</font></td>"
                    + "<td class=\"td-rightbottom\" width=\"6%\"  align=\"center\"><font style=\"font-size:13px\">天数</font></td>"
                    + "<td class=\"td-rightbottom\"  width=\"5%\" align=\"center\"><font style=\"font-size:13px\">利率%</font></td>"
                    + "<td class=\"td-bottom\" width=\"8%\" colspan=\"10\" align=\"center\"><font style=\"font-size:13px\">利息</font></td>"
                    + "</tr>"
                    + "        "
                  + "<tr>" //显示本金<!--------1---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                
                +info.getAmount1()
                + "</font></td>"
                //显示其息日期
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear1()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth1()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay1()
                + "</font></td>" //显示终息日期
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear1()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth1()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay1()
                + "</font></td>" //天数
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber1() + "</font></td>" //利率
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate1() + "</td>" //利息
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest1()
                + "</font></td>"
                + "</tr>"
                
                + "        "
                + "        "
                + "<tr>" //显示本金<!--------2---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount2()
                + "</font></td>"
                //显示其息日期
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay2()
                + "</font></td>" //显示终息日期
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay2()
                + "</font></td>" //天数
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber2() + "</font></td>" //利率
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate2() + "</td>" //利息
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                +info.getInterest2()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "        "
                + "<tr>" //显示本金<!--------3---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount3()
                + "</font></td>"
                //显示其息日期
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay3()
                + "</font></td>" //显示终息日期
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay3()
                + "</font></td>" //天数
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber3() + "</font></td>" //利率
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate3() + "</td>" //利息
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest3()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "        "
                + "<tr>" //显示本金<!--------4---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount4()
                + "</font></td>"
                //显示其息日期
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay4()
                + "</font></td>" //显示终息日期
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay4()
                + "</font></td>" //天数
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber4() + "</font></td>" //利率
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate4() + "</td>" //利息
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest4()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "<tr>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\" align=\"center\">&nbsp;</td>"
                + "</tr>"
                + "        "
                + "<tr>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-rightbottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td class=\"td-rightbottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\"   align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td  class=\"td-bottom\"  align=\"center\">&nbsp;</td>"
                + "<td class=\"td-bottom\" align=\"center\">&nbsp;</td>"
                + "</tr>"
                
                
                + "        "
                + "<tr>"
                + "<td class=\"td-rightbottom\" colspan=\"20\"><font style=\"font-size:13px\">合计（大写）："
                + info.getTotalInterestChinese()
                + "</font></td>"
                + "<td class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                +"￥"+ info.getTotalInterest()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "<tr>"
                + "<td colspan=\"44\">"
                + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                + "<tr>"
                + "<td width=\"50%\"><font style=\"font-size:13px\">备注：存单号："
                + info.getDepositBillNo()
                + "</font></td>"
                + "<td width=\"50%\">&nbsp;"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td width=\"100%\" colspan=\"2\"><font style=\"font-size:13px\">上列利息，已照付你单位第（"
                + info.getInterestAccountNo()
                + "）号账户</font></td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "      "
                + "</table>"
                + "</td>"
                + "<td width=\"20\" align=\"center\">第<br>"
                + "三<br>"
                + "联<br>"
                + "<br>"
                + "利<br>"
                + "息<br>"
                + "凭<br>"
                + "证<br>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</body>"
                + "  ' ); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }
    /**
     * 打印手续费收费清单
     * @param list
     * @param out
     * @throws Exception
     */
    public static void showCommissionBill(List list, JspWriter out) throws Exception
    {
        try
        {
            //out.println(" <html> " +
            //" <head> " );
            ////noShowPrintHeadAndFooter(out);
            out.print(
                "<Script Language=\"JavaScript\"> document.write(' "
                    + " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
                    + " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
                    + "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
                    + " <table width=\"87%\" border=\"0\" cellspacing=\"0\" cellpadding=\"4\" class=\"In1-table1\" height=\"60\">"); 
            	if(list != null && list.size() > 0){
            		out.print("<TR>"
            		+"<TD  height=20 width=\"14%\" class=\"In1-td-rightbottom\" align=\"center\">账户编号</TD>"
            		+"<TD  height=20 width=\"13%\" class=\"In1-td-rightbottom\" align=\"center\">账户名称</TD>"
            		+"<TD  height=20 width=\"11%\" class=\"In1-td-rightbottom\" align=\"center\">交易类型</TD>"
            		+"<TD  height=20 width=\"14%\" class=\"In1-td-rightbottom\" align=\"center\">交易号</TD>"
            		+"<TD  height=20 width=\"13%\" class=\"In1-td-rightbottom\" align=\"center\">交易金额</TD>"
            		+"<TD  height=20 width=\"12%\" class=\"In1-td-rightbottom\" align=\"center\">手续费</TD>"
            		+"<TD  height=20 width=\"12%\" class=\"In1-td-rightbottom\" align=\"center\">折扣率（%）</TD>"
            		+"<TD  height=20 width=\"12%\" class=\"In1-td-rightbottom\" align=\"center\">实收手续费</TD></TR>");
            	
            		Iterator it = list.iterator();
            		while(it.hasNext()){
            			TransCommissionResultInfo info = (TransCommissionResultInfo)it.next();
            			out.print("<TR>" 
            					+"<TD  height=20 width=\"14%\" class=\"In1-td-rightbottom\" align=\"center\">"
            					+NameRef.getAccountNoByID(info.getAccountId())
            					+"</TD><TD  height=20 width=\"13%\" class=\"In1-td-rightbottom\" align=\"center\">"
            					+NameRef.getClientNameByAccountID(info.getAccountId())
            					+"</TD><TD  height=20 width=\"11%\" class=\"In1-td-rightbottom\" align=\"center\">"
            					+SETTConstant.TransactionType.getName(info.getTransactionTypeId())
            					+"</TD><TD  height=20 width=\"14%\" class=\"In1-td-rightbottom\" align=\"center\">"
            					+DataFormat.formatStringForPrint(info.getTransNo())
            					+"</TD><TD  height=20 width=\"13%\" class=\"In1-td-rightbottom\" align=\"right\">"
            					+DataFormat.formatListAmount(info.getAmount())
            					+"</TD><TD  height=20 width=\"12%\" class=\"In1-td-rightbottom\" align=\"right\">"
            					+DataFormat.formatListAmount(info.getCommissionAmount())
            					+"</TD><TD  height=20 width=\"12%\" class=\"In1-td-rightbottom\" align=\"right\">"
            					+DataFormat.formatListAmount(info.getRebate())
            					+"</TD><TD  height=20 width=\"12%\" class=\"In1-td-rightbottom\" align=\"right\">"
            					+DataFormat.formatListAmount(info.getRebatecommissionAmount())
            					+"</TD></TR>");
            		}
            	}
            	out.println("</table>"
            				+ "</BODY> "
            				+ " '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }
    
    /**
     * 显示手续费凭证
     * 第一联 此联银行盖章后退回单位
     */
    public static void showCommission1(TransCommissionResultInfo info, JspWriter out) throws Exception
    {
        try
        {
            //out.println(" <html> " +
            //" <head> " );
            ////noShowPrintHeadAndFooter(out);
            out.print(
                "<Script Language=\"JavaScript\"> document.write(' "
                    + " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
                    + " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
                    + "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
                    + " <TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
                    + "<TR>     <TD width=\"90\">&nbsp; </TD><TD  width=\"50\">&nbsp;   </TD><TD width=\"310\">&nbsp;   </TD><TD width=\"70\">&nbsp;    </TD><TD width=\"110\">&nbsp;   </TD></TR>  "
                    + "<TR>     "
                    + "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;  </TD> "
                    + "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">"
                    + DataFormat.formatStringForPrint(Env.getClientName())
                    + "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
                    + "      手续费传票</font></font></strong> 　</strong></TD>"
                    + "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");

                //out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
            out.println(
                "</TD>"
                    + "</TR> "
                    + "<TR>     <TD width=\"90\">&nbsp; </TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;    </TD><TD width=\"110\">&nbsp;   </TD></TR>  "
                    + " </TABLE> "
                    + " <TABLE width=\"600\"> "
                    + " <TR> "
                    + "     <TD width=\"10%\"> "
                    + "     </TD> "
                    + "     <TD align=\"right\" width=\"50%\">"
                    + " 打印日期: "
                    + info.getYear()
                    + " 年 "
                    + info.getMonth()
                    + " 月 "
                    + info.getDay()
                    + " 日 "
                    + " </TD> "
                    + " <TD width=\"40%\" align=\"right\">交易编号："
                    + DataFormat.formatStringForPrint(info.getTransNo())
                    + " </TD> "
                    + "</TR> "
                    + " </TABLE>"
                    
                    +"<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "  
                    +"  <TR>  " 
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><font face=\"楷体_GB2312\">单位名称</font></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\"  align=\"left\" class=\"In1-td-bottom\">"
                    +NameRef.getClientNameByAccountID(info.getAccountId())
                    +"&nbsp; </TD> " 
                    +"  </TR> "  
                    +"  <TR>  "
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>  " 
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" align=\"left\" nowrap class=\"In1-td-bottom\">"
                    +NameRef.getAccountNoByID(info.getAccountId())
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <td align=\"center\" height=\"44\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">收费项目</font></b>  " 
                    +"    </td> " 
                    +"    <td height=\"44\" colspan=\"2\" align=\"left\" class=\"In1-td-bottom\">手续费</td> "  
                    +"  </TR> " 
                    +"  <TR>  " 
                    +"    <TD colspan=\"1\" align=\"center\" class=\"In1-td-rightbottom\" height=\"48\" nowrap><B><FONT face=\"楷体_GB2312\">人民币<BR> "  
                    +"      (大写)</FONT></B> </TD> "  
                    +"    <TD width=\"199\" class=\"In1-td-rightbottom\" ><B>&nbsp;"
                    +ChineseCurrency.showChinese(DataFormat.formatAmount(info.getCommissionAmount()), info.getCurrencyId())
                    +"</B> </TD> " 
                    +"    <TD width=\"199\" align=\"right\" nowrap class=\"In1-td-bottom\"><B>&nbsp;￥"
                    +DataFormat.formatDisabledAmount( info.getCommissionAmount() , 2 )
                    +"</B>  "  
                    +"    </TD> " 
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <TD colspan=\"1\" align=\"center\" height=\"55\" class=\"In1-td-right\" nowrap><B><FONT face=\"楷体_GB2312\">摘　　要</FONT></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" >"
                    +"记账日期："
                    +DataFormat.formatDate(info.getExecuteDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"起始日期："
                    +DataFormat.formatDate(info.getMinDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"终止日期："
                    +DataFormat.formatDate(info.getMaxDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"笔数："
                    +info.getCountNum()
                    +"笔"
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"</TABLE> " 
                    +"<TABLE width=\"30\" border=\"0\">      <TR>          <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>付<BR>款<BR>单<BR>位<BR>回<BR>单</FONT>          </TD>      </TR>  </TABLE>  "  

                    + "</BODY> "
                    + " '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }
    
    /**
     * 显示手续费凭证
     * 第二联 此联银行作付出传票
     */
    public static void showCommission2(TransCommissionResultInfo info, JspWriter out) throws Exception
    {
        try
        {
            //out.println(" <html> " +
            //" <head> " );
            ////noShowPrintHeadAndFooter(out);
            out.print(
                "<Script Language=\"JavaScript\"> document.write(' "
                    + " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
                    + " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
                    + "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
                    + " <TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
                    + "<TR>     <TD width=\"90\">&nbsp; </TD><TD  width=\"50\">&nbsp;   </TD><TD width=\"310\">&nbsp;   </TD><TD width=\"70\">&nbsp;    </TD><TD width=\"110\">&nbsp;   </TD></TR>  "
                    + "<TR>     "
                    + "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;  </TD> "
                    + "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">"
                    + DataFormat.formatStringForPrint(Env.getClientName())
                    + "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
                    + "      手续费传票</font></font></strong> 　</strong></TD>"
                    + "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");

                //out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
            out.println(
                "</TD>"
                    + "</TR> "
                    + "<TR>     <TD width=\"90\">&nbsp; </TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;    </TD><TD width=\"110\">&nbsp;   </TD></TR>  "
                    + " </TABLE> "
                    + " <TABLE width=\"600\"> "
                    + " <TR> "
                    + "     <TD width=\"10%\"> "
                    + "     </TD> "
                    + "     <TD align=\"right\" width=\"50%\">"
                    + " 打印日期: "
                    + info.getYear()
                    + " 年 "
                    + info.getMonth()
                    + " 月 "
                    + info.getDay()
                    + " 日 "
                    + " </TD> "
                    + " <TD width=\"40%\" align=\"right\">交易编号："
                    + DataFormat.formatStringForPrint(info.getTransNo())
                    + " </TD> "
                    + "</TR> "
                    +"<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "  
                    +"  <TR>  " 
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><font face=\"楷体_GB2312\">单位名称</font></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\"  align=\"left\" class=\"In1-td-bottom\">"
                    +NameRef.getClientNameByAccountID(info.getAccountId())
                    +"&nbsp; </TD> " 
                    +"  </TR> "  
                    +"  <TR>  "
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>  " 
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" align=\"left\" nowrap class=\"In1-td-bottom\">"
                    +NameRef.getAccountNoByID(info.getAccountId())
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <td align=\"center\" height=\"44\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">收费项目</font></b>  " 
                    +"    </td> " 
                    +"    <td height=\"44\" colspan=\"2\" align=\"left\" class=\"In1-td-bottom\">手续费</td> "  
                    +"  </TR> " 
                    +"  <TR>  " 
                    +"    <TD colspan=\"1\" align=\"center\" class=\"In1-td-rightbottom\" height=\"48\" nowrap><B><FONT face=\"楷体_GB2312\">人民币<BR> "  
                    +"      (大写)</FONT></B> </TD> "  
                    +"    <TD width=\"199\" class=\"In1-td-rightbottom\" ><B>&nbsp;"
                    +ChineseCurrency.showChinese(DataFormat.formatAmount(info.getCommissionAmount()), info.getCurrencyId())
                    +"</B> </TD> " 
                    +"    <TD width=\"199\" align=\"right\" nowrap class=\"In1-td-bottom\"><B>&nbsp;￥"
                    +DataFormat.formatDisabledAmount( info.getCommissionAmount() , 2 )
                    +"</B>  "  
                    +"    </TD> " 
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <TD colspan=\"1\" align=\"center\" height=\"55\" class=\"In1-td-right\" nowrap><B><FONT face=\"楷体_GB2312\">摘　　要</FONT></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" >"
                    +"记账日期："
                    +DataFormat.formatDate(info.getExecuteDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"起始日期："
                    +DataFormat.formatDate(info.getMinDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"终止日期："
                    +DataFormat.formatDate(info.getMaxDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"笔数："
                    +info.getCountNum()
                    +"笔"
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"</TABLE> " 
                    +"<TABLE width=\"30\" border=\"0\">      <TR>          <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>记<BR>账<BR>凭<BR>证</FONT>          </TD>      </TR>  </TABLE>  "  

                    + "</BODY> "
                    + " '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }
    
    /**
     * 显示手续费凭证
     * 第三联 此联银行作营业收入传票
     */
    public static void showCommission3(TransCommissionResultInfo info, JspWriter out) throws Exception
    {
        try
        {
            //out.println(" <html> " +
            //" <head> " );
            ////noShowPrintHeadAndFooter(out);
            out.print(
                "<Script Language=\"JavaScript\"> document.write(' "
                    + " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
                    + " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
                    + "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
                    + " <TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
                    + "<TR>     <TD width=\"90\">&nbsp; </TD><TD  width=\"50\">&nbsp;   </TD><TD width=\"310\">&nbsp;   </TD><TD width=\"70\">&nbsp;    </TD><TD width=\"110\">&nbsp;   </TD></TR>  "
                    + "<TR>     "
                    + "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;  </TD> "
                    + "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">"
                    + DataFormat.formatStringForPrint(Env.getClientName())
                    + "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
                    + "      邮电费、手续费、收费凭证</font></font></strong> 　</strong></TD>"
                    + "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");

                //out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
            out.println(
                "</TD>"
                    + "</TR> "
                    + "<TR>     <TD width=\"90\">&nbsp; </TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;    </TD><TD width=\"110\">&nbsp;   </TD></TR>  "
                    + " </TABLE> "
                    + " <TABLE width=\"600\"> "
                    + " <TR> "
                    + "     <TD width=\"10%\"> "
                    + "     </TD> "
                    + "     <TD align=\"right\" width=\"50%\">"
                    + " 打印日期: "
                    + info.getYear()
                    + " 年 "
                    + info.getMonth()
                    + " 月 "
                    + info.getDay()
                    + " 日 "
                    + " </TD> "
                    + " <TD width=\"40%\" align=\"right\">交易编号："
                    + DataFormat.formatStringForPrint(info.getTransNo())
                    + " </TD> "
                    + "</TR> "
                    + " </TABLE>"
                    
                    
                    +"<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "  
                    +"  <TR>  " 
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><font face=\"楷体_GB2312\">单位名称</font></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\"  align=\"left\" class=\"In1-td-bottom\">"
                    +NameRef.getClientNameByAccountID(info.getAccountId())
                    +"&nbsp; </TD> " 
                    +"  </TR> "  
                    +"  <TR>  "
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>  " 
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" align=\"left\" nowrap class=\"In1-td-bottom\">"
                    +NameRef.getAccountNoByID(info.getAccountId())
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <td align=\"center\" height=\"44\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">收费项目</font></b>  " 
                    +"    </td> " 
                    +"    <td height=\"44\" colspan=\"2\" align=\"left\" class=\"In1-td-bottom\">手续费</td> "  
                    +"  </TR> " 
                    +"  <TR>  " 
                    +"    <TD colspan=\"1\" align=\"center\" class=\"In1-td-rightbottom\" height=\"48\" nowrap><B><FONT face=\"楷体_GB2312\">人民币<BR> "  
                    +"      (大写)</FONT></B> </TD> "  
                    +"    <TD width=\"199\" class=\"In1-td-rightbottom\" ><B>&nbsp;"
                    +ChineseCurrency.showChinese(DataFormat.formatAmount(info.getCommissionAmount()), info.getCurrencyId())
                    +"</B> </TD> " 
                    +"    <TD width=\"199\" align=\"right\" nowrap class=\"In1-td-bottom\"><B>&nbsp;￥"
                    +DataFormat.formatDisabledAmount( info.getCommissionAmount() , 2 )
                    +"</B>  "  
                    +"    </TD> " 
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <TD colspan=\"1\" align=\"center\" height=\"55\" class=\"In1-td-right\" nowrap><B><FONT face=\"楷体_GB2312\">摘　　要</FONT></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" >"
                    +"记账日期："
                    +DataFormat.formatDate(info.getExecuteDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"起始日期："
                    +DataFormat.formatDate(info.getMinDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"终止日期："
                    +DataFormat.formatDate(info.getMaxDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"笔数："
                    +info.getCountNum()
                    +"笔"
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"</TABLE> " 
                    +"<TABLE width=\"30\" border=\"0\">      <TR>          <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>三<BR>联<BR><BR>此<BR>联<BR>银<BR>行<BR>作<BR>营<BR>业<BR>收<BR>入<BR>传<BR>票</FONT>          </TD>      </TR>  </TABLE>  "  


                    + "</BODY> "
                    + " '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }

	/**
	 *显示套打模版
	 *strCode数组内所存sCode顺序需要和字符串变量的顺序一致
	 *
	 */
	public static void showTemplate(
		JspWriter out,
		String[] strCode,
		long lTemplateID,
		String str1,
		String str2,
		String str3,
		String str4,
		String str5,
		String str6,
		String str7,
		String str8,
		String str9,
		String str10,
		String str11,
		String str12,
		String str13,
		String str14,
		String str15,
		String str16,
		String str17,
		String str18,
		String str19,
		String str20,
		String str21,
		String str22,
		String str23,
		String str24,
		String str25,
		String str26,
		String str27,
		String str28)
		throws Exception
	{
		try
		{
			//创建EJB
			IPrintTemplateSetting printTemplateSetting = new IPrintTemplateSetting();
			Collection c = printTemplateSetting.findPrintOptionDetailsByTemplateID(lTemplateID);
			//用常量表示
			PrintOptionInfo printOptionInfo = new PrintOptionInfo();
			int lparmNumber = 0;
			out.println("<html>" + "<head>");
			////noShowPrintHeadAndFooter(out);
			out.println(
				"<title>打印模版设置</title>"
					+ "<STYLE type=text/css>"
					+ "A.map:visited {	 COLOR: #FF6600;  TEXT-DECORATION: none}"
					+ "A.map:hover {	COLOR: #FFCC00;  TEXT-DECORATION: underline}"
					+ "A.g:link {	COLOR: #666600;  TEXT-DECORATION: none}"
					+ "A.g:visited {	 COLOR: #666600;  TEXT-DECORATION: none}"
					+ "A.g:hover {	COLOR: #FF9900;  TEXT-DECORATION: underline}"
					+ "A:link {	COLOR: #000000;  TEXT-DECORATION: none}"
					+ "A:visited {	COLOR: #000000;  TEXT-DECORATION: none}"
					+ "A:hover {	COLOR: #CC0000;  TEXT-DECORATION: underline}"
					+ "A.title:link {	COLOR: #ffffff;  TEXT-DECORATION: none}"
					+ "A.title:visited {	COLOR: #ffffff;  TEXT-DECORATION: none}"
					+ "A.title:hover {	 COLOR: #ffffff;  TEXT-DECORATION: underline}"
					+ "A.line:link {	COLOR: #3300FF;  TEXT-DECORATION: underline}"
					+ "A.line:visited {	COLOR: #3300FF;  TEXT-DECORATION: underline}"
					+ "A.line:hover {	 COLOR: #0066FF;  TEXT-DECORATION: underline}"
					+ "A.line1:link {	COLOR: #666666;  TEXT-DECORATION: underline}"
					+ "A.line1:visited {	COLOR: #666666;  TEXT-DECORATION: underline}"
					+ "A.line1:hover {	 COLOR: #CC0000;  TEXT-DECORATION: underline}"
					+ " ..mAmountPrint0{ letter-spacing: 0.0em; } "
					+ " ..mAmountPrint1{ letter-spacing: 0.1em; } "
					+ " ..mAmountPrint2{ letter-spacing: 0.2em; } "
					+ " ..mAmountPrint3{ letter-spacing: 0.3em; } "
					+ " ..mAmountPrint4{ letter-spacing: 0.4em; } "
					+ " ..mAmountPrint5{ letter-spacing: 0.5em; } "
					+ " ..mAmountPrint6{ letter-spacing: 0.6em; } "
					+ " ..mAmountPrint7{ letter-spacing: 0.7em; } "
					+ " ..mAmountPrint8{ letter-spacing: 0.8em; } "
					+ " ..mAmountPrint9{ letter-spacing: 0.9em; } "
					+ " ..mAmountPrint10{ letter-spacing: 1.0em; } "
					+ " ..mAmountPrint11 letter-spacing: 1.1em; } "
					+ " ..mAmountPrint12{ letter-spacing: 1.2em; } "
					+ " ..mAmountPrint13{ letter-spacing: 1.3em; } "
					+ " ..mAmountPrint14{ letter-spacing: 1.4em; } "
					+ " ..mAmountPrint15{ letter-spacing: 1.5em; } "
					+ "</STYLE>"
					+ "</head>"
					+ "<body >");
			if (c != null)
			{
				Iterator it = c.iterator();
				while (it.hasNext())
				{
					printOptionInfo = (PrintOptionInfo) it.next();
					String strDisplayDetails = "";
					for (int i = 1; i <= strCode.length; i++)
					{
						if (strCode[i - 1].equals(printOptionInfo.m_strCode))
						{
							lparmNumber = i;
							break;
						}
						if (i == strCode.length)
							lparmNumber = 0;
					}
					switch (lparmNumber)
					{
						case 1 :
							strDisplayDetails = str1;
							break;
						case 2 :
							strDisplayDetails = str2;
							break;
						case 3 :
							strDisplayDetails = str3;
							break;
						case 4 :
							strDisplayDetails = str4;
							break;
						case 5 :
							strDisplayDetails = str5;
							break;
						case 6 :
							strDisplayDetails = str6;
							break;
						case 7 :
							strDisplayDetails = str7;
							break;
						case 8 :
							strDisplayDetails = str8;
							break;
						case 9 :
							strDisplayDetails = str9;
							break;
						case 10 :
							strDisplayDetails = str10;
							break;
						case 11 :
							strDisplayDetails = str11;
							break;
						case 12 :
							strDisplayDetails = str12;
							break;
						case 13 :
							strDisplayDetails = str13;
							break;
						case 14 :
							strDisplayDetails = str14;
							break;
						case 15 :
							strDisplayDetails = str15;
							break;
						case 16 :
							strDisplayDetails = str16;
							break;
						case 17 :
							strDisplayDetails = str17;
							break;
						case 18 :
							strDisplayDetails = str18;
							break;
						case 19 :
							strDisplayDetails = str19;
							break;
						case 20 :
							strDisplayDetails = str20;
							break;
						case 21 :
							strDisplayDetails = str21;
							break;
						case 22 :
							strDisplayDetails = str22;
							break;
						case 23 :
							strDisplayDetails = str23;
							break;
						case 24 :
							strDisplayDetails = str24;
							break;
						case 25 :
							strDisplayDetails = str25;
							break;
						case 26 :
							strDisplayDetails = str26;
							break;
						case 27 :
							strDisplayDetails = str27;
							break;
						case 28 :
							strDisplayDetails = str28;
							break;
					}
					if (printOptionInfo.m_lTypeID > 0)
					{
					    if(!"".equals(strDisplayDetails))
					    {
					        String strDisplayDetailsTemp = "";
					        strDisplayDetailsTemp = strDisplayDetails.substring(0, 1);
					        for (int i = 1; i < strDisplayDetails.length(); i++)
					        {
					            if (!(strDisplayDetails.substring(i, i + 1).equals(".") || strDisplayDetails.substring(i, i + 1).equals(",")))
					                strDisplayDetailsTemp += strDisplayDetails.substring(i, i + 1);
					        }
					        strDisplayDetails = strDisplayDetailsTemp;
					    }
					}
					System.out.println("strDisplayDetails::::::::" + strDisplayDetails);
					double dLeft = printOptionInfo.m_dDetailsLeft + printOptionInfo.m_dTemplateLeft;
					double dTop = printOptionInfo.m_dDetailsTop + printOptionInfo.m_dTemplateTop;
					out.println("<div id=" + printOptionInfo.m_lTemplateDetailsID + "  style=\"position:absolute; left:" + dLeft + "px;  top:" + dTop + "px\"  > ");
					if (printOptionInfo.m_lFiledWidth > 0)
					{
						out.print("<table width=\"");
						out.print(printOptionInfo.m_lFiledWidth);
						out.println("\">");
						out.println("<tr>");
						if (printOptionInfo.m_lTypeID > 0)
							out.println("<td  align=\"right\">");
						else
							out.println("<td>");
					}
					if (printOptionInfo.m_lIsBold == 1)
						out.println("<b>");
					if (printOptionInfo.m_lIsItalic == 1)
						out.println("<i>");
					out.println(
						"<P class=\"mAmountPrint"
							+ printOptionInfo.m_lTypeID
							+ "\" ><font style=\"font-size:"
							+ printOptionInfo.m_lSize
							+ "px\""
							+ "face="
							+ printOptionInfo.m_strFont
							+ ">"
							+ strDisplayDetails
							+ "</font></P>");
					if (printOptionInfo.m_lIsItalic == 1)
						out.println("</i>");
					if (printOptionInfo.m_lIsBold == 1)
						out.println("</b>");
					if (printOptionInfo.m_lFiledWidth > 0)
					{
						out.println("</td>");
						out.println("</tr>");
						out.println("</table>");
					}
					out.println("</div>");
				}
			}
			out.println("</body>" + "</html>");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 'strFormName-Form的名字
	 * 'strFieldName-域的名字
	 * 'strForSelText-供选择的文本，用","分开
	 * 'strForSelValue-供选择的值，用","分开
	 * 'strSelText-已经选择的文本,用","分开
	 * 'strSelValue-已经选择的值,用","分开
	 * 'nType-显示类型1按钮都显示，2只显示增加按钮，3只显示删除按钮
	 */
	public static void showSelMultData(
		JspWriter out,
		String strFormName,
		String strFieldName,
		String strForSelText,
		String strForSelValue,
		String strSelText,
		String strSelValue,
		int nType)
		throws Exception
	{
		out.println("<script language=\"javascript\"> ");
		out.println(" ");
		out.println("function getSelectData(obj)//得到select框内的所有的值 ");
		out.println("{ ");
		out.println("  var cData; ");
		out.println("  cData=\"\" ");
		out.println("  for(i=0;i<obj.options.length;i++) ");
		out.println("  { ");
		out.println("    if (i>0) ");
		out.println("    { ");
		out.println("	  cData=cData+\",\"; ");
		out.println("    } ");
		out.println("    cData=cData+obj.options[i].value; ");
		out.println("  } ");
		out.println("  return cData; ");
		out.println("} ");
		out.println(" ");
		out.println("function setSelectData(obj,sText,sValue) ");
		out.println("{ ");
		out.println("  if(sValue.length>0) ");
		out.println("  { ");
		out.println("    var sSingleText,sSingleValue,oOption; ");
		out.println("    while(sValue.indexOf(\",\")>0) ");
		out.println("    { ");
		out.println("    	var nIndex; ");
		out.println("    	nIndex=sText.indexOf(\",\"); ");
		out.println("    	sSingleText=sText.substring(0,nIndex); ");
		out.println("    	sText=sText.substring(nIndex+1,sText.length); ");
		out.println("    	nIndex=sValue.indexOf(\",\"); ");
		out.println("    	sSingleValue=sValue.substring(0,nIndex); ");
		out.println("    	sValue=sValue.substring(nIndex+1,sValue.length); ");
		out.println("      oOption = document.createElement(\"OPTION\"); ");
		out.println("  	  oOption.text=sSingleText; ");
		out.println("      oOption.value=sSingleValue; ");
		out.println("      obj.add(oOption); ");
		out.println("    } ");
		out.println("    oOption = document.createElement(\"OPTION\"); ");
		out.println("  	oOption.text=sText; ");
		out.println("    oOption.value=sValue; ");
		out.println("    obj.add(oOption); ");
		out.println("     ");
		out.println("  } ");
		out.println("} ");
		out.println(" ");
		out.println("//将obj中已经有的value的项从objForDelete里删除 ");
		out.println("function deleteWasteData(objForDelete,obj) ");
		out.println("{ ");
		out.println("	for(i=0;i<obj.options.length;i++) ");
		out.println("	{ ");
		out.println("		for(j=0;j<objForDelete.options.length;j++) ");
		out.println("		{ ");
		out.println("			if (obj.options[i].value==objForDelete.options[j].value) ");
		out.println("			{ ");
		out.println("				objForDelete.remove(j); ");
		out.println("				break; ");
		out.println("			} ");
		out.println("		} ");
		out.println("	} ");
		out.println("} ");
		out.println(" ");
		out.println("function deleteRows(form) ");
		out.println("{ ");
		out.println("  var fFirst=true; ");
		out.println("  while(true) ");
		out.println("  { ");
		out.println("    for(i=0;i<form.haveselbbb.options.length;i++) ");
		out.println("    { ");
		out.println("      if(form.haveselbbb.options[i].selected==true) ");
		out.println("  	  { ");
		out.println("        fFirst=false ");
		out.println("        var oOption = document.createElement(\"OPTION\"); ");
		out.println("  	    oOption.text=form.haveselbbb.options[i].text; ");
		out.println("        oOption.value=form.haveselbbb.options[i].value; ");
		out.println("        form.forselectaaaa.add(oOption); ");
		out.println("        form.haveselbbb.remove(i); ");
		out.println(" ");
		out.println("        break; ");
		out.println("      } ");
		out.println("    } ");
		out.println("    if (fFirst ) ");
		out.println("    { ");
		out.println("		  break; ");
		out.println("	  } ");
		out.println("	  else ");
		out.println("	  { ");
		out.println("		  fFirst=true; ");
		out.println("	  } ");
		out.println("  } ");
		out.println("  form." + strFieldName + ".value=getSelectData(form.haveselbbb); ");
		out.println(" ");
		out.println("} ");
		out.println(" ");
		out.println("function insertRows(form) ");
		out.println("{ ");
		out.println("  var fFirst=true; ");
		out.println("  while(true) ");
		out.println("  { ");
		out.println("	  for(i=0;i<form.forselectaaaa.options.length;i++) ");
		out.println("	  { ");
		out.println("	    if(form.forselectaaaa.options[i].selected==true) ");
		out.println("		{ ");
		out.println("          fFirst=false; ");
		out.println("	      var oOption = document.createElement(\"OPTION\"); ");
		out.println("	  	  oOption.text=form.forselectaaaa.options[i].text; ");
		out.println("	      oOption.value=form.forselectaaaa.options[i].value; ");
		out.println("	      form.haveselbbb.add(oOption); ");
		out.println("		  form.forselectaaaa.remove(i); ");
		out.println("          break; ");
		out.println("	    } ");
		out.println("	  } ");
		out.println("      if (fFirst) ");
		out.println("      { ");
		out.println("		break; ");
		out.println("	  }     ");
		out.println("	else ");
		out.println("	{ ");
		out.println("		fFirst=true; ");
		out.println("	} ");
		out.println("  } ");
		out.println("  form." + strFieldName + ".value=getSelectData(form.haveselbbb); ");
		out.println("} ");
		out.println("</script> ");
		out.println("				<input type=\"hidden\" value=\"" + strSelValue + "\" name=\"" + strFieldName + "\"> ");
		out.println("				 ");
		out.println("						<table width=\"100%%\" border=\"0\"> ");
		out.println("                <tr>  ");
		out.println("                  <td width=\"30%\" rowspan=\"2\" align=\"center\">Choose a member</td> ");
		out.println("                  <td width=\"8\" >&nbsp; </td> ");
		out.println("                  <td width=\"30%\" rowspan=\"2\" align=\"center\">Chosen Members</td> ");
		out.println("                  <td width=\"32%\" rowspan=\"2\" align=\"center\">&nbsp;</td> ");
		out.println("                </tr> ");
		out.println("                <tr> </tr> ");
		out.println("                <tr>  ");
		out.println("                  <td width=\"30%\" rowspan=\"2\">  ");
		out.println("                    <select name=\"forselectaaaa\"  size=\"10\"  style=\"FONT-SIZE: 12px; WIDTH: 150px\" multiple> ");
		out.println("                    </select> ");
		out.println("                  </td> ");
		out.println("                  <td width=\"8%\" height=\"87\">  ");
		if ((nType == 1) || (nType == 2))
		{
			out.println("                    <input type=\"button\" value=\">>\" align=\"top\" name=\"button\" onClick=\"javascript:insertRows(" + strFormName + ")\"> ");
		}
		out.println("                  </td> ");
		out.println("                  <td width=\"30%\" rowspan=\"2\">  ");
		out.println("                    <select name=\"haveselbbb\"  size=\"10\"  style=\"FONT-SIZE: 12px; WIDTH: 150px\" multiple> ");
		out.println("                    </select> ");
		out.println("                  </td> ");
		out.println("                  <td width=\"32%\" rowspan=\"2\">&nbsp;</td> ");
		out.println("                </tr> ");
		out.println("                <tr>  ");
		out.println("                  <td width=\"8%\">  ");
		if ((nType == 1) || (nType == 3))
		{
			out.println("                    <input type=\"button\" value=\"&lt;&lt;\" align=\"top\" name=\"button2\" onClick=\"javascript:deleteRows(" + strFormName + ")\"> ");
		}
		out.println("                  </td> ");
		out.println("                </tr> ");
		out.println("              </table>				 ");
		out.println("<script language=\"javascript\"> ");
		out.println("	setSelectData(" + strFormName + ".forselectaaaa,\"" + strForSelText + "\",\"" + strForSelValue + "\"); ");
		out.println("	setSelectData(" + strFormName + ".haveselbbb,\"" + strSelText + "\",\"" + strSelValue + "\"); ");
		out.println("	deleteWasteData(" + strFormName + ".forselectaaaa," + strFormName + ".haveselbbb); ");
		out.println("</script> ");
	}


	/**
	 * 根据月份获取该月所在的季度
	 * Method getQuarterByMonth.
	 * @param lMonth
	 * @return String
	 */
	public static String getQuarterByMonth(long lMonth)
	{
		String strQuarter = "";
		switch ((int) lMonth)
		{
			case 1 :
				strQuarter = "一";
				break;
			case 2 :
				strQuarter = "一";
				break;
			case 3 :
				strQuarter = "一";
				break;
			case 4 :
				strQuarter = "二";
				break;
			case 5 :
				strQuarter = "二";
				break;
			case 6 :
				strQuarter = "二";
				break;
			case 7 :
				strQuarter = "三";
				break;
			case 8 :
				strQuarter = "三";
				break;
			case 9 :
				strQuarter = "三";
				break;
			case 10 :
				strQuarter = "四";
				break;
			case 11 :
				strQuarter = "四";
				break;
			case 12 :
				strQuarter = "四";
				break;
		}
		return strQuarter;
	}
	/**
	 * 得到TD的宽度
	 * @param nNumber 字数
	 * @return 返回TD的宽度
	 */
	public static long getTDWidth(int nNumber)
	{
		return 12 * nNumber;
	}
	/**
	 * 得到字体
	 * @param strData 字符串
	 * @param nNumber 允许字数（双字节，单字节算0.5个）
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
	 * 得到行数（内容所占用的行数）
	 * add by rxie
	 * @param strData 字符串
	 * @param nNumber 允许字数（双字节，单字节算0.5个）
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
	 * 得到一个报表中几个栏位中占据的最大行数
	 * @param strData 需要计算行数的栏位信息
	 * @param nNumber 需要计算行数的宽度信息
	 * @return
	 */
	public static int getMaxDataLine(String[] strData, int[] nNumber)
	{
		int lReturn = -1;
		int lRowNumber = -1;
		for (int i = 0; i < strData.length; i++)
		{
			lRowNumber = getDataLine(strData[i], nNumber[i]);
			if (lRowNumber > lReturn)
			{
				lReturn = lRowNumber;
			}
		}
		return lReturn;
	}
	/**
	 * 字符串自动换行
	 * add by hyzeng 2003-5-16
	 * @param strData 字符串
	 * @param nLength 允许字数（双字节算2个，单字节算1个）
	 * @return 返回字符串
	 */
	public static String formatStringLength(String strData, int nLength)
	{
		String strReturn = "";
		if (strData == null || strData.equals(""))
		{
			strReturn = "";
			return strReturn;
		}
		byte[] byTemp = strData.getBytes();
		if (byTemp.length <= nLength)
		{
			strReturn = strData;
			return strReturn;
		}
		int len = byTemp.length;
		int k = 0;
		k = (len / nLength) * 4 + len;
		char[] cReturn = new char[k];
		char[] cData = strData.toCharArray();
		byte[] byData = null;
		int j = 0;
		int count = 0;
		for (int i = 0; i < cData.length; i++)
		{
			byData = String.valueOf(cData[i]).getBytes();
			count = count + byData.length;
			cReturn[j] = cData[i];
			if (count >= nLength)
			{
				cReturn[j + 1] = '<';
				cReturn[j + 2] = 'b';
				cReturn[j + 3] = 'r';
				cReturn[j + 4] = '>';
				j = j + 4;
				count = 0;
			}
			j++;
		}
		strReturn = String.valueOf(cReturn);
		return strReturn;
	}
	public static String getAmountByOrder(String strAmount, int iOrderID) throws Exception
	{
		String strReturn = "&nbsp;";
		try
		{
			if (iOrderID < strAmount.length())
			{
				if (iOrderID <= 2)
				{
					return (strAmount.substring(strAmount.length() - iOrderID, strAmount.length() - iOrderID + 1));
				}
				else
				{
					if (strAmount.compareTo("1.00") >= 0)
					{
						return (strAmount.substring(strAmount.length() - iOrderID - 1, strAmount.length() - iOrderID));
					}
				}
			}
		}
		catch (Exception e)
		{
		}
		return strReturn;
	}
	/**
	 * 功能：打印模板ID取得打印模板名称
	 * @param lPrintTemplateID
	 * @return
	 * @
	 */
	public static String getPrintTemplateNameByID(long lPrintTemplateID)
	{
		String strReturn = "";
		try
		{
			String strSQL = "select sDesc from SETT_PRINTTEMPLATE where ID=" + lPrintTemplateID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sDesc");
			if (c != null)
			{
				strReturn = (String) c.iterator().next();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	/**
	 * 功能：得到账户的开户行的地址 省
	 * @param lPrintTemplateID
	 * @return
	 * @
	 */
	public static String getBankAccountProvinceByID(long lBankID)
	{
		String strReturn = "";
		try
		{
			String strSQL = "select sBranchProvince,sBranchCity,sPrintName from sett_branch where ID=" + lBankID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sBranchProvince");
			if (c != null)
			{
				strReturn = (String) c.iterator().next();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	/**
	 * 功能：得到账户的开户行的地址 市
	 * @param lPrintTemplateID
	 * @return
	 * @
	 */
	public static String getBankAccountCityByID(long lBankID)
	{
		String strReturn = "";
		try
		{
			String strSQL = "select sBranchProvince,sBranchCity,sPrintName from sett_branch where ID=" + lBankID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sBranchCity");
			if (c != null)
			{
				strReturn = (String) c.iterator().next();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	/**
	 * 功能：得到账户的开户行的 打印名称
	 * @param lPrintTemplateID
	 * @return
	 * @
	 */
	public static String getBankAccountPrintNameByID(long lBankID)
	{
		String strReturn = "";
		try
		{
			String strSQL = "select sBranchProvince,sBranchCity,sPrintName from sett_branch where ID=" + lBankID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sPrintName");
			if (c != null)
			{
				strReturn = (String) c.iterator().next();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	/**
	 * 功能：得到户行的 账户名称
	 * @param lPrintTemplateID
	 * @return
	 * @
	 */
	public static String getBankAccountCodeByID(long lBankID) throws Exception
	{
		String strReturn = "";
		try
		{
			String strSQL = "select sBankAccountCode from sett_branch where ID=" + lBankID;
			Collection c = SETTHTML.getCommonSelectList(strSQL, "sBankAccountCode");
			if (c != null)
			{
				strReturn = (String) c.iterator().next();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
	/**
	 * Method getTotalDailyAmountBanlance.
	 * 获取特定账户的在指定时间段中每日协定利息本金余额的总和---积数
	 * @param lAccountID
	 * @param StartDate
	 * @param EndDate
	 * @return double
	 */
	public static double getTotalDailyAccordAmountBanlance(long lAccountID, Timestamp StartDate, Timestamp EndDate) throws Exception
	{
		double dTotalBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL =
			"select sum(AC_MNEGOTIATEBALANCE) AS sumBalance  from sett_DailyAccountBalance where naccountid = "
				+ lAccountID
				+ " and dtdate >= to_date('"
				+ DataFormat.formatDate(StartDate)
				+ "','yyyy-mm-dd') and dtdate <= to_date('"
				+ DataFormat.formatDate(EndDate)
				+ "','yyyy-mm-dd')";
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dTotalBalance = rs.getDouble("sumBalance");
				System.out.println("~~~~~~~~~~~~~~~账户:" + NameRef.getAccountNoByID(lAccountID) + "~~~的协定积数：" + dTotalBalance);
			}
		}
		catch (Exception e)
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
			catch (Exception ex)
			{
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		return dTotalBalance;
	}
	/**
		 * Method getTotalDailyAmountBanlance.
		 * 获取特定账户的在指定时间段中每日活期利息本金余额的总和---积数
		 * @param lAccountID
		 * @param StartDate
		 * @param EndDate
		 * @return double
		 */
	public static double getTotalDailyAmountBanlance(long lAccountID, Timestamp StartDate, Timestamp EndDate) throws Exception
	{
		double dTotalBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL =
			"select sum(mInterestBalance) AS sumBalance  from sett_DailyAccountBalance where naccountid = "
				+ lAccountID
				+ " and dtdate >= to_date('"
				+ DataFormat.formatDate(StartDate)
				+ "','yyyy-mm-dd') and dtdate <= to_date('"
				+ DataFormat.formatDate(EndDate)
				+ "','yyyy-mm-dd')";
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dTotalBalance = rs.getDouble("sumBalance");
				System.out.println("~~~~~~~~~~~~~~~账户:" + NameRef.getAccountNoByID(lAccountID) + "~~~的活期积数：" + dTotalBalance);
			}
		}
		catch (Exception e)
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
			catch (Exception ex)
			{
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		return dTotalBalance;
	}
	/**
			 * Method getAccountBanlance.
			 * 活期平均余额
			 * @param lAccountID
			 * @param StartDate
			 * @param EndDate
			 * @return double
			 */
	public static double getCurrentAccountBanlance(long lAccountID, Timestamp StartDate, Timestamp EndDate) throws Exception
	{
		double dTotalBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL =
			"select  sum(round(nvl(mInterestBalance,0),2)) AS sumBalance  from sett_DailyAccountBalance where naccountid = "
				+ lAccountID
				+ " and dtdate >= to_date('"
				+ DataFormat.formatDate(StartDate)
				+ "','yyyy-mm-dd') and dtdate <= to_date('"
				+ DataFormat.formatDate(EndDate)
				+ "','yyyy-mm-dd')";
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dTotalBalance = rs.getDouble("sumBalance");
				System.out.println("~~~~~~~~~~~~~~~账户:" + NameRef.getAccountNoByID(lAccountID) + "~~~的余额：" + dTotalBalance);
			}
		}
		catch (Exception e)
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
			catch (Exception ex)
			{
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		return dTotalBalance;
	}
	/**
				 * Method getNegotiateBanlance.
				 * 协定平均余额
				 * @param lAccountID
				 * @param StartDate
				 * @param EndDate
				 * @return double
				 */
	public static double getNegotiateBanlance(long lAccountID, Timestamp StartDate, Timestamp EndDate) throws Exception
	{
		double dTotalBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL =
			"select  sum(round(nvl(AC_MNEGOTIATEBALANCE,0),2)) AS sumBalance  from sett_DailyAccountBalance where naccountid = "
				+ lAccountID
				+ " and dtdate >= to_date('"
				+ DataFormat.formatDate(StartDate)
				+ "','yyyy-mm-dd') and dtdate <= to_date('"
				+ DataFormat.formatDate(EndDate)
				+ "','yyyy-mm-dd')";
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dTotalBalance = rs.getDouble("sumBalance");
				System.out.println("~~~~~~~~~~~~~~~账户:" + NameRef.getAccountNoByID(lAccountID) + "~~~的协定本金：" + dTotalBalance);
			}
		}
		catch (Exception e)
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
			catch (Exception ex)
			{
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		return dTotalBalance;
	}
	/**
		* 查找一条贴现信息，操作数据库表DiscountApply，
		* @param lDiscountID 贴现标识
		*/
	public DiscountCredenceInfo findDiscountCredenceByID(long lDiscountCredenceID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		int nBillCount = 0;
		DiscountCredenceInfo lai = new DiscountCredenceInfo();
		try
		{
			con = Database.getConnection();
			strSQL = " select a.*,  b.ID nContractID,b.sContractCode " +
					 " from Loan_DiscountCredence a, Loan_ContractForm b " +
					 " where b.ID = a.nContractID and a.ID= "+lDiscountCredenceID;
			System.out.println("strSQL:" + strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//贴现申请
				lai.setDiscountContractID(rs.getLong("nContractID")); //贴现ID标识
				lai.setDiscountContractCode(rs.getString("sContractCode")); //贴现编号
				//贴现凭证
				lai.setID(lDiscountCredenceID); //贴现凭证标识
				lai.setCode(rs.getString("sCode")); //贴现凭证编号
				lai.setFillDate(rs.getTimestamp("dtFillDate"));
				lai.setDraftTypeID(rs.getLong("nDraftTypeID")); //贴现汇票种类标示
				lai.setDraftCode(rs.getString("sDraftCode")); //贴现汇票号码
				lai.setPublicDate(rs.getTimestamp("dtPublicDate")); //发票日
				lai.setAtTerm(rs.getTimestamp("dtAtTerm")); //到期日
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
			}
		}
		return lai;
	}
	/**
	 * 显示中国建设银行电子转账凭证
	 * 第一联
	 */
	public static void BankShowCcbTransVoucher1(BankShowCcbTransVoucherInfo info, JspWriter out) throws Exception
	{
		try
		{
			//out.println(" <html> " +
			//" <head> " );
			////noShowPrintHeadAndFooter(out);
			out.print(
				"<Script Language=\"JavaScript\"> document.write(' "
					+ " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
					+ " <link rel=\"stylesheet\" href=\"/websett/css/template.css\" type=\"text/css\"> "
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
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD  width=\"50\">&nbsp;	</TD><TD width=\"310\">&nbsp;	</TD><TD width=\"70\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "<TR> 	"
					+ "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;	</TD> "
					+ "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">"
					+ info.getBankAndVoucherName()
					+ " </font>　</strong></TD>"
					+ "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>"
					+ "</TD>"
					+ "</TR> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "	</TABLE> "
					+ "	<TABLE width=\"600\"> "
					+ " <TR> "
					+ "		<TD width=\"10%\"> "
					+ "		</TD> "
					+ "		<TD align=\"right\" width=\"50%\">"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ " </TD> "
					+ "	<TD width=\"40%\" align=\"right\"> "
					+ "	</TD> "
					+ "</TR> "
					+ "	</TABLE>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">付<BR> "
					+ "      款<BR> "
					+ "      人</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>  "
					+ "    </TD> "
					+ "    <TD  align=\"left\" nowrap class=\"In1-td-rightbottom\" width=\"199\">"
					+ info.getPayAccountName()
					+ "&nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">收<BR> "
					+ "      款<BR> "
					+ "      人</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-bottom\" width=\"199\">"
					+ info.getReceiveAccountName()
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-rightbottom\">"
					+ info.getPayAccountNo()
					+ "&nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-bottom\">"
					+ info.getReceiveAccountNo()
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">开 户 行</font></b>  "
					+ "    </td> "
					+ "    <td nowrap class=\"In1-td-rightbottom\" align=\"left\">"
					+ info.getPayBankName()
					+ "&nbsp;</td> "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">开 户 行</font></b>  "
					+ "    </td> "
					+ "    <td nowrap class=\"In1-td-bottom\" align=\"left\">"
					+ info.getReceiveBankName()
					+ "&nbsp;</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"1\" align=\"center\" class=\"In1-td-rightbottom\" height=\"30\" nowrap><B><FONT face=\"楷体_GB2312\">"
					+ "金额</FONT></B> </TD> "
					+ "    <TD colspan=\"4\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">"
					+ info.getCurrencyName()
					+ "(大写)</FONT>&nbsp;"
					+ info.getChineseAmount()
					+ "</B> </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-bottom\"><B><FONT face=\"楷体_GB2312\">"
					+ info.getCurrencyName()
					+ "(小写)</FONT>&nbsp;"
					+ info.getAmount()
					+ "</B> </TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"1\" align=\"center\" height=\"22\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">用途</FONT></B>  "
					+ "    </TD> "
					+ "    <TD colspan=\"2\" class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getAbstract()
					+ " </TD> "
					+ " <TD colspan=\"3\" ></TD>"
					+ "  </TR><TR>  "
					+ "    <TD colspan=\"3\" align=\"center\" height=\"30\" class=\"In1-td-right\" nowrap>&nbsp;<B><FONT face=\"楷体_GB2312\"></FONT></B>  "
					+ "    </TD> "
					+ " <TD colspan=\"3\" ></TD>"
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"3\" align=\"center\" height=\"22\" class=\"In1-td-rightbottom\" nowrap>&nbsp;<B><FONT face=\"楷体_GB2312\"></FONT></B>  "
					+ "    </TD> "
					+ "    <TD colspan=\"3\" class=\"In1-td-bottom\" align=\"center\">（银行盖章）</TD>"
					+ "</TR>"
					+ "  <TR>  "
					+ "    <TD colspan=\"1\" align=\"center\" height=\"22\" class=\"In1-td-right\" nowrap><B><FONT face=\"楷体_GB2312\">备注</FONT></B>  "
					+ "    </TD> "
					+ "    <TD colspan=\"5\">&nbsp;"
					+ info.getComment()
					+ " </TD> "
					+ "</TR>"
					+ "</TABLE> "
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"195\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>客<BR>户<BR>回<BR>单</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "<br>"
					+ "<Table width=\"600  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "    <TD height=\"22\" width=\"200\" nowrap>[主管]"
					+ info.getDirectorName()
					+ "</TD><TD  width=\"200\">"
					+ " &nbsp;[复核]"
					+ info.getCheckUserName()
					+ "&nbsp; </TD>"
					+ "    <TD height=\"22\" width=\"200\" nowrap>[制单]"
					+ info.getVoucherMakerName()
					+ " </TD> </TR>"
					+ "  </Table>"
					+ "</BODY> "
					+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * Method getDiscountTotalRepaymentAmount.
	 * 根据贴现凭证id查找发放金额
	 * @param lDiscountNoteID
	 * @return double
	 * @throws Exception
	 */
	public static double getDiscountTotalRepaymentAmount(long lDiscountNoteID) throws Exception
	{
		double dTotalAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";
		try
		{
			conn = Database.getConnection();
			strSQL = "select MDISCOUNTBILLAMOUNT from sett_transgrantdiscount where NDISCOUNTNOTEID=" + lDiscountNoteID;
			strSQL = strSQL + " and NSTATUSID=" + SETTConstant.TransactionStatus.CHECK;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dTotalAmount = rs.getDouble("MDISCOUNTBILLAMOUNT");
			}
		}
		catch (Exception e)
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
			catch (Exception ex)
			{
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		return dTotalAmount;
	}
	public static void main(String[] args)
	{
		System.out.println("aaaaaaa");
	}
}
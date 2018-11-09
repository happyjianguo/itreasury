/*
 * Created on 2005-3-18
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.bill.print;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.bill.print.templateinfo.ShowSpecialTransInfo;
import com.iss.itreasury.bill.print.templateinfo.ShowWithDrawInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IPrint;

/**
 * @author zntan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class IPrintTemplate extends IPrint
{
	/**
	 * 
	 */
	public IPrintTemplate()
	{
		super();
		// TODO Auto-generated constructor stub
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
					+ "	<link rel=\"stylesheet\" href=\"/websett/template.css\" type=\"text/css\"> "
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
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "      特种转账借方传票</font></font></strong> 　</strong></TD>"
					+ "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");
			out.println(
				"</TD>"
					+ "</TR> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"Debtor-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "	</TABLE><TABLE width=\"600\">	<TR> "
					+ "			<TD width=\"10%\">&nbsp;	 "
					+ "			</TD> "
					+ "			<TD align=\"right\" height=\"18\" width=\"51%\">	<b>"
					+ DataFormat.formatString(info.getYear())
					+ " 年 "
					+ DataFormat.formatString(info.getMonth())
					+ " 月 "
					+ DataFormat.formatString(info.getDay())
					+ " 日 "
					+ "	</b></TD> "
					+ "		<TD align=\"right\" width=\"39%\">交易编号："
					+ DataFormat.formatString(info.getTransNo())
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
					+ DataFormat.formatString(info.getPayAccountName())
					+ "&nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"Debtor-td-rightbottomleft\">	 "
					+ "      收<BR> "
					+ "      款<BR> "
					+ "      单<BR> "
					+ "      位 </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"Debtor-td-rightbottom\"> 全　　称 </TD> "
					+ "    <TD align=\"left\" width=\"214\" class=\"Debtor-td-rightbottomright\">"
					+ DataFormat.formatString(info.getReceiveAccountName())
					+ " &nbsp; </TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Debtor-td-rightbottom\">	 "
					+ "      账　　号</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Debtor-td-rightbottom\">"
					+ DataFormat.formatString(info.getPayAccountNo())
					+ "    &nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"12%\" class=\"Debtor-td-rightbottom\"> 账　　号</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Debtor-td-bottom\">"
					+ DataFormat.formatString(info.getReceiveAccountNo())
					+ "    &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR> "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Debtor-td-rightbottom-bottom\">	 "
					+ "      开户银行 </TD> "
					+ "    <TD align=\"left\" class=\"Debtor-td-rightbottom-bottom\">"
					+ DataFormat.formatString(info.getPayBankName())
					+ " &nbsp;</TD> "
					+ "   <TD align=\"center\" width=\"12%\" class=\"Debtor-td-rightbottom\"> 开户银行 </TD> "
					+ "   <TD align=\"left\" class=\"Debtor-td-bottom\">"
					+ DataFormat.formatString(info.getReceiveBankName())
					+ " &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" class=\"Debtor-td-rightbottom\"> 金<BR> "
					+ "      额 </TD> "
					+ "    <TD align=\"center\" class=\"Debtor-td-rightbottom\">"
					+ DataFormat.formatString(info.getCurrencyName())
					+ "<BR> "
					+ "      (大写) </TD> "
					+ "    <TD colspan=\"3\" class=\"Debtor-td-rightbottom\" width=\"33%\"><B>&nbsp;"
					+ DataFormat.formatString(info.getChineseAmount())
					+ "</B>  "
					+ "    </TD> "
					+ "    <TD align=\"right\" width=\"33%\" class=\"Debtor-td-rightbottomright\"><B>&nbsp;"
					+ DataFormat.formatString(info.getAmount())
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
					+ DataFormat.formatString(info.getAbstract())
					+ " </TD> "
					+ "        </TR> "
					+ "     </TABLE>  "
					+ "</TABLE> "
					+ "<br>"
					+ "<Table width=\"630\">"
					+ "<TR>"
					+ "<td align=\"right\">[录入]"
					+ DataFormat.formatString(info.getInputUserName())
					+ "&nbsp;[复核]"
					+ DataFormat.formatString(info.getCheckUserName())
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
					+ "	<link rel=\"stylesheet\" href=\"/websett/template.css\" type=\"text/css\"> "
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
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "      特种转账贷方传票</font></font></strong> 　</strong></TD>"
					+ "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");
			out.println(
				"</TD>"
					+ "</TR> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"Credit-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "	</TABLE><TABLE width=\"600\">	<TR> "
					+ "			<TD width=\"10%\">&nbsp;	 "
					+ "			</TD> "
					+ "			<TD align=\"right\" height=\"18\" width=\"51%\">	<b>"
					+ DataFormat.formatString(info.getYear())
					+ " 年 "
					+ DataFormat.formatString(info.getMonth())
					+ " 月 "
					+ DataFormat.formatString(info.getDay())
					+ " 日 "
					+ "	</b></TD> "
					+ "		<TD align=\"right\" width=\"39%\">交易编号："
					+ DataFormat.formatString(info.getTransNo())
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
					+ DataFormat.formatString(info.getPayAccountName())
					+ " &nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"Credit-td-rightbottomleft\">	 "
					+ "      收<BR> "
					+ "      款<BR> "
					+ "      单<BR> "
					+ "      位 </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"Credit-td-rightbottomtop\"> 全　　称 </TD> "
					+ "    <TD align=\"left\" width=\"214\" class=\"Credit-td-rightbottomtop\">"
					+ DataFormat.formatString(info.getReceiveAccountName())
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Credit-td-rightbottom\">	 "
					+ "      账　　号</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatString(info.getPayAccountNo())
					+ "    &nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"12%\" class=\"Credit-td-rightbottom\"> 账　　号</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatString(info.getReceiveAccountNo())
					+ "    &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Credit-td-rightbottom\"> "
					+ "      开户银行 </TD> "
					+ "    <TD class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatString(info.getPayBankName())
					+ "&nbsp; </TD> "
					+ "    <TD align=\"center\" width=\"12%\" class=\"Credit-td-rightbottom-bottom\"> 开户银行  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"Credit-td-rightbottom-bottom\">"
					+ DataFormat.formatString(info.getReceiveBankName())
					+ " &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" class=\"Credit-td-rightbottom\"> 金<BR> "
					+ "      额 </TD> "
					+ "    <TD align=\"center\" class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatString(info.getCurrencyName())
					+ "<BR> "
					+ "      (大写) </TD> "
					+ "    <TD colspan=\"3\" class=\"Credit-td-rightbottom\" width=\"33%\"><B>&nbsp;"
					+ DataFormat.formatString(info.getChineseAmount())
					+ "</B>  "
					+ "    </TD> "
					+ "    <TD align=\"right\" width=\"33%\" class=\"Credit-td-rightbottom-right\"><B>&nbsp;"
					+ DataFormat.formatString(info.getAmount())
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
					+ DataFormat.formatString(info.getAbstract())
					+ "</TD>"
					+ "        </TR>"
					+ "      </TABLE>"
					+ "      <br> </TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "<br>"
					+ "<Table width=\"630\">"
					+ "<TR>"
					+ "<td align=\"right\">[录入]"
					+ DataFormat.formatString(info.getInputUserName())
					+ "&nbsp;[复核]"
					+ DataFormat.formatString(info.getCheckUserName())
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
	 * 存款支取凭证第一联
	 * @param info
	 * @param out
	 * @throws Exception
	 */
	public static void showWithDraw1(ShowWithDrawInfo info, JspWriter out) throws Exception
	{
		try
		{
			//out.println(" <html> " +
			//" <head> " );
			////noShowPrintHeadAndFooter(out);
			out.print(
				"<Script Language=\"JavaScript\"> document.write(' "
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
					+ "<TR> 	"
					+ "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;	</TD> "
					+ "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "      存款支取凭证</font></font></strong> 　</strong></TD>"
					+ "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");
			out.println(
				"</TD>"
					+ "</TR> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "</TABLE> 	<TABLE width=\"592\">  "
					+ "<TR>		<TD width=\"177\"> </TD> 	"
					+ "	<TD align=\"center\" width=\"198\">"
					+ DataFormat.formatString(info.getYear())
					+ " 年 "
					+ DataFormat.formatString(info.getMonth())
					+ " 月 "
					+ DataFormat.formatString(info.getDay())
					+ " 日 "
					+ "</TD> 	"
					+ "	<TD width=\"210\" align=\"right\">交易编号："
					+ DataFormat.formatString(info.getTransNo())
					+ "</TD> "
					+ "</TR> "
					+ "	</TABLE>"
					+ " <table><tr><td>"
					+ " <TABLE width=\"590\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\">"
					+ "   <TR>      <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">付<BR>       款<BR>       人</FONT></B> </TD>"
					+ "     <TD align=\"center\" width=\"70\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>      </TD>"
					+ "     <TD align=\"left\"  colspan=\"4\" nowrap class=\"In1-td-rightbottom\" width=\"180\">"
					+ DataFormat.formatString(info.getPayAccountName())
					+ "&nbsp;  </TD>   "
					+ "  <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">收<BR>       款<BR>       人</FONT></B> </TD>"
					+ "     <TD align=\"center\" width=\"70\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>      </TD>"
					+ "     <TD align=\"left\" colspan=\"4\" nowrap class=\"In1-td-bottom\" width=\"180\">"
					+ DataFormat.formatString(info.getReceiveAccountName())
					+ "&nbsp; </TD>  "
					+ " </TR> "
					+ "  <TR>    <TD align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>      </TD>  "
					+ "   <TD colspan=\"4\" align=\"left\" nowrap class=\"In1-td-rightbottom\">"
					+ DataFormat.formatString(info.getPayAccountNo())
					+ "&nbsp;</TD> "
					+ "    <TD align=\"center\"  class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>      </TD>    "
					+ " <TD colspan=\"4\" align=\"left\" nowrap class=\"In1-td-bottom\">"
					+ DataFormat.formatString(info.getReceiveAccountNo())
					+ "&nbsp;</TD>   "
					+ "</TR>"
					+ "   <TR>   <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">开户银行</font></b>      </td>  "
					+ "   <td  nowrap colspan=\"4\" class=\"In1-td-rightbottom\">"
					+ DataFormat.formatString(info.getPayBankName())
					+ "&nbsp;</td>  "
					+ "   <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">汇入地点</font></b>      </td>  "
					+ "   <td width=\"50\" nowrap colspan =\"2\" class=\"In1-td-rightbottom\">"
					+ DataFormat.formatString(info.getReceiveRemitInAddress())
					+ "&nbsp;</td>     "
					+ "<td width=\"45\" nowrap class=\"In1-td-rightbottom\"><b><font face=\"楷体_GB2312\">汇入行<br>       名称</font></b></td>    "
					+ " <td width=\"104\" nowrap class=\"In1-td-bottom\">"
					+ DataFormat.formatString(info.getReceiveBankName())
					+ "&nbsp;</td> "
					+ "  </TR>  "
					+ " <TR>    "
					+ "  <TD colspan=\"2\" align=\"center\" class=\"In1-td-topbottom2right\" height=\"30\" nowrap><B><FONT face=\"楷体_GB2312\">"
					+ DataFormat.formatString(info.getCurrencyName())
					+ "<BR>       (大写)</FONT></B> </TD>    "
					+ " <TD colspan=\"6\" class=\"In1-td-topbottom2right\" nowrap><B>"
					+ DataFormat.formatString(info.getChineseAmount())
					+ "&nbsp;</B> </TD>    "
					+ " <TD colspan=\"4\" align=\"right\" nowrap class=\"In1-td-topbottom2\"><B>"
					+ DataFormat.formatString(info.getAmount())
					+ "&nbsp; </B>      </TD> "
					+ "  </TR>  "
					+ " <TR>    "
					+ "  <TD align=\"center\" class=\"In1-td-rightbottom\" colspan=\"2\"><b> 摘　　要 </b>&nbsp;</TD>    "
					+ " <TD colspan=\"12\" class=\"In1-td-bottom\" >"
					+ DataFormat.formatString(info.getAbstract())
					+ "&nbsp; </TD>   "
					+ "</TR>  "
					+ "<TR>    "
					+ "   <TD colspan=\"14\" ><B>以上款项已在你单位账下付讫</B>&nbsp; </TD>  "
					+ " </TR> "
					+ " </TABLE> "
					+ "<TABLE width=\"30\" border=\"0\"> 	"
					+ "	<TR> 	"
					+ "		<TD height=\"160\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>记<BR>账<BR>凭<BR>证</FONT> </TD> "
					+ "		</TR> 	</TABLE>"
					+ " </td></tr></table>"
					+ "<br>"
					+ "<table width=\"590\"><TR align=\"right\">     "
					+ "   <TD  colspan=\"3\" align=\"right\">&nbsp;  客户网上银行 </TD>     "
					+ "<TD width=\"360\" height=\"22\" colspan=\"11\"  nowrap>[录入] &nbsp;"
					+ DataFormat.formatString(info.getOBInputUserName())
					+ "&nbsp;[复核]&nbsp;"
					+ DataFormat.formatString(info.getOBCheckUserName())
					+ "&nbsp; [签认]&nbsp;"
					+ DataFormat.formatString(info.getOBSignUserName())
					+ " </TD>  "
					+ " </TR>   "
					+ "<TR align=\"right\">   "
					+ "   <TD  colspan=\"3\" align=\"right\">&nbsp;  "
					+ DataFormat.formatString(Env.getClientName())
					+ " </TD>    "
					+ " <TD colspan=\"11\" height=\"22\"  nowrap>[录入]&nbsp;"
					+ DataFormat.formatString(info.getInputUserName())
					+ "&nbsp;[复核]&nbsp;"
					+ DataFormat.formatString(info.getCheckUserName())
					+ " </TD>   "
					+ "</TR>"
					+ "</table>"
					+ " </BODY> "
					+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * 存款支取凭证第二联
	 * @param info
	 * @param out
	 * @throws Exception
	 */
	public static void showWithDraw2(ShowWithDrawInfo info, JspWriter out) throws Exception
	{
		try
		{
			//out.println(" <html> " +
			//" <head> " );
			////noShowPrintHeadAndFooter(out);
			out.print(
				" <Script Language=\"JavaScript\"> document.write(' "
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
					+ "<TR> 	"
					+ "<TD colspan=\"2\" width=\"140\" height=\"46\" nowrap>&nbsp;	</TD> "
					+ "  <TD  align=\"center\" width=\"310\" nowrap><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br>"
					+ "      存款支取凭证</font></font></strong> 　</strong></TD>"
					+ "    <TD colspan=\"2\" width=\"180\" align=\"left\" nowrap>");
			out.println(
				"</TD>"
					+ "</TR> "
					+ "<TR> 	<TD width=\"90\">&nbsp;	</TD><TD class=\"In1-td-top\" colspan=\"3\" width=\"450\">&nbsp;	</TD><TD width=\"110\">&nbsp;	</TD></TR> 	"
					+ "</TABLE> 	<TABLE width=\"592\">  "
					+ "<TR>		<TD width=\"177\"> </TD> 	"
					+ "	<TD align=\"center\" width=\"198\">"
					+ DataFormat.formatString(info.getYear())
					+ " 年 "
					+ DataFormat.formatString(info.getMonth())
					+ " 月 "
					+ DataFormat.formatString(info.getDay())
					+ " 日 "
					+ "</TD> 	"
					+ "	<TD width=\"210\" align=\"right\">交易编号："
					+ DataFormat.formatString(info.getTransNo())
					+ "</TD> "
					+ "</TR> "
					+ "	</TABLE>"
					+ " <table><tr><td>"
					+ " <TABLE width=\"590\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\">"
					+ "   <TR>      <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">付<BR>       款<BR>       人</FONT></B> </TD>"
					+ "     <TD align=\"center\" width=\"70\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>      </TD>"
					+ "     <TD align=\"left\"  colspan=\"4\" nowrap class=\"In1-td-rightbottom\" width=\"180\">"
					+ DataFormat.formatString(info.getPayAccountName())
					+ "&nbsp;  </TD>   "
					+ "  <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">收<BR>       款<BR>       人</FONT></B> </TD>"
					+ "     <TD align=\"center\" width=\"70\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">全　　称</FONT></B>      </TD>"
					+ "     <TD align=\"left\" colspan=\"4\" nowrap class=\"In1-td-bottom\" width=\"180\">"
					+ DataFormat.formatString(info.getReceiveAccountName())
					+ "&nbsp; </TD>  "
					+ " </TR> "
					+ "  <TR>    <TD align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>      </TD>  "
					+ "   <TD colspan=\"4\" align=\"left\" nowrap class=\"In1-td-rightbottom\">"
					+ DataFormat.formatString(info.getPayAccountNo())
					+ "&nbsp;</TD> "
					+ "    <TD align=\"center\"  class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"楷体_GB2312\">账　　号</FONT></B>      </TD>    "
					+ " <TD colspan=\"4\" align=\"left\" nowrap class=\"In1-td-bottom\">"
					+ DataFormat.formatString(info.getReceiveAccountNo())
					+ "&nbsp;</TD>   "
					+ "</TR>"
					+ "   <TR>   <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">开户银行</font></b>      </td>  "
					+ "   <td  nowrap colspan=\"4\" class=\"In1-td-rightbottom\">"
					+ DataFormat.formatString(info.getPayBankName())
					+ "&nbsp;</td>  "
					+ "   <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"楷体_GB2312\">汇入地点</font></b>      </td>  "
					+ "   <td width=\"50\" nowrap colspan =\"2\" class=\"In1-td-rightbottom\">"
					+ DataFormat.formatString(info.getReceiveRemitInAddress())
					+ "&nbsp;</td>     "
					+ "<td width=\"45\" nowrap class=\"In1-td-rightbottom\"><b><font face=\"楷体_GB2312\">汇入行<br>       名称</font></b></td>    "
					+ " <td width=\"104\" nowrap class=\"In1-td-bottom\">"
					+ DataFormat.formatString(info.getReceiveBankName())
					+ "&nbsp;</td> "
					+ "  </TR>  "
					+ " <TR>    "
					+ "  <TD colspan=\"2\" align=\"center\" class=\"In1-td-topbottom2right\" height=\"30\" nowrap><B><FONT face=\"楷体_GB2312\">"
					+ DataFormat.formatString(info.getCurrencyName())
					+ "<BR>       (大写)</FONT></B> </TD>    "
					+ " <TD colspan=\"6\" class=\"In1-td-topbottom2right\" nowrap><B>"
					+ DataFormat.formatString(info.getChineseAmount())
					+ "&nbsp;</B> </TD>    "
					+ " <TD colspan=\"4\" align=\"right\" nowrap class=\"In1-td-topbottom2\"><B>"
					+ DataFormat.formatString(info.getAmount())
					+ "&nbsp; </B>      </TD> "
					+ "  </TR>  "
					+ " <TR>    "
					+ "  <TD align=\"center\" class=\"In1-td-rightbottom\" colspan=\"2\"><b> 摘　　要 </b>&nbsp;</TD>    "
					+ " <TD colspan=\"12\" class=\"In1-td-bottom\" >"
					+ DataFormat.formatString(info.getAbstract())
					+ "&nbsp; </TD>   "
					+ "</TR>  "
					+ "<TR>    "
					+ "   <TD colspan=\"14\" ><B>以上款项已在你单位账下付讫</B>&nbsp; </TD>  "
					+ " </TR> "
					+ " </TABLE> "
					+ "<TABLE width=\"30\" border=\"0\"> 	"
					+ "	<TR> 	"
					+ "		<TD height=\"160\" ><FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>回<BR>单</FONT> </TD> "
					+ "		</TR> 	</TABLE>"
					+ " </td></tr></table>"
					+ "<br>"
					+ "<table width=\"590\"><TR align=\"right\">     "
					+ "   <TD  colspan=\"3\" align=\"right\">&nbsp;  客户网上银行 </TD>     "
					+ "<TD width=\"360\" height=\"22\" colspan=\"11\"  nowrap>[录入] &nbsp;"
					+ DataFormat.formatString(info.getOBInputUserName())
					+ "&nbsp;[复核]&nbsp;"
					+ DataFormat.formatString(info.getOBCheckUserName())
					+ "&nbsp; [签认]&nbsp;"
					+ DataFormat.formatString(info.getOBSignUserName())
					+ " </TD>  "
					+ " </TR>   "
					+ "<TR align=\"right\">   "
					+ "   <TD  colspan=\"3\" align=\"right\">&nbsp;  "
					+ DataFormat.formatString(Env.getClientName())
					+ " </TD>    "
					+ " <TD colspan=\"11\" height=\"22\"  nowrap>[录入]&nbsp;"
					+ DataFormat.formatString(info.getInputUserName())
					+ "&nbsp;[复核]&nbsp;"
					+ DataFormat.formatString(info.getCheckUserName())
					+ " </TD>   "
					+ "</TR>"
					+ "</table>"
					+ " </BODY> "
					+ "  '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
}

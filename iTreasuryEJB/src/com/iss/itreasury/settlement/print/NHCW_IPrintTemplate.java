/*
* Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
*
* ƾ֤��ӡģ��
*
* ʹ��ע�����
* 1
* 2
*
* ����: rxie
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
	public static final long ISPRINT = -1000; //�Ƿ��ӡ�շŻ��߸���ƾ֤��ʾ Ϊ-1000ʱ����ӡ
	/**
	 * ��ʾ���˵�
	 * ��һ��
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
					+ "      ��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;��</font></font></strong> ��</strong></TD>"
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
                    + " ��ӡ����: "
					+ info.getYear()
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " �� "
					+ " </TD> "
					+ "	<TD width=\"40%\" align=\"right\">���ױ�ţ�"
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "	</TD> "
					+ "</TR> "
					+ "	</TABLE>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">��<BR> "
					+ "      ��<BR> "
					+ "      ��</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">ȫ������</FONT></B>  "
					+ "    </TD> "
					+ "    <TD  align=\"left\" class=\"In1-td-rightbottom\" width=\"199\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountName())
					+ "&nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">��<BR> "
					+ "      ��<BR> "
					+ "      ��</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">ȫ������</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"In1-td-bottom\" width=\"199\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountName())
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">�ˡ�����</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountNo())
					+ "&nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">�ˡ�����</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-bottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountNo())
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"����_GB2312\">��������</font></b>  "
					+ "    </td> "
					+ "    <td class=\"In1-td-rightbottom\" align=\"left\">"
					+ DataFormat.formatStringForPrint(info.getPayBankName())
					+ "&nbsp;</td> "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"����_GB2312\">��������</font></b>  "
					+ "    </td> "
					+ "    <td class=\"In1-td-bottom\" align=\"left\">"
					+ DataFormat.formatStringForPrint(info.getReceiveBankName())
					+ "&nbsp;</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"2\" align=\"center\" class=\"In1-td-topbottom2right\" height=\"30\" nowrap><B><FONT face=\"����_GB2312\">"
					+ DataFormat.formatStringForPrint(info.getCurrencyName())
					+ "<BR> "
					+ "      (��д)</FONT></B> </TD> "
					+ "    <TD colspan=\"3\" class=\"In1-td-topbottom2right\" ><B>&nbsp;"
					+ info.getChineseAmount()
					+ "</B> </TD> "
					+ "    <TD align=\"right\" nowrap class=\"In1-td-topbottom2\"><B>&nbsp;"
					+ info.getAmount()
					+ "</B> </TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"2\" align=\"center\" height=\"22\" class=\"In1-td-right\" nowrap><B><FONT face=\"����_GB2312\">ժҪ</FONT></B>  "
					+ "    </TD> "
					+ "    <TD colspan=\"4\" >&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAbstract())
					+ " </TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"160\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>һ<BR>��<BR><BR>��<BR>��</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "<Table width=\"600  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" nowrap>&nbsp; </TD>"
					+ "    <TD nowrap>[������]&nbsp; "
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ " &nbsp;[������]&nbsp; "
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
	 * ��ʾ���˵�
	 * �ڶ���
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
					+ "       ��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;��</font></font></strong> ��</strong></TD>"
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
                    + " ��ӡ����: "
					+ info.getYear()
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " �� "
					+ " </TD> "
					+ "	<TD width=\"40%\" align=\"right\">���ױ�ţ�"
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "	</TD> "
					+ "</TR> "
					+ "	</TABLE>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">��<BR> "
					+ "      ��<BR> "
					+ "      ��</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">ȫ������</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"In1-td-rightbottom\" width=\"199\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountName())
					+ "&nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">��<BR> "
					+ "      ��<BR> "
					+ "      ��</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">ȫ������</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"In1-td-bottom\" width=\"199\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountName())
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">�ˡ�����</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"In1-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountNo())
					+ "&nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">�ˡ�����</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"In1-td-bottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountNo())
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"����_GB2312\">��������</font></b>  "
					+ "    </td> "
					+ "    <td class=\"In1-td-rightbottom\" align=\"left\">"
					+ DataFormat.formatStringForPrint( info.getPayBankName())
					+ "&nbsp;</td> "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"����_GB2312\">��������</font></b>  "
					+ "    </td> "
					+ "    <td class=\"In1-td-bottom\" align=\"left\">"
					+ DataFormat.formatStringForPrint(info.getReceiveBankName())
					+ "&nbsp;</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"2\" align=\"center\" class=\"In1-td-topbottom2right\" height=\"30\" nowrap><B><FONT face=\"����_GB2312\">"
					+ DataFormat.formatStringForPrint(info.getCurrencyName())
					+ "<BR> "
					+ "      (��д)</FONT></B> </TD> "
					+ "    <TD colspan=\"3\" class=\"In1-td-topbottom2right\" ><B>&nbsp;"
					+ info.getChineseAmount()
					+ "</B> </TD> "
					+ "    <TD align=\"right\" nowrap class=\"In1-td-topbottom2\"><B>&nbsp;"
					+ info.getAmount()
					+ "</B> </TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"2\" align=\"center\" height=\"22\" class=\"In1-td-right\" nowrap><B><FONT face=\"����_GB2312\">ժҪ</FONT></B>  "
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
					+ "			<TD height=\"160\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>��<BR>��<BR>ƾ<BR>֤</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					 
					+ "<Table width=\"600  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\"  nowrap>&nbsp; </TD>"
					+ "    <TD  nowrap>[������]&nbsp; "
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ " &nbsp;[������]&nbsp; "
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
	 * ֪ͨ����֤ʵ��
	 * ������
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
					+ "֪ͨ����֤ʵ��&nbsp;</font></font></strong></strong></div></td>");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				"  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">�浥�ţ�"
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ױ�ţ�"
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
					+ "              <p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;����&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;Ϣ&nbsp;�գ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getStartInterestDate())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�"+Constant.CurrencyType.getName(info.getCurrencyID())+"����д��&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Сд��&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;Ʒ&nbsp;&nbsp;&nbsp;&nbsp;�֣�"
					+ info.getType()
					+ "                <br><br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "              ��ע��<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;��֤ʵ����Դ���˿���֤ʵ��������Ϊ��Ѻ��Ȩ��ƾ֤��<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;���浥һʽ���ݣ�������λ��һ�ݣ�����˾�ֶ��ݡ�<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">��<br>��<br>"
					+ "      ��<br>"
					+ "      <br>"
					+ "      ��<br>"
					+ "      ��<br>"
					+ "      ��<br>"
					+ "      ��<br>"
					+ "      λ</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[���ž���]&nbsp;"
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
                    +"    <td height=\"24\"><div align=\"center\"><font style=\"font-size:20px\"><b>����ҵ�λ֪ͨ���֧ȡ֪ͨ</b></font></div></td>" 
                    +"  </tr>"  
                    +"  <tr> "  
                    +"    <td height=\"24\"> <div align=\"left\">"  
                    +DataFormat.formatStringForPrint( Env.getClientName())
                    +"  </div></td>" 
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\"> "
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +" �ҵ�λ�����ƣ�__________________________Ԥ��_____��__��__�մ���֪ͨ����˻����˺�<u>"
                    + DataFormat.formatStringForPrint(info.getAccountNo())
                    +"</u>��</td>"  
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\">֧ȡ����ң���д��______________________________Ԫ��Сд��__________________Ԫ�������˾���趨��</td>" 
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\">���ڽ���֧ȡ������ͬ��Ϣת������ָ��֮�˻���</td>" 
                    +"  </tr>" 
                    +"  <tr>"  
                    +"&nbsp;&nbsp;"
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"�˺ţ�</td>" 
                    +"  </tr>" 
                    +"  <tr>"  
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"�������У�</td>"  
                    +"  </tr>" 
                    +"  <tr>"  
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"�ش�֪ͨ</td>"  
                    +"  </tr>" 
                    +"  <tr>" 
                    +"    <td height=\"34\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;��λԤ��ӡ����</div></td>"
                    +"  </tr>" 
                    +"  <tr>" 
                    +"    <td height=\"24\"><b>ע����֪ͨ��һ������ͬ���ڴ��֤ʵ���ύ����˾Ӫҵ����</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_____��__��__��</td>"  
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
	 *֪ͨ����֤ʵ��
	 * �ڶ���
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
					+ "֪ͨ����֤ʵ��&nbsp;</font></font></strong></strong></div></td>");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				      "  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">�浥�ţ�"
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ױ�ţ�"
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
					+ "              <p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;����&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;Ϣ&nbsp;�գ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getStartInterestDate())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�"+ Constant.CurrencyType.getName(info.getCurrencyID()) +"����д��&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Сд��&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;Ʒ&nbsp;&nbsp;&nbsp;&nbsp;�֣�"
					+ info.getType()
					+ "                <br><br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "              ��ע��<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;��֤ʵ����Դ���˿���֤ʵ��������Ϊ��Ѻ��Ȩ��ƾ֤��<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;���浥һʽ���ݣ�������λ��һ�ݣ�����˾�ֶ��ݡ�<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">��<br>��<br>"
					+ "      ��<br>"
					+ "      <br>"
					+ "      ҵ<br>"
					+ "      ��<br>"
					+ "      ��<br>"
					+ "      ��</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[������];"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[���ž���]&nbsp;"
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
	 *֪ͨ����֤ʵ��
	 * ��һ��
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
					+ "֪ͨ����֤ʵ��&nbsp;</font></font></strong></strong></div></td>");
			//out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			out.println(
				    "  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">�浥�ţ�"
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ױ�ţ�"
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
					+ "              <p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;����&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;Ϣ&nbsp;�գ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getStartInterestDate())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�"+Constant.CurrencyType.getName(info.getCurrencyID())+"����д��&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Сд��&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;Ʒ&nbsp;&nbsp;&nbsp;&nbsp;�֣�"
					+ info.getType()
					+ "                <br><br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "              ��ע��<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;��֤ʵ����Դ���˿���֤ʵ��������Ϊ��Ѻ��Ȩ��ƾ֤��<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;���浥һʽ���ݣ�������λ��һ�ݣ�����˾�ֶ��ݡ�<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">��<br>һ<br>"
					+ "      ��<br>"
					+ "      <br>"
					+ "      ��<br>"
					+ "      ��<br>"
					+ "      ƾ<br>"
					+ "      ֤</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[���ž���]&nbsp;"
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
	 * ���ڴ���֤ʵ��
	 * ��һ��
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
					+ "���ڴ���֤ʵ��&nbsp;</font></font></strong></strong></div></td>");
					
			//sb.append("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			sb.append(
				    "  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">�浥�ţ�"
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ױ�ţ�"
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
					+ "<p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;����&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;Ϣ&nbsp;�գ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getDateOpenAccount()));
			/*  TOCONFIG��TODELETE  */
			/*
					if(Env.getProjectName().compareToIgnoreCase(Constant.ProjectName.SEFC) == 0)
					{
						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
								+ "��&nbsp;��&nbsp;�գ�&nbsp;"
								+ info.getEndDate());
					}
					*/
			
					sb.append(
					"<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�"+ info.getCurrencyName() +"����д��&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Сд��&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ޣ�"
					+ info.getInterval());
					/*  TOCONFIG��TODELETE  */
					/*
					if(Env.getProjectName().compareToIgnoreCase(Constant.ProjectName.SEFC) == 0)
					{
						sb.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ժ&nbsp;&nbsp;&nbsp;&nbsp;Ҫ��&nbsp;"
						+ info.getAbstract());
					}
					*/
					sb.append(
					 "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ʣ�"
					+ info.getRate()
					+ "������Ϣ��<br>"
					+ "                <br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��ע��<br>"
					+ "          &nbsp;&nbsp;&nbsp;&nbsp;��֤ʵ����Դ���˿���֤ʵ��������Ϊ��Ѻ��Ȩ��ƾ֤��<br>"
					+ "          &nbsp;&nbsp;&nbsp;&nbsp;���浥һʽ���ݣ�������λ��һ�ݣ�����˾�ֶ��ݡ�<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">��<br>һ<br>"
					+ "      ��<br>"
					+ "      <br>"
					+ "      ��<br>"
					+ "      ��<br>"
					+ "      ƾ<br>"
					+ "      ֤</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[���ž���]&nbsp;"
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
	 * ���ڴ���֤ʵ��
	 * �ڶ���
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
					+ "���ڴ���֤ʵ��&nbsp;</font></font></strong></strong></div></td>");
			//sb.append("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			sb.append(
				 "  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">�浥�ţ�"
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ױ�ţ�"
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
					+ "              <p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;����&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;Ϣ&nbsp;�գ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getDateOpenAccount()));
			/*  TOCONFIG��TODELETE  */
			/*
					if(Env.getProjectName().compareToIgnoreCase(Constant.ProjectName.SEFC) == 0)
					{
						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
								+ "��&nbsp;��&nbsp;�գ�&nbsp;"
								+ info.getEndDate());
					}
					*/
					sb.append(
					 "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�"+ info.getCurrencyName() +"����д��&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Сд��&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ޣ�"
					+ info.getInterval());
					/*  TOCONFIG��TODELETE  */
					/*
					if(Env.getProjectName().compareToIgnoreCase(Constant.ProjectName.SEFC) == 0)
					{
						sb.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ժ&nbsp;&nbsp;&nbsp;&nbsp;Ҫ��&nbsp;"
						+ info.getAbstract());
					}
					*/
					sb.append(
					 "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ʣ�"
					+ info.getRate()
					+ "������Ϣ��<br>"
					+ "                <br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "              ��ע��<br>"
					+ "           &nbsp;&nbsp;&nbsp;&nbsp;��֤ʵ����Դ���˿���֤ʵ��������Ϊ��Ѻ��Ȩ��ƾ֤��<br>"
					+ "           &nbsp;&nbsp;&nbsp;&nbsp;���浥һʽ���ݣ�������λ��һ�ݣ�����˾�ֶ��ݡ�<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">��<br>��<br>"
					+ "      ��<br>"
					+ "      <br>"
					+ "      ҵ<br>"
					+ "      ��<br>"
					+ "      ��<br>"
					+ "      ��</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[���ž���]&nbsp;"
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
	 * ���ڴ���֤ʵ��
	 * ������
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
					+ "���ڴ���֤ʵ��&nbsp;</font></font></strong></strong></div></td>");
					
			//sb.append("<img src=" + Env.SETTLEMENT_URL + "../../websett/image/dayin_logo.gif  height=\"46\">");
			sb.append(
				    "  </tr>"
					+ "  <tr>"
					+ "<td colspan=\"2\">"
					+ "<table><tr><td width=\"350\" ><div align=\"left\">�浥�ţ�"
					+ DataFormat.formatStringForPrint(info.getDepositBillNo())
					+ "</div></td>"
					+ "	<td  width=\"280\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ױ�ţ�"
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
					+ "              <p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;����&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountName())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;Ϣ&nbsp;�գ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getDateOpenAccount()));
			
					sb.append(
					 "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�"+ info.getCurrencyName() +"����д��&nbsp;"
					+ info.getChineseAmount()
					+ "<br><br>"
					+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Сд��&nbsp;"
					+ info.getAmount()
					+ "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ޣ�"
					+ info.getInterval());
					
					sb.append(
					 "<br><br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;�ʣ�"
					+ info.getRate()
					+ "������Ϣ��<br>"
					+ "                <br>"
					+ "              </p>"
					+ "              "
					+ "            </div></td>"
					+ "        </tr>"
					+ "        <tr>"
					+ "          <td><p><br>"
					+ "              ��ע��<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;��֤ʵ����Դ���˿���֤ʵ��������Ϊ��Ѻ��Ȩ��ƾ֤��<br>"
					+ "              &nbsp;&nbsp;&nbsp;&nbsp;���浥һʽ���ݣ�������λ��һ�ݣ�����˾�ֶ��ݡ�<br>"
					+ "              <br>"
					+ "            </p>"
					+ "            </td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "    <td width=\"30\"><font style=\"FONT-SIZE: 13px\">��<br>��<br>"
					+ "      ��<br>"
					+ "      <br>"
					+ "      ��<br>"
					+ "      ��<br>"
					+ "      ��<br>"
					+ "      ��<br>"
					+ "      λ</font></td>"
					+ "  </tr>"
					+ "</table>"
					+ "<br>"
					+ "<table width=\"600\" border=\"0\">"
					+ "  <tr> "
					+ "    <td width=\"100%\" height=\"24\">"
					+ "<div align=\"left\"> "
					+ "[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCheckUserName())
					+ "&nbsp;&nbsp;&nbsp;&nbsp;[���ž���]&nbsp;"
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
                    +"    <td height=\"24\"><div align=\"center\"><font style=\"font-size:20px\"><b>����ҵ�λ���ڴ��֧ȡ֪ͨ</b></font></div></td>" 
                    +"  </tr>"  
                    +"  <tr> "  
                    +"    <td height=\"24\"> <div align=\"left\">"  
                    + DataFormat.formatStringForPrint(Env.getClientName())
                    +" �� </div></td>" 
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\"> "
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +" �ҵ�λ�����ƣ�__________________________Ԥ��_____��__��__�մ��Ҷ��ڴ���˻����˺�<u>"
                    + DataFormat.formatStringForPrint(info.getAccountNo())
                    +"</u>��</td>"  
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\">֧ȡ����ң���д��______________________________Ԫ��Сд��__________________Ԫ�������˾���趨��</td>" 
                    +"  </tr>"  
                    +"  <tr>" 
                    +"    <td height=\"24\">���ڽ���֧ȡ������ͬ��Ϣת������ָ��֮�˻���</td>" 
                    +"  </tr>" 
                    +"  <tr>"  
                    +"&nbsp;&nbsp;"
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"�˺ţ�</td>" 
                    +"  </tr>" 
                    +"  <tr>"  
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"�������У�</td>"  
                    +"  </tr>" 
                    +"  <tr>"  
                    +"    <td height=\"24\">"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    +"�ش�֪ͨ</td>"  
                    +"  </tr>" 
                    +"  <tr>" 
                    +"    <td height=\"34\"><div align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;��λԤ��ӡ����</div></td>"
                    +"  </tr>" 
                    +"  <tr>" 
                    +"    <td height=\"24\"><b>ע����֪ͨ��һ������ͬ���ڴ��֤ʵ���ύ����˾Ӫҵ����</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_____��__��__��</td>"  
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
	 * ��ʾ����ת�˽跽��Ʊ
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
					+ "      ����ת�˽跽��Ʊ</font></font></strong> ��</strong></TD>"
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
                    + " ��ӡ����: "
					+ info.getYear()
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " �� "
					+ "	</TD> "
					+ "		<TD align=\"right\" width=\"39%\">���ױ�ţ�"
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "		</TD>  "
					+ "		</TR> "
					+ "	</TABLE> "
					+ " <TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"Debtor-table1\"> "
					+ "  <TR>  "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"Debtor-td-rightbottom2left\">	 "
					+ "      ��<BR> "
					+ "      ��<BR> "
					+ "      ��<BR> "
					+ "      λ </TD> "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"Debtor-td-rightbottomtop\">	 "
					+ "      ȫ������ </TD> "
					+ "    <TD align=\"left\" width=\"214\" class=\"Debtor-td-rightbottomtop\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountName())
					+ "&nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"Debtor-td-rightbottomleft\">	 "
					+ "      ��<BR> "
					+ "      ��<BR> "
					+ "      ��<BR> "
					+ "      λ </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"Debtor-td-rightbottom\"> ȫ������ </TD> "
					+ "    <TD align=\"left\" width=\"214\" class=\"Debtor-td-rightbottomright\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountName())
					+ " &nbsp; </TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Debtor-td-rightbottom\">	 "
					+ "      �ˡ�����</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Debtor-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountNo())
					+ "    &nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"12%\" class=\"Debtor-td-rightbottom\"> �ˡ�����</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Debtor-td-bottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountNo())
					+ "    &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR> "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Debtor-td-rightbottom-bottom\">	 "
					+ "      �������� </TD> "
					+ "    <TD align=\"left\" class=\"Debtor-td-rightbottom-bottom\">"
					+ DataFormat.formatStringForPrint(info.getPayBankName())
					+ " &nbsp;</TD> "
					+ "   <TD align=\"center\" width=\"12%\" class=\"Debtor-td-rightbottom\"> �������� </TD> "
					+ "   <TD align=\"left\" class=\"Debtor-td-bottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveBankName())
					+ " &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" class=\"Debtor-td-rightbottom\"> ��<BR> "
					+ "      �� </TD> "
					+ "    <TD align=\"center\" class=\"Debtor-td-rightbottom\">"
					+ info.getCurrencyName()
					+ "<BR> "
					+ "      (��д) </TD> "
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
					+ "      ת<BR> "
					+ "      ��<BR> "
					+ "      ԭ<BR> "
					+ "      ��<BR> &nbsp; </TD> "
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
					+ "<td align=\"right\">[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;[������]&nbsp; "
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
	 * ��ʾ����ת�˴�����Ʊ
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
					+ "      ����ת�˴�����Ʊ</font></font></strong> ��</strong></TD>"
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
                    + " ��ӡ����: "
					+ info.getYear()
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " �� "
					+ "	</TD> "
					+ "		<TD align=\"right\" width=\"39%\">���ױ�ţ�"
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "		</TD>  "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "<TABLE width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"Credit-table1\"> "
					+ "  <TR>  "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"Credit-td-rightbottom\"> ��<BR> "
					+ "      ��<BR> "
					+ "      ��<BR> "
					+ "      λ </TD> "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"Credit-td-rightbottom\">	 "
					+ "      ȫ������ </TD> "
					+ "    <TD  align=\"left\" width=\214\" class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountName())
					+ " &nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"Credit-td-rightbottomleft\">	 "
					+ "      ��<BR> "
					+ "      ��<BR> "
					+ "      ��<BR> "
					+ "      λ </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"Credit-td-rightbottomtop\"> ȫ������ </TD> "
					+ "    <TD align=\"left\" width=\"214\" class=\"Credit-td-rightbottomtop\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountName())
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Credit-td-rightbottom\">	 "
					+ "      �ˡ�����</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayAccountNo())
					+ "    &nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"12%\" class=\"Credit-td-rightbottom\"> �ˡ�����</TD> "
					+ "    <TD align=\"left\" width=\"33%\" class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveAccountNo())
					+ "    &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" height=\"24\" width=\"12%\" class=\"Credit-td-rightbottom\"> "
					+ "      �������� </TD> "
					+ "    <TD class=\"Credit-td-rightbottom\">"
					+ DataFormat.formatStringForPrint(info.getPayBankName())
					+ "&nbsp; </TD> "
					+ "    <TD align=\"center\" width=\"12%\" class=\"Credit-td-rightbottom-bottom\"> ��������  "
					+ "    </TD> "
					+ "    <TD align=\"left\" class=\"Credit-td-rightbottom-bottom\">"
					+ DataFormat.formatStringForPrint(info.getReceiveBankName())
					+ " &nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" class=\"Credit-td-rightbottom\"> ��<BR> "
					+ "      �� </TD> "
					+ "    <TD align=\"center\" class=\"Credit-td-rightbottom\">"
					+ info.getCurrencyName()
					+ "<BR> "
					+ "      (��д) </TD> "
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
					+ "      ת<BR> "
					+ "      ��<BR> "
					+ "      ԭ<BR> "
					+ "      ��<BR> &nbsp; </TD> "
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
					+ "<td align=\"right\">[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;[������]&nbsp;"
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
	 *ȫ��ģ�棻��Ϣ֪ͨ��
	 * ��Ӫ֧ȡ
	 * ��Ӫ������Ϣ֪ͨ����һ�����տ����ƾ֤
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
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"������Ϣ֪ͨ��</font></b></font>"
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
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " ��"
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
					+ "    <TD width=\"50%\" align=\"left\">��λ���ƣ�"
					+ DataFormat.formatStringForPrint(info.getClientName())
					+ " </TD>"
					+ "    <TD width=\"50%\" align=\"right\">�˺����ͣ�"
					+ DataFormat.formatStringForPrint(info.getAccountType())
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD>���ױ�ţ�"
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</TD>"
					+ "    <TD align=\"right\">&nbsp;&nbsp;�˺ţ�"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "        <tr align=\"center\"> "
					+ "          <td class=\"td-rightbottom\" width=\"80\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��ֹ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\"align=\"center\">���ʣ�%��</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\"> ��Ϣ </td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������Ϣ</td>"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">����</td>"
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
					+ "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">���ڷ�Ϣ</td>"
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
					+ "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">��Ϣ�ܶ�</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getTotalInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������</td>"
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
					+ "                <td> ������Ϣ���������㵥λ�ڣ�"
					+ DataFormat.formatStringForPrint(info.getInterestAccountNo())
					+ "�����˻��� </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> ����Ӧ�˻��ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCurrentAccountNo())
					+ "  �� </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> ����Ӧ��ͬ�ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getContractNo())
					+ " �� </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> ����Ӧ�ſ�֪ͨ���ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getLoanBillNo())
					+ "�� </td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">ת�����ڣ�&nbsp; "
					+ DataFormat.formatStringForPrint(info.getTransAccountDate())
					+ " </td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> �����£� </td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>һ<BR>��<BR><BR>��<BR>��<BR>��<BR>��<BR>ƾ<BR>֤</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;[������]&nbsp;"
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
	 * ��Ӫ������Ϣ֪ͨ���ڶ����������տ�֪ͨ
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
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"������Ϣ֪ͨ��</font></b></font>"
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
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " ��"
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
					+ "    <TD width=\"50%\" align=\"left\"> ��λ���ƣ�"
					+ DataFormat.formatStringForPrint(info.getClientName())
					+ " </TD>"
					+ "    <TD width=\"50%\" align=\"right\">�˺����ͣ�"
					+ DataFormat.formatStringForPrint(info.getAccountType())
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD>���ױ�ţ�"
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</TD>"
					+ "    <TD align=\"right\">&nbsp;&nbsp;�˺ţ�"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "        <tr align=\"center\"> "
					+ "          <td class=\"td-rightbottom\" width=\"80\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��ֹ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\"align=\"center\">���ʣ�%��</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\">��Ϣ</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������Ϣ</td>"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">����</td>"
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
					+ "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">���ڷ�Ϣ</td>"
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
					+ "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">��Ϣ�ܶ�</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getTotalInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������</td>"
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
					+ "                <td> ������Ϣ���������㵥λ�ڣ�"
					+ DataFormat.formatStringForPrint(info.getInterestAccountNo())
					+ "�����˻��� </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> ����Ӧ�˻��ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCurrentAccountNo())
					+ "  ��</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> ����Ӧ��ͬ�ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getContractNo())
					+ " ��</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> ����Ӧ�ſ�֪ͨ���ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getLoanBillNo())
					+ "��</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">ת�����ڣ�&nbsp; "
					+ DataFormat.formatStringForPrint(info.getTransAccountDate())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> �����£� </td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>��<BR>��<BR>��<BR>��<BR>��<BR>ͨ<BR>֪</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;[������]&nbsp;"
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
	 * ��Ӫ������Ϣ֪ͨ����������ҵ���տ�֪ͨ
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
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"������Ϣ֪ͨ��</font></b></font>"
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
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " ��"
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
					+ "    <TD width=\"50%\" align=\"left\">��λ���ƣ�"
					+ DataFormat.formatStringForPrint(info.getClientName())
					+ " </TD>"
					+ "    <TD width=\"50%\" align=\"right\">�˺����ͣ�"
					+ DataFormat.formatStringForPrint(info.getAccountType())
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD>���ױ�ţ�"
					+ DataFormat.formatStringForPrint(info.getTransNo())
					+ "</TD>"
					+ "    <TD align=\"right\">&nbsp;&nbsp;�˺ţ�"
					+ DataFormat.formatStringForPrint(info.getAccountNo())
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "        <tr align=\"center\"> "
					+ "          <td class=\"td-rightbottom\" width=\"80\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��ֹ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\"align=\"center\">���ʣ�%��</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\"> ��Ϣ </td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������Ϣ</td>"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">����</td>"
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
					+ "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">���ڷ�Ϣ</td>"
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
					+ "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">��Ϣ�ܶ�</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"center\">&nbsp;"
					+ DataFormat.formatStringForPrint(info.getTotalInterest())
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������</td>"
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
					+ "                <td> ������Ϣ���������㵥λ�ڣ�"
					+ DataFormat.formatStringForPrint(info.getInterestAccountNo())
					+ "�����˻��� </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> ����Ӧ�˻��ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getCurrentAccountNo())
					+ "  �� </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> ����Ӧ��ͬ�ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getContractNo())
					+ " �� </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> ����Ӧ�ſ�֪ͨ���ţ�&nbsp;"
					+ DataFormat.formatStringForPrint(info.getLoanBillNo())
					+ "�� </td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">ת�����ڣ�&nbsp; "
					+ DataFormat.formatStringForPrint(info.getTransAccountDate())
					+ " </td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> �����£� </td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>ҵ<BR>��<BR>��<BR>��<BR>��<BR>ͨ<BR>֪</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[������]&nbsp;"
					+ DataFormat.formatStringForPrint(info.getInputUserName())
					+ "&nbsp;[������]&nbsp;"
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
     * ��Ӫ������Ϣ֪ͨ��������������跽ƾ֤
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
                    + "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"������Ϣ֪ͨ��</font></b></font>"
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
                    + " �� "
                    + info.getMonth()
                    + " �� "
                    + info.getDay()
                    + " ��"
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
                    + "    <TD width=\"50%\" align=\"left\">��λ���ƣ�"
                    + DataFormat.formatStringForPrint(info.getClientName())
                    + " </TD>"
                    + "    <TD width=\"50%\" align=\"right\">�˺����ͣ�"
                    + DataFormat.formatStringForPrint(info.getAccountType())
                    + " </TD>"
                    + "  </TR>"
                    + "  <TR>"
                    + "    <TD>���ױ�ţ�"
                    + DataFormat.formatStringForPrint(info.getTransNo())
                    + "</TD>"
                    + "    <TD align=\"right\">&nbsp;&nbsp;�˺ţ�"
                    + DataFormat.formatStringForPrint(info.getAccountNo())
                    + "</TD>"
                    + "  </TR>"
                    + "</TABLE>"
                    + " <TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
                    + "     <TR>"
                    + "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                    + "        <tr align=\"center\"> "
                    + "          <td class=\"td-rightbottom\" width=\"80\" align=\"center\">����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��ֹ����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">����</td>"
                    + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\"align=\"center\">���ʣ�%��</td>"
                    + "          <td class=\"td-bottom\" width=\"90\" align=\"center\"> ��Ϣ </td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������Ϣ</td>"
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
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">����</td>"
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
                    + "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">���ڷ�Ϣ</td>"
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
                    + "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">��Ϣ�ܶ�</td>"
                    + "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getTotalInterest())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������</td>"
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
                    + "                <td> ������Ϣ���������㵥λ�ڣ�"
                    + DataFormat.formatStringForPrint(info.getInterestAccountNo())
                    + "�����˻��� </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> ����Ӧ�˻��ţ�&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCurrentAccountNo())
                    + "  �� </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> ����Ӧ��ͬ�ţ�&nbsp;"
                    + DataFormat.formatStringForPrint(info.getContractNo())
                    + " �� </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> ����Ӧ�ſ�֪ͨ���ţ�&nbsp;"
                    + DataFormat.formatStringForPrint(info.getLoanBillNo())
                    + "�� </td>"
                    + "              </tr>"
                    + "            </table></td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"3\">ת�����ڣ�&nbsp; "
                    + DataFormat.formatStringForPrint(info.getTransAccountDate())
                    + " </td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> �����£� </td>"
                    + "        </tr>"
                    + "      </table></TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "</td><td>"
                    + " <TABLE width=\"30\" border=\"0\"> "
                    + "     <TR> "
                    + "         <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>��<BR>��<BR>��<BR>��<BR>ƾ<BR>֤</FONT> "
                    + "         </TD> "
                    + "     </TR> "
                    + " </TABLE> "
                    + "</td></tr></table>"
                    + "<br>"
                    + "<Table width=\"630  \" border=\"0\">"
                    + "  <TR align=\"right\"> "
                    + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
                    + "    <TD height=\"22\" nowrap>[������]&nbsp;"
                    + DataFormat.formatStringForPrint(info.getInputUserName())
                    + "&nbsp;[������]&nbsp;"
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
     * ��Ӫ������Ϣ֪ͨ���������������˸���֪ͨ
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
                    + "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"������Ϣ֪ͨ��</font></b></font>"
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
                    + " �� "
                    + info.getMonth()
                    + " �� "
                    + info.getDay()
                    + " ��"
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
                    + "    <TD width=\"50%\" align=\"left\">��λ���ƣ�"
                    + DataFormat.formatStringForPrint(info.getClientName())
                    + " </TD>"
                    + "    <TD width=\"50%\" align=\"right\">�˺����ͣ�"
                    + DataFormat.formatStringForPrint(info.getAccountType())
                    + " </TD>"
                    + "  </TR>"
                    + "  <TR>"
                    + "    <TD>���ױ�ţ�"
                    + DataFormat.formatStringForPrint(info.getTransNo())
                    + "</TD>"
                    + "    <TD align=\"right\">&nbsp;&nbsp;�˺ţ�"
                    + DataFormat.formatStringForPrint(info.getAccountNo())
                    + "</TD>"
                    + "  </TR>"
                    + "</TABLE>"
                    + " <TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
                    + "     <TR>"
                    + "    <TD> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                    + "        <tr align=\"center\"> "
                    + "          <td class=\"td-rightbottom\" width=\"80\" align=\"center\">����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��ֹ����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">����</td>"
                    + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\"align=\"center\">���ʣ�%��</td>"
                    + "          <td class=\"td-bottom\" width=\"90\" align=\"center\"> ��Ϣ </td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������Ϣ</td>"
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
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">����</td>"
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
                    + "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">���ڷ�Ϣ</td>"
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
                    + "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">��Ϣ�ܶ�</td>"
                    + "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
                    + "          <td width=\"90\" class=\"td-bottom\" align=\"center\">&nbsp;"
                    + DataFormat.formatStringForPrint(info.getTotalInterest())
                    + "</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������</td>"
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
                    + "                <td> ������Ϣ���������㵥λ�ڣ�"
                    + DataFormat.formatStringForPrint(info.getInterestAccountNo())
                    + "�����˻��� </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> ����Ӧ�˻��ţ�&nbsp;"
                    + DataFormat.formatStringForPrint(info.getCurrentAccountNo())
                    + "  �� </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> ����Ӧ��ͬ�ţ�&nbsp;"
                    + DataFormat.formatStringForPrint(info.getContractNo())
                    + " �� </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td> ����Ӧ�ſ�֪ͨ���ţ�&nbsp;"
                    + DataFormat.formatStringForPrint(info.getLoanBillNo())
                    + "�� </td>"
                    + "              </tr>"
                    + "            </table></td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"3\">ת�����ڣ�&nbsp; "
                    + DataFormat.formatStringForPrint(info.getTransAccountDate())
                    + " </td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> �����£� </td>"
                    + "        </tr>"
                    + "      </table></TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "</td><td>"
                    + " <TABLE width=\"30\" border=\"0\"> "
                    + "     <TR> "
                    + "         <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>��<BR>��<BR>��<BR>��<BR>��<BR>ͨ<BR>֪</FONT> "
                    + "         </TD> "
                    + "     </TR> "
                    + " </TABLE> "
                    + "</td></tr></table>"
                    + "<br>"
                    + "<Table width=\"630  \" border=\"0\">"
                    + "  <TR align=\"right\"> "
                    + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
                    + "    <TD height=\"22\" nowrap>[������]&nbsp;"
                    + DataFormat.formatStringForPrint(info.getInputUserName())
                    + "&nbsp;[������]&nbsp;"
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
	 *ȫ��ģ�棻��Ϣ֪ͨ��
	 * ί��֧ȡ
	 * ί�д�����Ϣ֪ͨ��
     * ��һ�� �տ����ƾ֤
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
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"������Ϣ֪ͨ��</font></b></font>"
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
					+ " ��ӡ����: "
                    + info.getYear()
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " ��"
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
					+ "    <TD width=\"46%\" align=\"left\">��λ���ƣ�"
					+ info.getClientName()
					+ " </TD>"
					+ "    <TD width=\"33%\" align=\"right\">ί�е�λ���ƣ�"
					+ info.getConsignClientName()
					+ " </TD>"
					+ "    <TD width=\"20%\" align=\"right\">�˺����ͣ�"
					+ info.getAccountType()
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD width=\"46%\" align=\"left\">���ױ�ţ�"
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
					+ "    <TD width=\"20%\" align=\"right\">�˺ţ�"
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
					+ "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">���ʣ�%��</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\">��Ϣ</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">������Ϣ</td>"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">����</td>"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">���ڷ�Ϣ</td>"
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
					+ "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">��Ϣ�ܶ�</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getTotalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������</td>"
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
					+ "                <td> ������Ϣ���������㵥λ��("
					+ info.getInterestAccountNo()
					+ ")���˻��� </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ�˻��ţ�&nbsp;"
					+ info.getCurrentAccountNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ��ͬ�ţ�&nbsp;"
					+ info.getContractNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ��ݺţ�&nbsp;"
					+ info.getLoanBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ�浥�ţ�&nbsp;"
					+ info.getDepositBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">ת�����ڣ�&nbsp;"
					+ info.getTransAccountDate()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(����)</td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>һ<BR>��<BR><BR>��<BR>��<BR>��<BR>��<BR>ƾ<BR>֤</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[������]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[������]&nbsp;"
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
	 *ȫ��ģ�棻��Ϣ֪ͨ��
	 * ί��֧ȡ
	 * ί�д�����Ϣ֪ͨ��
     * �ڶ��� �Ŵ����տ�֪ͨ
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
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"������Ϣ֪ͨ��</font></b></font>"
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
					+ " ��ӡ����: "
                    + info.getYear()
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " ��"
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
					+ "    <TD width=\"46%\" align=\"left\">��λ���ƣ�"
					+ info.getClientName()
					+ " </TD>"
					+ "    <TD width=\"33%\" align=\"right\">ί�е�λ���ƣ�"
					+ info.getConsignClientName()
					+ " </TD>"
					+ "    <TD width=\"20%\" align=\"right\">�˺����ͣ�"
					+ info.getAccountType()
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD width=\"46%\" align=\"left\">���ױ�ţ�"
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
					+ "    <TD width=\"20%\" align=\"right\">�˺ţ�"
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
					+ "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">���ʣ�%��</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\">��Ϣ</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">������Ϣ</td>"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">����</td>"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">���ڷ�Ϣ</td>"
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
					+ "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">��Ϣ�ܶ�</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getTotalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������</td>"
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
					+ "                <td> ������Ϣ���������㵥λ��("
					+ info.getInterestAccountNo()
					+ ")���˻��� </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ�˻��ţ�&nbsp;"
					+ info.getCurrentAccountNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ��ͬ�ţ�&nbsp;"
					+ info.getContractNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ��ݺţ�&nbsp;"
					+ info.getLoanBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ�浥�ţ�&nbsp;"
					+ info.getDepositBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">ת�����ڣ�&nbsp;"
					+ info.getTransAccountDate()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(����)</td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>��<BR>��<BR>��<BR>��<BR>��<BR>ͨ<BR>֪</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[������]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[������]&nbsp;"
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
	 * ȫ��ģ�棻��Ϣ֪ͨ��
	 * ί��֧ȡ
	 * ί�д�����Ϣ֪ͨ��
     * ������ ҵ���տ�֪ͨ
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
					+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"������Ϣ֪ͨ��</font></b></font>"
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
					+ " ��ӡ����: "
                    + info.getYear()
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " ��"
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
					+ "    <TD width=\"46%\" align=\"left\">��λ���ƣ�"
					+ info.getClientName()
					+ " </TD>"
					+ "    <TD width=\"33%\" align=\"right\">ί�е�λ���ƣ�"
					+ info.getConsignClientName()
					+ " </TD>"
					+ "    <TD width=\"20%\" align=\"right\">�˺����ͣ�"
					+ info.getAccountType()
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD width=\"46%\" align=\"left\">���ױ�ţ�"
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
					+ "    <TD width=\"20%\" align=\"right\">�˺ţ�"
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
					+ "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">���ʣ�%��</td>"
					+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\">��Ϣ</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">������Ϣ</td>"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">����</td>"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">���ڷ�Ϣ</td>"
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
					+ "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">��Ϣ�ܶ�</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getTotalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������</td>"
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
					+ "                <td> ������Ϣ���������㵥λ��("
					+ info.getInterestAccountNo()
					+ ")���˻��� </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ�˻��ţ�&nbsp;"
					+ info.getCurrentAccountNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ��ͬ�ţ�&nbsp;"
					+ info.getContractNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ��ݺţ�&nbsp;"
					+ info.getLoanBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ�浥�ţ�&nbsp;"
					+ info.getDepositBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">ת�����ڣ�&nbsp;"
					+ info.getTransAccountDate()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(����)</td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td><td>"
					+ "	<TABLE width=\"30\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>ҵ<BR>��<BR>��<BR>��<BR>��<BR>ͨ<BR>֪</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "</td></tr></table>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[������]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[������]&nbsp;"
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
	 *ȫ��ģ�棻��Ϣ֪ͨ��
	 * ί��֧ȡ
	 * ί�д�����Ϣ֪ͨ��
     * ������ ����跽ƾ֤
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
				+ "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"������Ϣ֪ͨ��</font></b></font>"
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
				+ " ��ӡ����: "
                + info.getYear()
				+ " �� "
				+ info.getMonth()
				+ " �� "
				+ info.getDay()
				+ " ��"
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
				+ "    <TD width=\"46%\" align=\"left\">��λ���ƣ�"
				+ info.getClientName()
				+ " </TD>"
				+ "    <TD width=\"33%\" align=\"right\">ί�е�λ���ƣ�"
				+ info.getConsignClientName()
				+ " </TD>"
				+ "    <TD width=\"20%\" align=\"right\">�˺����ͣ�"
				+ info.getAccountType()
				+ " </TD>"
				+ "  </TR>"
				+ "  <TR>"
				+ "    <TD width=\"46%\" align=\"left\">���ױ�ţ�"
				+ info.getTransNo()
				+ "</TD>"
				+ "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
				+ "    <TD width=\"20%\" align=\"right\">�˺ţ�"
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
				+ "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">����</td>"
				+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
				+ "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
				+ "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">����</td>"
				+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">����</td>"
				+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">���ʣ�%��</td>"
				+ "          <td class=\"td-bottom\" width=\"90\" align=\"center\">��Ϣ</td>"
				+ "        </tr>"
				+ "        <tr align=\"center\"> "
				+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">������Ϣ</td>"
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
				+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">����</td>"
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
				+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">���ڷ�Ϣ</td>"
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
				+ "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">��Ϣ�ܶ�</td>"
				+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
				+ "&nbsp;</td>"
				+ "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
				+ info.getTotalInterest()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr align=\"center\"> "
				+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������</td>"
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
				+ "                <td> ������Ϣ���������㵥λ��("
				+ info.getInterestAccountNo()
				+ ")���˻��� </td>"
				+ "              </tr>"
				+ "              <tr> "
				+ "                <td>(��Ӧ�˻��ţ�&nbsp;"
				+ info.getCurrentAccountNo()
				+ ")</td>"
				+ "              </tr>"
				+ "              <tr> "
				+ "                <td>(��Ӧ��ͬ�ţ�&nbsp;"
				+ info.getContractNo()
				+ ")</td>"
				+ "              </tr>"
				+ "              <tr> "
				+ "                <td>(��Ӧ��ݺţ�&nbsp;"
				+ info.getLoanBillNo()
				+ ")</td>"
				+ "              </tr>"
				+ "              <tr> "
				+ "                <td>(��Ӧ�浥�ţ�&nbsp;"
				+ info.getDepositBillNo()
				+ ")</td>"
				+ "              </tr>"
				+ "            </table></td>"
				+ "        </tr>"
				+ "        <tr> "
				+ "          <td colspan=\"3\">ת�����ڣ�&nbsp;"
				+ info.getTransAccountDate()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr> "
				+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(����)</td>"
				+ "        </tr>"
				+ "      </table></TD>"
				+ "		</TR>"
				+ "	</TABLE>"
				+ "</td><td>"
				+ "	<TABLE width=\"30\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"> "
				+ "		<TR> "
				+ "			<TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>��<BR>��<BR>��<BR>��<BR>ƾ<BR>֤</FONT> "
				+ "			</TD> "
				+ "		</TR> "
				+ "	</TABLE> "
				+ "</td></tr></table>"
				+ "<br>"
				+ "<Table width=\"630  \" border=\"0\">"
				+ "  <TR align=\"right\"> "
				+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
				+ "    <TD height=\"22\" nowrap>[������]&nbsp;"
				+ info.getInputUserName()
				+ "&nbsp;[������]&nbsp;"
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
 * ȫ��ģ�棻��Ϣ֪ͨ��
 * ί��֧ȡ
 * ί�д�����Ϣ֪ͨ��
 * ������ ���λ����
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
                + "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"������Ϣ֪ͨ��</font></b></font>"
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
                + " ��ӡ����: "
                + info.getYear()
                + " �� "
                + info.getMonth()
                + " �� "
                + info.getDay()
                + " ��"
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
                + "    <TD width=\"46%\" align=\"left\">��λ���ƣ�"
                + info.getClientName()
                + " </TD>"
                + "    <TD width=\"33%\" align=\"right\">ί�е�λ���ƣ�"
                + info.getConsignClientName()
                + " </TD>"
                + "    <TD width=\"20%\" align=\"right\">�˺����ͣ�"
                + info.getAccountType()
                + " </TD>"
                + "  </TR>"
                + "  <TR>"
                + "    <TD width=\"46%\" align=\"left\">���ױ�ţ�"
                + info.getTransNo()
                + "</TD>"
                + "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
                + "    <TD width=\"20%\" align=\"right\">�˺ţ�"
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
                + "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">����</td>"
                + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
                + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
                + "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">����</td>"
                + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">����</td>"
                + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">���ʣ�%��</td>"
                + "          <td class=\"td-bottom\" width=\"90\" align=\"center\">��Ϣ</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">������Ϣ</td>"
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
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">����</td>"
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
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">���ڷ�Ϣ</td>"
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
                + "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">��Ϣ�ܶ�</td>"
                + "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
                + "&nbsp;</td>"
                + "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getTotalInterest()
                + "</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������</td>"
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
                + "                <td> ������Ϣ���������㵥λ��("
                + info.getInterestAccountNo()
                + ")���˻��� </td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(��Ӧ�˻��ţ�&nbsp;"
                + info.getCurrentAccountNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(��Ӧ��ͬ�ţ�&nbsp;"
                + info.getContractNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(��Ӧ��ݺţ�&nbsp;"
                + info.getLoanBillNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(��Ӧ�浥�ţ�&nbsp;"
                + info.getDepositBillNo()
                + ")</td>"
                + "              </tr>"
                + "            </table></td>"
                + "        </tr>"
                + "        <tr> "
                + "          <td colspan=\"3\">ת�����ڣ�&nbsp;"
                + info.getTransAccountDate()
                + "</td>"
                + "        </tr>"
                + "        <tr> "
                + "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(����)</td>"
                + "        </tr>"
                + "      </table></TD>"
                + "     </TR>"
                + " </TABLE>"
                + "</td><td>"
                + " <TABLE width=\"30\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"> "
                + "     <TR> "
                + "         <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>��<BR>��<BR>��<BR>λ<BR>��<BR>��</FONT> "
                + "         </TD> "
                + "     </TR> "
                + " </TABLE> "
                + "</td></tr></table>"
                + "<br>"
                + "<Table width=\"630  \" border=\"0\">"
                + "  <TR align=\"right\"> "
                + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
                + "    <TD height=\"22\" nowrap>[������]&nbsp;"
                + info.getInputUserName()
                + "&nbsp;[������]&nbsp;"
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
     *ȫ��ģ�棻��Ϣ֪ͨ��
     * ί��֧ȡ
     * ί�д�����Ϣ֪ͨ��
     * ������ �����˸���֪ͨ
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
                + "</font><br><font style=\"font-size:20px\">"+Constant.CurrencyType.getName(info.getCurrencyID())+"������Ϣ֪ͨ��</font></b></font>"
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
                + " ��ӡ����: "
                + info.getYear()
                + " �� "
                + info.getMonth()
                + " �� "
                + info.getDay()
                + " ��"
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
                + "    <TD width=\"46%\" align=\"left\">��λ���ƣ�"
                + info.getClientName()
                + " </TD>"
                + "    <TD width=\"33%\" align=\"right\">ί�е�λ���ƣ�"
                + info.getConsignClientName()
                + " </TD>"
                + "    <TD width=\"20%\" align=\"right\">�˺����ͣ�"
                + info.getAccountType()
                + " </TD>"
                + "  </TR>"
                + "  <TR>"
                + "    <TD width=\"46%\" align=\"left\">���ױ�ţ�"
                + info.getTransNo()
                + "</TD>"
                + "    <TD width=\"33%\" align=\"right\">&nbsp;</TD>"
                + "    <TD width=\"20%\" align=\"right\">�˺ţ�"
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
                + "          <td class=\"td-rightbottom\" width=\"80\" height=\"7\" align=\"center\">����</td>"
                + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
                + "          <td class=\"td-rightbottom\" width=\"105\" align=\"center\">��Ϣ����</td>"
                + "          <td class=\"td-rightbottom\" width=\"75\" align=\"center\">����</td>"
                + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"110\" align=\"center\">����</td>"
                + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">���ʣ�%��</td>"
                + "          <td class=\"td-bottom\" width=\"90\" align=\"center\">��Ϣ</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">������Ϣ</td>"
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
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">����</td>"
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
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\">���ڷ�Ϣ</td>"
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
                + "          <td width=\"80\"  height=\"7\"  class=\"td-rightbottom\" align=\"center\">��Ϣ�ܶ�</td>"
                + "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;"
                + "&nbsp;</td>"
                + "          <td width=\"90\" height=\"16\" class=\"td-bottom\" align=\"right\">&nbsp;"
                + info.getTotalInterest()
                + "</td>"
                + "        </tr>"
                + "        <tr align=\"center\"> "
                + "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">������</td>"
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
                + "                <td> ������Ϣ���������㵥λ��("
                + info.getInterestAccountNo()
                + ")���˻��� </td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(��Ӧ�˻��ţ�&nbsp;"
                + info.getCurrentAccountNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(��Ӧ��ͬ�ţ�&nbsp;"
                + info.getContractNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(��Ӧ��ݺţ�&nbsp;"
                + info.getLoanBillNo()
                + ")</td>"
                + "              </tr>"
                + "              <tr> "
                + "                <td>(��Ӧ�浥�ţ�&nbsp;"
                + info.getDepositBillNo()
                + ")</td>"
                + "              </tr>"
                + "            </table></td>"
                + "        </tr>"
                + "        <tr> "
                + "          <td colspan=\"3\">ת�����ڣ�&nbsp;"
                + info.getTransAccountDate()
                + "</td>"
                + "        </tr>"
                + "        <tr> "
                + "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(����)</td>"
                + "        </tr>"
                + "      </table></TD>"
                + "     </TR>"
                + " </TABLE>"
                + "</td><td>"
                + " <TABLE width=\"30\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\"> "
                + "     <TR> "
                + "         <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>��<BR>��<BR>��<BR>��<BR>��<BR>ͨ<BR>֪</FONT> "
                + "         </TD> "
                + "     </TR> "
                + " </TABLE> "
                + "</td></tr></table>"
                + "<br>"
                + "<Table width=\"630  \" border=\"0\">"
                + "  <TR align=\"right\"> "
                + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp; </TD>"
                + "    <TD height=\"22\" nowrap>[������]&nbsp;"
                + info.getInputUserName()
                + "&nbsp;[������]&nbsp;"
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
	 * ȫ��ģ�棻��Ϣ֪ͨ��
	 * ����
     * ��һ�� �ص�
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
					+ "������Ϣ֪ͨ��</font></b>"
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
					+ " ��ӡ����: "
                    + info.getYear()
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " ��"
					+ "			</TD>"
					+ "		</TR>"
					+ "	</TABLE><BR>"
					+ "	"
					+ "<TABLE width=\"600\" border=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"50%\" align=\"left\">�˻����ƣ�"
					+ info.getAccountName()
					+ "    <TD width=\"50%\" align=\"right\">�˺����ͣ�"
					+ info.getAccountType()
					+ " </TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD width=\"50%\" align=\"left\">���ױ�ţ�"
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD width=\"50%\" align=\"right\">�˺ţ�"
					+ info.getAccountNo()
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "	<TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
					+ "		<TR>"
					+ "    <TD height=\"24\"> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
					+ "        <tr align=\"center\"> "
					+ "          <td class=\"td-rightbottom\" width=\"77\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">��Ϣ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">��Ϣ����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"39\" align=\"center\">����</td>"
					+ "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"130\" align=\"center\">ƽ�����</td>"
					+ "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">����(%)</td>"
					+ "          <td class=\"td-bottom\" width=\"123\" align=\"center\">��Ϣ</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">������Ϣ</td>"
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
					+ "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">Э����Ϣ</td>"
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
					+ "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">��Ϣ�ϼ�</td>"
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
					+ "                <td> ������Ϣ�����ո��㵥λ��("
					+ info.getInterestAccountNo()
					+ ")���˻��� </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ�˻��ţ�&nbsp;"
					+ info.getCurrentAccountNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ��ͬ�ţ�&nbsp;"
					+ info.getContractNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ��ݺţ�&nbsp;"
					+ info.getLoanBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td>(��Ӧ�浥�ţ�&nbsp;"
					+ info.getDepositBillNo()
					+ ")</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">ת�����ڣ�&nbsp;"
					+ info.getTransAccountDate()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(����)</td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "<br>"
					+ "<Table width=\"630  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp;</TD>"
					+ "    <TD height=\"22\" nowrap>[������]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[������]&nbsp;"
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
     * ȫ��ģ�棻��Ϣ֪ͨ��
     * ����
     * �ڶ��� ����ƾ֤
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
                    + "������Ϣ֪ͨ��</font></b>"
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
                    + " ��ӡ����: "
                    + info.getYear()
                    + " �� "
                    + info.getMonth()
                    + " �� "
                    + info.getDay()
                    + " ��"
                    + "         </TD>"
                    + "     </TR>"
                    + " </TABLE><BR>"
                    + " "
                    + "<TABLE width=\"600\" border=\"0\">"
                    + "  <TR> "
                    + "    <TD width=\"50%\" align=\"left\">�˻����ƣ�"
                    + info.getAccountName()
                    + "    <TD width=\"50%\" align=\"right\">�˺����ͣ�"
                    + info.getAccountType()
                    + " </TD>"
                    + "  </TR>"
                    + "  <TR>"
                    + "    <TD width=\"50%\" align=\"left\">���ױ�ţ�"
                    + info.getTransNo()
                    + "</TD>"
                    + "    <TD width=\"50%\" align=\"right\">�˺ţ�"
                    + info.getAccountNo()
                    + "</TD>"
                    + "  </TR>"
                    + "</TABLE>"
                    + " <TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
                    + "     <TR>"
                    + "    <TD height=\"24\"> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                    + "        <tr align=\"center\"> "
                    + "          <td class=\"td-rightbottom\" width=\"77\" align=\"center\">����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">��Ϣ����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">��Ϣ����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"39\" align=\"center\">����</td>"
                    + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"130\" align=\"center\">ƽ�����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">����(%)</td>"
                    + "          <td class=\"td-bottom\" width=\"123\" align=\"center\">��Ϣ</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">������Ϣ</td>"
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
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">Э����Ϣ</td>"
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
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">��Ϣ�ϼ�</td>"
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
                    + "                <td> ������Ϣ�����ո��㵥λ��("
                    + info.getInterestAccountNo()
                    + ")���˻��� </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(��Ӧ�˻��ţ�&nbsp;"
                    + info.getCurrentAccountNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(��Ӧ��ͬ�ţ�&nbsp;"
                    + info.getContractNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(��Ӧ��ݺţ�&nbsp;"
                    + info.getLoanBillNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(��Ӧ�浥�ţ�&nbsp;"
                    + info.getDepositBillNo()
                    + ")</td>"
                    + "              </tr>"
                    + "            </table></td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"3\">ת�����ڣ�&nbsp;"
                    + info.getTransAccountDate()
                    + "</td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(����)</td>"
                    + "        </tr>"
                    + "      </table></TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "<br>"
                    + "<Table width=\"630  \" border=\"0\">"
                    + "  <TR align=\"right\"> "
                    + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp;</TD>"
                    + "    <TD height=\"22\" nowrap>[������]&nbsp;"
                    + info.getInputUserName()
                    + "&nbsp;[������]&nbsp;"
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
     * ȫ��ģ�棻��Ϣ֪ͨ��
     * ����
     * ������ ��Ϣƾ֤
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
                    + "������Ϣ֪ͨ��</font></b>"
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
                    + " ��ӡ����: "
                    + info.getYear()
                    + " �� "
                    + info.getMonth()
                    + " �� "
                    + info.getDay()
                    + " ��"
                    + "         </TD>"
                    + "     </TR>"
                    + " </TABLE><BR>"
                    + " "
                    + "<TABLE width=\"600\" border=\"0\">"
                    + "  <TR> "
                    + "    <TD width=\"50%\" align=\"left\">�˻����ƣ�"
                    + info.getAccountName()
                    + "    <TD width=\"50%\" align=\"right\">�˺����ͣ�"
                    + info.getAccountType()
                    + " </TD>"
                    + "  </TR>"
                    + "  <TR>"
                    + "    <TD width=\"50%\" align=\"left\">���ױ�ţ�"
                    + info.getTransNo()
                    + "</TD>"
                    + "    <TD width=\"50%\" align=\"right\">�˺ţ�"
                    + info.getAccountNo()
                    + "</TD>"
                    + "  </TR>"
                    + "</TABLE>"
                    + " <TABLE width=\"630\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
                    + "     <TR>"
                    + "    <TD height=\"24\"> <table width=\"630\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"table1\">"
                    + "        <tr align=\"center\"> "
                    + "          <td class=\"td-rightbottom\" width=\"77\" align=\"center\">����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">��Ϣ����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"85\" align=\"center\">��Ϣ����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"39\" align=\"center\">����</td>"
                    + "          <td class=\"td-rightbottom\" colspan=\"2\" width=\"130\" align=\"center\">ƽ�����</td>"
                    + "          <td class=\"td-rightbottom\" width=\"65\" align=\"center\">����(%)</td>"
                    + "          <td class=\"td-bottom\" width=\"123\" align=\"center\">��Ϣ</td>"
                    + "        </tr>"
                    + "        <tr align=\"center\"> "
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">������Ϣ</td>"
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
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">Э����Ϣ</td>"
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
                    + "          <td width=\"77\" height=\"7\" class=\"td-rightbottom\">��Ϣ�ϼ�</td>"
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
                    + "                <td> ������Ϣ�����ո��㵥λ��("
                    + info.getInterestAccountNo()
                    + ")���˻��� </td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(��Ӧ�˻��ţ�&nbsp;"
                    + info.getCurrentAccountNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(��Ӧ��ͬ�ţ�&nbsp;"
                    + info.getContractNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(��Ӧ��ݺţ�&nbsp;"
                    + info.getLoanBillNo()
                    + ")</td>"
                    + "              </tr>"
                    + "              <tr> "
                    + "                <td>(��Ӧ�浥�ţ�&nbsp;"
                    + info.getDepositBillNo()
                    + ")</td>"
                    + "              </tr>"
                    + "            </table></td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"3\">ת�����ڣ�&nbsp;"
                    + info.getTransAccountDate()
                    + "</td>"
                    + "        </tr>"
                    + "        <tr> "
                    + "          <td colspan=\"5\" class=\"td-right\" align=\"right\">(����)</td>"
                    + "        </tr>"
                    + "      </table></TD>"
                    + "     </TR>"
                    + " </TABLE>"
                    + "<br>"
                    + "<Table width=\"630  \" border=\"0\">"
                    + "  <TR align=\"right\"> "
                    + "  <TD colspan=\"6\" height=\"22\" nowrap>&nbsp;</TD>"
                    + "    <TD height=\"22\" nowrap>[������]&nbsp;"
                    + info.getInputUserName()
                    + "&nbsp;[������]&nbsp;"
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
	 * �����Ϣ�Ƹ�֪ͨ��
	 * ��һ�� ��Ϣ�ص�
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
					+ "<FONT face=\"Arial Black\"><font style=\"font-size:20px\">�����Ϣ�Ƹ�֪ͨ��</font></FONT></strong></td>"
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
					+ " ��ӡ����: "
                    + info.getYear()
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " ��</font></td>"
					+ "<td width=\"254\" align=\"center\" colspan=\"3\" ><font style=\"font-size:13px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ױ�ţ�"
					+ info.getTransNo()
					+ "</font></td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td width=\"200\" align=\"left\" nowrap><font style=\"font-size:13px\">��λ���ƣ�<U>"
					+ info.getClientName()
					+ "</U></font></td>"
					+ "<td width=\"35\" align=\"right\">&nbsp;</td>"
					+ " "
					+ "    "
					+ "<td width=\"152\"> "
					+ "      "
					+ "<font style=\"font-size:13px\">�˺ţ�<U>"
					+ info.getAccountNo()
					+ "</U></font></td>"
					+ "<td width=\"74\">&nbsp;</td>"
					+ "<td width=\"170\" align=\"right\"><font style=\"font-size:13px\">������ࣺ<U>"
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
					+ "<td class=\"td-rightbottom\" colspan=\"12\" align=\"center\"><font style=\"font-size:13px\">����</font></td>"
					+ "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">��Ϣ����</font></td>"
					+ "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">��������</font></td>"
					+ "<td class=\"td-rightbottom\" width=\"6%\" align=\"center\"><font style=\"font-size:13px\">����</font></td>"
					+ "<td class=\"td-rightbottom\" width=\"5%\" align=\"center\"><font style=\"font-size:13px\">����%</font></td>"
					+ "<td class=\"td-bottom\" width=\"8%\" colspan=\"10\" align=\"center\"><font style=\"font-size:13px\">��Ϣ</font></td>"
					+ "</tr>"
					+ "        "
                    
                    
				+ "<tr>" //��ʾ����<!--------1---------!>
			    +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
				+info.getAmount1()
				+ "</font></td>"
                //��ʾ��Ϣ����
			    +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getBeginYear1()
				+ "</font></td>"
				+ "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getBeginMonth1()
				+ "</font></td>"
				+ "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getBeginDay1()
				+ "</font></td>" //��ʾ��Ϣ����
				+"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getOverYear1()
				+ "</font></td>"
				+ "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getOverMonth1()
				+ "</font></td>"
				+ "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getOverDay1()
				+ "</font></td>" //����
				+"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber1() + "</font></td>" //����
				+"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate1() + "</td>" //��Ϣ
				+"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
				+ info.getInterest1()
				+ "</font></td>"
				+ "</tr>"
                
				+ "        "
				+ "        "
				+ "<tr>" //��ʾ����<!--------2---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount2()
                + "</font></td>"
                //��ʾ��Ϣ����
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay2()
                + "</font></td>" //��ʾ��Ϣ����
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay2()
                + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber2() + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate2() + "</td>" //��Ϣ
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                +info.getInterest2()
                + "</font></td>"
                + "</tr>"
				+ "        "
				+ "        "
				+ "<tr>" //��ʾ����<!--------3---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount3()
                + "</font></td>"
                //��ʾ��Ϣ����
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay3()
                + "</font></td>" //��ʾ��Ϣ����
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay3()
                + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber3() + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate3() + "</td>" //��Ϣ
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest3()
                + "</font></td>"
                + "</tr>"
				+ "        "
				+ "        "
				+ "<tr>" //��ʾ����<!--------4---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount4()
                + "</font></td>"
                //��ʾ��Ϣ����
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay4()
                + "</font></td>" //��ʾ��Ϣ����
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay4()
                + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber4() + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate4() + "</td>" //��Ϣ
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
				+ "<td class=\"td-rightbottom\" colspan=\"20\"><font style=\"font-size:13px\">�ϼƣ���д����"
				+ info.getTotalInterestChinese()
				+ "</font></td>"
				+ "<td class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
				+"��"+ info.getTotalInterest()
				+ "</font></td>"
				+ "</tr>"
				+ "        "
				+ "<tr>"
				+ "<td colspan=\"44\">"
				+ "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				+ "<tr>"
				+ "<td width=\"50%\"><font style=\"font-size:13px\">��ע���浥�ţ�"
				+ info.getDepositBillNo()
				+ "</font></td>"
				+ "<td width=\"50%\">&nbsp;"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td width=\"100%\" colspan=\"2\"><font style=\"font-size:13px\">������Ϣ�����ո��㵥λ�ڣ�"
				+ info.getInterestAccountNo()
				+ "�����˻�</font></td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "      "
				+ "</table>"
				+ "</td>"
				+ "<td width=\"20\" align=\"center\">��<br>"
				+ "һ<br>"
				+ "��<br>"
				+ "<br>"
				+ "��<br>"
				+ "Ϣ<br>"
				+ "��<br>"
				+ "��<br>"
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
	 *ȫ��ģ�棻�����Ϣ�Ƹ�֪ͨ��
	 * �ڶ��� ����ƾ֤
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
                    + "<FONT face=\"Arial Black\"><font style=\"font-size:20px\">�����Ϣ�Ƹ�֪ͨ��</font></FONT></strong></td>"
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
                    + " ��ӡ����: "
                    + info.getYear()
                    + " �� "
                    + info.getMonth()
                    + " �� "
                    + info.getDay()
                    + " ��</font></td>"
                    + "<td width=\"254\" align=\"center\" colspan=\"3\" ><font style=\"font-size:13px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ױ�ţ�"
                    + info.getTransNo()
                    + "</font></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td width=\"200\" align=\"left\" nowrap><font style=\"font-size:13px\">��λ���ƣ�<U>"
                    + info.getClientName()
                    + "</U></font></td>"
                    + "<td width=\"35\" align=\"right\">&nbsp;</td>"
                    + " "
                    + "    "
                    + "<td width=\"152\"> "
                    + "      "
                    + "<font style=\"font-size:13px\">�˺ţ�<U>"
                    + info.getAccountNo()
                    + "</U></font></td>"
                    + "<td width=\"74\">&nbsp;</td>"
                    + "<td width=\"170\" align=\"right\"><font style=\"font-size:13px\">������ࣺ<U>"
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
                    + "<td class=\"td-rightbottom\" colspan=\"12\" align=\"center\"><font style=\"font-size:13px\">����</font></td>"
                    + "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">��Ϣ����</font></td>"
                    + "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">��������</font></td>"
                    + "<td class=\"td-rightbottom\" width=\"6%\"  align=\"center\"><font style=\"font-size:13px\">����</font></td>"
                    + "<td class=\"td-rightbottom\"  width=\"5%\" align=\"center\"><font style=\"font-size:13px\">����%</font></td>"
                    + "<td class=\"td-bottom\" width=\"8%\" colspan=\"10\" align=\"center\"><font style=\"font-size:13px\">��Ϣ</font></td>"
                    + "</tr>"
                    + "        "
                 + "<tr>" //��ʾ����<!--------1---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                
                +info.getAmount1()
                + "</font></td>"
                //��ʾ��Ϣ����
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear1()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth1()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay1()
                + "</font></td>" //��ʾ��Ϣ����
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear1()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth1()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay1()
                + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber1() + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate1() + "</td>" //��Ϣ
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest1()
                + "</font></td>"
                + "</tr>"
                
                + "        "
                + "        "
                + "<tr>" //��ʾ����<!--------2---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount2()
                + "</font></td>"
                //��ʾ��Ϣ����
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay2()
                + "</font></td>" //��ʾ��Ϣ����
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay2()
                + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber2() + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate2() + "</td>" //��Ϣ
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                +info.getInterest2()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "        "
                + "<tr>" //��ʾ����<!--------3---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount3()
                + "</font></td>"
                //��ʾ��Ϣ����
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay3()
                + "</font></td>" //��ʾ��Ϣ����
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay3()
                + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber3() + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate3() + "</td>" //��Ϣ
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest3()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "        "
                + "<tr>" //��ʾ����<!--------4---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount4()
                + "</font></td>"
                //��ʾ��Ϣ����
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay4()
                + "</font></td>" //��ʾ��Ϣ����
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay4()
                + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber4() + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate4() + "</td>" //��Ϣ
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
                + "<td class=\"td-rightbottom\" colspan=\"20\"><font style=\"font-size:13px\">�ϼƣ���д����"
                + info.getTotalInterestChinese()
                + "</font></td>"
                + "<td class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                +"��"+ info.getTotalInterest()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "<tr>"
                + "<td colspan=\"44\">"
                + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                + "<tr>"
                + "<td width=\"50%\"><font style=\"font-size:13px\">��ע���浥�ţ�"
                + info.getDepositBillNo()
                + "</font></td>"
                + "<td width=\"50%\">&nbsp;"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td width=\"100%\" colspan=\"2\"><font style=\"font-size:13px\">������Ϣ�����ո��㵥λ�ڣ�"
                + info.getInterestAccountNo()
                + "�����˻�</font></td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "      "
                + "</table>"
                + "</td>"
                + "<td width=\"20\" align=\"center\">��<br>"
                + "��<br>"
                + "��<br>"
                + "<br>"
                + "��<br>"
                + "��<br>"
                + "ƾ<br>"
                + "֤<br>"
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
     *ȫ��ģ�棻�����Ϣ�Ƹ�֪ͨ��
     * ������ ��Ϣƾ֤
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
                    + "<FONT face=\"Arial Black\"><font style=\"font-size:20px\">�����Ϣ�Ƹ�֪ͨ��</font></FONT></strong></td>"
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
                    + " ��ӡ����: "
                    + info.getYear()
                    + " �� "
                    + info.getMonth()
                    + " �� "
                    + info.getDay()
                    + " ��</font></td>"
                    + "<td width=\"254\" align=\"center\" colspan=\"3\" ><font style=\"font-size:13px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ױ�ţ�"
                    + info.getTransNo()
                    + "</font></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td width=\"200\" align=\"left\" nowrap><font style=\"font-size:13px\">��λ���ƣ�<U>"
                    + info.getClientName()
                    + "</U></font></td>"
                    + "<td width=\"35\" align=\"right\">&nbsp;</td>"
                    + " "
                    + "    "
                    + "<td width=\"152\"> "
                    + "      "
                    + "<font style=\"font-size:13px\">�˺ţ�<U>"
                    + info.getAccountNo()
                    + "</U></font></td>"
                    + "<td width=\"74\">&nbsp;</td>"
                    + "<td width=\"170\" align=\"right\"><font style=\"font-size:13px\">������ࣺ<U>"
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
                    + "<td class=\"td-rightbottom\" colspan=\"12\" align=\"center\"><font style=\"font-size:13px\">����</font></td>"
                    + "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">��Ϣ����</font></td>"
                    + "<td class=\"td-rightbottom\" align=\"center\" colspan=\"3\"><font style=\"font-size:13px\">��������</font></td>"
                    + "<td class=\"td-rightbottom\" width=\"6%\"  align=\"center\"><font style=\"font-size:13px\">����</font></td>"
                    + "<td class=\"td-rightbottom\"  width=\"5%\" align=\"center\"><font style=\"font-size:13px\">����%</font></td>"
                    + "<td class=\"td-bottom\" width=\"8%\" colspan=\"10\" align=\"center\"><font style=\"font-size:13px\">��Ϣ</font></td>"
                    + "</tr>"
                    + "        "
                  + "<tr>" //��ʾ����<!--------1---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                
                +info.getAmount1()
                + "</font></td>"
                //��ʾ��Ϣ����
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear1()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth1()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay1()
                + "</font></td>" //��ʾ��Ϣ����
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear1()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth1()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay1()
                + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber1() + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate1() + "</td>" //��Ϣ
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest1()
                + "</font></td>"
                + "</tr>"
                
                + "        "
                + "        "
                + "<tr>" //��ʾ����<!--------2---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount2()
                + "</font></td>"
                //��ʾ��Ϣ����
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay2()
                + "</font></td>" //��ʾ��Ϣ����
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear2()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth2()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay2()
                + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber2() + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate2() + "</td>" //��Ϣ
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                +info.getInterest2()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "        "
                + "<tr>" //��ʾ����<!--------3---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount3()
                + "</font></td>"
                //��ʾ��Ϣ����
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay3()
                + "</font></td>" //��ʾ��Ϣ����
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear3()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth3()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay3()
                + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber3() + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate3() + "</td>" //��Ϣ
                +"<td  class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                + info.getInterest3()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "        "
                + "<tr>" //��ʾ����<!--------4---------!>
                +"<td class=\"td-rightbottom\"  align=\"right\" colspan=\"12\"><font style=\"font-size:10px\">&nbsp;"
                + info.getAmount4()
                + "</font></td>"
                //��ʾ��Ϣ����
                +"<td  class=\"td-bottom\"  align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getBeginDay4()
                + "</font></td>" //��ʾ��Ϣ����
                +"<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverYear4()
                + "</font></td>"
                + "<td class=\"td-bottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverMonth4()
                + "</font></td>"
                + "<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;"
                + info.getOverDay4()
                + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\"> &nbsp;" + info.getDayNumber4() + "</font></td>" //����
                +"<td class=\"td-rightbottom\"   align=\"center\"><font style=\"font-size:10px\">&nbsp;" + info.getRate4() + "</td>" //��Ϣ
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
                + "<td class=\"td-rightbottom\" colspan=\"20\"><font style=\"font-size:13px\">�ϼƣ���д����"
                + info.getTotalInterestChinese()
                + "</font></td>"
                + "<td class=\"td-bottom\"  align=\"right\" colspan=\"10\"><font style=\"font-size:10px\">&nbsp;"
                +"��"+ info.getTotalInterest()
                + "</font></td>"
                + "</tr>"
                + "        "
                + "<tr>"
                + "<td colspan=\"44\">"
                + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                + "<tr>"
                + "<td width=\"50%\"><font style=\"font-size:13px\">��ע���浥�ţ�"
                + info.getDepositBillNo()
                + "</font></td>"
                + "<td width=\"50%\">&nbsp;"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td width=\"100%\" colspan=\"2\"><font style=\"font-size:13px\">������Ϣ�����ո��㵥λ�ڣ�"
                + info.getInterestAccountNo()
                + "�����˻�</font></td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "      "
                + "</table>"
                + "</td>"
                + "<td width=\"20\" align=\"center\">��<br>"
                + "��<br>"
                + "��<br>"
                + "<br>"
                + "��<br>"
                + "Ϣ<br>"
                + "ƾ<br>"
                + "֤<br>"
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
     * ��ӡ�������շ��嵥
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
            		+"<TD  height=20 width=\"14%\" class=\"In1-td-rightbottom\" align=\"center\">�˻����</TD>"
            		+"<TD  height=20 width=\"13%\" class=\"In1-td-rightbottom\" align=\"center\">�˻�����</TD>"
            		+"<TD  height=20 width=\"11%\" class=\"In1-td-rightbottom\" align=\"center\">��������</TD>"
            		+"<TD  height=20 width=\"14%\" class=\"In1-td-rightbottom\" align=\"center\">���׺�</TD>"
            		+"<TD  height=20 width=\"13%\" class=\"In1-td-rightbottom\" align=\"center\">���׽��</TD>"
            		+"<TD  height=20 width=\"12%\" class=\"In1-td-rightbottom\" align=\"center\">������</TD>"
            		+"<TD  height=20 width=\"12%\" class=\"In1-td-rightbottom\" align=\"center\">�ۿ��ʣ�%��</TD>"
            		+"<TD  height=20 width=\"12%\" class=\"In1-td-rightbottom\" align=\"center\">ʵ��������</TD></TR>");
            	
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
     * ��ʾ������ƾ֤
     * ��һ�� �������и��º��˻ص�λ
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
                    + "      �����Ѵ�Ʊ</font></font></strong> ��</strong></TD>"
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
                    + " ��ӡ����: "
                    + info.getYear()
                    + " �� "
                    + info.getMonth()
                    + " �� "
                    + info.getDay()
                    + " �� "
                    + " </TD> "
                    + " <TD width=\"40%\" align=\"right\">���ױ�ţ�"
                    + DataFormat.formatStringForPrint(info.getTransNo())
                    + " </TD> "
                    + "</TR> "
                    + " </TABLE>"
                    
                    +"<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "  
                    +"  <TR>  " 
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><font face=\"����_GB2312\">��λ����</font></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\"  align=\"left\" class=\"In1-td-bottom\">"
                    +NameRef.getClientNameByAccountID(info.getAccountId())
                    +"&nbsp; </TD> " 
                    +"  </TR> "  
                    +"  <TR>  "
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">�ˡ�����</FONT></B>  " 
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" align=\"left\" nowrap class=\"In1-td-bottom\">"
                    +NameRef.getAccountNoByID(info.getAccountId())
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <td align=\"center\" height=\"44\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"����_GB2312\">�շ���Ŀ</font></b>  " 
                    +"    </td> " 
                    +"    <td height=\"44\" colspan=\"2\" align=\"left\" class=\"In1-td-bottom\">������</td> "  
                    +"  </TR> " 
                    +"  <TR>  " 
                    +"    <TD colspan=\"1\" align=\"center\" class=\"In1-td-rightbottom\" height=\"48\" nowrap><B><FONT face=\"����_GB2312\">�����<BR> "  
                    +"      (��д)</FONT></B> </TD> "  
                    +"    <TD width=\"199\" class=\"In1-td-rightbottom\" ><B>&nbsp;"
                    +ChineseCurrency.showChinese(DataFormat.formatAmount(info.getCommissionAmount()), info.getCurrencyId())
                    +"</B> </TD> " 
                    +"    <TD width=\"199\" align=\"right\" nowrap class=\"In1-td-bottom\"><B>&nbsp;��"
                    +DataFormat.formatDisabledAmount( info.getCommissionAmount() , 2 )
                    +"</B>  "  
                    +"    </TD> " 
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <TD colspan=\"1\" align=\"center\" height=\"55\" class=\"In1-td-right\" nowrap><B><FONT face=\"����_GB2312\">ժ����Ҫ</FONT></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" >"
                    +"�������ڣ�"
                    +DataFormat.formatDate(info.getExecuteDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"��ʼ���ڣ�"
                    +DataFormat.formatDate(info.getMinDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"��ֹ���ڣ�"
                    +DataFormat.formatDate(info.getMaxDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"������"
                    +info.getCountNum()
                    +"��"
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"</TABLE> " 
                    +"<TABLE width=\"30\" border=\"0\">      <TR>          <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>һ<BR>��<BR><BR>��<BR>��<BR>��<BR>λ<BR>��<BR>��</FONT>          </TD>      </TR>  </TABLE>  "  

                    + "</BODY> "
                    + " '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }
    
    /**
     * ��ʾ������ƾ֤
     * �ڶ��� ����������������Ʊ
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
                    + "      �����Ѵ�Ʊ</font></font></strong> ��</strong></TD>"
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
                    + " ��ӡ����: "
                    + info.getYear()
                    + " �� "
                    + info.getMonth()
                    + " �� "
                    + info.getDay()
                    + " �� "
                    + " </TD> "
                    + " <TD width=\"40%\" align=\"right\">���ױ�ţ�"
                    + DataFormat.formatStringForPrint(info.getTransNo())
                    + " </TD> "
                    + "</TR> "
                    +"<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "  
                    +"  <TR>  " 
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><font face=\"����_GB2312\">��λ����</font></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\"  align=\"left\" class=\"In1-td-bottom\">"
                    +NameRef.getClientNameByAccountID(info.getAccountId())
                    +"&nbsp; </TD> " 
                    +"  </TR> "  
                    +"  <TR>  "
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">�ˡ�����</FONT></B>  " 
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" align=\"left\" nowrap class=\"In1-td-bottom\">"
                    +NameRef.getAccountNoByID(info.getAccountId())
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <td align=\"center\" height=\"44\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"����_GB2312\">�շ���Ŀ</font></b>  " 
                    +"    </td> " 
                    +"    <td height=\"44\" colspan=\"2\" align=\"left\" class=\"In1-td-bottom\">������</td> "  
                    +"  </TR> " 
                    +"  <TR>  " 
                    +"    <TD colspan=\"1\" align=\"center\" class=\"In1-td-rightbottom\" height=\"48\" nowrap><B><FONT face=\"����_GB2312\">�����<BR> "  
                    +"      (��д)</FONT></B> </TD> "  
                    +"    <TD width=\"199\" class=\"In1-td-rightbottom\" ><B>&nbsp;"
                    +ChineseCurrency.showChinese(DataFormat.formatAmount(info.getCommissionAmount()), info.getCurrencyId())
                    +"</B> </TD> " 
                    +"    <TD width=\"199\" align=\"right\" nowrap class=\"In1-td-bottom\"><B>&nbsp;��"
                    +DataFormat.formatDisabledAmount( info.getCommissionAmount() , 2 )
                    +"</B>  "  
                    +"    </TD> " 
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <TD colspan=\"1\" align=\"center\" height=\"55\" class=\"In1-td-right\" nowrap><B><FONT face=\"����_GB2312\">ժ����Ҫ</FONT></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" >"
                    +"�������ڣ�"
                    +DataFormat.formatDate(info.getExecuteDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"��ʼ���ڣ�"
                    +DataFormat.formatDate(info.getMinDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"��ֹ���ڣ�"
                    +DataFormat.formatDate(info.getMaxDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"������"
                    +info.getCountNum()
                    +"��"
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"</TABLE> " 
                    +"<TABLE width=\"30\" border=\"0\">      <TR>          <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>��<BR>��<BR>ƾ<BR>֤</FONT>          </TD>      </TR>  </TABLE>  "  

                    + "</BODY> "
                    + " '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }
    
    /**
     * ��ʾ������ƾ֤
     * ������ ����������Ӫҵ���봫Ʊ
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
                    + "      �ʵ�ѡ������ѡ��շ�ƾ֤</font></font></strong> ��</strong></TD>"
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
                    + " ��ӡ����: "
                    + info.getYear()
                    + " �� "
                    + info.getMonth()
                    + " �� "
                    + info.getDay()
                    + " �� "
                    + " </TD> "
                    + " <TD width=\"40%\" align=\"right\">���ױ�ţ�"
                    + DataFormat.formatStringForPrint(info.getTransNo())
                    + " </TD> "
                    + "</TR> "
                    + " </TABLE>"
                    
                    
                    +"<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "  
                    +"  <TR>  " 
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><font face=\"����_GB2312\">��λ����</font></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\"  align=\"left\" class=\"In1-td-bottom\">"
                    +NameRef.getClientNameByAccountID(info.getAccountId())
                    +"&nbsp; </TD> " 
                    +"  </TR> "  
                    +"  <TR>  "
                    +"    <TD align=\"center\" width=\"76\" height=\"38\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">�ˡ�����</FONT></B>  " 
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" align=\"left\" nowrap class=\"In1-td-bottom\">"
                    +NameRef.getAccountNoByID(info.getAccountId())
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <td align=\"center\" height=\"44\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"����_GB2312\">�շ���Ŀ</font></b>  " 
                    +"    </td> " 
                    +"    <td height=\"44\" colspan=\"2\" align=\"left\" class=\"In1-td-bottom\">������</td> "  
                    +"  </TR> " 
                    +"  <TR>  " 
                    +"    <TD colspan=\"1\" align=\"center\" class=\"In1-td-rightbottom\" height=\"48\" nowrap><B><FONT face=\"����_GB2312\">�����<BR> "  
                    +"      (��д)</FONT></B> </TD> "  
                    +"    <TD width=\"199\" class=\"In1-td-rightbottom\" ><B>&nbsp;"
                    +ChineseCurrency.showChinese(DataFormat.formatAmount(info.getCommissionAmount()), info.getCurrencyId())
                    +"</B> </TD> " 
                    +"    <TD width=\"199\" align=\"right\" nowrap class=\"In1-td-bottom\"><B>&nbsp;��"
                    +DataFormat.formatDisabledAmount( info.getCommissionAmount() , 2 )
                    +"</B>  "  
                    +"    </TD> " 
                    +"  </TR> " 
                    +"  <TR>  "  
                    +"    <TD colspan=\"1\" align=\"center\" height=\"55\" class=\"In1-td-right\" nowrap><B><FONT face=\"����_GB2312\">ժ����Ҫ</FONT></B>  "  
                    +"    </TD> "  
                    +"    <TD colspan=\"2\" >"
                    +"�������ڣ�"
                    +DataFormat.formatDate(info.getExecuteDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"��ʼ���ڣ�"
                    +DataFormat.formatDate(info.getMinDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"��ֹ���ڣ�"
                    +DataFormat.formatDate(info.getMaxDate())
                    +"&nbsp;&nbsp;&nbsp;"
                    +"������"
                    +info.getCountNum()
                    +"��"
                    +"&nbsp; </TD> "  
                    +"  </TR> " 
                    +"</TABLE> " 
                    +"<TABLE width=\"30\" border=\"0\">      <TR>          <TD height=\"140\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>��<BR>��<BR><BR>��<BR>��<BR>��<BR>��<BR>��<BR>Ӫ<BR>ҵ<BR>��<BR>��<BR>��<BR>Ʊ</FONT>          </TD>      </TR>  </TABLE>  "  


                    + "</BODY> "
                    + " '); </SCRIPT>  ");
        }
        catch (Exception e)
        {
        }
    }

	/**
	 *��ʾ�״�ģ��
	 *strCode����������sCode˳����Ҫ���ַ���������˳��һ��
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
			//����EJB
			IPrintTemplateSetting printTemplateSetting = new IPrintTemplateSetting();
			Collection c = printTemplateSetting.findPrintOptionDetailsByTemplateID(lTemplateID);
			//�ó�����ʾ
			PrintOptionInfo printOptionInfo = new PrintOptionInfo();
			int lparmNumber = 0;
			out.println("<html>" + "<head>");
			////noShowPrintHeadAndFooter(out);
			out.println(
				"<title>��ӡģ������</title>"
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
	 * 'strFormName-Form������
	 * 'strFieldName-�������
	 * 'strForSelText-��ѡ����ı�����","�ֿ�
	 * 'strForSelValue-��ѡ���ֵ����","�ֿ�
	 * 'strSelText-�Ѿ�ѡ����ı�,��","�ֿ�
	 * 'strSelValue-�Ѿ�ѡ���ֵ,��","�ֿ�
	 * 'nType-��ʾ����1��ť����ʾ��2ֻ��ʾ���Ӱ�ť��3ֻ��ʾɾ����ť
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
		out.println("function getSelectData(obj)//�õ�select���ڵ����е�ֵ ");
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
		out.println("//��obj���Ѿ��е�value�����objForDelete��ɾ�� ");
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
	 * �����·ݻ�ȡ�������ڵļ���
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
				strQuarter = "һ";
				break;
			case 2 :
				strQuarter = "һ";
				break;
			case 3 :
				strQuarter = "һ";
				break;
			case 4 :
				strQuarter = "��";
				break;
			case 5 :
				strQuarter = "��";
				break;
			case 6 :
				strQuarter = "��";
				break;
			case 7 :
				strQuarter = "��";
				break;
			case 8 :
				strQuarter = "��";
				break;
			case 9 :
				strQuarter = "��";
				break;
			case 10 :
				strQuarter = "��";
				break;
			case 11 :
				strQuarter = "��";
				break;
			case 12 :
				strQuarter = "��";
				break;
		}
		return strQuarter;
	}
	/**
	 * �õ�TD�Ŀ��
	 * @param nNumber ����
	 * @return ����TD�Ŀ��
	 */
	public static long getTDWidth(int nNumber)
	{
		return 12 * nNumber;
	}
	/**
	 * �õ�����
	 * @param strData �ַ���
	 * @param nNumber ����������˫�ֽڣ����ֽ���0.5����
	 * @return ������ȷ��class
	 */
	public static String getDataFont(String strData, String strClass, int nNumber)
	{
		if (strData == null)
		{
			strData = "";
		}
		//�õ����ֽ���
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
	 * �õ�������������ռ�õ�������
	 * add by rxie
	 * @param strData �ַ���
	 * @param nNumber ����������˫�ֽڣ����ֽ���0.5����
	 * @return ��������
	 */
	public static int getDataLine(String strData, int nNumber)
	{
		int lReturn = -1;
		if (strData == null)
		{
			strData = "";
		}
		//�õ����ֽ���
		byte[] byTemp = strData.getBytes();
		int nLength = byTemp.length;
		nNumber = nNumber * 2;
		if (nLength % nNumber == 0 && nLength / nNumber != 0) //�����ַ��������п�����
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
	 * �õ�һ�������м�����λ��ռ�ݵ��������
	 * @param strData ��Ҫ������������λ��Ϣ
	 * @param nNumber ��Ҫ���������Ŀ����Ϣ
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
	 * �ַ����Զ�����
	 * add by hyzeng 2003-5-16
	 * @param strData �ַ���
	 * @param nLength ����������˫�ֽ���2�������ֽ���1����
	 * @return �����ַ���
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
	 * ���ܣ���ӡģ��IDȡ�ô�ӡģ������
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
	 * ���ܣ��õ��˻��Ŀ����еĵ�ַ ʡ
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
	 * ���ܣ��õ��˻��Ŀ����еĵ�ַ ��
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
	 * ���ܣ��õ��˻��Ŀ����е� ��ӡ����
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
	 * ���ܣ��õ����е� �˻�����
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
	 * ��ȡ�ض��˻�����ָ��ʱ�����ÿ��Э����Ϣ���������ܺ�---����
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
				System.out.println("~~~~~~~~~~~~~~~�˻�:" + NameRef.getAccountNoByID(lAccountID) + "~~~��Э��������" + dTotalBalance);
			}
		}
		catch (Exception e)
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
			catch (Exception ex)
			{
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return dTotalBalance;
	}
	/**
		 * Method getTotalDailyAmountBanlance.
		 * ��ȡ�ض��˻�����ָ��ʱ�����ÿ�ջ�����Ϣ���������ܺ�---����
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
				System.out.println("~~~~~~~~~~~~~~~�˻�:" + NameRef.getAccountNoByID(lAccountID) + "~~~�Ļ��ڻ�����" + dTotalBalance);
			}
		}
		catch (Exception e)
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
			catch (Exception ex)
			{
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return dTotalBalance;
	}
	/**
			 * Method getAccountBanlance.
			 * ����ƽ�����
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
				System.out.println("~~~~~~~~~~~~~~~�˻�:" + NameRef.getAccountNoByID(lAccountID) + "~~~����" + dTotalBalance);
			}
		}
		catch (Exception e)
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
			catch (Exception ex)
			{
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return dTotalBalance;
	}
	/**
				 * Method getNegotiateBanlance.
				 * Э��ƽ�����
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
				System.out.println("~~~~~~~~~~~~~~~�˻�:" + NameRef.getAccountNoByID(lAccountID) + "~~~��Э������" + dTotalBalance);
			}
		}
		catch (Exception e)
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
			catch (Exception ex)
			{
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return dTotalBalance;
	}
	/**
		* ����һ��������Ϣ���������ݿ��DiscountApply��
		* @param lDiscountID ���ֱ�ʶ
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
				//��������
				lai.setDiscountContractID(rs.getLong("nContractID")); //����ID��ʶ
				lai.setDiscountContractCode(rs.getString("sContractCode")); //���ֱ��
				//����ƾ֤
				lai.setID(lDiscountCredenceID); //����ƾ֤��ʶ
				lai.setCode(rs.getString("sCode")); //����ƾ֤���
				lai.setFillDate(rs.getTimestamp("dtFillDate"));
				lai.setDraftTypeID(rs.getLong("nDraftTypeID")); //���ֻ�Ʊ�����ʾ
				lai.setDraftCode(rs.getString("sDraftCode")); //���ֻ�Ʊ����
				lai.setPublicDate(rs.getTimestamp("dtPublicDate")); //��Ʊ��
				lai.setAtTerm(rs.getTimestamp("dtAtTerm")); //������
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
	 * ��ʾ�й��������е���ת��ƾ֤
	 * ��һ��
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
					+ " </font>��</strong></TD>"
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
					+ " �� "
					+ info.getMonth()
					+ " �� "
					+ info.getDay()
					+ " �� "
					+ " </TD> "
					+ "	<TD width=\"40%\" align=\"right\"> "
					+ "	</TD> "
					+ "</TR> "
					+ "	</TABLE>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">��<BR> "
					+ "      ��<BR> "
					+ "      ��</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">ȫ������</FONT></B>  "
					+ "    </TD> "
					+ "    <TD  align=\"left\" nowrap class=\"In1-td-rightbottom\" width=\"199\">"
					+ info.getPayAccountName()
					+ "&nbsp;</TD> "
					+ "    <TD rowspan=\"3\" align=\"center\" width=\"25\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">��<BR> "
					+ "      ��<BR> "
					+ "      ��</FONT></B> </TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">ȫ������</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-bottom\" width=\"199\">"
					+ info.getReceiveAccountName()
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"center\" width=\"76\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">�ˡ�����</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-rightbottom\">"
					+ info.getPayAccountNo()
					+ "&nbsp;</TD> "
					+ "    <TD align=\"center\" width=\"76\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">�ˡ�����</FONT></B>  "
					+ "    </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-bottom\">"
					+ info.getReceiveAccountNo()
					+ "&nbsp;</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"����_GB2312\">�� �� ��</font></b>  "
					+ "    </td> "
					+ "    <td nowrap class=\"In1-td-rightbottom\" align=\"left\">"
					+ info.getPayBankName()
					+ "&nbsp;</td> "
					+ "    <td align=\"center\" height=\"24\" class=\"In1-td-rightbottom\" nowrap><b><font face=\"����_GB2312\">�� �� ��</font></b>  "
					+ "    </td> "
					+ "    <td nowrap class=\"In1-td-bottom\" align=\"left\">"
					+ info.getReceiveBankName()
					+ "&nbsp;</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"1\" align=\"center\" class=\"In1-td-rightbottom\" height=\"30\" nowrap><B><FONT face=\"����_GB2312\">"
					+ "���</FONT></B> </TD> "
					+ "    <TD colspan=\"4\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">"
					+ info.getCurrencyName()
					+ "(��д)</FONT>&nbsp;"
					+ info.getChineseAmount()
					+ "</B> </TD> "
					+ "    <TD align=\"left\" nowrap class=\"In1-td-bottom\"><B><FONT face=\"����_GB2312\">"
					+ info.getCurrencyName()
					+ "(Сд)</FONT>&nbsp;"
					+ info.getAmount()
					+ "</B> </TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"1\" align=\"center\" height=\"22\" class=\"In1-td-rightbottom\" nowrap><B><FONT face=\"����_GB2312\">��;</FONT></B>  "
					+ "    </TD> "
					+ "    <TD colspan=\"2\" class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getAbstract()
					+ " </TD> "
					+ " <TD colspan=\"3\" ></TD>"
					+ "  </TR><TR>  "
					+ "    <TD colspan=\"3\" align=\"center\" height=\"30\" class=\"In1-td-right\" nowrap>&nbsp;<B><FONT face=\"����_GB2312\"></FONT></B>  "
					+ "    </TD> "
					+ " <TD colspan=\"3\" ></TD>"
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD colspan=\"3\" align=\"center\" height=\"22\" class=\"In1-td-rightbottom\" nowrap>&nbsp;<B><FONT face=\"����_GB2312\"></FONT></B>  "
					+ "    </TD> "
					+ "    <TD colspan=\"3\" class=\"In1-td-bottom\" align=\"center\">�����и��£�</TD>"
					+ "</TR>"
					+ "  <TR>  "
					+ "    <TD colspan=\"1\" align=\"center\" height=\"22\" class=\"In1-td-right\" nowrap><B><FONT face=\"����_GB2312\">��ע</FONT></B>  "
					+ "    </TD> "
					+ "    <TD colspan=\"5\">&nbsp;"
					+ info.getComment()
					+ " </TD> "
					+ "</TR>"
					+ "</TABLE> "
					+ "	<TABLE width=\"30\" border=\"0\"> "
					+ "		<TR> "
					+ "			<TD height=\"195\" ><FONT style=\"FONT-SIZE: 13px\">��<BR>һ<BR>��<BR><BR>��<BR>��<BR>��<BR>��</FONT> "
					+ "			</TD> "
					+ "		</TR> "
					+ "	</TABLE> "
					+ "<br>"
					+ "<Table width=\"600  \" border=\"0\">"
					+ "  <TR align=\"right\"> "
					+ "    <TD height=\"22\" width=\"200\" nowrap>[����]"
					+ info.getDirectorName()
					+ "</TD><TD  width=\"200\">"
					+ " &nbsp;[����]"
					+ info.getCheckUserName()
					+ "&nbsp; </TD>"
					+ "    <TD height=\"22\" width=\"200\" nowrap>[�Ƶ�]"
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
	 * ��������ƾ֤id���ҷ��Ž��
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
			catch (Exception ex)
			{
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return dTotalAmount;
	}
	public static void main(String[] args)
	{
		System.out.println("aaaaaaa");
	}
}
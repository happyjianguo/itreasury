package com.iss.itreasury.settlement.print;
import java.util.Vector;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.print.templateinfo.ShowGrantLoanInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowPayInterestInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowRepaymentLoanInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowSynLoanAgentFeeInfo;
import com.iss.itreasury.settlement.print.templateinfo.SynLoanGrantDetailInfo;
import com.iss.itreasury.settlement.print.templateinfo.SynLoanRepayDetailInfo;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Constant;
/**
 * @author gqzhang
 *
 * 银团打印模版
 */
public class ISynLoanPrintTemplate extends SettlementDAO
{
	/**
			 * 显示银团发放凭证 
			 * 此凭证包括“第一联：记账凭证”
			 */
	public static void showSynLoanGrantLoan1(ShowGrantLoanInfo info, JspWriter out) throws Exception
	{
		try
		{
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
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"615\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD width=\"70\" height=\"46\" nowrap>&nbsp;"
					+ "			</TD> "
					+ "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"400\" nowrap><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
					+ "银团贷款发放凭证</font></font></strong></strong></TD> "
					+ "<TD width=\"100\"  align=\"left\" nowrap>");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
				"</TD> "
					+ "		</TR> "
					+ "		<TR> "
					+ "			<TD width=\"90\">&nbsp;	 "
					+ "			</TD> "
					+ "	</TABLE> "
					+ "	 "
					+ "<TABLE width=\"615\"> "
					+ "  <tr>  "
					+ "    <TD align=\"left\" width=\"200\"><B>货款种类：</B> <u><b>"
					+ info.getLoanType()
					+ "</b></u></TD> "
					+ "    <TD width=\"200\" align=\"center\"><B>"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "</B> </TD> "
					+ "    <TD align=\"left\" width=\"230\"><B>交易编号：</B> <b>"
					+ info.getTransNo()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "	 "
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>借款单位</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanUnit()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款账户号</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B></TD> "
					+ "    <td height=\"12\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getContractCode()
					+ "</td> "
					+ "    <td nowrap class=\"In1-td-rightbottom\"  align=\"center\"><B>放款单号</B></td> "
					+ "    <td nowrap class=\"In1-td-bottom\" align=\"left\">&nbsp;"
					+ info.getBillCode()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>开户银行</B> </TD> "
					+ "    <TD align=\"left\" width=\"154\" height=\"24\" class=\"In1-td-rightbottom\" nowrap>&nbsp;"
					+ info.getOpenBankName()
					+ "</TD> "
					+ "    <TD width=\"154\" align=\"center\" nowrap class=\"In1-td-rightbottom\"><b>账号</b></TD> "
					+ "    <TD width=\"154\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款期限</B> </TD> "
					+ "    <td height=\"24\" colspan=\"3\" align=\"center\" nowrap class=\"In1-td-bottom\"> &nbsp;"
					+ info.getLoanInterval()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>"
					+ info.getCurrencyName()
					+ "<br> "
					+ "      （大写）</B> </TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;<b>"
					+ info.getChineseAmount()
					+ "</b></td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getAmount()
					+ "</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同利率</B></TD> "
					+ "    <td align=\"right\" height=\"24\" class=\"In1-td-rightbottom\"  nowrap><b>"
					+ info.getContractRate()
					+ "％</b></td> "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>代理费率</B></TD> "
					+ "    <td align=\"right\" height=\"24\" class=\"In1-td-bottom\"  nowrap><b>"
					+ info.getAgentFeeRate()
					+ "‰</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-right\"><B>摘要</B> </TD> "
					+ "    <td width=\"400\" height=\"24\" colspan=\"3\" align=\"center\" nowrap>&nbsp;"
					+ info.getAbstract()
					+ "</td> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>记<BR>账<BR>附<BR>件</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "<tr><td colspan=\"3\" align=\"center\" class=\"In1-td-bottom\"><b>发放贷款明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\"><b>贷款单位名称</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷比例(%)</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-bottom\"><b>承贷金额(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanGrantDetailInfo detailInfo = null;
			Vector vctGrantDetailInfo = info.getSynLoanGrantDetail();
			if (vctGrantDetailInfo != null && vctGrantDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctGrantDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanGrantDetailInfo) vctGrantDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getLoanUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"200\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getLoanRate()
							+ "</td>"
							+ "<td width=\"200\" align=\"right\" class=\"In1-td-bottom\">&nbsp;￥"
							+ detailInfo.getLoanAmount()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"200\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"200\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.println(
				"<tr>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"right\" class=\"In1-td-right\">"
					+ info.getSumAssumeLoanRate()
					+ "</td>"
					+ "<td width=\"200\" align=\"right\">￥"
					+ info.getSunAssumeLoanAmount()
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600  \" border=\"0\" align=\"right\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD  height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[录入] &nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp; "
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
			 * 显示银团发放凭证 
			 * 此凭证包括“第二联：代理行留存””
			 */
	public static void showSynLoanGrantLoan2(ShowGrantLoanInfo info, JspWriter out) throws Exception
	{
		try
		{
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
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"615\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD width=\"70\" height=\"46\" nowrap>&nbsp;"
					+ "			</TD> "
					+ "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"400\" nowrap><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
					+ "      银团贷款发放凭证</font></font></strong></strong></TD> "
					+ "			<TD width=\"100\"  align=\"left\" nowrap>");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
				"</TD> "
					+ "		</TR> "
					+ "		<TR> "
					+ "			<TD width=\"90\">&nbsp;	 "
					+ "			</TD> "
					+ "	</TABLE> "
					+ "	 "
					+ "<TABLE width=\"615\"> "
					+ "  <tr>  "
					+ "    <TD align=\"left\" width=\"200\"><B>货款种类：</B> <u><b>"
					+ info.getLoanType()
					+ "</b></u></TD> "
					+ "    <TD width=\"200\" align=\"center\"><B>"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "</B> </TD> "
					+ "    <TD align=\"left\" width=\"230\"><B>交易编号：</B> <b>"
					+ info.getTransNo()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "	 "
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>借款单位</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanUnit()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款账户号</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B></TD> "
					+ "    <td height=\"12\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getContractCode()
					+ "</td> "
					+ "    <td nowrap class=\"In1-td-rightbottom\"  align=\"center\"><B>放款单号</B></td> "
					+ "    <td nowrap class=\"In1-td-bottom\" align=\"left\">&nbsp;"
					+ info.getBillCode()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>开户银行</B> </TD> "
					+ "    <TD align=\"left\" width=\"154\" height=\"24\" class=\"In1-td-rightbottom\" nowrap>&nbsp;"
					+ info.getOpenBankName()
					+ "</TD> "
					+ "    <TD width=\"154\" align=\"center\" nowrap class=\"In1-td-rightbottom\"><b>账号</b></TD> "
					+ "    <TD width=\"154\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款期限</B> </TD> "
					+ "    <td height=\"24\" colspan=\"3\" align=\"center\" nowrap class=\"In1-td-bottom\"> &nbsp;"
					+ info.getLoanInterval()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>"
					+ info.getCurrencyName()
					+ "<br> "
					+ "      （大写）</B> </TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;<b>"
					+ info.getChineseAmount()
					+ "</b></td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getAmount()
					+ "</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同利率</B></TD> "
					+ "    <td align=\"right\" height=\"24\" class=\"In1-td-rightbottom\"  nowrap><b>"
					+ info.getContractRate()
					+ "％</b></td> "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>代理费率</B></TD> "
					+ "    <td align=\"right\" height=\"24\" class=\"In1-td-bottom\"  nowrap><b>"
					+ info.getAgentFeeRate()
					+ "‰</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-right\"><B>摘要</B> </TD> "
					+ "    <td width=\"400\" height=\"24\" colspan=\"3\" align=\"center\" nowrap>&nbsp;"
					+ info.getAbstract()
					+ "</td> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>代<BR>理<BR>行<BR>留<BR>存</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "<tr><td colspan=\"3\" align=\"center\" class=\"In1-td-bottom\"><b>发放贷款明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\"><b>贷款单位名称</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷比例(%)</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-bottom\"><b>承贷金额(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanGrantDetailInfo detailInfo = null;
			Vector vctGrantDetailInfo = info.getSynLoanGrantDetail();
			if (vctGrantDetailInfo != null && vctGrantDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctGrantDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanGrantDetailInfo) vctGrantDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getLoanUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"200\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getLoanRate()
							+ "</td>"
							+ "<td width=\"200\" align=\"right\" class=\"In1-td-bottom\">&nbsp;￥"
							+ detailInfo.getLoanAmount()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"200\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"200\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.println(
				"<tr>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"right\" class=\"In1-td-right\">"
					+ info.getSumAssumeLoanRate()
					+ "</td>"
					+ "<td width=\"200\" align=\"right\">￥"
					+ info.getSunAssumeLoanAmount()
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600  \" border=\"0\" align=\"right\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD  height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[录入] &nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp; "
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
			 * 显示银团发放凭证 
			 * 此凭证包括“第三联：企业留存””
			 */
	public static void showSynLoanGrantLoan3(ShowGrantLoanInfo info, JspWriter out) throws Exception
	{
		try
		{
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
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"615\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD width=\"70\" height=\"46\" nowrap>&nbsp;"
					+ "			</TD> "
					+ "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"400\" nowrap><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
					+ "      银团贷款发放凭证</font></font></strong></strong></TD> "
					+ "			<TD width=\"100\"  align=\"left\" nowrap>");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
				"</TD> "
					+ "		</TR> "
					+ "		<TR> "
					+ "			<TD width=\"90\">&nbsp;	 "
					+ "			</TD> "
					+ "	</TABLE> "
					+ "	 "
					+ "<TABLE width=\"615\"> "
					+ "  <tr>  "
					+ "    <TD align=\"left\" width=\"200\"><B>货款种类：</B> <u><b>"
					+ info.getLoanType()
					+ "</b></u></TD> "
					+ "    <TD width=\"200\" align=\"center\"><B>"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "</B> </TD> "
					+ "    <TD align=\"left\" width=\"230\"><B>交易编号：</B> <b>"
					+ info.getTransNo()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "	 "
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>借款单位</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanUnit()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款账户号</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B></TD> "
					+ "    <td height=\"12\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getContractCode()
					+ "</td> "
					+ "    <td nowrap class=\"In1-td-rightbottom\"  align=\"center\"><B>放款单号</B></td> "
					+ "    <td nowrap class=\"In1-td-bottom\" align=\"left\">&nbsp;"
					+ info.getBillCode()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>开户银行</B> </TD> "
					+ "    <TD align=\"left\" width=\"154\" height=\"24\" class=\"In1-td-rightbottom\" nowrap>&nbsp;"
					+ info.getOpenBankName()
					+ "</TD> "
					+ "    <TD width=\"154\" align=\"center\" nowrap class=\"In1-td-rightbottom\"><b>账号</b></TD> "
					+ "    <TD width=\"154\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款期限</B> </TD> "
					+ "    <td height=\"24\" colspan=\"3\" align=\"center\" nowrap class=\"In1-td-bottom\"> &nbsp;"
					+ info.getLoanInterval()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>"
					+ info.getCurrencyName()
					+ "<br> "
					+ "      （大写）</B> </TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;<b>"
					+ info.getChineseAmount()
					+ "</b></td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getAmount()
					+ "</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同利率</B></TD> "
					+ "    <td align=\"right\" height=\"24\" class=\"In1-td-rightbottom\"  nowrap><b>"
					+ info.getContractRate()
					+ "％</b></td> "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>代理费率</B></TD> "
					+ "    <td align=\"right\" height=\"24\" class=\"In1-td-bottom\"  nowrap><b>"
					+ info.getAgentFeeRate()
					+ "‰</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-right\"><B>摘要</B> </TD> "
					+ "    <td width=\"400\" height=\"24\" colspan=\"3\" align=\"center\" nowrap>&nbsp;"
					+ info.getAbstract()
					+ "</td> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>三<BR>联<BR><BR>企<BR>业<BR>留<BR>存</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "<tr><td colspan=\"3\" align=\"center\" class=\"In1-td-bottom\"><b>发放贷款明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\"><b>贷款单位名称</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷比例(%)</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-bottom\"><b>承贷金额(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanGrantDetailInfo detailInfo = null;
			Vector vctGrantDetailInfo = info.getSynLoanGrantDetail();
			if (vctGrantDetailInfo != null && vctGrantDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctGrantDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanGrantDetailInfo) vctGrantDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getLoanUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"200\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getLoanRate()
							+ "</td>"
							+ "<td width=\"200\" align=\"right\" class=\"In1-td-bottom\">&nbsp;￥"
							+ detailInfo.getLoanAmount()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"200\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"200\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.println(
				"<tr>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"right\" class=\"In1-td-right\">"
					+ info.getSumAssumeLoanRate()
					+ "</td>"
					+ "<td width=\"200\" align=\"right\">￥"
					+ info.getSunAssumeLoanAmount()
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600  \" border=\"0\" align=\"right\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD  height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[录入] &nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp; "
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
			 * 显示银团发放凭证 
			 * 此凭证包括“第四联：留存卡片
			 */
	public static void showSynLoanGrantLoan4(ShowGrantLoanInfo info, JspWriter out) throws Exception
	{
		try
		{
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
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"615\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD width=\"70\" height=\"46\" nowrap>&nbsp;"
					+ "			</TD> "
					+ "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"400\" nowrap><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
					+ "      银团贷款发放凭证</font></font></strong></strong></TD> "
					+ "			<TD width=\"100\"  align=\"left\" nowrap>");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
				"</TD> "
					+ "		</TR> "
					+ "		<TR> "
					+ "			<TD width=\"90\">&nbsp;	 "
					+ "			</TD> "
					+ "	</TABLE> "
					+ "	 "
					+ "<TABLE width=\"615\"> "
					+ "  <tr>  "
					+ "    <TD align=\"left\" width=\"200\"><B>货款种类：</B> <u><b>"
					+ info.getLoanType()
					+ "</b></u></TD> "
					+ "    <TD width=\"200\" align=\"center\"><B>"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "</B> </TD> "
					+ "    <TD align=\"left\" width=\"230\"><B>交易编号：</B> <b>"
					+ info.getTransNo()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "	 "
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>借款单位</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanUnit()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款账户号</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B></TD> "
					+ "    <td height=\"12\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getContractCode()
					+ "</td> "
					+ "    <td nowrap class=\"In1-td-rightbottom\"  align=\"center\"><B>放款单号</B></td> "
					+ "    <td nowrap class=\"In1-td-bottom\" align=\"left\">&nbsp;"
					+ info.getBillCode()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>开户银行</B> </TD> "
					+ "    <TD align=\"left\" width=\"154\" height=\"24\" class=\"In1-td-rightbottom\" nowrap>&nbsp;"
					+ info.getOpenBankName()
					+ "</TD> "
					+ "    <TD width=\"154\" align=\"center\" nowrap class=\"In1-td-rightbottom\"><b>账号</b></TD> "
					+ "    <TD width=\"154\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款期限</B> </TD> "
					+ "    <td height=\"24\" colspan=\"3\" align=\"center\" nowrap class=\"In1-td-bottom\"> &nbsp;"
					+ info.getLoanInterval()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>"
					+ info.getCurrencyName()
					+ "<br> "
					+ "      （大写）</B> </TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;<b>"
					+ info.getChineseAmount()
					+ "</b></td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getAmount()
					+ "</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同利率</B></TD> "
					+ "    <td align=\"right\" height=\"24\" class=\"In1-td-rightbottom\"  nowrap><b>"
					+ info.getContractRate()
					+ "％</b></td> "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>代理费率</B></TD> "
					+ "    <td align=\"right\" height=\"24\" class=\"In1-td-bottom\"  nowrap><b>"
					+ info.getAgentFeeRate()
					+ "‰</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-right\"><B>摘要</B> </TD> "
					+ "    <td width=\"400\" height=\"24\" colspan=\"3\" align=\"center\" nowrap>&nbsp;"
					+ info.getAbstract()
					+ "</td> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>四<BR>联<BR><BR>留<BR>存<BR>卡<BR>片</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "<tr><td colspan=\"3\" align=\"center\" class=\"In1-td-bottom\"><b>发放贷款明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\"><b>贷款单位名称</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷比例(%)</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-bottom\"><b>承贷金额(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanGrantDetailInfo detailInfo = null;
			Vector vctGrantDetailInfo = info.getSynLoanGrantDetail();
			if (vctGrantDetailInfo != null && vctGrantDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctGrantDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanGrantDetailInfo) vctGrantDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getLoanUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"200\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getLoanRate()
							+ "</td>"
							+ "<td width=\"200\" align=\"right\" class=\"In1-td-bottom\">&nbsp;￥"
							+ detailInfo.getLoanAmount()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"200\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"200\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.println(
				"<tr>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"right\" class=\"In1-td-right\">"
					+ info.getSumAssumeLoanRate()
					+ "</td>"
					+ "<td width=\"200\" align=\"right\">￥"
					+ info.getSunAssumeLoanAmount()
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600  \" border=\"0\" align=\"right\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD  height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[录入] &nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp; "
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
			 * 显示银团发放凭证 
			 * 此凭证包括“第五－N联：参与行留存”
			 */
	public static void showSynLoanGrantLoan5(ShowGrantLoanInfo info, JspWriter out) throws Exception
	{
		try
		{
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
					+ " --> "
					+ " </style> "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"615\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD width=\"70\" height=\"46\" nowrap>&nbsp;"
					+ "			</TD> "
					+ "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"400\" nowrap><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
					+ "      银团贷款发放凭证</font></font></strong></strong></TD> "
					+ "			<TD width=\"100\"  align=\"left\" nowrap>");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
				"</TD> "
					+ "		</TR> "
					+ "		<TR> "
					+ "			<TD width=\"90\">&nbsp;	 "
					+ "			</TD> "
					+ "	</TABLE> "
					+ "	 "
					+ "<TABLE width=\"615\"> "
					+ "  <tr>  "
					+ "    <TD align=\"left\" width=\"200\"><B>货款种类：</B> <u><b>"
					+ info.getLoanType()
					+ "</b></u></TD> "
					+ "    <TD width=\"200\" align=\"center\"><B>"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "</B> </TD> "
					+ "    <TD align=\"left\" width=\"230\"><B>交易编号：</B> <b>"
					+ info.getTransNo()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "	 "
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>借款单位</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanUnit()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款账户号</B> </TD> "
					+ "    <TD colspan=\"3\" align=\"left\" class=\"In1-td-bottom\">&nbsp;"
					+ info.getLoanAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B></TD> "
					+ "    <td height=\"12\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getContractCode()
					+ "</td> "
					+ "    <td nowrap class=\"In1-td-rightbottom\"  align=\"center\"><B>放款单号</B></td> "
					+ "    <td nowrap class=\"In1-td-bottom\" align=\"left\">&nbsp;"
					+ info.getBillCode()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>开户银行</B> </TD> "
					+ "    <TD align=\"left\" width=\"154\" height=\"24\" class=\"In1-td-rightbottom\" nowrap>&nbsp;"
					+ info.getOpenBankName()
					+ "</TD> "
					+ "    <TD width=\"154\" align=\"center\" nowrap class=\"In1-td-rightbottom\"><b>账号</b></TD> "
					+ "    <TD width=\"154\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getAccountNo()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>贷款期限</B> </TD> "
					+ "    <td height=\"24\" colspan=\"3\" align=\"center\" nowrap class=\"In1-td-bottom\"> &nbsp;"
					+ info.getLoanInterval()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>"
					+ info.getCurrencyName()
					+ "<br> "
					+ "      （大写）</B> </TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;<b>"
					+ info.getChineseAmount()
					+ "</b></td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getAmount()
					+ "</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同利率</B></TD> "
					+ "    <td align=\"right\" height=\"24\" class=\"In1-td-rightbottom\"  nowrap><b>"
					+ info.getContractRate()
					+ "％</b></td> "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-rightbottom\"><B>代理费率</B></TD> "
					+ "    <td align=\"right\" height=\"24\" class=\"In1-td-bottom\"  nowrap><b>"
					+ info.getAgentFeeRate()
					+ "‰</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"154\" align=\"center\" class=\"In1-td-right\"><B>摘要</B> </TD> "
					+ "    <td width=\"400\" height=\"24\" colspan=\"3\" align=\"center\" nowrap>&nbsp;"
					+ info.getAbstract()
					+ "</td> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>"
					+ info.getNum()
					+ "<BR>联<BR><BR>参<BR>与<BR>行<BR>留<BR>存</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "<tr><td colspan=\"3\" align=\"center\" class=\"In1-td-bottom\"><b>发放贷款明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\"><b>贷款单位名称</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷比例(%)</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-bottom\"><b>承贷金额(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanGrantDetailInfo detailInfo = null;
			Vector vctGrantDetailInfo = info.getSynLoanGrantDetail();
			if (vctGrantDetailInfo != null && vctGrantDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctGrantDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanGrantDetailInfo) vctGrantDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getLoanUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"200\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getLoanRate()
							+ "</td>"
							+ "<td width=\"200\" align=\"right\" class=\"In1-td-bottom\">&nbsp;￥"
							+ detailInfo.getLoanAmount()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"200\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"200\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"200\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.println(
				"<tr>"
					+ "<td width=\"200\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b>"
					+ "</td>"
					+ "<td width=\"200\" align=\"right\" class=\"In1-td-right\">"
					+ info.getSumAssumeLoanRate()
					+ "</td>"
					+ "<td width=\"200\" align=\"right\">￥"
					+ info.getSunAssumeLoanAmount()
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600\" border=\"0\" align=\"right\">"
					+ "  <TR align=\"right\"> "
					+ "  <TD  height=\"22\" nowrap>&nbsp; </TD>"
					+ "    <TD height=\"22\" nowrap>[录入] &nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp; "
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
		 * 显示银团贷款本金回收凭证 第一联记账凭证
		 */
	public static void showSynLoanRepaymentLoan1(ShowRepaymentLoanInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				"<Script Language=\"JavaScript\"> document.write('"
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
					+ "<!-- saved from url=(0022)http://internet.e-mail --> "
					+ " "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"615\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD width=\"60\" height=\"46\" nowrap>&nbsp;	 "
					+ "			</TD> "
					+ "			 "
					+ "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"400\" nowrap><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
					+ "      银团贷款本金回收凭证</font></font></strong> </strong></TD> "
					+ "			<TD width=\"100\" nowrap align=\"left\">");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
				"			</TD> "
					+ "		</TR> "
					+ "		<TR> "
					+ "			<TD width=\"90\">&nbsp;	 "
					+ "			</TD> "
					+ "	</TABLE> "
					+ "	 "
					+ "<TABLE width=\"615\"> "
					+ "  <tr>  "
					+ "    <TD align=\"left\" width=\"200\"><B>货款种类：</B> <u><b>"
					+ info.getLoanType()
					+ "</b></u></TD> "
					+ "    <TD width=\"200\" align=\"center\"><B>"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "</B> </TD> "
					+ "    <TD align=\"left\" width=\"230\" ><B>交易编号：</B> <b>"
					+ info.getTransNo()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" rowspan=\"4\" align=\"center\" class=\"In1-td-rightbottom\"><b>还<br> "
					+ "      款<br> "
					+ "      单<br> "
					+ "      位</b></TD> "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>名&nbsp;&nbsp;称</b> </TD> "
					+ "    <TD width=\"444\" colspan=\"3\" align=\"left\" class=\"In1-td-bottom\" >&nbsp;"
					+ info.getRepaymentUnitName()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>起止日期</b> </TD> "
					+ "    <TD height=\"24\" colspan=\"3\" align=\"center\" nowrap class=\"In1-td-bottom\"> &nbsp;"
					+ info.getLoanInterval()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B> </TD> "
					+ "    <td width=\"129\" height=\"12\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getContractCode()
					+ "</td> "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><B>放款单号</B> </TD> "
					+ "    <td width=\"129\" height=\"12\" align=\"left\" nowrap class=\"In1-td-bottom\" >&nbsp;"
					+ info.getBillCode()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>付款银行</b> </TD> "
					+ "    <td width=\"100\" height=\"22\" align=\"center\" nowrap class=\"In1-td-rightbottom\">"
					+ info.getRepaymentBankName()
					+ "&nbsp;</td> "
					+ "    <td height=\"22\" align=\"center\" nowrap class=\"In1-td-rightbottom\" width=\"90\"><b>付款账号</b></td> "
					+ "    <td height=\"22\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getRepaymentAccountNo()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"left\" class=\"In1-td-rightbottom\"><B>本次还款金额</B> </TD> "
					+ "    <TD align=\"center\" class=\"In1-td-rightbottom\">"
					+ info.getCurrencyName()
					+ "<br> "
					+ "      （大写）"
					+ "</TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">"
					+ "    <b>  "
					+ info.getChineseAmount()
					+ "  </b>&nbsp; </td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getAmount()
					+ " </b> &nbsp;</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD  rowspan=\"2\" align=\"left\" class=\"In1-td-rightbottom\"><B>摘要</B> "
					+ "</TD> "
					+ "    <TD colspan=\"2\" rowspan=\"2\" align=\"left\" class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getAbstract()
					+ "</TD> "
					+ "    <td height=\"24\" align=\"center\" nowrap class=\"In1-td-rightbottom\">累计<br> "
					+ "      还款</td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getTotalRepayAmount()
					+ "</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD height=\"24\" align=\"center\" class=\"In1-td-rightbottom\">余额</TD> "
					+ "    <TD height=\"24\" align=\"right\" class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getBalance()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>记<BR>账<BR>凭<BR>证</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "<tr><td colspan=\"5\" align=\"center\" class=\"In1-td-bottom\"><b>贷款收回明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>收款单位名称</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷比例(%)</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>开户银行</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>银行账号</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-bottom\"><b>收款金额(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanRepayDetailInfo detailInfo = null;
			Vector vctRepayDetailInfo = info.getSynLoanRepayDetail();
			if (vctRepayDetailInfo != null && vctRepayDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctRepayDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanRepayDetailInfo) vctRepayDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getReceiveAmountUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getLoanRate()
							+ "</td>"
							+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getOpenBank()
							+ "&nbsp;</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getBankAccountNo()
							+ "</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-bottom\">&nbsp;￥"
							+ detailInfo.getReciveAmount()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.print(
				"<tr>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\" class=\"In1-td-right\">"
					+ info.getSumLoanRate()
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;￥"
					+ info.getSumReciveAmount()
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600\" border=\"0\" align=\"right\">"
					+ "<TR align=\"right\"> "
					+ "<TD  height=\"22\" nowrap>&nbsp;</TD>"
					+ "<TD height=\"22\" nowrap>[录入]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ "'); </SCRIPT>");
		}
		catch (Exception e)
		{
		}
	}
	/**
		 * 显示银团贷款本金回收凭证 ，第二行 代理行留存 
		 */
	public static void showSynLoanRepaymentLoan2(ShowRepaymentLoanInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				"<Script Language=\"JavaScript\"> document.write('"
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
					+ "<!-- saved from url=(0022)http://internet.e-mail --> "
					+ " "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"615\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD width=\"60\" height=\"46\" nowrap>&nbsp;	 "
					+ "			</TD> "
					+ "			 "
					+ "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"400\" nowrap><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
					+ "      银团贷款本金回收凭证</font></font></strong> </strong></TD> "
					+ "			<TD width=\"100\" nowrap align=\"left\">");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
				"			</TD> "
					+ "		</TR> "
					+ "		<TR> "
					+ "			<TD width=\"90\">&nbsp;	 "
					+ "			</TD> "
					+ "	</TABLE> "
					+ "	 "
					+ "<TABLE width=\"615\"> "
					+ "  <tr>  "
					+ "    <TD align=\"left\" width=\"200\"><B>货款种类：</B> <u><b>"
					+ info.getLoanType()
					+ "</b></u></TD> "
					+ "    <TD width=\"200\" align=\"center\"><B>"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "</B> </TD> "
					+ "    <TD align=\"left\" width=\"230\" ><B>交易编号：</B> <b>"
					+ info.getTransNo()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" rowspan=\"4\" align=\"center\" class=\"In1-td-rightbottom\"><b>还<br> "
					+ "      款<br> "
					+ "      单<br> "
					+ "      位</b></TD> "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>名&nbsp;&nbsp;称</b> </TD> "
					+ "    <TD width=\"444\" colspan=\"3\" align=\"left\" class=\"In1-td-bottom\" >&nbsp;"
					+ info.getRepaymentUnitName()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>起止日期</b> </TD> "
					+ "    <TD height=\"24\" colspan=\"3\" align=\"center\" nowrap class=\"In1-td-bottom\"> &nbsp;"
					+ info.getLoanInterval()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B> </TD> "
					+ "    <td width=\"129\" height=\"12\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getContractCode()
					+ "</td> "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><B>放款单号</B> </TD> "
					+ "    <td width=\"129\" height=\"12\" align=\"left\" nowrap class=\"In1-td-bottom\" >&nbsp;"
					+ info.getBillCode()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>付款银行</b> </TD> "
					+ "    <td width=\"100\" height=\"22\" align=\"center\" nowrap class=\"In1-td-rightbottom\">"
					+ info.getRepaymentBankName()
					+ "&nbsp;</td> "
					+ "    <td height=\"22\" align=\"center\" nowrap class=\"In1-td-rightbottom\" width=\"90\"><b>付款账号</b></td> "
					+ "    <td height=\"22\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getRepaymentAccountNo()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"left\" class=\"In1-td-rightbottom\"><B>本次还款金额</B> </TD> "
					+ "    <TD align=\"center\" class=\"In1-td-rightbottom\">"
					+ info.getCurrencyName()
					+ "<br> "
					+ "      （大写）"
					+ "</TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">"
					+ "    <b>  "
					+ info.getChineseAmount()
					+ "  </b>&nbsp; </td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getAmount()
					+ " </b> &nbsp;</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD  rowspan=\"2\" align=\"left\" class=\"In1-td-rightbottom\"><B>摘要</B> "
					+ "</TD> "
					+ "    <TD colspan=\"2\" rowspan=\"2\" align=\"left\" class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getAbstract()
					+ "</TD> "
					+ "    <td height=\"24\" align=\"center\" nowrap class=\"In1-td-rightbottom\">累计<br> "
					+ "      还款</td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getTotalRepayAmount()
					+ "</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD height=\"24\" align=\"center\" class=\"In1-td-rightbottom\">余额</TD> "
					+ "    <TD height=\"24\" align=\"right\" class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getBalance()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>代<BR>理<BR>行<BR>留<BR>存</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "<tr><td colspan=\"5\" align=\"center\" class=\"In1-td-bottom\"><b>贷款收回明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>收款单位名称</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷比例(%)</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>开户银行</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>银行账号</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-bottom\"><b>收款金额(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanRepayDetailInfo detailInfo = null;
			Vector vctRepayDetailInfo = info.getSynLoanRepayDetail();
			if (vctRepayDetailInfo != null && vctRepayDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctRepayDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanRepayDetailInfo) vctRepayDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getReceiveAmountUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getLoanRate()
							+ "</td>"
							+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getOpenBank()
							+ "&nbsp;</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getBankAccountNo()
							+ "</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-bottom\">&nbsp;￥"
							+ detailInfo.getReciveAmount()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.print(
				"<tr>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\" class=\"In1-td-right\">"
					+ info.getSumLoanRate()
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;￥"
					+ info.getSumReciveAmount()
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600\" border=\"0\" align=\"right\">"
					+ "<TR align=\"right\"> "
					+ "<TD  height=\"22\" nowrap>&nbsp;</TD>"
					+ "<TD height=\"22\" nowrap>[录入]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ "'); </SCRIPT>");
		}
		catch (Exception e)
		{
		}
	}
	/**
		 * 显示银团贷款本金回收凭证 ，第三联 企业留存 
		 */
	public static void showSynLoanRepaymentLoan3(ShowRepaymentLoanInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				"<Script Language=\"JavaScript\"> document.write('"
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
					+ "<!-- saved from url=(0022)http://internet.e-mail --> "
					+ " "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"615\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD width=\"60\" height=\"46\" nowrap>&nbsp;	 "
					+ "			</TD> "
					+ "			 "
					+ "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"400\" nowrap><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
					+ "      银团贷款本金回收凭证</font></font></strong> </strong></TD> "
					+ "			<TD width=\"100\" nowrap align=\"left\">");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
				"			</TD> "
					+ "		</TR> "
					+ "		<TR> "
					+ "			<TD width=\"90\">&nbsp;	 "
					+ "			</TD> "
					+ "	</TABLE> "
					+ "	 "
					+ "<TABLE width=\"615\"> "
					+ "  <tr>  "
					+ "    <TD align=\"left\" width=\"200\"><B>货款种类：</B> <u><b>"
					+ info.getLoanType()
					+ "</b></u></TD> "
					+ "    <TD width=\"200\" align=\"center\"><B>"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "</B> </TD> "
					+ "    <TD align=\"left\" width=\"230\" ><B>交易编号：</B> <b>"
					+ info.getTransNo()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" rowspan=\"4\" align=\"center\" class=\"In1-td-rightbottom\"><b>还<br> "
					+ "      款<br> "
					+ "      单<br> "
					+ "      位</b></TD> "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>名&nbsp;&nbsp;称</b> </TD> "
					+ "    <TD width=\"444\" colspan=\"3\" align=\"left\" class=\"In1-td-bottom\" >&nbsp;"
					+ info.getRepaymentUnitName()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>起止日期</b> </TD> "
					+ "    <TD height=\"24\" colspan=\"3\" align=\"center\" nowrap class=\"In1-td-bottom\"> &nbsp;"
					+ info.getLoanInterval()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B> </TD> "
					+ "    <td width=\"129\" height=\"12\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getContractCode()
					+ "</td> "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><B>放款单号</B> </TD> "
					+ "    <td width=\"129\" height=\"12\" align=\"left\" nowrap class=\"In1-td-bottom\" >&nbsp;"
					+ info.getBillCode()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>付款银行</b> </TD> "
					+ "    <td width=\"100\" height=\"22\" align=\"center\" nowrap class=\"In1-td-rightbottom\">"
					+ info.getRepaymentBankName()
					+ "&nbsp;</td> "
					+ "    <td height=\"22\" align=\"center\" nowrap class=\"In1-td-rightbottom\" width=\"90\"><b>付款账号</b></td> "
					+ "    <td height=\"22\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getRepaymentAccountNo()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"left\" class=\"In1-td-rightbottom\"><B>本次还款金额</B> </TD> "
					+ "    <TD align=\"center\" class=\"In1-td-rightbottom\">"
					+ info.getCurrencyName()
					+ "<br> "
					+ "      （大写）"
					+ "</TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">"
					+ "    <b>  "
					+ info.getChineseAmount()
					+ "  </b>&nbsp; </td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getAmount()
					+ " </b> &nbsp;</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD  rowspan=\"2\" align=\"left\" class=\"In1-td-rightbottom\"><B>摘要</B> "
					+ "</TD> "
					+ "    <TD colspan=\"2\" rowspan=\"2\" align=\"left\" class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getAbstract()
					+ "</TD> "
					+ "    <td height=\"24\" align=\"center\" nowrap class=\"In1-td-rightbottom\">累计<br> "
					+ "      还款</td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getTotalRepayAmount()
					+ "</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD height=\"24\" align=\"center\" class=\"In1-td-rightbottom\">余额</TD> "
					+ "    <TD height=\"24\" align=\"right\" class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getBalance()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>三<BR>联<BR><BR>企<BR>业<BR>留<BR>存</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "<tr><td colspan=\"5\" align=\"center\" class=\"In1-td-bottom\"><b>贷款收回明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>收款单位名称</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷比例(%)</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>开户银行</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>银行账号</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-bottom\"><b>收款金额(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanRepayDetailInfo detailInfo = null;
			Vector vctRepayDetailInfo = info.getSynLoanRepayDetail();
			if (vctRepayDetailInfo != null && vctRepayDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctRepayDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanRepayDetailInfo) vctRepayDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getReceiveAmountUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getLoanRate()
							+ "</td>"
							+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getOpenBank()
							+ "&nbsp;</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getBankAccountNo()
							+ "</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-bottom\">&nbsp;￥"
							+ detailInfo.getReciveAmount()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.print(
				"<tr>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\" class=\"In1-td-right\">"
					+ info.getSumLoanRate()
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;￥"
					+ info.getSumReciveAmount()
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600\" border=\"0\" align=\"right\">"
					+ "<TR align=\"right\"> "
					+ "<TD  height=\"22\" nowrap>&nbsp;</TD>"
					+ "<TD height=\"22\" nowrap>[录入]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ "'); </SCRIPT>");
		}
		catch (Exception e)
		{
		}
	}
	/**
		 * 显示银团贷款本金回收凭证 第n联参与行留存
		 */
	public static void showSynLoanRepaymentLoan4(ShowRepaymentLoanInfo info, JspWriter out) throws Exception
	{
		try
		{
			out.print(
				"<Script Language=\"JavaScript\"> document.write('"
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
					+ "<!-- saved from url=(0022)http://internet.e-mail --> "
					+ " "
					+ "<BODY text=\"#000000\" bgcolor=\"#FFFFFF\"> "
					+ "	<TABLE width=\"615\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> "
					+ "		<TR> "
					+ "			<TD width=\"60\" height=\"46\" nowrap>&nbsp;	 "
					+ "			</TD> "
					+ "			 "
					+ "    <TD class=\"In1-td-bottom2\" align=\"center\" width=\"400\" nowrap><strong><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><strong><font style=\"font-size:20px\"><font face=\"Arial Black\"><br> "
					+ "      银团贷款本金回收凭证</font></font></strong> </strong></TD> "
					+ "			<TD width=\"100\" nowrap align=\"left\">");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
				"			</TD> "
					+ "		</TR> "
					+ "		<TR> "
					+ "			<TD width=\"90\">&nbsp;	 "
					+ "			</TD> "
					+ "	</TABLE> "
					+ "	 "
					+ "<TABLE width=\"615\"> "
					+ "  <tr>  "
					+ "    <TD align=\"left\" width=\"200\"><B>货款种类：</B> <u><b>"
					+ info.getLoanType()
					+ "</b></u></TD> "
					+ "    <TD width=\"200\" align=\"center\"><B>"
					+ info.getYear()
					+ " 年 "
					+ info.getMonth()
					+ " 月 "
					+ info.getDay()
					+ " 日 "
					+ "</B> </TD> "
					+ "    <TD align=\"left\" width=\"230\" ><B>交易编号：</B> <b>"
					+ info.getTransNo()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
					+ "<TABLE width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" rowspan=\"4\" align=\"center\" class=\"In1-td-rightbottom\"><b>还<br> "
					+ "      款<br> "
					+ "      单<br> "
					+ "      位</b></TD> "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>名&nbsp;&nbsp;称</b> </TD> "
					+ "    <TD width=\"444\" colspan=\"3\" align=\"left\" class=\"In1-td-bottom\" >&nbsp;"
					+ info.getRepaymentUnitName()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>起止日期</b> </TD> "
					+ "    <TD height=\"24\" colspan=\"3\" align=\"center\" nowrap class=\"In1-td-bottom\"> &nbsp;"
					+ info.getLoanInterval()
					+ "</TD> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><B>合同编号</B> </TD> "
					+ "    <td width=\"129\" height=\"12\" align=\"left\" nowrap class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getContractCode()
					+ "</td> "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><B>放款单号</B> </TD> "
					+ "    <td width=\"129\" height=\"12\" align=\"left\" nowrap class=\"In1-td-bottom\" >&nbsp;"
					+ info.getBillCode()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD width=\"90\" align=\"center\" class=\"In1-td-rightbottom\"><b>付款银行</b> </TD> "
					+ "    <td width=\"100\" height=\"22\" align=\"center\" nowrap class=\"In1-td-rightbottom\">"
					+ info.getRepaymentBankName()
					+ "&nbsp;</td> "
					+ "    <td height=\"22\" align=\"center\" nowrap class=\"In1-td-rightbottom\" width=\"90\"><b>付款账号</b></td> "
					+ "    <td height=\"22\" align=\"left\" nowrap class=\"In1-td-bottom\">&nbsp;"
					+ info.getRepaymentAccountNo()
					+ "</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD align=\"left\" class=\"In1-td-rightbottom\"><B>本次还款金额</B> </TD> "
					+ "    <TD align=\"center\" class=\"In1-td-rightbottom\">"
					+ info.getCurrencyName()
					+ "<br> "
					+ "      （大写）"
					+ "</TD> "
					+ "    <td height=\"24\" colspan=\"2\" align=\"left\" nowrap class=\"In1-td-rightbottom\">"
					+ "    <b>  "
					+ info.getChineseAmount()
					+ "  </b>&nbsp; </td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getAmount()
					+ " </b> &nbsp;</td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD  rowspan=\"2\" align=\"left\" class=\"In1-td-rightbottom\"><B>摘要</B> "
					+ "</TD> "
					+ "    <TD colspan=\"2\" rowspan=\"2\" align=\"left\" class=\"In1-td-rightbottom\">&nbsp;"
					+ info.getAbstract()
					+ "</TD> "
					+ "    <td height=\"24\" align=\"center\" nowrap class=\"In1-td-rightbottom\">累计<br> "
					+ "      还款</td> "
					+ "    <td height=\"24\" align=\"right\" nowrap class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getTotalRepayAmount()
					+ "</b></td> "
					+ "  </TR> "
					+ "  <TR>  "
					+ "    <TD height=\"24\" align=\"center\" class=\"In1-td-rightbottom\">余额</TD> "
					+ "    <TD height=\"24\" align=\"right\" class=\"In1-td-bottom\">&nbsp;<b>"
					+ info.getBalance()
					+ "</b></TD> "
					+ "  </TR> "
					+ "</TABLE> "
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>"
					+ info.getNum()
					+ "<BR>联<BR><BR>参<BR>与<BR>行<BR>留<BR>存</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"In1-table1\" align=\"left\"> "
					+ "<tr><td colspan=\"5\" align=\"center\" class=\"In1-td-bottom\"><b>贷款收回明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>收款单位名称</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷比例(%)</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>开户银行</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\"><b>银行账号</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-bottom\"><b>收款金额(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanRepayDetailInfo detailInfo = null;
			Vector vctRepayDetailInfo = info.getSynLoanRepayDetail();
			if (vctRepayDetailInfo != null && vctRepayDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctRepayDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanRepayDetailInfo) vctRepayDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getReceiveAmountUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getLoanRate()
							+ "</td>"
							+ "<td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getOpenBank()
							+ "&nbsp;</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">&nbsp;"
							+ detailInfo.getBankAccountNo()
							+ "</td>"
							+ "<td width=\"120\" align=\"right\" class=\"In1-td-bottom\">&nbsp;￥"
							+ detailInfo.getReciveAmount()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"120\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"120\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.print(
				"<tr>"
					+ "<td width=\"120\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b>"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\" class=\"In1-td-right\">"
					+ info.getSumLoanRate()
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;"
					+ "</td>"
					+ "<td width=\"120\" align=\"right\">&nbsp;￥"
					+ info.getSumReciveAmount()
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600\" border=\"0\" align=\"right\">"
					+ "<TR align=\"right\"> "
					+ "<TD  height=\"22\" nowrap>&nbsp;</TD>"
					+ "<TD height=\"22\" nowrap>[录入]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ "'); </SCRIPT>");
		}
		catch (Exception e)
		{
		}
	}
	/**
	* 银团贷款利息通知单 第一联 记账凭证 第二联 代理行留存 第三联 企业留存 第四到N联 参与行留存
	 */
	public static void showSynLoanPayInterestNotice1(ShowPayInterestInfo info, JspWriter out) throws Exception
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
					+ "			<TD width=\"70\">&nbsp;	"
					+ "			</TD>"
					+ "			<TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><br><font style=\"font-size:20px\">银团贷款利息收回凭证</font></b></font>"
					+ "			</TD>"
					+ "			<TD width=\"90\">");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
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
					+ "<TABLE width=\"600\" border=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"50%\" align=\"left\">借款单位名称："
					+ info.getClientName()
					+ " </TD>"
					+ "    <TD width=\"50%\" align=\"right\">贷款类型："
					+ " 银团贷款</TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD>交易编号："
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD align=\"right\">&nbsp;&nbsp;账号："
					+ info.getAccountNo()
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
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
					+ info.getNormalInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDay()
					+ "&nbsp;</td>"
					+ "          <td width=\"110\" colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">复利</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
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
					+ "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">逾期罚息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
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
					+ "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getTotalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">担保费</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getAssureFeeAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getAssureFee()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "              <tr> "
					+ "                <td> 上列利息，已照收你单位第（"
					+ info.getInterestAccountNo()
					+ "）号账户。 </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应账户号：&nbsp;"
					+ info.getCurrentAccountNo()
					+ "  ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应合同号：&nbsp;"
					+ info.getContractNo()
					+ " ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应放款通知单号：&nbsp;"
					+ info.getLoanBillNo()
					+ "） </td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">转账日期：&nbsp; "
					+ info.getTransAccountDate()
					+ " </td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> （盖章） </td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>一<BR>联<BR><BR>记<BR>账<BR>凭<BR>证</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"table1\" align=\"left\"> "
					+ "<tr><td colspan=\"4\" align=\"center\" class=\"td-bottom\"><b>贷款利息明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>收息单位名称</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>开户银行</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>银行账号</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-bottom\"><b>应收利息(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanRepayDetailInfo detailInfo = null;
			Vector vctRepayDetailInfo = info.getSynLoanRepayDetail();
			if (vctRepayDetailInfo != null && vctRepayDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctRepayDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanRepayDetailInfo) vctRepayDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
							+ detailInfo.getReveiveInterestUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">&nbsp;"
							+ detailInfo.getOpenBank()
							+ "</td>"
							+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
							+ detailInfo.getBankAccountNo()
							+ "&nbsp;</td>"
							+ "<td width=\"150\" align=\"right\" class=\"td-bottom\">&nbsp;￥"
							+ detailInfo.getReceiveInterest()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.print(
				"<tr><td width=\"150\" align=\"center\" class=\"td-rightbottom\" >"
					+ "<b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b></td>"
					+ "<td  align=\"right\" class=\"td-bottom\" colspan=\"3\">&nbsp;￥"
					+ info.getSumInterest()
					+ "</td></tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600\" border=\"0\" align=\"right\">"
					+ "<TR align=\"right\"> "
					+ "<TD  height=\"22\" nowrap>&nbsp;</TD>"
					+ "<TD height=\"22\" nowrap>[录入]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ "'); </SCRIPT>");
		}
		catch (Exception e)
		{
		}
	}
	/**
	* 银团贷款利息通知单 第一联 记账凭证 第二联 代理行留存 第三联 企业留存 第四到N联 参与行留存
	 */
	public static void showSynLoanPayInterestNotice2(ShowPayInterestInfo info, JspWriter out) throws Exception
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
					+ "			<TD width=\"70\">&nbsp;	"
					+ "			</TD>"
					+ "			<TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><br><font style=\"font-size:20px\">银团贷款利息收回凭证</font></b></font>"
					+ "			</TD>"
					+ "			<TD width=\"90\">");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
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
					+ "<TABLE width=\"600\" border=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"50%\" align=\"left\">借款单位名称："
					+ info.getClientName()
					+ " </TD>"
					+ "    <TD width=\"50%\" align=\"right\">贷款类型："
					+ " 银团贷款</TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD>交易编号："
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD align=\"right\">&nbsp;&nbsp;账号："
					+ info.getAccountNo()
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
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
					+ info.getNormalInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDay()
					+ "&nbsp;</td>"
					+ "          <td width=\"110\" colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">复利</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
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
					+ "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">逾期罚息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
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
					+ "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getTotalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">担保费</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getAssureFeeAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getAssureFee()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "              <tr> "
					+ "                <td> 上列利息，已照收你单位第（"
					+ info.getInterestAccountNo()
					+ "）号账户。 </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应账户号：&nbsp;"
					+ info.getCurrentAccountNo()
					+ "  ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应合同号：&nbsp;"
					+ info.getContractNo()
					+ " ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应放款通知单号：&nbsp;"
					+ info.getLoanBillNo()
					+ "） </td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">转账日期：&nbsp; "
					+ info.getTransAccountDate()
					+ " </td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> （盖章） </td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>二<BR>联<BR><BR>代<BR>理<BR>行<BR>留<BR>存</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"table1\" align=\"left\"> "
					+ "<tr><td colspan=\"4\" align=\"center\" class=\"td-bottom\"><b>贷款利息明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>收息单位名称</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>开户银行</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>银行账号</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-bottom\"><b>应收利息(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanRepayDetailInfo detailInfo = null;
			Vector vctRepayDetailInfo = info.getSynLoanRepayDetail();
			if (vctRepayDetailInfo != null && vctRepayDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctRepayDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanRepayDetailInfo) vctRepayDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
							+ detailInfo.getReveiveInterestUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">&nbsp;"
							+ detailInfo.getOpenBank()
							+ "</td>"
							+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
							+ detailInfo.getBankAccountNo()
							+ "&nbsp;</td>"
							+ "<td width=\"150\" align=\"right\" class=\"td-bottom\">&nbsp;￥"
							+ detailInfo.getReceiveInterest()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.print(
				"<tr><td width=\"150\" align=\"center\" class=\"td-rightbottom\" >"
					+ "<b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b></td>"
					+ "<td  align=\"right\" class=\"td-bottom\" colspan=\"3\">&nbsp;￥"
					+ info.getSumInterest()
					+ "</td></tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600\" border=\"0\" align=\"right\">"
					+ "<TR align=\"right\"> "
					+ "<TD  height=\"22\" nowrap>&nbsp;</TD>"
					+ "<TD height=\"22\" nowrap>[录入]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ "'); </SCRIPT>");
		}
		catch (Exception e)
		{
		}
	}
	/**
	* 银团贷款利息通知单 第一联 记账凭证 第二联 代理行留存 第三联 企业留存 第四到N联 参与行留存
	 */
	public static void showSynLoanPayInterestNotice3(ShowPayInterestInfo info, JspWriter out) throws Exception
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
					+ "			<TD width=\"70\">&nbsp;	"
					+ "			</TD>"
					+ "			<TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><br><font style=\"font-size:20px\">银团贷款利息收回凭证</font></b></font>"
					+ "			</TD>"
					+ "			<TD width=\"90\">");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
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
					+ "<TABLE width=\"600\" border=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"50%\" align=\"left\">借款单位名称："
					+ info.getClientName()
					+ " </TD>"
					+ "    <TD width=\"50%\" align=\"right\">贷款类型："
					+ " 银团贷款</TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD>交易编号："
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD align=\"right\">&nbsp;&nbsp;账号："
					+ info.getAccountNo()
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
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
					+ info.getNormalInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDay()
					+ "&nbsp;</td>"
					+ "          <td width=\"110\" colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">复利</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
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
					+ "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">逾期罚息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
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
					+ "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getTotalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">担保费</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getAssureFeeAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getAssureFee()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "              <tr> "
					+ "                <td> 上列利息，已照收你单位第（"
					+ info.getInterestAccountNo()
					+ "）号账户。 </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应账户号：&nbsp;"
					+ info.getCurrentAccountNo()
					+ "  ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应合同号：&nbsp;"
					+ info.getContractNo()
					+ " ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应放款通知单号：&nbsp;"
					+ info.getLoanBillNo()
					+ "） </td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">转账日期：&nbsp; "
					+ info.getTransAccountDate()
					+ " </td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> （盖章） </td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>三<BR>联<BR><BR>企<BR>业<BR>留<BR>存</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"table1\" align=\"left\"> "
					+ "<tr><td colspan=\"4\" align=\"center\" class=\"td-bottom\"><b>贷款利息明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>收息单位名称</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>开户银行</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>银行账号</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-bottom\"><b>应收利息(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanRepayDetailInfo detailInfo = null;
			Vector vctRepayDetailInfo = info.getSynLoanRepayDetail();
			if (vctRepayDetailInfo != null && vctRepayDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctRepayDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanRepayDetailInfo) vctRepayDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
							+ detailInfo.getReveiveInterestUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">&nbsp;"
							+ detailInfo.getOpenBank()
							+ "</td>"
							+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
							+ detailInfo.getBankAccountNo()
							+ "&nbsp;</td>"
							+ "<td width=\"150\" align=\"right\" class=\"td-bottom\">&nbsp;￥"
							+ detailInfo.getReceiveInterest()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.print(
				"<tr><td width=\"150\" align=\"center\" class=\"td-rightbottom\" >"
					+ "<b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b></td>"
					+ "<td  align=\"right\" class=\"td-bottom\" colspan=\"3\">&nbsp;￥"
					+ info.getSumInterest()
					+ "</td></tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600\" border=\"0\" align=\"right\">"
					+ "<TR align=\"right\"> "
					+ "<TD  height=\"22\" nowrap>&nbsp;</TD>"
					+ "<TD height=\"22\" nowrap>[录入]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ "'); </SCRIPT>");
		}
		catch (Exception e)
		{
		}
	}
	/**
	* 银团贷款利息通知单 第一联 记账凭证 第二联 代理行留存 第三联 企业留存 第四到N联 参与行留存
	 */
	public static void showSynLoanPayInterestNotice4(ShowPayInterestInfo info, JspWriter out) throws Exception
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
					+ "			<TD width=\"70\">&nbsp;	"
					+ "			</TD>"
					+ "			<TD width=\"470\" height=\"20\" align=\"center\" class=\"td-bottom2\"><font style=\"font-size:16px\"><b><font style=\"font-size:16px\">"
					+ Env.getClientName()
					+ "</font><br><font style=\"font-size:20px\">银团贷款利息收回凭证</font></b></font>"
					+ "			</TD>"
					+ "			<TD width=\"90\">");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif  height=\"46\">");
			 
			out.print(
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
					+ "<TABLE width=\"600\" border=\"0\">"
					+ "  <TR> "
					+ "    <TD width=\"50%\" align=\"left\">借款单位名称："
					+ info.getClientName()
					+ " </TD>"
					+ "    <TD width=\"50%\" align=\"right\">贷款类型："
					+ " 银团贷款</TD>"
					+ "  </TR>"
					+ "  <TR>"
					+ "    <TD>交易编号："
					+ info.getTransNo()
					+ "</TD>"
					+ "    <TD align=\"right\">&nbsp;&nbsp;账号："
					+ info.getAccountNo()
					+ "</TD>"
					+ "  </TR>"
					+ "</TABLE>"
					+ "<table width=\"630\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"left\">"
					+ "<tr>"
					+ "<td>"
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
					+ info.getNormalInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getNormalInterestDay()
					+ "&nbsp;</td>"
					+ "          <td width=\"110\" colspan=\"2\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
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
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">复利</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getCompoundInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
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
					+ "          <td  width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">逾期罚息</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getOverInterestDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
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
					+ "          <td width=\"80\" height=\"7\"  class=\"td-rightbottom\" align=\"center\">利息总额</td>"
					+ "          <td  class=\"td-rightbottom\" colspan=\"6\">&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getTotalInterest()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr align=\"center\"> "
					+ "          <td width=\"80\" height=\"7\" class=\"td-rightbottom\" align=\"center\">担保费</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDateStart()
					+ "&nbsp;</td>"
					+ "          <td width=\"105\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDateEnd()
					+ "&nbsp;</td>"
					+ "          <td width=\"75\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeDay()
					+ "&nbsp;</td>"
					+ "          <td colspan=\"2\" width=\"110\" class=\"td-rightbottom\" align=\"right\">&nbsp;"
					+ info.getAssureFeeAmount()
					+ "</td>"
					+ "          <td width=\"65\" class=\"td-rightbottom\" align=\"center\">&nbsp;"
					+ info.getAssureFeeRate()
					+ "&nbsp;</td>"
					+ "          <td width=\"90\" class=\"td-bottom\" align=\"right\">&nbsp;"
					+ info.getAssureFee()
					+ "</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td class=\"td-right\" colspan=\"5\" rowspan=\"2\" align=\"center\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">"
					+ "              <tr> "
					+ "                <td> 上列利息，已照收你单位第（"
					+ info.getInterestAccountNo()
					+ "）号账户。 </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应账户号：&nbsp;"
					+ info.getCurrentAccountNo()
					+ "  ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应合同号：&nbsp;"
					+ info.getContractNo()
					+ " ） </td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td> （对应放款通知单号：&nbsp;"
					+ info.getLoanBillNo()
					+ "） </td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"3\">转账日期：&nbsp; "
					+ info.getTransAccountDate()
					+ " </td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td colspan=\"5\" class=\"td-right\" align=\"right\"> （盖章） </td>"
					+ "        </tr>"
					+ "      </table></TD>"
					+ "		</TR>"
					+ "	</TABLE>"
					+ "</td>"
					+ "<td rowspan=\"3\">"
					+ "			<FONT style=\"FONT-SIZE: 13px\">第<BR>"
					+ info.getNum()
					+ "<BR>联<BR><BR>参<BR>与<BR>行<BR>留<BR>存</FONT> "
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"table1\" align=\"left\"> "
					+ "<tr><td colspan=\"4\" align=\"center\" class=\"td-bottom\"><b>贷款利息明细表</b></td></tr>"
					+ "<tr>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>收息单位名称</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>开户银行</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\"><b>银行账号</b>"
					+ "</td>"
					+ "<td width=\"150\" align=\"center\" class=\"td-bottom\"><b>应收利息(元)</b>"
					+ "</td>"
					+ "</tr>");
			SynLoanRepayDetailInfo detailInfo = null;
			Vector vctRepayDetailInfo = info.getSynLoanRepayDetail();
			if (vctRepayDetailInfo != null && vctRepayDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctRepayDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanRepayDetailInfo) vctRepayDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
							+ detailInfo.getReveiveInterestUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">&nbsp;"
							+ detailInfo.getOpenBank()
							+ "</td>"
							+ "<td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
							+ detailInfo.getBankAccountNo()
							+ "&nbsp;</td>"
							+ "<td width=\"150\" align=\"right\" class=\"td-bottom\">&nbsp;￥"
							+ detailInfo.getReceiveInterest()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"150\" align=\"center\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"150\" align=\"right\" class=\"td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.print(
				"<tr><td width=\"150\" align=\"center\" class=\"td-rightbottom\" >"
					+ "<b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b></td>"
					+ "<td  align=\"right\" class=\"td-bottom\" colspan=\"3\">&nbsp;￥"
					+ info.getSumInterest()
					+ "</td></tr>"
					+ "</table>"
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+ "<Table width=\"600\" border=\"0\" align=\"right\">"
					+ "<TR align=\"right\"> "
					+ "<TD  height=\"22\" nowrap>&nbsp;</TD>"
					+ "<TD height=\"22\" nowrap>[录入]&nbsp;"
					+ info.getInputUserName()
					+ "&nbsp;[复核]&nbsp;"
					+ info.getCheckUserName()
					+ "</TD>"
					+ "  </TR>"
					+ "  </Table>"
					+ "</td>"
					+ "</tr>"
					+ "</table>"
					+ "</BODY> "
					+ "'); </SCRIPT>");
		}
		catch (Exception e)
		{
		}
	}
	/**
		 * Method showReceiveAgentFee1.
		 * 银团贷款代理费收取凭证 第一联 记账凭证
		 * @param info
		 * @param out
		 * @throws Exception
		 */
	public static void showReceiveAgentFee1(ShowSynLoanAgentFeeInfo info, JspWriter out) throws Exception
	{
		try
		{
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
					+ " --> "
					+ " </style> "
					+ "<body text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "<table width=\"615\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\">"
					+ "  <tr> "
					+ "    <td ><table width=\"615\" border=\"0\" >"
					+ "        <tr> "
					+ "          <td width=\"100\" height=\"46\" rowspan=\"2\">&nbsp;</td>"
					+ "          <td height=\"22\" width=\"415\" align=\"center\"><font style=\"font-size:16px\"><b>中国华能财务有限责任公司</b></font></td>"
					+ "          <td width=\"100\" height=\"46\" rowspan=\"2\">");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif>");
			 
			out.print(
				"</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td height=\"22\" align=\"center\" class=\"In1-td-bottom2\"><font style=\"font-size:20px\"  face=\"Arial Black\"><b>银团贷款代理费收取凭证</b></font></td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td align=\"center\"><b>&nbsp;"
					+ info.getYear()
					+ "&nbsp;年&nbsp;"
					+ info.getMonth()
					+ "&nbsp;月&nbsp;"
					+ info.getDay()
					+ "&nbsp;日</b></td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td align=\"center\">&nbsp;</td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td> <table width=\"630\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" >"
					+ "        <tr> "
					+ "          <td width=\"600\"> <table width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"In1-table1\">"
					+ "              <tr> "
					+ "                <td width=\"75\" height=\"25\" align=\"left\" class=\"In1-td-bottom\"><b>收款单位：</b></td>"
					+ "                <td width=\"145\" align=\"left\" class=\"In1-td-rightbottom\">"
					+ info.getReceiveUnit()
					+ "</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>合同编号</b></td>"
					+ "                <td width=\"105\" align=\"center\" class=\"In1-td-rightbottom\">"
					+ info.getContractNo()
					+ "</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>放款单号</b></td>"
					+ "                <td width=\"105\" align=\"center\"  class=\"In1-td-bottom\">"
					+ info.getLoanNoteNo()
					+ "</td>"
					+ "              </tr>"
					+ "              <tr > "
					+ "                <td width=\"75\" height=\"25\" align=\"left\" class=\"In1-td-bottom\"><b>付款银行：</b></td>"
					+ "                <td width=\"145\" align=\"left\" class=\"In1-td-rightbottom\">见明细表</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>开户行</b></td>"
					+ "                <td width=\"105\" align=\"center\" class=\"In1-td-rightbottom\">"
					+ info.getOpenBank()
					+ "</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>收款账户</b></td>"
					+ "                <td width=\"105\" align=\"center\" class=\"In1-td-bottom\">"
					+ info.getReceiveAccountNo()
					+ "</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td height=\"25\" align=\"left\" class=\"In1-td-bottom\"><b>贷款期限：</b></td>"
					+ "                <td colspan=\"5\" align=\"center\" class=\"In1-td-bottom\">"
					+ info.getInterval()
					+ "&nbsp;年，自&nbsp;"
					+ info.getStartYear()
					+ "&nbsp;年&nbsp;"
					+ info.getStartMonth()
					+ "&nbsp;月&nbsp;"
					+ info.getStartDay()
					+ "&nbsp;日&nbsp;至&nbsp;"
					+ info.getEndYear()
					+ "&nbsp;年&nbsp;"
					+ info.getEndMonth()
					+ "&nbsp;月&nbsp;"
					+ info.getEndDay()
					+ "&nbsp;日止</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td height=\"25\" align=\"left\" ><b>代理费率：</b></td>"
					+ "                <td align=\"right\" class=\"In1-td-right\" >"
					+ info.getAgentRate()
					+ "‰</td>"
					+ "                <td align=\"left\" ><b>&nbsp;代理费：</b></td>"
					+ "                <td colspan=\"3\" align=\"right\">￥"
					+ info.getAgentFee()
					+ "元</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "          <td width=\"35\" rowspan=\"3\" align=\"center\">第<br>"
					+ "            一<br>"
					+ "            联<br>"
					+ "            &nbsp;&nbsp;<br>"
					+ "            记<br>"
					+ "            账<br>"
					+ "            凭<br>"
					+ "            证</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td height=\"25\" align=\"center\" ><b>代理费明细表</b></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"In1-table1\">"
					+ "              <tr> "
					+ "                <td width=\"193\" height=\"25\" align=\"center\" class=\"In1-td-rightbottom\"><b>付款单位名称</b></td>"
					+ "                <td width=\"193\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷金额（万元）</b></td>"
					+ "                <td width=\"194\" align=\"center\" class=\"In1-td-bottom\"><b>应付代理费（元）</b></td>"
					+ "              </tr>");
			SynLoanGrantDetailInfo detailInfo = null;
			Vector vctGrantDetailInfo = info.getSynLoanAgentFeeDetail();
			if (vctGrantDetailInfo != null && vctGrantDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctGrantDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanGrantDetailInfo) vctGrantDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"193\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getPayUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"193\" align=\"right\" class=\"In1-td-rightbottom\">￥"
							+ detailInfo.getLoanAmount()
							+ "</td>"
							+ "<td width=\"194\" align=\"right\" class=\"In1-td-bottom\">￥"
							+ detailInfo.getAgentFee()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"193\" height=\"25\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"193\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"194\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.println(
				"              <tr> "
					+ "                <td width=\"193\" height=\"25\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b></td>"
					+ "                <td width=\"193\" align=\"right\" class=\"In1-td-right\">￥"
					+ info.getSumLoanAmount()
					+ "</td>"
					+ "                <td width=\"194\" align=\"right\">￥"
					+ info.getSumAgentFee()
					+ "</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "		<tr>"
					+ "		<td width=\"580\" height=\"25\" align=\"right\">[录入]&nbsp;"
					+ info.getInputUser()
					+ "&nbsp;[复核]&nbsp;"
					+ info.getCheckerUser()
					+ "</td>"
					+ "		<td width=\"35\">&nbsp;</td>"
					+ "		</tr>"
					+ "      </table></td>"
					+ "  </tr>"
					+ "  "
					+ "</table>"
					+ "</body>"
					+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
			 * Method showReceiveAgentFee2.
			 * 银团贷款代理费收取凭证 第二联 代理行留存
			 * @param info
			 * @param out
			 * @throws Exception
			 */
	public static void showReceiveAgentFee2(ShowSynLoanAgentFeeInfo info, JspWriter out) throws Exception
	{
		try
		{
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
					+ " --> "
					+ " </style> "
					+ "<body text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "<table width=\"615\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\">"
					+ "  <tr> "
					+ "    <td ><table width=\"615\" border=\"0\" >"
					+ "        <tr> "
					+ "          <td width=\"100\" height=\"46\" rowspan=\"2\">&nbsp;</td>"
					+ "          <td height=\"22\" width=\"415\" align=\"center\"><font style=\"font-size:16px\"><b>中国华能财务有限责任公司</b></font></td>"
					+ "          <td width=\"100\" height=\"46\" rowspan=\"2\">");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif>");
			 
			out.print(
				"</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td height=\"22\" align=\"center\" class=\"In1-td-bottom2\"><font style=\"font-size:20px\"  face=\"Arial Black\"><b>银团贷款代理费收取凭证</b></font></td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td align=\"center\"><b>&nbsp;"
					+ info.getYear()
					+ "&nbsp;年&nbsp;"
					+ info.getMonth()
					+ "&nbsp;月&nbsp;"
					+ info.getDay()
					+ "&nbsp;日</b></td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td align=\"center\">&nbsp;</td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td> <table width=\"630\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" >"
					+ "        <tr> "
					+ "          <td width=\"600\"> <table width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"In1-table1\">"
					+ "              <tr> "
					+ "                <td width=\"75\" height=\"25\" align=\"left\" class=\"In1-td-bottom\"><b>收款单位：</b></td>"
					+ "                <td width=\"145\" align=\"left\" class=\"In1-td-rightbottom\">"
					+ info.getReceiveUnit()
					+ "</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>合同编号</b></td>"
					+ "                <td width=\"105\" align=\"center\" class=\"In1-td-rightbottom\">"
					+ info.getContractNo()
					+ "</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>放款单号</b></td>"
					+ "                <td width=\"105\" align=\"center\"  class=\"In1-td-bottom\">"
					+ info.getLoanNoteNo()
					+ "</td>"
					+ "              </tr>"
					+ "              <tr > "
					+ "                <td width=\"75\" height=\"25\" align=\"left\" class=\"In1-td-bottom\"><b>付款银行：</b></td>"
					+ "                <td width=\"145\" align=\"left\" class=\"In1-td-rightbottom\">见明细表</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>开户行</b></td>"
					+ "                <td width=\"105\" align=\"center\" class=\"In1-td-rightbottom\">"
					+ info.getOpenBank()
					+ "</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>收款账户</b></td>"
					+ "                <td width=\"105\" align=\"center\" class=\"In1-td-bottom\">"
					+ info.getReceiveAccountNo()
					+ "</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td height=\"25\" align=\"left\" class=\"In1-td-bottom\"><b>贷款期限：</b></td>"
					+ "                <td colspan=\"5\" align=\"center\" class=\"In1-td-bottom\">"
					+ info.getInterval()
					+ "&nbsp;年，自&nbsp;"
					+ info.getStartYear()
					+ "&nbsp;年&nbsp;"
					+ info.getStartMonth()
					+ "&nbsp;月&nbsp;"
					+ info.getStartDay()
					+ "&nbsp;日&nbsp;至&nbsp;"
					+ info.getEndYear()
					+ "&nbsp;年&nbsp;"
					+ info.getEndMonth()
					+ "&nbsp;月&nbsp;"
					+ info.getEndDay()
					+ "&nbsp;日止</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td height=\"25\" align=\"left\" ><b>代理费率：</b></td>"
					+ "                <td align=\"right\" class=\"In1-td-right\" >"
					+ info.getAgentRate()
					+ "‰</td>"
					+ "                <td align=\"left\" ><b>&nbsp;代理费：</b></td>"
					+ "                <td colspan=\"3\" align=\"right\">￥"
					+ info.getAgentFee()
					+ "元</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "          <td width=\"35\" rowspan=\"3\" align=\"center\">第<br>"
					+ "            二<br>"
					+ "            联<br>"
					+ "            &nbsp;&nbsp;<br>"
					+ "            代<br>"
					+ "            理<br>"
					+ "            行<br>"
					+ "            留<br>存</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td height=\"25\" align=\"center\" ><b>代理费明细表</b></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"In1-table1\">"
					+ "              <tr> "
					+ "                <td width=\"193\" height=\"25\" align=\"center\" class=\"In1-td-rightbottom\"><b>付款单位名称</b></td>"
					+ "                <td width=\"193\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷金额（万元）</b></td>"
					+ "                <td width=\"194\" align=\"center\" class=\"In1-td-bottom\"><b>应付代理费（元）</b></td>"
					+ "              </tr>");
			SynLoanGrantDetailInfo detailInfo = null;
			Vector vctGrantDetailInfo = info.getSynLoanAgentFeeDetail();
			if (vctGrantDetailInfo != null && vctGrantDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctGrantDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanGrantDetailInfo) vctGrantDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"193\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getPayUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"193\" align=\"right\" class=\"In1-td-rightbottom\">￥"
							+ detailInfo.getLoanAmount()
							+ "</td>"
							+ "<td width=\"194\" align=\"right\" class=\"In1-td-bottom\">￥"
							+ detailInfo.getAgentFee()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"193\" height=\"25\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"193\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"194\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.println(
				"              <tr> "
					+ "                <td width=\"193\" height=\"25\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b></td>"
					+ "                <td width=\"193\" align=\"right\" class=\"In1-td-right\">￥"
					+ info.getSumLoanAmount()
					+ "</td>"
					+ "                <td width=\"194\" align=\"right\">￥"
					+ info.getSumAgentFee()
					+ "</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "		<tr>"
					+ "		<td width=\"580\" height=\"25\" align=\"right\">[录入]&nbsp;"
					+ info.getInputUser()
					+ "&nbsp;[复核]&nbsp;"
					+ info.getCheckerUser()
					+ "</td>"
					+ "		<td width=\"35\">&nbsp;</td>"
					+ "		</tr>"
					+ "      </table></td>"
					+ "  </tr>"
					+ "  "
					+ "</table>"
					+ "</body>"
					+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	/**
		 * Method showReceiveAgentFee3.
		 * 银团贷款代理费收取凭证 第三联 参与行留存
		 * @param info
		 * @param out
		 * @throws Exception
		 */
	public static void showReceiveAgentFee3(ShowSynLoanAgentFeeInfo info, JspWriter out) throws Exception
	{
		try
		{
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
					+ " --> "
					+ " </style> "
					+ "<body text=\"#000000\" bgcolor=\"#FFFFFF\">"
					+ "<table width=\"615\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\">"
					+ "  <tr> "
					+ "    <td ><table width=\"615\" border=\"0\" >"
					+ "        <tr> "
					+ "          <td width=\"100\" height=\"46\" rowspan=\"2\">&nbsp;</td>"
					+ "          <td height=\"22\" width=\"415\" align=\"center\"><font style=\"font-size:16px\"><b>中国华能财务有限责任公司</b></font></td>"
					+ "          <td width=\"100\" height=\"46\" rowspan=\"2\">");
			 
				out.print("<img src=" + Env.SETTLEMENT_URL + "../../websett/dayin_logo.gif>");
			 
			out.print(
				"</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td height=\"22\" align=\"center\" class=\"In1-td-bottom2\"><font style=\"font-size:20px\"  face=\"Arial Black\"><b>银团贷款代理费收取凭证</b></font></td>"
					+ "        </tr>"
					+ "      </table></td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td align=\"center\"><b>&nbsp;"
					+ info.getYear()
					+ "&nbsp;年&nbsp;"
					+ info.getMonth()
					+ "&nbsp;月&nbsp;"
					+ info.getDay()
					+ "&nbsp;日</b></td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td align=\"center\">&nbsp;</td>"
					+ "  </tr>"
					+ "  <tr> "
					+ "    <td> <table width=\"630\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" >"
					+ "        <tr> "
					+ "          <td width=\"600\"> <table width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"In1-table1\">"
					+ "              <tr> "
					+ "                <td width=\"75\" height=\"25\" align=\"left\" class=\"In1-td-bottom\"><b>收款单位：</b></td>"
					+ "                <td width=\"145\" align=\"left\" class=\"In1-td-rightbottom\">"
					+ info.getReceiveUnit()
					+ "</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>合同编号</b></td>"
					+ "                <td width=\"105\" align=\"center\" class=\"In1-td-rightbottom\">"
					+ info.getContractNo()
					+ "</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>放款单号</b></td>"
					+ "                <td width=\"105\" align=\"center\"  class=\"In1-td-bottom\">"
					+ info.getLoanNoteNo()
					+ "</td>"
					+ "              </tr>"
					+ "              <tr > "
					+ "                <td width=\"75\" height=\"25\" align=\"left\" class=\"In1-td-bottom\"><b>付款银行：</b></td>"
					+ "                <td width=\"145\" align=\"left\" class=\"In1-td-rightbottom\">见明细表</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>开户行</b></td>"
					+ "                <td width=\"105\" align=\"center\" class=\"In1-td-rightbottom\">"
					+ info.getOpenBank()
					+ "</td>"
					+ "                <td width=\"75\" align=\"center\" class=\"In1-td-rightbottom\"><b>收款账户</b></td>"
					+ "                <td width=\"105\" align=\"center\" class=\"In1-td-bottom\">"
					+ info.getReceiveAccountNo()
					+ "</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td height=\"25\" align=\"left\" class=\"In1-td-bottom\"><b>贷款期限：</b></td>"
					+ "                <td colspan=\"5\" align=\"center\" class=\"In1-td-bottom\">"
					+ info.getInterval()
					+ "&nbsp;年，自&nbsp;"
					+ info.getStartYear()
					+ "&nbsp;年&nbsp;"
					+ info.getStartMonth()
					+ "&nbsp;月&nbsp;"
					+ info.getStartDay()
					+ "&nbsp;日&nbsp;至&nbsp;"
					+ info.getEndYear()
					+ "&nbsp;年&nbsp;"
					+ info.getEndMonth()
					+ "&nbsp;月&nbsp;"
					+ info.getEndDay()
					+ "&nbsp;日止</td>"
					+ "              </tr>"
					+ "              <tr> "
					+ "                <td height=\"25\" align=\"left\" ><b>代理费率：</b></td>"
					+ "                <td align=\"right\" class=\"In1-td-right\" >"
					+ info.getAgentRate()
					+ "‰</td>"
					+ "                <td align=\"left\" ><b>&nbsp;代理费：</b></td>"
					+ "                <td colspan=\"3\" align=\"right\">￥"
					+ info.getAgentFee()
					+ "元</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "          <td width=\"35\" rowspan=\"3\" align=\"center\">第<br>"
					+ info.getNum()
					+ "            <br>"
					+ "            联<br>"
					+ "            &nbsp;&nbsp;<br>"
					+ "            参<br>"
					+ "            与<br>"
					+ "            行<br>"
					+ "            留<br>"
					+ "存</td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td height=\"25\" align=\"center\" ><b>代理费明细表</b></td>"
					+ "        </tr>"
					+ "        <tr> "
					+ "          <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"In1-table1\">"
					+ "              <tr> "
					+ "                <td width=\"193\" height=\"25\" align=\"center\" class=\"In1-td-rightbottom\"><b>付款单位名称</b></td>"
					+ "                <td width=\"193\" align=\"center\" class=\"In1-td-rightbottom\"><b>承贷金额（万元）</b></td>"
					+ "                <td width=\"194\" align=\"center\" class=\"In1-td-bottom\"><b>应付代理费（元）</b></td>"
					+ "              </tr>");
			SynLoanGrantDetailInfo detailInfo = null;
			Vector vctGrantDetailInfo = info.getSynLoanAgentFeeDetail();
			if (vctGrantDetailInfo != null && vctGrantDetailInfo.size() > 0)
			{
				for (int i = 0; i < vctGrantDetailInfo.size(); i++)
				{
					detailInfo = (SynLoanGrantDetailInfo) vctGrantDetailInfo.elementAt(i);
					out.print(
						"<tr>"
							+ "<td width=\"193\" align=\"center\" class=\"In1-td-rightbottom\">"
							+ detailInfo.getPayUnitName()
							+ "&nbsp;</td>"
							+ "<td width=\"193\" align=\"right\" class=\"In1-td-rightbottom\">￥"
							+ detailInfo.getLoanAmount()
							+ "</td>"
							+ "<td width=\"194\" align=\"right\" class=\"In1-td-bottom\">￥"
							+ detailInfo.getAgentFee()
							+ "</td>"
							+ "</tr>");
				}
			}
			else
			{
				out.print(
					"<tr><td width=\"193\" height=\"25\" align=\"center\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"193\" align=\"right\" class=\"In1-td-rightbottom\">"
						+ "&nbsp;</td>"
						+ "<td width=\"194\" align=\"right\" class=\"In1-td-bottom\">"
						+ "&nbsp;</td></tr>");
			}
			out.println(
				"              <tr> "
					+ "                <td width=\"193\" height=\"25\" align=\"center\" class=\"In1-td-right\"><b>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</b></td>"
					+ "                <td width=\"193\" align=\"right\" class=\"In1-td-right\">￥"
					+ info.getSumLoanAmount()
					+ "</td>"
					+ "                <td width=\"194\" align=\"right\">￥"
					+ info.getSumAgentFee()
					+ "</td>"
					+ "              </tr>"
					+ "            </table></td>"
					+ "        </tr>"
					+ "		<tr>"
					+ "		<td width=\"580\" height=\"25\" align=\"right\">[录入]&nbsp;"
					+ info.getInputUser()
					+ "&nbsp;[复核]&nbsp;"
					+ info.getCheckerUser()
					+ "</td>"
					+ "		<td width=\"35\">&nbsp;</td>"
					+ "		</tr>"
					+ "      </table></td>"
					+ "  </tr>"
					+ "  "
					+ "</table>"
					+ "</body>"
					+ " '); </SCRIPT>  ");
		}
		catch (Exception e)
		{
		}
	}
	public static void main(java.lang.String[] args) throws Exception
	{
		//在此处插入用来启动应用程序的代码。
		try
		{
			ShowGrantLoanInfo info = new ShowGrantLoanInfo();
			JspWriter out = null;
			ISynLoanPrintTemplate.showSynLoanGrantLoan1(info, out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
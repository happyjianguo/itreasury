package com.iss.itreasury.settlement.print;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.loan.bizdelegation.YTLoanAttendBankDelegation;
import com.iss.itreasury.loan.setting.dataentity.YTLoanAttendBankInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.print.templateinfo.ShowGrantLoanInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowPayInterestInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowRepaymentLoanInfo;
import com.iss.itreasury.settlement.print.templateinfo.ShowSynLoanAgentFeeInfo;
import com.iss.itreasury.settlement.print.templateinfo.SynLoanGrantDetailInfo;
import com.iss.itreasury.settlement.print.templateinfo.SynLoanRepayDetailInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.BankLoanDrawInfo;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SyndicationLoanInterestInfo;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
/**
 * @author gqzhang
 *
 * 银团打印信息设置
 * 功能：完成从业务数据到打印凭证模版数据之间的对应关系
 */
public class SynLoanPrintVoucher
{
	/**
	 * Method PrintLoanInterestNotice.
	 * 打印应付贷款利息通知单 放款单明细条目-用于银团贷款
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintSynLoanInterestNoticeDetails(Vector vctLoanNoticeDetails, JspWriter out) throws Exception
	{
		try
		{
			PrintInfo pi = null;
			boolean bIsFirst = true;
			for (int i = 0; i < vctLoanNoticeDetails.size(); i++)
			{
				//取得每个合同的汇总 和 明细条目
				pi = (PrintInfo) vctLoanNoticeDetails.elementAt(i);
				LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
				lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
				Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
				lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
				System.out.println("===放款通知单：" + pi.getLoanNoteID());
				System.out.println("===起息日：" + lpfdinfo.getInterestStart());
				//利率
				PrintInfo tmpPrintInfo = new PrintInfo();
				//判断利率调整日期是否在起息日和结息日之间
				if (pi.getAdjustRateDate() != null && pi.getInterestStartDate() != null && pi.getClearInterestDate() != null)
				{
					if (pi.getAdjustRateDate().after(pi.getInterestStartDate())
						&& (pi.getAdjustRateDate().before(pi.getClearInterestDate()) || pi.getAdjustRateDate() == pi.getClearInterestDate()))
					{
						//调整日期在起息日和结息日之间
						//执行利率
						tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
						//贷款利率调整日期
						tmpPrintInfo.setAdjustRateDate(pi.getAdjustRateDate());
						//调整后年息
						tmpPrintInfo.setExecuteRateNew(pi.getExecuteRateNew());
					}
					else
						if (!pi.getAdjustRateDate().after(pi.getInterestStartDate()))
						{
							//调整日期在起息日之前，或在起息日当日
							if (pi.getExecuteRateNew() > 0 && (pi.getExecuteRate() != pi.getExecuteRateNew()))
							{
								tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
								//贷款利率调整日期
								tmpPrintInfo.setAdjustRateDate(null);
								//调整后年息
								tmpPrintInfo.setExecuteRateNew(0.0);
							}
							else
							{
								tmpPrintInfo.setExecuteRate(pi.getExecuteRate());
								//贷款利率调整日期
								tmpPrintInfo.setAdjustRateDate(null);
								//调整后年息
								tmpPrintInfo.setExecuteRateNew(0.0);
							}
						}
				}
				else
				{
					if (pi.getExecuteRateNew() == 0)
					{
						//执行利率
						tmpPrintInfo.setExecuteRate(pi.getExecuteRate());
						//贷款利率调整日期
						tmpPrintInfo.setAdjustRateDate(null);
						//调整后年息
						tmpPrintInfo.setExecuteRateNew(0.0);
					}
					else
					{
						//调整后年息
						tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
						//贷款利率调整日期
						tmpPrintInfo.setAdjustRateDate(null);
						//调整后年息
						tmpPrintInfo.setExecuteRateNew(0.0);
					}
				}
				
				//根据合同查找牵头行与参与行的承贷比例情况
				BankLoanQuery bankLoanQuery = new BankLoanQuery();
				Collection collSyn = bankLoanQuery.findByPayFormID(pi.getLoanNoteID());
				SyndicationLoanInterestInfo syndicationLoanInterestInfo = null;
				Vector  vctSyn = new Vector();
				//在明细中增设牵头行和各参与行利息的情况
				if(collSyn != null)
				{
					Iterator it = collSyn.iterator();
					while (it.hasNext())
					{
						syndicationLoanInterestInfo =  (SyndicationLoanInterestInfo)it.next();
						
						//打印放款单明细开始
						if (bIsFirst)
						{
							bIsFirst = false;
							out.println("<br clear=all style='page-break-before:always'>");
							out.println(
								" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"> "
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
									+ " --> "
									+ " </style> "
									+ "<!-- saved from url=(0022)http://internet.e-mail --> "
									+ "<body>"
									+ "<table width=\"630\" border=\"0\" align=\"center\">"
									+ "  <tr> "
									+ "    <td align=\"right\" height=\"30\"><font style=\"font-size:12px\"><b>编号：<u>&nbsp;&nbsp;"
									+ pi.getFormYear()
									+ "&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;"
									+ pi.getFormNo()
									+ "&nbsp;&nbsp;</u>号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></font></td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td align=\"center\" height=\"40\"><font style=\"font-size:24px\"><b>"
									+ Env.getClientName()
									+ "</b></font></td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td align=\"center\" height=\"40\"><font style=\"font-size:20px\"><b>"+pi.getDetailTitle()+"（附录）</b></font></td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td height=\"30\">&nbsp;</td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td align=\"left\" height=\"40\"><font style=\"font-size:20px\"><b>借款人：<u>&nbsp;"
									+ pi.getBorrowClientName()
									+ "&nbsp;</u></b></font></td>"
									+ "  <tr> "
									+ "    <td align=\"left\" height=\"40\"><font style=\"font-size:20px\"><b>贷款合同号：<u>&nbsp;"
									+ NameRef.getContractNoByID(pi.getContractID())
									+ "&nbsp;</u></b></font></td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td align=\"left\" height=\"40\"><font style=\"font-size:20px\"><b>放款通知单编号：<u>&nbsp;"
									+ NameRef.getPayFormNoByID(pi.getLoanNoteID())
									+ "&nbsp;</u></b></font></td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td>"
									+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"table1\" width=\"640\">"
									+ "<tr>"
									+ "<td class=\"td-right\" align=\"center\">贷款银行</td>"
									+ "<td class=\"td-right\" align=\"center\">贷款金额</td>"
									+ "<td class=\"td-right\" align=\"center\">放款日期</td>"
									+ "<td class=\"td-right\" align=\"center\">起息日</td>"
									+ "<td class=\"td-right\" align=\"center\">止息日</td>"
									+ "<td class=\"td-right\" align=\"center\">利率％</td>"
									+ "<td class=\"td-right\" align=\"center\">应付利息</td>"
									+ "<td class=\"td-right\" align=\"center\">应付复利</td>"
									+ "<td class=\"td-right\" align=\"center\">应付罚息</td>"
									+ "<td  align=\"center\">应付利息合计</td>"
									+ "</tr>");
						}
						out.println(
							"<tr>"
								+ "<td class=\"td-topright\" align=\"center\"><font style=\"font-size:12px\">"
								+ syndicationLoanInterestInfo.getBankName()
								+ "</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(lpfdinfo.getAmount()*syndicationLoanInterestInfo.getRate()/100))
								+ "</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">"
								+ DataFormat.formatDate(lpfdinfo.getInterestStart())
								+ "</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">"
								+ DataFormat.formatDate(pi.getInterestStartDate())
								+ "</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">"
								+ DataFormat.formatDate(DataFormat.getPreviousDate(pi.getClearInterestDate()))
								+ "</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">"
								+ DataFormat.formatRate(DataFormat.formatRate(String.valueOf(DataFormat.formatRate(lpfdinfo.getInterestRate()))))
								+ "</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ (pi.getInterest()>0?"￥":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getInterest()*syndicationLoanInterestInfo.getRate()/100)
								+ "</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ (pi.getCompoundInterest()>0?"￥":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getCompoundInterest()*syndicationLoanInterestInfo.getRate()/100)
								+ "</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ (pi.getOverDueInterest()>0?"￥":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getOverDueInterest()*syndicationLoanInterestInfo.getRate()/100)
								+ "</font></td>"
								+ "<td class=\"td-top\" align=\"right\"><font style=\"font-size:12px\">"
								+ "￥"
								+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()*syndicationLoanInterestInfo.getRate()/100) + DataFormat.formatDouble(pi.getCompoundInterest()*syndicationLoanInterestInfo.getRate()/100) + DataFormat.formatDouble(pi.getOverDueInterest()*syndicationLoanInterestInfo.getRate()/100)))
								+ "</font></td>"
								+ "</tr>");
						System.out.println("打印银团贷款汇总的利息金额为："+DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getInterest()*syndicationLoanInterestInfo.getRate()/100) + DataFormat.formatDouble(pi.getCompoundInterest()*syndicationLoanInterestInfo.getRate()/100) + DataFormat.formatDouble(pi.getOverDueInterest()*syndicationLoanInterestInfo.getRate()/100)));
						System.out.println("1金额为："+DataFormat.formatDouble(pi.getInterest()*syndicationLoanInterestInfo.getRate()/100));
						System.out.println("2金额为："+DataFormat.formatDouble(pi.getCompoundInterest()*syndicationLoanInterestInfo.getRate()/100));
						System.out.println("3金额为："+DataFormat.formatDouble(pi.getOverDueInterest()*syndicationLoanInterestInfo.getRate()/100));

						//打印放款单明细结束
					}
					//打印放款单明细汇总
					out.println(
							"<tr>"
								+ "<td class=\"td-topright\" align=\"center\"><font style=\"font-size:12px\"><B>合计</B></font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ "￥"
								+ DataFormat.formatDisabledAmount(lpfdinfo.getAmount())
								+ "</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ (pi.getInterest()>0?"￥":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getInterest())
								+ "</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ (pi.getCompoundInterest()>0?"￥":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getCompoundInterest())
								+ "</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ (pi.getOverDueInterest()>0?"￥":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getOverDueInterest())
								+ "</font></td>"
								+ "<td class=\"td-top\" align=\"right\"><font style=\"font-size:12px\">"
								+ "￥"
								+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()) + pi.getOverDueInterest()))
								+ "</font></td>"
								+ "</tr>");
					//打印放款单明细汇总end
					out.println("</table></td></tr></table>" + "</body>");
					bIsFirst = true;
				}	


			} //end for
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
	}

	/**
	 * Method PrintSynLoanGrant.
	 * 银团贷款发放凭证
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintSynLoanGrant(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowGrantLoanInfo info = new ShowGrantLoanInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			info.setAbstract(pi.getAbstract());
			info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
			info.setAmount(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setChargeRate(DataFormat.formatDisabledAmount(pi.getRealCommission()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//合同利率
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			//代理费利率
			info.setAgentFeeRate(DataFormat.formatRate(String.valueOf(lpfdinfo.getCommissionRate())));
			//委托单位
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//贷款账户号
			info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//借款单位，即自营贷款账户
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//收款人账号
				info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//收款人开户银行名称
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else
				if (pi.getReceiveBankID() > 0)
				{
					//收款人账号
					info.setAccountNo(pi.getExtAccountNo());
					//收款人开户银行名称
					info.setOpenBankName(pi.getExtRemitInBank());
				}
			info.setTransNo(pi.getTransNo());
			//设置发放贷款明细，银行名称，承贷比例，承贷金额
			double dSumRate = 0.0;
			double dSumAmount = 0.0;
			Vector vctDetail = pi.getDetail();
			Vector vctTemp = new Vector();
			Vector vctWithOutHead = new Vector();
			BankLoanDrawInfo bankLoanDrawInfo = null;
			SynLoanGrantDetailInfo synLoanGrantDetailInfo = null;
			if (vctDetail != null)
			{
				System.out.println("===发放贷款明细 not null===");
				for (int i = 0; i < vctDetail.size(); i++)
				{
					synLoanGrantDetailInfo = new SynLoanGrantDetailInfo();
					bankLoanDrawInfo = (BankLoanDrawInfo) vctDetail.elementAt(i);
					synLoanGrantDetailInfo.setLoanUnitName(DataFormat.formatString(bankLoanDrawInfo.getBankName()));
					synLoanGrantDetailInfo.setLoanRate(DataFormat.formatRate(String.valueOf(DataFormat.formatDouble(bankLoanDrawInfo.getRate()))));
					synLoanGrantDetailInfo.setLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(bankLoanDrawInfo.getDrawAmount())));
					dSumRate = DataFormat.formatDouble(dSumRate + DataFormat.formatDouble(bankLoanDrawInfo.getRate()));
					dSumAmount = DataFormat.formatDouble(dSumAmount + DataFormat.formatDouble(bankLoanDrawInfo.getDrawAmount()));
					if (bankLoanDrawInfo.getIsHead() != 1)
					{
						vctWithOutHead.add(synLoanGrantDetailInfo);
						vctTemp.add(synLoanGrantDetailInfo);
					}
					else
					{
						vctTemp.add(synLoanGrantDetailInfo);
					}
				}
				info.setSynLoanGrantDetail(vctTemp);
			}
			//合计承贷比例
			info.setSumAssumeLoanRate(DataFormat.formatRate(String.valueOf(dSumRate)));
			//合计承贷金额
			info.setSunAssumeLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(dSumAmount)));
			ISynLoanPrintTemplate.showSynLoanGrantLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanGrantLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanGrantLoan3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanGrantLoan4(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			//参与行留存凭证
			info.setSynLoanGrantDetail(vctWithOutHead); //特殊处理
			vctTemp = info.getSynLoanGrantDetail();
			synLoanGrantDetailInfo = null;
			Vector vctTemp2 = null;
			if (vctTemp != null)
			{
				for (int i = 0; i < vctTemp.size(); i++)
				{
					vctTemp2 = new Vector();
					synLoanGrantDetailInfo = (SynLoanGrantDetailInfo) vctTemp.elementAt(i);
					//合计承贷比例
					info.setSumAssumeLoanRate(synLoanGrantDetailInfo.getLoanRate());
					//合计承贷金额
					info.setSunAssumeLoanAmount(synLoanGrantDetailInfo.getLoanAmount());
					vctTemp2.add(synLoanGrantDetailInfo);
					info.setSynLoanGrantDetail(vctTemp2);
					info.setNum(getChineseNumByNum(i + 5) + "");
					ISynLoanPrintTemplate.showSynLoanGrantLoan5(info, out);
					if (i != (vctTemp.size() - 1))
					{
						out.println("<br clear=all style='page-break-before:always'>");
					}
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}
	/**
	 * Method PrintSynbLoanAgentFee.
	 * 银团贷款代理费收取凭证
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintSynbLoanAgentFee(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowSynLoanAgentFeeInfo info = new ShowSynLoanAgentFeeInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			//收款单位 ，取值于"办事处编号设置中总部的名称"
			info.setReceiveUnit(NameRef.getOfficeNameByID(1));
			//合同编号
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//放款单号
			info.setLoanNoteNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//开户行,取值贷款合同中牵头行所对应的开户行（置于后面处理）
			info.setOpenBank("&nbsp;");
			//收款账号，取值贷款合同中牵头行所对应的账户编号(置于后面处理)
			info.setReceiveAccountNo("&nbsp;");
			//贷款期限
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			info.setInterval(lpfdinfo.getLoanTerm() / 12 + "");
			String strLoanStart = DataFormat.formatDate(lpfdinfo.getStart());
			if (strLoanStart.length() < 10)
			{
				strLoanStart = "0000000000";
			}
			info.setStartYear(strLoanStart.substring(0, 4));
			info.setStartMonth(strLoanStart.substring(5, 7));
			info.setStartDay(strLoanStart.substring(8, 10));
			String strLoanEnd = DataFormat.formatDate(lpfdinfo.getEnd());
			if (strLoanEnd.length() < 10)
			{
				strLoanEnd = "0000000000";
			}
			info.setEndYear(strLoanEnd.substring(0, 4));
			info.setEndMonth(strLoanEnd.substring(5, 7));
			info.setEndDay(strLoanEnd.substring(8, 10));
			//代理费率
			info.setAgentRate(DataFormat.formatRate(String.valueOf(lpfdinfo.getCommissionRate())));
			//设置代理费用明细，付款单位名称，承贷金额，应付代理费
			double dSumAmount = 0.0;
			double dSumAgentFee = 0.0;
			Vector vctDetail = pi.getDetail();
			Vector vctTemp = new Vector();
			BankLoanDrawInfo bankLoanDrawInfo = null;
			SynLoanGrantDetailInfo synLoanGrantDetailInfo = null;
			if (vctDetail != null)
			{
				System.out.println("===发放贷款明细 not null===");
				for (int i = 0; i < vctDetail.size(); i++)
				{
					synLoanGrantDetailInfo = new SynLoanGrantDetailInfo();
					bankLoanDrawInfo = (BankLoanDrawInfo) vctDetail.elementAt(i);
					if (bankLoanDrawInfo.getIsHead() != 1)
					{
						synLoanGrantDetailInfo.setPayUnitName(DataFormat.formatString(bankLoanDrawInfo.getBankName()));
						synLoanGrantDetailInfo.setLoanAmount(DataFormat.formatAmount(bankLoanDrawInfo.getDrawAmount() / 10000, 0));
						synLoanGrantDetailInfo.setAgentFee(
							DataFormat.formatString(DataFormat.formatDisabledAmount(DataFormat.formatDouble(bankLoanDrawInfo.getCommission())) + ""));
						dSumAmount = DataFormat.formatDouble(dSumAmount + DataFormat.formatDouble(bankLoanDrawInfo.getDrawAmount() / 10000));
						dSumAgentFee = DataFormat.formatDouble(dSumAgentFee + DataFormat.formatDouble(bankLoanDrawInfo.getCommission()));
						vctTemp.add(synLoanGrantDetailInfo);
					}
					else
					{
						//开户行,取值贷款合同中牵头行所对应的开户行（承前说明）
						//收款账号，取值贷款合同中牵头行所对应的账户编号（承前说明）
						YTLoanAttendBankInfo ytLoanAttendBankInfo = null;
						YTLoanAttendBankDelegation delegation = new YTLoanAttendBankDelegation();
						ytLoanAttendBankInfo = delegation.findById(bankLoanDrawInfo.getBankID());
						if (ytLoanAttendBankInfo != null)
						{
							info.setOpenBank(DataFormat.formatString(ytLoanAttendBankInfo.getBank()));
							info.setReceiveAccountNo(DataFormat.formatString(ytLoanAttendBankInfo.getBankAccountNo()));
						}
					}
				}
				info.setSynLoanAgentFeeDetail(vctTemp);
			}
			//代理费
			info.setAgentFee(DataFormat.formatString(DataFormat.formatDisabledAmount(DataFormat.formatDouble(dSumAgentFee)) + ""));
			//承贷金额合计,单位万元
			info.setSumLoanAmount(DataFormat.formatAmount(dSumAmount, 0));
			//应付代理费合计
			info.setSumAgentFee(DataFormat.formatDisabledAmount(dSumAgentFee, 0));
			//录入人
			info.setInputUser(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setCheckerUser(NameRef.getUserNameByID(pi.getCheckUserID()));
			ISynLoanPrintTemplate.showReceiveAgentFee1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showReceiveAgentFee2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			//参与行留存凭证
			vctTemp = info.getSynLoanAgentFeeDetail();
			synLoanGrantDetailInfo = null;
			Vector vctTemp2 = null;
			if (vctTemp != null)
			{
				for (int i = 0; i < vctTemp.size(); i++)
				{
					vctTemp2 = new Vector();
					synLoanGrantDetailInfo = (SynLoanGrantDetailInfo) vctTemp.elementAt(i);
					//合计承贷金额
					info.setSumLoanAmount(synLoanGrantDetailInfo.getLoanAmount());
					//合计承贷比例
					info.setSumAgentFee(synLoanGrantDetailInfo.getAgentFee());
					vctTemp2.add(synLoanGrantDetailInfo);
					info.setSynLoanAgentFeeDetail(vctTemp2);
					//第几联
					info.setNum(getChineseNumByNum(i + 3) + "");
					ISynLoanPrintTemplate.showReceiveAgentFee3(info, out);
					if (i != (vctTemp.size() - 1))
					{
						out.println("<br clear=all style='page-break-before:always'>");
					}
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}
	/**
	 * Method PrintSynLoanRepayment.
	 * 银团贷款本金收回凭证
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintSynLoanRepayment(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowRepaymentLoanInfo info = new ShowRepaymentLoanInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			String strRealInterest = "";
			String strRealCompoundInterest = "";
			String strRealOverDueInterest = "";
			if (pi.getRealInterest() == 0.0)
			{
				strRealInterest = "0.00";
			}
			else
			{
				strRealInterest = pi.getRealInterest() + "";
			}
			if (pi.getRealCompoundInterest() == 0.0)
			{
				strRealCompoundInterest = "0.00";
			}
			else
			{
				strRealCompoundInterest = pi.getRealCompoundInterest() + "";
			}
			if (pi.getRealOverDueInterest() == 0.0)
			{
				strRealOverDueInterest = "0.00";
			}
			else
			{
				strRealOverDueInterest = pi.getRealOverDueInterest() + "";
			}
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			info.setAbstract(
				pi.getAbstract()
					+ "已收正常利息："
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealInterest
					+ "；已收复利："
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealCompoundInterest
					+ "；已收罚息："
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealOverDueInterest);
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//合同利率
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			//委托单位
			info.setConsignUnit(lpfdinfo.getClientName());
			//余额
			if (pi.getCurrentBalance() > 0)
			{
				info.setBalance(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			//累计还款
			if ((lpfdinfo.getAmount() - pi.getCurrentBalance()) > 0)
			{
				info.setTotalRepayAmount(
					PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(lpfdinfo.getAmount() - pi.getCurrentBalance()));
			}
			else
			{
				info.setTotalRepayAmount(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setDateStart(DataFormat.getChineseDateString(pi.getStartDate()));
			info.setDateEnd(DataFormat.getChineseDateString(pi.getEndDate()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterest(DataFormat.formatDisabledAmount(pi.getInterest()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " 至 " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanRate(DataFormat.formatDisabledAmount(pi.getRate()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setNote(pi.getAbstract());
			info.setOverDueInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
			info.setRepaymentUnitName(NameRef.getClientNameByID(pi.getPayClientID()));
			info.setTotalInterest(DataFormat.formatDisabledAmount(pi.getRealInterest() + pi.getRealCompoundInterest() + pi.getRealOverDueInterest() + pi.getRealSuretyFee()));
			if (pi.getPayBankID() > 0)
			{
				info.setRepaymentBankName(NameRef.getBankNameByID(pi.getPayBankID()));
				info.setRepaymentAccountNo(DataFormat.formatString(IPrintTemplate.getBankAccountCodeByID(pi.getPayBankID())));
			}
			else
			{
				info.setRepaymentBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
				info.setRepaymentAccountNo(DataFormat.formatString(NameRef.getAccountNoByID(pi.getPayAccountID())));
			}
			info.setTransNo(pi.getTransNo());
			double dSumLoanRate = 0.0;
			double dSumReciveAmount = 0.0;
			BankLoanQuery bankLoanQuery = new BankLoanQuery();
			Vector vctDetail = new Vector();
			Vector vctWithOutHead = new Vector();
			Collection collDetail = null;
			BankLoanDrawInfo bankLoanDrawInfo = null;
			SynLoanRepayDetailInfo repayDetailInfo = null;
			collDetail = bankLoanQuery.findByFormID(pi.getLoanNoteID());
			System.out.println("放款通知单ID:" + pi.getLoanNoteID());
			//设置本金
			info.setAmount(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getAmount()));
			if (collDetail != null)
			{
				Iterator it = null;
				it = collDetail.iterator();
				while (it.hasNext())
				{
					bankLoanDrawInfo = (BankLoanDrawInfo) it.next();
					repayDetailInfo = new SynLoanRepayDetailInfo();
					if (bankLoanDrawInfo != null)
					{
						//收款单位名称
						repayDetailInfo.setReceiveAmountUnitName(DataFormat.formatString(bankLoanDrawInfo.getBankName()));
						//承贷比例
						repayDetailInfo.setLoanRate(DataFormat.formatString(DataFormat.formatAmount(bankLoanDrawInfo.getRate(), 0) + ""));
						dSumLoanRate = DataFormat.formatDouble(dSumLoanRate + DataFormat.formatDouble(bankLoanDrawInfo.getRate()));
						YTLoanAttendBankInfo ytLoanAttendBankInfo = null;
						YTLoanAttendBankDelegation delegation = new YTLoanAttendBankDelegation();
						ytLoanAttendBankInfo = delegation.findById(bankLoanDrawInfo.getBankID());
						if (ytLoanAttendBankInfo != null)
						{
							//开户银行
							repayDetailInfo.setOpenBank(DataFormat.formatString(ytLoanAttendBankInfo.getBank()));
							//收款金额
							repayDetailInfo.setBankAccountNo(DataFormat.formatString(ytLoanAttendBankInfo.getBankAccountNo()));
						}
						else
						{
							//开户银行
							repayDetailInfo.setOpenBank("&nbsp;");
							//银行账号
							repayDetailInfo.setBankAccountNo("&nbsp;");
						}
						//收款金额
						repayDetailInfo.setReciveAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount() * bankLoanDrawInfo.getRate() / 100)));
					}
					if (bankLoanDrawInfo.getIsHead() != 1)
					{
						vctWithOutHead.add(repayDetailInfo);
						vctDetail.add(repayDetailInfo);
					}
					else
					{
						vctDetail.add(repayDetailInfo);
					}
				}
			}
			info.setSumLoanRate(String.valueOf(dSumLoanRate));
			info.setSumReciveAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount())) + "");
			info.setSynLoanRepayDetail(vctDetail);
			ISynLoanPrintTemplate.showSynLoanRepaymentLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanRepaymentLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanRepaymentLoan3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			//参与行留存凭证
			info.setSynLoanRepayDetail(vctWithOutHead); //特殊处理
			vctDetail = info.getSynLoanRepayDetail();
			repayDetailInfo = null;
			Vector vctTemp = null;
			if (vctDetail != null)
			{
				for (int i = 0; i < vctDetail.size(); i++)
				{
					vctTemp = new Vector();
					repayDetailInfo = (SynLoanRepayDetailInfo) vctDetail.elementAt(i);
					//合计承贷比例
					info.setSumLoanRate(DataFormat.formatRate(repayDetailInfo.getLoanRate()));
					//合计承贷金额
					info.setSumReciveAmount(repayDetailInfo.getReciveAmount());
					vctTemp.add(repayDetailInfo);
					info.setSynLoanRepayDetail(vctTemp);
					info.setNum(getChineseNumByNum(i + 4) + "");
					ISynLoanPrintTemplate.showSynLoanRepaymentLoan4(info, out);
					if (i != (vctDetail.size() - 1))
					{
						out.println("<br clear=all style='page-break-before:always'>");
					}
				}
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}
	}
	/**
	 * Method PrintSynLoanInterest.
	 * 银团贷款利息收回凭证
	 * @param pi
	 * @param out
	 * @throws Exception
	 */
	public static void PrintSynLoanInterest(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowPayInterestInfo info = new ShowPayInterestInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			//借款单位名称
			if (pi.getPayClientID() > 0)
			{
				info.setClientName(NameRef.getClientNameByID(pi.getPayClientID()));
			}
			else
			{
				info.setClientName(pi.getBorrowClientName());
			}
			//委托单位名称
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//账号类型
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//交易编号
			info.setTransNo(pi.getTransNo());
			//账号&账户名称
			long lPayAccountID = -1;
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE
				|| pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE
				|| pi.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				if (pi.getReceiveAccountID() > 0)
				{
					lPayAccountID = pi.getReceiveAccountID();
					info.setAccountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
					info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				}
				else
					if (pi.getPayAccountID() > 0)
					{
						lPayAccountID = pi.getPayAccountID();
						info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
						info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
					}
			}
			else
			{
				lPayAccountID = pi.getPayAccountID();
				info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
				info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			}
			System.out.println("实际支付利息：" + pi.getRealInterest());
			System.out.println("付款账户号：" + lPayAccountID);
			System.out.println("放款通知单号：" + pi.getLoanNoteID());
			System.out.println("算息起止日期:" + pi.getNormalInterestStart() + "---" + pi.getNormalInterestEnd());
			if (pi.getRealInterest() > 0)
			{
				/*//加入分段利息
				//正常利息
				//加入分段利息
				SubsectionInterest dao = new SubsectionInterest();
				SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
				PrintSubsectionInfo =
					dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getLatestInterestClearDate(), DataFormat.getPreviousDate(pi.getInterestClearDate()));
				info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //正常利息开始日期
				info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
				info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //正常利息本金
				info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //正常利息率
				info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //正常利息
				info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
				*/
				info.setNormalInterestDateStart(DataFormat.getDateString(pi.getLatestInterestClearDate())); //正常利息开始日期
				info.setNormalInterestDateEnd(DataFormat.getDateString(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				info.setNormalInterestAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount()))); //正常利息本金
				info.setNormalInterestRate(DataFormat.formatRate(String.valueOf(DataFormat.formatRate(pi.getCompoundRate())))); //正常利息率
				info.setNormalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getRealInterest()))); //正常利息
				info.setNormalInterestDay((PrintVoucher.getIntervalDays(pi.getLatestInterestClearDate(), pi.getInterestClearDate(), 1)) + "");
				//加入分段利息End
			}
			//复利
			if (pi.getRealCompoundInterest() > 0)
			{
				//复利利息起息日期
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//复利利息终息日期
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{
					info.setCompoundInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setCompoundInterestDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//复利利息天数
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((PrintVoucher.getIntervalDays(pi.getCompoundInterestStart(), pi.getInterestStartDate(), 1)) + "");
				}
				info.setCompoundInterestAmount("&nbsp;");
				//复利利率
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate()+""));
				//复利利息	
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
				if (pi.getRealCompoundInterest() == 0.0)
				{
					info.setCompoundInterest("0.00");
				}
			}
			//逾期罚息
			if (pi.getRealOverDueInterest() > 0)
			{
				//逾期罚息利息起息日期
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDateStart(DataFormat.formatDate(pi.getOverDueStart()));
				}
				//逾期罚息利息终息日期
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setOverInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setOverInterestDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//逾期罚息利息天数
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((PrintVoucher.getIntervalDays(pi.getOverDueStart(), pi.getInterestClearDate(), 1)) + "");
				}
				//逾期罚息本金
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				//逾期罚息利率
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate() + ""));
				//逾期罚息利息
				info.setOverInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
				if (pi.getRealOverDueInterest() == 0.0)
				{
					info.setOverInterest("0.00");
				}
			}
			//手续费
			if (pi.getRealCommission() > 0)
			{
				//手续费利息起息日期
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				//手续费利息终息日期
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setCommissionFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setCommissionFeeDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//手续费利息天数
				info.setCommissionFeeDay((PrintVoucher.getIntervalDays(pi.getCommissionStart(), pi.getInterestClearDate(), 1)) + "");
				//手续费利息本金		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//手续费利率				
				System.out.print("========手续费利率：" + pi.getCommissionRate());
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
				//手续费利息
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
				if (pi.getRealCommission() == 0.0)
				{
					info.setCommissionFee("0.00");
				}
			}
			if (pi.getRealSuretyFee() > 0)
			{
				//担保费
				//担保费利息起息日期
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				//担保费利息终息日期
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setAssureFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setAssureFeeDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//担保费利息天数				
				info.setAssureFeeDay((PrintVoucher.getIntervalDays(pi.getSuretyFeeStart(), pi.getInterestClearDate(), 1)) + "");
				//担保费利息本金		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//担保费利率
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
				//担保费利息
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
				if (pi.getRealSuretyFee() == 0.0)
				{
					info.setAssureFee("0.00");
				}
			}
			//利息总额
			info.setTotalInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())));
			info.setTotalInterestChinese(
				ChineseCurrency.showChinese(
					DataFormat.formatAmount(
						DataFormat.formatDouble(pi.getRealInterest())
							+ DataFormat.formatDouble(pi.getRealCompoundInterest())
							+ DataFormat.formatDouble(pi.getRealOverDueInterest())),
					pi.getCurrencyID()));
			if (DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())
				== 0.0)
			{
				info.setTotalInterest("0.00");
			}
			//利息账户号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//对应的账户号
			info.setCurrentAccountNo(NameRef.getAccountNoByID(lPayAccountID));
			//对应的合同号
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//对应借据号
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//对应存单号
			info.setDepositBillNo(pi.getFixedDepositNo());
			//转账日
			info.setTransAccountDate(DataFormat.formatDate(pi.getInterestStartDate()));
			//录入人
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("机制");
			}
			//复核人
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					info.setCheckUserName("机制");
				}
				else
				{
					info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp");
				}
			}
			//设置贷款利息明细，收息单位名称，开户银行，银行账号，应收利息（元）
			Vector vctDetail = pi.getDetail();
			Vector vctTemp = new Vector();
			Vector vctWithOutHead = new Vector();
			SyndicationLoanInterestInfo interestInfo = null;
			SynLoanRepayDetailInfo synLoanRepayDetailInfo = null;
			double dSumInterest = 0.0;
			double dTotalSumInterest = 0.0;
			if (vctDetail != null)
			{
				System.out.println("===贷款利息明细 not null===");
				for (int i = 0; i < vctDetail.size(); i++)
				{
					synLoanRepayDetailInfo = new SynLoanRepayDetailInfo();
					interestInfo = (SyndicationLoanInterestInfo) vctDetail.elementAt(i);
					//收息单位名称
					synLoanRepayDetailInfo.setReveiveInterestUnitName(interestInfo.getBankName());
					YTLoanAttendBankInfo ytLoanAttendBankInfo = null;
					YTLoanAttendBankDelegation delegation = new YTLoanAttendBankDelegation();
					ytLoanAttendBankInfo = delegation.findById(interestInfo.getBankID());
					if (ytLoanAttendBankInfo != null)
					{
						synLoanRepayDetailInfo.setOpenBank(DataFormat.formatString(ytLoanAttendBankInfo.getBank()));
						synLoanRepayDetailInfo.setBankAccountNo(DataFormat.formatString(ytLoanAttendBankInfo.getBankAccountNo()));
					}
					else
					{
						//开户银行
						synLoanRepayDetailInfo.setOpenBank("&nbsp;");
						//银行账号
						synLoanRepayDetailInfo.setBankAccountNo("&nbsp;");
					}
					//应收利息（元）
					dSumInterest =
						DataFormat.formatDouble(
							DataFormat.formatDouble(interestInfo.getInterest())
								+ DataFormat.formatDouble(interestInfo.getCompoundInterest())
								+ DataFormat.formatDouble(interestInfo.getForpeitInterest()));
					//合计利息
					synLoanRepayDetailInfo.setReceiveInterest(DataFormat.formatDisabledAmount(dSumInterest) + "");
					dTotalSumInterest = DataFormat.formatDouble(dTotalSumInterest + DataFormat.formatDouble(dSumInterest));
					if (interestInfo.getIsHead() != 1)
					{
						vctTemp.add(synLoanRepayDetailInfo);
						vctWithOutHead.add(synLoanRepayDetailInfo);
					}
					else
					{
						vctTemp.add(synLoanRepayDetailInfo);
					}
				}
				info.setSynLoanRepayDetail(vctTemp);
				info.setSumInterest(DataFormat.formatDisabledAmount(dTotalSumInterest) + "");
			}
			ISynLoanPrintTemplate.showSynLoanPayInterestNotice1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanPayInterestNotice2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanPayInterestNotice3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			//参与行留存凭证
			info.setSynLoanRepayDetail(vctWithOutHead); //特殊处理
			vctDetail = info.getSynLoanRepayDetail();
			synLoanRepayDetailInfo = null;
			vctTemp = null;
			if (vctDetail != null)
			{
				for (int i = 0; i < vctDetail.size(); i++)
				{
					vctTemp = new Vector();
					synLoanRepayDetailInfo = (SynLoanRepayDetailInfo) vctDetail.elementAt(i);
					vctTemp.add(synLoanRepayDetailInfo);
					info.setSynLoanRepayDetail(vctTemp);
					info.setNum(getChineseNumByNum(i + 4) + "");
					info.setSumInterest(synLoanRepayDetailInfo.getReceiveInterest());
					ISynLoanPrintTemplate.showSynLoanPayInterestNotice4(info, out);
					if (i != (vctDetail.size() - 1))
					{
						out.println("<br clear=all style='page-break-before:always'>");
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
	}
	/**
		*  银团利息通知单(结息单专用)打印
		* @throws Exception
		*/
	public static void PrintSynLoanInterest1(PrintInfo pi, JspWriter out) throws Exception
	{
		try
		{
			ShowPayInterestInfo info = new ShowPayInterestInfo();
			String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			info.setYear(strExecuteDate.substring(0, 4));
			info.setMonth(strExecuteDate.substring(5, 7));
			info.setDay(strExecuteDate.substring(8, 10));
			//借款单位名称
			info.setClientName(pi.getBorrowClientName());
			//委托单位名称(需求变更2004/04/12改为账户名称)
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//账号类型
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//交易编号
			info.setTransNo(pi.getTransNo());
			//账号&账户名称
			long lPayAccountID = -1;
			if (pi.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
				if (pi.getReceiveAccountID() > 0)
				{
					lPayAccountID = pi.getReceiveAccountID();
					info.setAccountName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
					info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				}
				else
					if (pi.getPayAccountID() > 0)
					{
						lPayAccountID = pi.getPayAccountID();
						info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
						info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
					}
			}
			else
			{
				lPayAccountID = pi.getPayAccountID();
				info.setAccountName(NameRef.getAccountNameByID(pi.getPayAccountID()));
				info.setAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			}
			if (pi.getRealInterest() > 0)
			{
				//加入分段利息
				/*SubsectionInterest dao = new SubsectionInterest();
				SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
				PrintSubsectionInfo = dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getNormalInterestStart(), pi.getNormalInterestEnd());
				info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //正常利息开始日期
				info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
				info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //正常利息本金
				info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //正常利息率
				info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //正常利息
				info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
				*/
				//加入分段利息End
				//正常利息开始日期
				info.setNormalInterestDateStart(DataFormat.formatDate(pi.getNormalInterestStart()));
				//正常利息结束日期
				info.setNormalInterestDateEnd(DataFormat.formatDate(pi.getNormalInterestEnd()));
				//正常利息本金
				info.setNormalInterestAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				//正常利息率
				info.setNormalInterestRate(DataFormat.formatRate(pi.getRate() + ""));
				//正常利息
				info.setNormalInterest(DataFormat.formatDisabledAmount(pi.getRealInterest()));
				//天数
				info.setNormalInterestDay((PrintVoucher.getIntervalDays(pi.getNormalInterestStart(), pi.getNormalInterestEnd(), 1) + 1) + "");
			}
			//复利
			//起始日期--放款日期后第一个复利设置日期，利率等于正常利息的利率
			if (pi.getRealCompoundInterest() > 0)
			{
				//复利利息起息日期
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//复利利息终息日期
				info.setCompoundInterestDateEnd(DataFormat.formatDate(pi.getCompoundInterestEnd()));
				//复利利息天数
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((PrintVoucher.getIntervalDays(pi.getCompoundInterestStart(), pi.getCompoundInterestEnd(), 1) + 1) + "");
				}
				//复利本金显示为空
				info.setCompoundInterestAmount("&nbsp;");
				//复利利率
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate() + ""));
				//复利利息	
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
			}
			//逾期罚息
			//起始日期罚息通知单的罚息日期/上一次结息的日期（根据不同情况），利率取罚息通知单的罚息日期
			if (pi.getRealOverDueInterest() > 0)
			{
				//逾期罚息利息起息日期
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDateStart(DataFormat.formatDate(pi.getOverDueStart()));
				}
				//逾期罚息利息终息日期
				info.setOverInterestDateEnd(DataFormat.formatDate(pi.getOverDueEnd()));
				//逾期罚息利息天数
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((PrintVoucher.getIntervalDays(pi.getOverDueStart(), pi.getOverDueEnd(), 1) + 1) + "");
				}
				//逾期罚息本金
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				//逾期罚息利率
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate() + ""));
				//逾期罚息利息
				info.setOverInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
			}
			//手续费
			//起始日期取放款通知单的起始日期/上一次结算手续费的日期
			if (pi.getRealCommission() > 0)
			{
				//手续费利息起息日期
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				//手续费利息终息日期
				info.setCommissionFeeDateEnd(DataFormat.formatDate(pi.getCommissionFeeEnd()));
				//手续费利息天数
				info.setCommissionFeeDay((PrintVoucher.getIntervalDays(pi.getCommissionStart(), pi.getCommissionFeeEnd(), 1) + 1) + "");
				//手续费利息本金		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getCommissionAmount()));
				if (pi.getCommissionAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//手续费利率				
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
				//手续费利息
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
			}
			if (pi.getRealSuretyFee() > 0)
			{
				//担保费
				//起始日期取放款通知单的起始日期/上一次结算但报费的日期，利率从放款通知单取
				//担保费利息起息日期
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				//担保费利息终息日期
				info.setAssureFeeDateEnd(DataFormat.formatDate(pi.getSuretyFeeEnd()));
				//担保费利息天数				
				info.setAssureFeeDay((PrintVoucher.getIntervalDays(pi.getSuretyFeeStart(), pi.getSuretyFeeEnd(), 1) + 1) + "");
				//担保费利息本金		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getSuretyFeeAmount()));
				if (pi.getSuretyFeeAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//担保费利率
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
				//担保费利息
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
			}
			//利息总额
			info.setTotalInterest(
				DataFormat.formatDisabledAmount(
					DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())));
			info.setTotalInterestChinese(
				ChineseCurrency.showChinese(
					DataFormat.formatAmount(
						DataFormat.formatDouble(pi.getRealInterest())
							+ DataFormat.formatDouble(pi.getRealCompoundInterest())
							+ DataFormat.formatDouble(pi.getRealOverDueInterest())),
					pi.getCurrencyID()));
			if (DataFormat.formatDouble(pi.getRealInterest()) + DataFormat.formatDouble(pi.getRealCompoundInterest()) + DataFormat.formatDouble(pi.getRealOverDueInterest())
				== 0.0)
			{
				info.setTotalInterest("0.00");
			}
			//利息账户号
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//对应的活期账户号
			info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//对应的合同号
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//对应借据号
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//对应存单号
			info.setDepositBillNo(pi.getFixedDepositNo());
			//转账日
			info.setTransAccountDate(DataFormat.formatDate(pi.getExecuteDate()));
			//录入人
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("机制");
			}
			//复核人			
			if(pi.getCheckUserID() > 0)
			    info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					info.setCheckUserName("机制");
				}
				else
				{
					info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp");
				}
			}
			
			System.out.println("贷款类型:" + pi.getTransTypeID());
			if (pi.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				System.out.println("贷款类型－银团");
				//				设置贷款利息明细，收息单位名称，开户银行，银行账号，应收利息（元）
				Vector vctDetail = pi.getDetail();
				Vector vctTemp = new Vector();
				Vector vctWithOutHead = new Vector();
				SyndicationLoanInterestInfo interestInfo = null;
				SynLoanRepayDetailInfo synLoanRepayDetailInfo = null;
				double dSumInterest = 0.0;
				double dTotalSumInterest = 0.0;
				if (vctDetail != null)
				{
					System.out.println("===贷款利息明细 not null===");
					for (int i = 0; i < vctDetail.size(); i++)
					{
						synLoanRepayDetailInfo = new SynLoanRepayDetailInfo();
						interestInfo = (SyndicationLoanInterestInfo) vctDetail.elementAt(i);
						//收息单位名称
						synLoanRepayDetailInfo.setReveiveInterestUnitName(interestInfo.getBankName());
						YTLoanAttendBankInfo ytLoanAttendBankInfo = null;
						YTLoanAttendBankDelegation delegation = new YTLoanAttendBankDelegation();
						ytLoanAttendBankInfo = delegation.findById(interestInfo.getBankID());
						if (ytLoanAttendBankInfo != null)
						{
							synLoanRepayDetailInfo.setOpenBank(DataFormat.formatString(ytLoanAttendBankInfo.getBank()));
							synLoanRepayDetailInfo.setBankAccountNo(DataFormat.formatString(ytLoanAttendBankInfo.getBankAccountNo()));
						}
						else
						{
							//开户银行
							synLoanRepayDetailInfo.setOpenBank("&nbsp;");
							//银行账号
							synLoanRepayDetailInfo.setBankAccountNo("&nbsp;");
						}
						//应收利息（元）
						dSumInterest =
							DataFormat.formatDouble(
								DataFormat.formatDouble(interestInfo.getInterest())
									+ DataFormat.formatDouble(interestInfo.getCompoundInterest())
									+ DataFormat.formatDouble(interestInfo.getForpeitInterest()));
						//合计利息
						synLoanRepayDetailInfo.setReceiveInterest(DataFormat.formatDisabledAmount(dSumInterest) + "");
						dTotalSumInterest = DataFormat.formatDouble(dTotalSumInterest + DataFormat.formatDouble(dSumInterest));
						if (interestInfo.getIsHead() != 1)
						{
							vctTemp.add(synLoanRepayDetailInfo);
							vctWithOutHead.add(synLoanRepayDetailInfo);
						}
						else
						{
							vctTemp.add(synLoanRepayDetailInfo);
						}
					}
					info.setSynLoanRepayDetail(vctTemp);
					info.setSumInterest(DataFormat.formatDisabledAmount(dTotalSumInterest) + "");
				}
				ISynLoanPrintTemplate.showSynLoanPayInterestNotice1(info, out);
				out.println("<br clear=all style='page-break-before:always'>");
				ISynLoanPrintTemplate.showSynLoanPayInterestNotice2(info, out);
				out.println("<br clear=all style='page-break-before:always'>");
				ISynLoanPrintTemplate.showSynLoanPayInterestNotice3(info, out);
				out.println("<br clear=all style='page-break-before:always'>");
				//参与行留存凭证
				info.setSynLoanRepayDetail(vctWithOutHead); //特殊处理
				vctDetail = info.getSynLoanRepayDetail();
				synLoanRepayDetailInfo = null;
				vctTemp = null;
				if (vctDetail != null)
				{
					for (int i = 0; i < vctDetail.size(); i++)
					{
						vctTemp = new Vector();
						synLoanRepayDetailInfo = (SynLoanRepayDetailInfo) vctDetail.elementAt(i);
						vctTemp.add(synLoanRepayDetailInfo);
						info.setSynLoanRepayDetail(vctTemp);
						info.setNum(getChineseNumByNum(i + 4) + "");
						info.setSumInterest(synLoanRepayDetailInfo.getReceiveInterest());
						ISynLoanPrintTemplate.showSynLoanPayInterestNotice4(info, out);
						if (i != (vctDetail.size() - 1))
						{
							out.println("<br clear=all style='page-break-before:always'>");
						}
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
	}
	public static String getChineseNumByNum(int nNum)
	{
		String strReturn = "";
		switch (nNum)
		{
			case 1 :
				strReturn = "一";
				break;
			case 2 :
				strReturn = "二";
				break;
			case 3 :
				strReturn = "三";
				break;
			case 4 :
				strReturn = "四";
				break;
			case 5 :
				strReturn = "五";
				break;
			case 6 :
				strReturn = "六";
				break;
			case 7 :
				strReturn = "七";
				break;
			case 8 :
				strReturn = "八";
				break;
			case 9 :
				strReturn = "九";
				break;
			case 10 :
				strReturn = "十";
				break;
			case 11 :
				strReturn = "十一";
				break;
			case 12 :
				strReturn = "十二";
				break;
			case 13 :
				strReturn = "十三";
				break;
			case 14 :
				strReturn = "十四";
				break;
			default :
				strReturn = "&nbsp;&nbsp;";
		}
		return strReturn;
	}
}
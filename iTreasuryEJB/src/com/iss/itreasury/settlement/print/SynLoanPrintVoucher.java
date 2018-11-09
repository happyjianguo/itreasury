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
 * ���Ŵ�ӡ��Ϣ����
 * ���ܣ���ɴ�ҵ�����ݵ���ӡƾ֤ģ������֮��Ķ�Ӧ��ϵ
 */
public class SynLoanPrintVoucher
{
	/**
	 * Method PrintLoanInterestNotice.
	 * ��ӡӦ��������Ϣ֪ͨ�� �ſ��ϸ��Ŀ-�������Ŵ���
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
				//ȡ��ÿ����ͬ�Ļ��� �� ��ϸ��Ŀ
				pi = (PrintInfo) vctLoanNoticeDetails.elementAt(i);
				LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
				lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
				Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
				lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
				System.out.println("===�ſ�֪ͨ����" + pi.getLoanNoteID());
				System.out.println("===��Ϣ�գ�" + lpfdinfo.getInterestStart());
				//����
				PrintInfo tmpPrintInfo = new PrintInfo();
				//�ж����ʵ��������Ƿ�����Ϣ�պͽ�Ϣ��֮��
				if (pi.getAdjustRateDate() != null && pi.getInterestStartDate() != null && pi.getClearInterestDate() != null)
				{
					if (pi.getAdjustRateDate().after(pi.getInterestStartDate())
						&& (pi.getAdjustRateDate().before(pi.getClearInterestDate()) || pi.getAdjustRateDate() == pi.getClearInterestDate()))
					{
						//������������Ϣ�պͽ�Ϣ��֮��
						//ִ������
						tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
						//�������ʵ�������
						tmpPrintInfo.setAdjustRateDate(pi.getAdjustRateDate());
						//��������Ϣ
						tmpPrintInfo.setExecuteRateNew(pi.getExecuteRateNew());
					}
					else
						if (!pi.getAdjustRateDate().after(pi.getInterestStartDate()))
						{
							//������������Ϣ��֮ǰ��������Ϣ�յ���
							if (pi.getExecuteRateNew() > 0 && (pi.getExecuteRate() != pi.getExecuteRateNew()))
							{
								tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
								//�������ʵ�������
								tmpPrintInfo.setAdjustRateDate(null);
								//��������Ϣ
								tmpPrintInfo.setExecuteRateNew(0.0);
							}
							else
							{
								tmpPrintInfo.setExecuteRate(pi.getExecuteRate());
								//�������ʵ�������
								tmpPrintInfo.setAdjustRateDate(null);
								//��������Ϣ
								tmpPrintInfo.setExecuteRateNew(0.0);
							}
						}
				}
				else
				{
					if (pi.getExecuteRateNew() == 0)
					{
						//ִ������
						tmpPrintInfo.setExecuteRate(pi.getExecuteRate());
						//�������ʵ�������
						tmpPrintInfo.setAdjustRateDate(null);
						//��������Ϣ
						tmpPrintInfo.setExecuteRateNew(0.0);
					}
					else
					{
						//��������Ϣ
						tmpPrintInfo.setExecuteRate(pi.getExecuteRateNew());
						//�������ʵ�������
						tmpPrintInfo.setAdjustRateDate(null);
						//��������Ϣ
						tmpPrintInfo.setExecuteRateNew(0.0);
					}
				}
				
				//���ݺ�ͬ����ǣͷ��������еĳд��������
				BankLoanQuery bankLoanQuery = new BankLoanQuery();
				Collection collSyn = bankLoanQuery.findByPayFormID(pi.getLoanNoteID());
				SyndicationLoanInterestInfo syndicationLoanInterestInfo = null;
				Vector  vctSyn = new Vector();
				//����ϸ������ǣͷ�к͸���������Ϣ�����
				if(collSyn != null)
				{
					Iterator it = collSyn.iterator();
					while (it.hasNext())
					{
						syndicationLoanInterestInfo =  (SyndicationLoanInterestInfo)it.next();
						
						//��ӡ�ſ��ϸ��ʼ
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
									+ "    <td align=\"right\" height=\"30\"><font style=\"font-size:12px\"><b>��ţ�<u>&nbsp;&nbsp;"
									+ pi.getFormYear()
									+ "&nbsp;&nbsp;</u>��<u>&nbsp;&nbsp;"
									+ pi.getFormNo()
									+ "&nbsp;&nbsp;</u>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></font></td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td align=\"center\" height=\"40\"><font style=\"font-size:24px\"><b>"
									+ Env.getClientName()
									+ "</b></font></td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td align=\"center\" height=\"40\"><font style=\"font-size:20px\"><b>"+pi.getDetailTitle()+"����¼��</b></font></td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td height=\"30\">&nbsp;</td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td align=\"left\" height=\"40\"><font style=\"font-size:20px\"><b>����ˣ�<u>&nbsp;"
									+ pi.getBorrowClientName()
									+ "&nbsp;</u></b></font></td>"
									+ "  <tr> "
									+ "    <td align=\"left\" height=\"40\"><font style=\"font-size:20px\"><b>�����ͬ�ţ�<u>&nbsp;"
									+ NameRef.getContractNoByID(pi.getContractID())
									+ "&nbsp;</u></b></font></td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td align=\"left\" height=\"40\"><font style=\"font-size:20px\"><b>�ſ�֪ͨ����ţ�<u>&nbsp;"
									+ NameRef.getPayFormNoByID(pi.getLoanNoteID())
									+ "&nbsp;</u></b></font></td>"
									+ "  </tr>"
									+ "  <tr> "
									+ "    <td>"
									+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"table1\" width=\"640\">"
									+ "<tr>"
									+ "<td class=\"td-right\" align=\"center\">��������</td>"
									+ "<td class=\"td-right\" align=\"center\">������</td>"
									+ "<td class=\"td-right\" align=\"center\">�ſ�����</td>"
									+ "<td class=\"td-right\" align=\"center\">��Ϣ��</td>"
									+ "<td class=\"td-right\" align=\"center\">ֹϢ��</td>"
									+ "<td class=\"td-right\" align=\"center\">���ʣ�</td>"
									+ "<td class=\"td-right\" align=\"center\">Ӧ����Ϣ</td>"
									+ "<td class=\"td-right\" align=\"center\">Ӧ������</td>"
									+ "<td class=\"td-right\" align=\"center\">Ӧ����Ϣ</td>"
									+ "<td  align=\"center\">Ӧ����Ϣ�ϼ�</td>"
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
								+ (pi.getInterest()>0?"��":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getInterest()*syndicationLoanInterestInfo.getRate()/100)
								+ "</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ (pi.getCompoundInterest()>0?"��":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getCompoundInterest()*syndicationLoanInterestInfo.getRate()/100)
								+ "</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ (pi.getOverDueInterest()>0?"��":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getOverDueInterest()*syndicationLoanInterestInfo.getRate()/100)
								+ "</font></td>"
								+ "<td class=\"td-top\" align=\"right\"><font style=\"font-size:12px\">"
								+ "��"
								+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()*syndicationLoanInterestInfo.getRate()/100) + DataFormat.formatDouble(pi.getCompoundInterest()*syndicationLoanInterestInfo.getRate()/100) + DataFormat.formatDouble(pi.getOverDueInterest()*syndicationLoanInterestInfo.getRate()/100)))
								+ "</font></td>"
								+ "</tr>");
						System.out.println("��ӡ���Ŵ�����ܵ���Ϣ���Ϊ��"+DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getInterest()*syndicationLoanInterestInfo.getRate()/100) + DataFormat.formatDouble(pi.getCompoundInterest()*syndicationLoanInterestInfo.getRate()/100) + DataFormat.formatDouble(pi.getOverDueInterest()*syndicationLoanInterestInfo.getRate()/100)));
						System.out.println("1���Ϊ��"+DataFormat.formatDouble(pi.getInterest()*syndicationLoanInterestInfo.getRate()/100));
						System.out.println("2���Ϊ��"+DataFormat.formatDouble(pi.getCompoundInterest()*syndicationLoanInterestInfo.getRate()/100));
						System.out.println("3���Ϊ��"+DataFormat.formatDouble(pi.getOverDueInterest()*syndicationLoanInterestInfo.getRate()/100));

						//��ӡ�ſ��ϸ����
					}
					//��ӡ�ſ��ϸ����
					out.println(
							"<tr>"
								+ "<td class=\"td-topright\" align=\"center\"><font style=\"font-size:12px\"><B>�ϼ�</B></font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ "��"
								+ DataFormat.formatDisabledAmount(lpfdinfo.getAmount())
								+ "</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
								+ "<td class=\"td-topright\"><font style=\"font-size:12px\">&nbsp;</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ (pi.getInterest()>0?"��":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getInterest())
								+ "</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ (pi.getCompoundInterest()>0?"��":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getCompoundInterest())
								+ "</font></td>"
								+ "<td class=\"td-topright\" align=\"right\"><font style=\"font-size:12px\">"
								+ (pi.getOverDueInterest()>0?"��":"&nbsp;")
								+ DataFormat.formatDisabledAmount(pi.getOverDueInterest())
								+ "</font></td>"
								+ "<td class=\"td-top\" align=\"right\"><font style=\"font-size:12px\">"
								+ "��"
								+ DataFormat.formatDisabledAmount(DataFormat.formatDouble(DataFormat.formatDouble(pi.getInterest()) + DataFormat.formatDouble(pi.getCompoundInterest()) + pi.getOverDueInterest()))
								+ "</font></td>"
								+ "</tr>");
					//��ӡ�ſ��ϸ����end
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
	 * ���Ŵ����ƾ֤
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
			//��ͬ����
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			//���������
			info.setAgentFeeRate(DataFormat.formatRate(String.valueOf(lpfdinfo.getCommissionRate())));
			//ί�е�λ
			info.setConsignUnit(lpfdinfo.getClientName());
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " �� " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
			info.setLoanType(LOANConstant.LoanType.getName(lpfdinfo.getLoanType()));
			info.setLoanUnit(NameRef.getClientNameByID(pi.getReceiveClientID()));
			info.setNote(pi.getAbstract());
			//�����˻���
			info.setLoanAccountNo(NameRef.getAccountNoByID(pi.getLoanAccountID()));
			//��λ������Ӫ�����˻�
			info.setLoanUnit(NameRef.getAccountNameByID(pi.getLoanAccountID()));
			if (pi.getReceiveAccountID() > 0)
			{
				//�տ����˺�
				info.setAccountNo(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
				//�տ��˿�����������
				info.setOpenBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
			}
			else
				if (pi.getReceiveBankID() > 0)
				{
					//�տ����˺�
					info.setAccountNo(pi.getExtAccountNo());
					//�տ��˿�����������
					info.setOpenBankName(pi.getExtRemitInBank());
				}
			info.setTransNo(pi.getTransNo());
			//���÷��Ŵ�����ϸ���������ƣ��д��������д����
			double dSumRate = 0.0;
			double dSumAmount = 0.0;
			Vector vctDetail = pi.getDetail();
			Vector vctTemp = new Vector();
			Vector vctWithOutHead = new Vector();
			BankLoanDrawInfo bankLoanDrawInfo = null;
			SynLoanGrantDetailInfo synLoanGrantDetailInfo = null;
			if (vctDetail != null)
			{
				System.out.println("===���Ŵ�����ϸ not null===");
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
			//�ϼƳд�����
			info.setSumAssumeLoanRate(DataFormat.formatRate(String.valueOf(dSumRate)));
			//�ϼƳд����
			info.setSunAssumeLoanAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(dSumAmount)));
			ISynLoanPrintTemplate.showSynLoanGrantLoan1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanGrantLoan2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanGrantLoan3(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showSynLoanGrantLoan4(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			//����������ƾ֤
			info.setSynLoanGrantDetail(vctWithOutHead); //���⴦��
			vctTemp = info.getSynLoanGrantDetail();
			synLoanGrantDetailInfo = null;
			Vector vctTemp2 = null;
			if (vctTemp != null)
			{
				for (int i = 0; i < vctTemp.size(); i++)
				{
					vctTemp2 = new Vector();
					synLoanGrantDetailInfo = (SynLoanGrantDetailInfo) vctTemp.elementAt(i);
					//�ϼƳд�����
					info.setSumAssumeLoanRate(synLoanGrantDetailInfo.getLoanRate());
					//�ϼƳд����
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
	 * ���Ŵ���������ȡƾ֤
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
			//�տλ ��ȡֵ��"���´�����������ܲ�������"
			info.setReceiveUnit(NameRef.getOfficeNameByID(1));
			//��ͬ���
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//�ſ��
			info.setLoanNoteNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//������,ȡֵ�����ͬ��ǣͷ������Ӧ�Ŀ����У����ں��洦��
			info.setOpenBank("&nbsp;");
			//�տ��˺ţ�ȡֵ�����ͬ��ǣͷ������Ӧ���˻����(���ں��洦��)
			info.setReceiveAccountNo("&nbsp;");
			//��������
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
			//�������
			info.setAgentRate(DataFormat.formatRate(String.valueOf(lpfdinfo.getCommissionRate())));
			//���ô��������ϸ�����λ���ƣ��д���Ӧ�������
			double dSumAmount = 0.0;
			double dSumAgentFee = 0.0;
			Vector vctDetail = pi.getDetail();
			Vector vctTemp = new Vector();
			BankLoanDrawInfo bankLoanDrawInfo = null;
			SynLoanGrantDetailInfo synLoanGrantDetailInfo = null;
			if (vctDetail != null)
			{
				System.out.println("===���Ŵ�����ϸ not null===");
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
						//������,ȡֵ�����ͬ��ǣͷ������Ӧ�Ŀ����У���ǰ˵����
						//�տ��˺ţ�ȡֵ�����ͬ��ǣͷ������Ӧ���˻���ţ���ǰ˵����
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
			//�����
			info.setAgentFee(DataFormat.formatString(DataFormat.formatDisabledAmount(DataFormat.formatDouble(dSumAgentFee)) + ""));
			//�д����ϼ�,��λ��Ԫ
			info.setSumLoanAmount(DataFormat.formatAmount(dSumAmount, 0));
			//Ӧ������Ѻϼ�
			info.setSumAgentFee(DataFormat.formatDisabledAmount(dSumAgentFee, 0));
			//¼����
			info.setInputUser(NameRef.getUserNameByID(pi.getInputUserID()));
			info.setCheckerUser(NameRef.getUserNameByID(pi.getCheckUserID()));
			ISynLoanPrintTemplate.showReceiveAgentFee1(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			ISynLoanPrintTemplate.showReceiveAgentFee2(info, out);
			out.println("<br clear=all style='page-break-before:always'>");
			//����������ƾ֤
			vctTemp = info.getSynLoanAgentFeeDetail();
			synLoanGrantDetailInfo = null;
			Vector vctTemp2 = null;
			if (vctTemp != null)
			{
				for (int i = 0; i < vctTemp.size(); i++)
				{
					vctTemp2 = new Vector();
					synLoanGrantDetailInfo = (SynLoanGrantDetailInfo) vctTemp.elementAt(i);
					//�ϼƳд����
					info.setSumLoanAmount(synLoanGrantDetailInfo.getLoanAmount());
					//�ϼƳд�����
					info.setSumAgentFee(synLoanGrantDetailInfo.getAgentFee());
					vctTemp2.add(synLoanGrantDetailInfo);
					info.setSynLoanAgentFeeDetail(vctTemp2);
					//�ڼ���
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
	 * ���Ŵ�����ջ�ƾ֤
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
					+ "����������Ϣ��"
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealInterest
					+ "�����ո�����"
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealCompoundInterest
					+ "�����շ�Ϣ��"
					+ PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID())
					+ strRealOverDueInterest);
			info.setBillCode(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getAmount()), pi.getCurrencyID()));
			info.setContractCode(NameRef.getContractNoByID(pi.getContractID()));
			//��ͬ����
			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(pi.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
			info.setContractRate(DataFormat.formatDisabledAmount(lpfdinfo.getInterestRate()));
			//ί�е�λ
			info.setConsignUnit(lpfdinfo.getClientName());
			//���
			if (pi.getCurrentBalance() > 0)
			{
				info.setBalance(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getCurrentBalance()));
			}
			else
			{
				info.setBalance(PrintVoucher.getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + "0.00");
			}
			//�ۼƻ���
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
			info.setLoanInterval(DataFormat.getChineseDateString(lpfdinfo.getStart()) + " �� " + DataFormat.getChineseDateString(lpfdinfo.getEnd()));
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
			System.out.println("�ſ�֪ͨ��ID:" + pi.getLoanNoteID());
			//���ñ���
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
						//�տλ����
						repayDetailInfo.setReceiveAmountUnitName(DataFormat.formatString(bankLoanDrawInfo.getBankName()));
						//�д�����
						repayDetailInfo.setLoanRate(DataFormat.formatString(DataFormat.formatAmount(bankLoanDrawInfo.getRate(), 0) + ""));
						dSumLoanRate = DataFormat.formatDouble(dSumLoanRate + DataFormat.formatDouble(bankLoanDrawInfo.getRate()));
						YTLoanAttendBankInfo ytLoanAttendBankInfo = null;
						YTLoanAttendBankDelegation delegation = new YTLoanAttendBankDelegation();
						ytLoanAttendBankInfo = delegation.findById(bankLoanDrawInfo.getBankID());
						if (ytLoanAttendBankInfo != null)
						{
							//��������
							repayDetailInfo.setOpenBank(DataFormat.formatString(ytLoanAttendBankInfo.getBank()));
							//�տ���
							repayDetailInfo.setBankAccountNo(DataFormat.formatString(ytLoanAttendBankInfo.getBankAccountNo()));
						}
						else
						{
							//��������
							repayDetailInfo.setOpenBank("&nbsp;");
							//�����˺�
							repayDetailInfo.setBankAccountNo("&nbsp;");
						}
						//�տ���
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
			//����������ƾ֤
			info.setSynLoanRepayDetail(vctWithOutHead); //���⴦��
			vctDetail = info.getSynLoanRepayDetail();
			repayDetailInfo = null;
			Vector vctTemp = null;
			if (vctDetail != null)
			{
				for (int i = 0; i < vctDetail.size(); i++)
				{
					vctTemp = new Vector();
					repayDetailInfo = (SynLoanRepayDetailInfo) vctDetail.elementAt(i);
					//�ϼƳд�����
					info.setSumLoanRate(DataFormat.formatRate(repayDetailInfo.getLoanRate()));
					//�ϼƳд����
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
	 * ���Ŵ�����Ϣ�ջ�ƾ֤
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
			//��λ����
			if (pi.getPayClientID() > 0)
			{
				info.setClientName(NameRef.getClientNameByID(pi.getPayClientID()));
			}
			else
			{
				info.setClientName(pi.getBorrowClientName());
			}
			//ί�е�λ����
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//�˺�����
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//���ױ��
			info.setTransNo(pi.getTransNo());
			//�˺�&�˻�����
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
			System.out.println("ʵ��֧����Ϣ��" + pi.getRealInterest());
			System.out.println("�����˻��ţ�" + lPayAccountID);
			System.out.println("�ſ�֪ͨ���ţ�" + pi.getLoanNoteID());
			System.out.println("��Ϣ��ֹ����:" + pi.getNormalInterestStart() + "---" + pi.getNormalInterestEnd());
			if (pi.getRealInterest() > 0)
			{
				/*//����ֶ���Ϣ
				//������Ϣ
				//����ֶ���Ϣ
				SubsectionInterest dao = new SubsectionInterest();
				SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
				PrintSubsectionInfo =
					dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getLatestInterestClearDate(), DataFormat.getPreviousDate(pi.getInterestClearDate()));
				info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //������Ϣ��ʼ����
				info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
				info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //������Ϣ����
				info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //������Ϣ��
				info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //������Ϣ
				info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
				*/
				info.setNormalInterestDateStart(DataFormat.getDateString(pi.getLatestInterestClearDate())); //������Ϣ��ʼ����
				info.setNormalInterestDateEnd(DataFormat.getDateString(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				info.setNormalInterestAmount(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getAmount()))); //������Ϣ����
				info.setNormalInterestRate(DataFormat.formatRate(String.valueOf(DataFormat.formatRate(pi.getCompoundRate())))); //������Ϣ��
				info.setNormalInterest(DataFormat.formatDisabledAmount(DataFormat.formatDouble(pi.getRealInterest()))); //������Ϣ
				info.setNormalInterestDay((PrintVoucher.getIntervalDays(pi.getLatestInterestClearDate(), pi.getInterestClearDate(), 1)) + "");
				//����ֶ���ϢEnd
			}
			//����
			if (pi.getRealCompoundInterest() > 0)
			{
				//������Ϣ��Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//������Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE || pi.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{
					info.setCompoundInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setCompoundInterestDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//������Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((PrintVoucher.getIntervalDays(pi.getCompoundInterestStart(), pi.getInterestStartDate(), 1)) + "");
				}
				info.setCompoundInterestAmount("&nbsp;");
				//��������
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate()+""));
				//������Ϣ	
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
				if (pi.getRealCompoundInterest() == 0.0)
				{
					info.setCompoundInterest("0.00");
				}
			}
			//���ڷ�Ϣ
			if (pi.getRealOverDueInterest() > 0)
			{
				//���ڷ�Ϣ��Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDateStart(DataFormat.formatDate(pi.getOverDueStart()));
				}
				//���ڷ�Ϣ��Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setOverInterestDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setOverInterestDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//���ڷ�Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((PrintVoucher.getIntervalDays(pi.getOverDueStart(), pi.getInterestClearDate(), 1)) + "");
				}
				//���ڷ�Ϣ����
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				//���ڷ�Ϣ����
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate() + ""));
				//���ڷ�Ϣ��Ϣ
				info.setOverInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
				if (pi.getRealOverDueInterest() == 0.0)
				{
					info.setOverInterest("0.00");
				}
			}
			//������
			if (pi.getRealCommission() > 0)
			{
				//��������Ϣ��Ϣ����
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				//��������Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setCommissionFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setCommissionFeeDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//��������Ϣ����
				info.setCommissionFeeDay((PrintVoucher.getIntervalDays(pi.getCommissionStart(), pi.getInterestClearDate(), 1)) + "");
				//��������Ϣ����		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//����������				
				System.out.print("========���������ʣ�" + pi.getCommissionRate());
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
				//��������Ϣ
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
				if (pi.getRealCommission() == 0.0)
				{
					info.setCommissionFee("0.00");
				}
			}
			if (pi.getRealSuretyFee() > 0)
			{
				//������
				//��������Ϣ��Ϣ����
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				//��������Ϣ��Ϣ����
				if (pi.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					info.setAssureFeeDateEnd(DataFormat.formatDate(DataFormat.getPreviousDate(pi.getInterestClearDate())));
				}
				else
				{
					info.setAssureFeeDateEnd(DataFormat.formatDate(pi.getInterestClearDate()));
				}
				//��������Ϣ����				
				info.setAssureFeeDay((PrintVoucher.getIntervalDays(pi.getSuretyFeeStart(), pi.getInterestClearDate(), 1)) + "");
				//��������Ϣ����		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				if (pi.getAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//����������
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
				//��������Ϣ
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
				if (pi.getRealSuretyFee() == 0.0)
				{
					info.setAssureFee("0.00");
				}
			}
			//��Ϣ�ܶ�
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
			//��Ϣ�˻���
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//��Ӧ���˻���
			info.setCurrentAccountNo(NameRef.getAccountNoByID(lPayAccountID));
			//��Ӧ�ĺ�ͬ��
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//��Ӧ��ݺ�
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//��Ӧ�浥��
			info.setDepositBillNo(pi.getFixedDepositNo());
			//ת����
			info.setTransAccountDate(DataFormat.formatDate(pi.getInterestStartDate()));
			//¼����
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("����");
			}
			//������
			info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			if (pi.getCheckUserID() < 0)
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					info.setCheckUserName("����");
				}
				else
				{
					info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp");
				}
			}
			//���ô�����Ϣ��ϸ����Ϣ��λ���ƣ��������У������˺ţ�Ӧ����Ϣ��Ԫ��
			Vector vctDetail = pi.getDetail();
			Vector vctTemp = new Vector();
			Vector vctWithOutHead = new Vector();
			SyndicationLoanInterestInfo interestInfo = null;
			SynLoanRepayDetailInfo synLoanRepayDetailInfo = null;
			double dSumInterest = 0.0;
			double dTotalSumInterest = 0.0;
			if (vctDetail != null)
			{
				System.out.println("===������Ϣ��ϸ not null===");
				for (int i = 0; i < vctDetail.size(); i++)
				{
					synLoanRepayDetailInfo = new SynLoanRepayDetailInfo();
					interestInfo = (SyndicationLoanInterestInfo) vctDetail.elementAt(i);
					//��Ϣ��λ����
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
						//��������
						synLoanRepayDetailInfo.setOpenBank("&nbsp;");
						//�����˺�
						synLoanRepayDetailInfo.setBankAccountNo("&nbsp;");
					}
					//Ӧ����Ϣ��Ԫ��
					dSumInterest =
						DataFormat.formatDouble(
							DataFormat.formatDouble(interestInfo.getInterest())
								+ DataFormat.formatDouble(interestInfo.getCompoundInterest())
								+ DataFormat.formatDouble(interestInfo.getForpeitInterest()));
					//�ϼ���Ϣ
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
			//����������ƾ֤
			info.setSynLoanRepayDetail(vctWithOutHead); //���⴦��
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
		*  ������Ϣ֪ͨ��(��Ϣ��ר��)��ӡ
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
			//��λ����
			info.setClientName(pi.getBorrowClientName());
			//ί�е�λ����(������2004/04/12��Ϊ�˻�����)
			info.setConsignClientName(NameRef.getClientNameByID(pi.getConsignClientID()));
			//�˺�����
			info.setAccountType(SETTConstant.AccountType.getName(pi.getAccountTypeID()));
			//���ױ��
			info.setTransNo(pi.getTransNo());
			//�˺�&�˻�����
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
				//����ֶ���Ϣ
				/*SubsectionInterest dao = new SubsectionInterest();
				SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
				PrintSubsectionInfo = dao.getSubsectionInterest(lPayAccountID, -1, pi.getLoanNoteID(), pi.getNormalInterestStart(), pi.getNormalInterestEnd());
				info.setNormalInterestDateStart(PrintSubsectionInfo.getPrintStartDate()); //������Ϣ��ʼ����
				info.setNormalInterestDateEnd(PrintSubsectionInfo.getPrintEndDate());
				info.setNormalInterestAmount(PrintSubsectionInfo.getPrintBalance()); //������Ϣ����
				info.setNormalInterestRate(PrintSubsectionInfo.getPrintInterestRate()); //������Ϣ��
				info.setNormalInterest(PrintSubsectionInfo.getPrintInterest()); //������Ϣ
				info.setNormalInterestDay(PrintSubsectionInfo.getPrintDays());
				*/
				//����ֶ���ϢEnd
				//������Ϣ��ʼ����
				info.setNormalInterestDateStart(DataFormat.formatDate(pi.getNormalInterestStart()));
				//������Ϣ��������
				info.setNormalInterestDateEnd(DataFormat.formatDate(pi.getNormalInterestEnd()));
				//������Ϣ����
				info.setNormalInterestAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
				//������Ϣ��
				info.setNormalInterestRate(DataFormat.formatRate(pi.getRate() + ""));
				//������Ϣ
				info.setNormalInterest(DataFormat.formatDisabledAmount(pi.getRealInterest()));
				//����
				info.setNormalInterestDay((PrintVoucher.getIntervalDays(pi.getNormalInterestStart(), pi.getNormalInterestEnd(), 1) + 1) + "");
			}
			//����
			//��ʼ����--�ſ����ں��һ�������������ڣ����ʵ���������Ϣ������
			if (pi.getRealCompoundInterest() > 0)
			{
				//������Ϣ��Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDateStart(DataFormat.formatDate(pi.getCompoundInterestStart()));
				}
				//������Ϣ��Ϣ����
				info.setCompoundInterestDateEnd(DataFormat.formatDate(pi.getCompoundInterestEnd()));
				//������Ϣ����
				if (pi.getCompoundInterestStart() != null)
				{
					info.setCompoundInterestDay((PrintVoucher.getIntervalDays(pi.getCompoundInterestStart(), pi.getCompoundInterestEnd(), 1) + 1) + "");
				}
				//����������ʾΪ��
				info.setCompoundInterestAmount("&nbsp;");
				//��������
				info.setCompoundInterestRate(DataFormat.formatRate(pi.getCompoundRate() + ""));
				//������Ϣ	
				info.setCompoundInterest(DataFormat.formatDisabledAmount(pi.getRealCompoundInterest()));
			}
			//���ڷ�Ϣ
			//��ʼ���ڷ�Ϣ֪ͨ���ķ�Ϣ����/��һ�ν�Ϣ�����ڣ����ݲ�ͬ�����������ȡ��Ϣ֪ͨ���ķ�Ϣ����
			if (pi.getRealOverDueInterest() > 0)
			{
				//���ڷ�Ϣ��Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDateStart(DataFormat.formatDate(pi.getOverDueStart()));
				}
				//���ڷ�Ϣ��Ϣ��Ϣ����
				info.setOverInterestDateEnd(DataFormat.formatDate(pi.getOverDueEnd()));
				//���ڷ�Ϣ��Ϣ����
				if (pi.getOverDueStart() != null)
				{
					info.setOverInterestDay((PrintVoucher.getIntervalDays(pi.getOverDueStart(), pi.getOverDueEnd(), 1) + 1) + "");
				}
				//���ڷ�Ϣ����
				info.setOverInterestAmount(DataFormat.formatDisabledAmount(pi.getOverDueAmount()));
				if (pi.getOverDueAmount() == 0.00)
				{
					info.setOverInterestAmount("0.00");
				}
				//���ڷ�Ϣ����
				info.setOverInterestRate(DataFormat.formatRate(pi.getOverDueRate() + ""));
				//���ڷ�Ϣ��Ϣ
				info.setOverInterest(DataFormat.formatDisabledAmount(pi.getRealOverDueInterest()));
			}
			//������
			//��ʼ����ȡ�ſ�֪ͨ������ʼ����/��һ�ν��������ѵ�����
			if (pi.getRealCommission() > 0)
			{
				//��������Ϣ��Ϣ����
				info.setCommissionFeeDateStart(DataFormat.formatDate(pi.getCommissionStart()));
				//��������Ϣ��Ϣ����
				info.setCommissionFeeDateEnd(DataFormat.formatDate(pi.getCommissionFeeEnd()));
				//��������Ϣ����
				info.setCommissionFeeDay((PrintVoucher.getIntervalDays(pi.getCommissionStart(), pi.getCommissionFeeEnd(), 1) + 1) + "");
				//��������Ϣ����		
				info.setCommissionFeeAmount(DataFormat.formatDisabledAmount(pi.getCommissionAmount()));
				if (pi.getCommissionAmount() == 0.00)
				{
					info.setCommissionFeeAmount("0.00");
				}
				//����������				
				info.setCommissionFeeRate(DataFormat.formatRate(pi.getCommissionRate() + ""));
				//��������Ϣ
				info.setCommissionFee(DataFormat.formatDisabledAmount(pi.getRealCommission()));
			}
			if (pi.getRealSuretyFee() > 0)
			{
				//������
				//��ʼ����ȡ�ſ�֪ͨ������ʼ����/��һ�ν��㵫���ѵ����ڣ����ʴӷſ�֪ͨ��ȡ
				//��������Ϣ��Ϣ����
				info.setAssureFeeDateStart(DataFormat.formatDate(pi.getSuretyFeeStart()));
				//��������Ϣ��Ϣ����
				info.setAssureFeeDateEnd(DataFormat.formatDate(pi.getSuretyFeeEnd()));
				//��������Ϣ����				
				info.setAssureFeeDay((PrintVoucher.getIntervalDays(pi.getSuretyFeeStart(), pi.getSuretyFeeEnd(), 1) + 1) + "");
				//��������Ϣ����		
				info.setAssureFeeAmount(DataFormat.formatDisabledAmount(pi.getSuretyFeeAmount()));
				if (pi.getSuretyFeeAmount() == 0.0)
				{
					info.setAssureFee("0.00");
				}
				//����������
				info.setAssureFeeRate(DataFormat.formatRate(pi.getSuretyFeeRate() + ""));
				//��������Ϣ
				info.setAssureFee(DataFormat.formatDisabledAmount(pi.getRealSuretyFee()));
			}
			//��Ϣ�ܶ�
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
			//��Ϣ�˻���
			info.setInterestAccountNo(NameRef.getAccountNoByID(pi.getPayInterestAccountID()));
			//��Ӧ�Ļ����˻���
			info.setCurrentAccountNo(NameRef.getAccountNoByID(pi.getPayAccountID()));
			//��Ӧ�ĺ�ͬ��
			info.setContractNo(NameRef.getContractNoByID(pi.getContractID()));
			//��Ӧ��ݺ�
			info.setLoanBillNo(NameRef.getPayFormNoByID(pi.getLoanNoteID()));
			//��Ӧ�浥��
			info.setDepositBillNo(pi.getFixedDepositNo());
			//ת����
			info.setTransAccountDate(DataFormat.formatDate(pi.getExecuteDate()));
			//¼����
			info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
			if (pi.getInputUserID() < 0)
			{
				info.setInputUserName("����");
			}
			//������			
			if(pi.getCheckUserID() > 0)
			    info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
			else
			{
				if (pi.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					info.setCheckUserName("����");
				}
				else
				{
					info.setCheckUserName("&nbsp;&nbsp;&nbsp;&nbsp");
				}
			}
			
			System.out.println("��������:" + pi.getTransTypeID());
			if (pi.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				System.out.println("�������ͣ�����");
				//				���ô�����Ϣ��ϸ����Ϣ��λ���ƣ��������У������˺ţ�Ӧ����Ϣ��Ԫ��
				Vector vctDetail = pi.getDetail();
				Vector vctTemp = new Vector();
				Vector vctWithOutHead = new Vector();
				SyndicationLoanInterestInfo interestInfo = null;
				SynLoanRepayDetailInfo synLoanRepayDetailInfo = null;
				double dSumInterest = 0.0;
				double dTotalSumInterest = 0.0;
				if (vctDetail != null)
				{
					System.out.println("===������Ϣ��ϸ not null===");
					for (int i = 0; i < vctDetail.size(); i++)
					{
						synLoanRepayDetailInfo = new SynLoanRepayDetailInfo();
						interestInfo = (SyndicationLoanInterestInfo) vctDetail.elementAt(i);
						//��Ϣ��λ����
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
							//��������
							synLoanRepayDetailInfo.setOpenBank("&nbsp;");
							//�����˺�
							synLoanRepayDetailInfo.setBankAccountNo("&nbsp;");
						}
						//Ӧ����Ϣ��Ԫ��
						dSumInterest =
							DataFormat.formatDouble(
								DataFormat.formatDouble(interestInfo.getInterest())
									+ DataFormat.formatDouble(interestInfo.getCompoundInterest())
									+ DataFormat.formatDouble(interestInfo.getForpeitInterest()));
						//�ϼ���Ϣ
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
				//����������ƾ֤
				info.setSynLoanRepayDetail(vctWithOutHead); //���⴦��
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
				strReturn = "һ";
				break;
			case 2 :
				strReturn = "��";
				break;
			case 3 :
				strReturn = "��";
				break;
			case 4 :
				strReturn = "��";
				break;
			case 5 :
				strReturn = "��";
				break;
			case 6 :
				strReturn = "��";
				break;
			case 7 :
				strReturn = "��";
				break;
			case 8 :
				strReturn = "��";
				break;
			case 9 :
				strReturn = "��";
				break;
			case 10 :
				strReturn = "ʮ";
				break;
			case 11 :
				strReturn = "ʮһ";
				break;
			case 12 :
				strReturn = "ʮ��";
				break;
			case 13 :
				strReturn = "ʮ��";
				break;
			case 14 :
				strReturn = "ʮ��";
				break;
			default :
				strReturn = "&nbsp;&nbsp;";
		}
		return strReturn;
	}
}
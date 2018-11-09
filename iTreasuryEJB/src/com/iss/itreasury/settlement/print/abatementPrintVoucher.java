/*
 * Created on 2004-9-23
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.print;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation;
import com.iss.itreasury.settlement.print.templateinfo.ShowGrantDiscountInfo;
import com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementDetailInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
/**
 * @author ygniu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class abatementPrintVoucher
{
    public static boolean setDetailInfoToPrintInfo(TransAbatementDetailInfo DetailInfo,PrintInfo printInfo,int direct) throws Exception
	{
        //���ø�����ӡ����
	    if(direct==1)
	    {
	        if(DetailInfo.getAccountID()>0)
				printInfo.setPayAccountID(DetailInfo.getAccountID());
	    }  
	    //�����շ���ӡ����
	    else if(direct==2)
	    {
	        if(DetailInfo.getAccountID()>0)
				printInfo.setReceiveAccountID(DetailInfo.getAccountID());
	    }
	    else  return false;
	    //���ñ�����
	    if(DetailInfo.getAmount()!=0.0)
	        printInfo.setAmount(DetailInfo.getAmount());
	    //������������
	    if(DetailInfo.getAccountID()!=-1)
	        printInfo.setExtAccountNo(NameRef.getAccountNoByID(DetailInfo.getAccountID()));
	    if(DetailInfo.getClientID()!=-1)
	        printInfo.setExtClientName(NameRef.findClientNameByID(DetailInfo.getClientID()));	   
	    return true;
	}
    
    public static String getCurrencySymbolByCurrencyID(long lCurrencyID)
    {
    	String strReturn = "";
    	if (lCurrencyID == 1)
    	{
    		strReturn = "��";
    	}
    	if (lCurrencyID == 2)
    	{
    		strReturn = "��";
    	}
    	return strReturn;
    }
    /**
     * ���ַ���ƾ֤��ӡ 
     * @throws Exception
     */
public static void printDiscountVoucher(PrintInfo pi, JspWriter out) throws Exception
{
try
{
	ShowGrantDiscountInfo info = new ShowGrantDiscountInfo();
	String strExecuteDate = DataFormat.formatDate(pi.getExecuteDate());
	if (strExecuteDate.length() < 10)
	{
		strExecuteDate = "0000000000";
	}
	info.setYear(strExecuteDate.substring(0, 4));
	info.setMonth(strExecuteDate.substring(5, 7));
	info.setDay(strExecuteDate.substring(8, 10));
	info.setTransactionNo(pi.getTransNo());
	//ƾ֤��findbyid����
	TransDiscountDelegation transDiscountDelegation=new TransDiscountDelegation();
	DiscountCredenceInfo discountCredenceInfo =transDiscountDelegation.findDiscountCredenceByID(pi.getDiscountNoteID());
	if(discountCredenceInfo==null) return;
	if(pi.getAbstract()!=null&&pi.getAbstract().length()>0)
		info.setAbstract("&nbsp;"+pi.getAbstract() + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ֺ�ͬ�ţ�" + NameRef.getContractNoByID(discountCredenceInfo.getDiscountContractID()) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ƾ֤��ţ�" + NameRef.getDiscountCredenceNoByID(discountCredenceInfo.getID()));
	else 
		info.setAbstract("<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ֺ�ͬ�ţ�" + NameRef.getContractNoByID(discountCredenceInfo.getDiscountContractID()) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ƾ֤��ţ�" + NameRef.getDiscountCredenceNoByID(discountCredenceInfo.getID()));
	//info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
	//��Ʊ����Ϣ
	if (pi.getReceiveAccountID() > 0)
	{
		//��Ʊ������
		info.setBillKeeperName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
		//��Ʊ���˺�
		info.setBillKeeperAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
		//��Ʊ�˿�����������
		info.setBillKeeperBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
	}
	else
		{
			//��Ʊ������
			//info.setBillKeeperName(NameRef.getBankNameByID(pi.getReceiveBankID()));
			info.setBillKeeperName(pi.getExtClientName());
			//��Ʊ���˺�
			info.setBillKeeperAccount(pi.getExtAccountNo());
			//��Ʊ�˿�����������
			info.setBillKeeperBankName(pi.getExtRemitInBank());
		}
	//�ر�˵��:��Ʊ��Ʊ����Ϣ��ʱ����Ҫ
	//��Ʊ��Ʊ���˺�
	info.setBillOutAccount("&nbsp;");
	info.setBillOutBankName("&nbsp;");
	//��Ʊ��Ʊ������
	info.setBillOutName("&nbsp;");
	info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
	//info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getDiscountAmount()), pi.getCurrencyID()));
	info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
	//��Ʊ���
	info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(discountCredenceInfo.getBillAmount()));
	//��Ʊ��д���
	info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(discountCredenceInfo.getBillAmount()), pi.getCurrencyID()));
	//������
	info.setDateBillEnd(DataFormat.getChineseDateString(discountCredenceInfo.getAtTerm()));
	//��Ʊ��
	info.setDateBillOut(DataFormat.getChineseDateString(discountCredenceInfo.getPublicDate()));
	//������Ϣ
	info.setDiscountInterest(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(discountCredenceInfo.getBillInterest()));
	//���ֺ���
	info.setDiscountNo(NameRef.getDiscountCredenceNoByID(discountCredenceInfo.getID()));
	//������
	info.setDiscountRate(DataFormat.formatDisabledAmount(discountCredenceInfo.getDiscountRate()));
	//��������
	info.setDiscountType(LOANConstant.TransDiscountType.getName(discountCredenceInfo.getDraftTypeID()));
	info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
	//ʵ�����ֽ��
	info.setRealPayDiscountAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(discountCredenceInfo.getBillCheckAmount()));
	IPrintTemplate.showDiscountGrantLoan1(info, out);
	out.println("<br clear=all style='page-break-before:always'>");
	IPrintTemplate.showDiscountGrantLoan2(info, out);
	out.println("<br clear=all style='page-break-before:always'>");
	IPrintTemplate.showDiscountGrantLoan3(info, out);
	out.println("<br clear=all style='page-break-before:always'>");
	IPrintTemplate.showDiscountGrantLoan4(info, out);
	out.println("<br clear=all style='page-break-before:always'>");
	IPrintTemplate.showDiscountGrantLoan5(info, out);
}
catch (Exception exp)
{
	throw exp;
}
finally
{
}
}
}

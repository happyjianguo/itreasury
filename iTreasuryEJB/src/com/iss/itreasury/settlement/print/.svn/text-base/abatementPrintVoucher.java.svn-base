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
        //设置付方打印资料
	    if(direct==1)
	    {
	        if(DetailInfo.getAccountID()>0)
				printInfo.setPayAccountID(DetailInfo.getAccountID());
	    }  
	    //设置收方打印资料
	    else if(direct==2)
	    {
	        if(DetailInfo.getAccountID()>0)
				printInfo.setReceiveAccountID(DetailInfo.getAccountID());
	    }
	    else  return false;
	    //设置本金金额
	    if(DetailInfo.getAmount()!=0.0)
	        printInfo.setAmount(DetailInfo.getAmount());
	    //设置银行资料
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
    		strReturn = "￥";
    	}
    	if (lCurrencyID == 2)
    	{
    		strReturn = "＄";
    	}
    	return strReturn;
    }
    /**
     * 贴现发放凭证打印 
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
	//凭证的findbyid方法
	TransDiscountDelegation transDiscountDelegation=new TransDiscountDelegation();
	DiscountCredenceInfo discountCredenceInfo =transDiscountDelegation.findDiscountCredenceByID(pi.getDiscountNoteID());
	if(discountCredenceInfo==null) return;
	if(pi.getAbstract()!=null&&pi.getAbstract().length()>0)
		info.setAbstract("&nbsp;"+pi.getAbstract() + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贴现合同号：" + NameRef.getContractNoByID(discountCredenceInfo.getDiscountContractID()) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贴现凭证编号：" + NameRef.getDiscountCredenceNoByID(discountCredenceInfo.getID()));
	else 
		info.setAbstract("<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贴现合同号：" + NameRef.getContractNoByID(discountCredenceInfo.getDiscountContractID()) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贴现凭证编号：" + NameRef.getDiscountCredenceNoByID(discountCredenceInfo.getID()));
	//info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
	//持票人信息
	if (pi.getReceiveAccountID() > 0)
	{
		//持票人名称
		info.setBillKeeperName(NameRef.getAccountNameByID(pi.getReceiveAccountID()));
		//持票人账号
		info.setBillKeeperAccount(NameRef.getAccountNoByID(pi.getReceiveAccountID()));
		//持票人开户银行名称
		info.setBillKeeperBankName(NameRef.getOfficeNameByID(pi.getOfficeID()));
	}
	else
		{
			//持票人名称
			//info.setBillKeeperName(NameRef.getBankNameByID(pi.getReceiveBankID()));
			info.setBillKeeperName(pi.getExtClientName());
			//持票人账号
			info.setBillKeeperAccount(pi.getExtAccountNo());
			//持票人开户银行名称
			info.setBillKeeperBankName(pi.getExtRemitInBank());
		}
	//特别说明:汇票出票人信息暂时不需要
	//汇票出票人账号
	info.setBillOutAccount("&nbsp;");
	info.setBillOutBankName("&nbsp;");
	//汇票出票人名称
	info.setBillOutName("&nbsp;");
	info.setCheckUserName(NameRef.getUserNameByID(pi.getCheckUserID()));
	//info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(pi.getDiscountAmount()), pi.getCurrencyID()));
	info.setCurrencyName(Constant.CurrencyType.getName(pi.getCurrencyID()));
	//汇票金额
	info.setAmount(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(discountCredenceInfo.getBillAmount()));
	//汇票大写金额
	info.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(discountCredenceInfo.getBillAmount()), pi.getCurrencyID()));
	//到期日
	info.setDateBillEnd(DataFormat.getChineseDateString(discountCredenceInfo.getAtTerm()));
	//出票日
	info.setDateBillOut(DataFormat.getChineseDateString(discountCredenceInfo.getPublicDate()));
	//贴现利息
	info.setDiscountInterest(getCurrencySymbolByCurrencyID(pi.getCurrencyID()) + DataFormat.formatDisabledAmount(discountCredenceInfo.getBillInterest()));
	//贴现号码
	info.setDiscountNo(NameRef.getDiscountCredenceNoByID(discountCredenceInfo.getID()));
	//贴现率
	info.setDiscountRate(DataFormat.formatDisabledAmount(discountCredenceInfo.getDiscountRate()));
	//贴现种类
	info.setDiscountType(LOANConstant.TransDiscountType.getName(discountCredenceInfo.getDraftTypeID()));
	info.setInputUserName(NameRef.getUserNameByID(pi.getInputUserID()));
	//实付贴现金额
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

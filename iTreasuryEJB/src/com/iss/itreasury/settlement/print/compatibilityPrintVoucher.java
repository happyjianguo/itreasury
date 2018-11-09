/*
 * Created on 2004-9-2
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.print;

import java.sql.SQLException;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityDetailInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class compatibilityPrintVoucher {
    
    /**
     * �����ո�������ҵ��Ĵ�ӡ��Ϣ
     * @param DetailInfo
     * @param printInfo
     * @param direct ����Ϊ1���շ�Ϊ2
     * @throws Exception
     * @return ���óɹ�����true������ʧ�ܷ���false
     * @author ygniu
     */
    public static boolean setDetailInfoToPrintInfo(TransCompatibilityDetailInfo DetailInfo,PrintInfo printInfo,int direct) throws Exception
	{
        //���ø�����ӡ����
	    if(direct==1)
	    {
	        if(DetailInfo.getAccountID()>0)
				printInfo.setPayAccountID(DetailInfo.getAccountID());
			if(DetailInfo.getBankID()>0)
			{
				printInfo.setPayBankID(DetailInfo.getBankID());
				printInfo.setPayExtClientName(NameRef.getBankNameByID(DetailInfo.getBankID()));	
				printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(DetailInfo.getBankID()));
				printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(DetailInfo.getBankID()));
				printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(DetailInfo.getBankID()));
				printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(DetailInfo.getBankID()));
			}
			if(DetailInfo.getGLID()>0)
				printInfo.setPayGL(DetailInfo.getGLID());	
			
	    }  
	    //�����շ���ӡ����
	    else if(direct==2)
	    {
	        if(DetailInfo.getAccountID()>0)
				printInfo.setReceiveAccountID(DetailInfo.getAccountID());
			if(DetailInfo.getBankID()>0)
			{
				printInfo.setReceiveBankID(DetailInfo.getBankID());
				printInfo.setReceiveExtClientName(NameRef.getBankNameByID(DetailInfo.getBankID()));	
				printInfo.setReceiveExtAccountNo(IPrintTemplate.getBankAccountCodeByID(DetailInfo.getBankID()));
				printInfo.setReceiveExtRemitInBank(NameRef.getBankNameByID(DetailInfo.getBankID()));
				printInfo.setReceiveExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(DetailInfo.getBankID()));
				printInfo.setReceiveExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(DetailInfo.getBankID()));
			}
			if(DetailInfo.getGLID()>0)
				printInfo.setReceiveGL(DetailInfo.getGLID());	
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
	    if(DetailInfo.getRemitProvince()!=null&&DetailInfo.getRemitProvince().length()>0)
	        printInfo.setExtRemitInProvince(DetailInfo.getRemitProvince());
	    if(DetailInfo.getRemitCity()!=null&&DetailInfo.getRemitCity().length()>0)
	        printInfo.setExtRemitInCity(DetailInfo.getRemitCity());
	    if(DetailInfo.getRemitBank()!=null&&DetailInfo.getRemitBank().length()>0)
	        printInfo.setExtRemitInBank(DetailInfo.getRemitBank());
	   //������ϸƾ֤
	    if(DetailInfo.getDueBillID()>0)
			printInfo.setDiscountNoteID(DetailInfo.getDueBillID());
	    
	    return true;
	}
    /**
     * ���ݽ�����ϸ�ж�ƾ֤�ո���
     * @param DetailInfo
     * @return true������,false�����շ�
     * @throws SQLException
     */
    public static boolean isPaySide(TransCompatibilityDetailInfo DetailInfo) throws SQLException
    {
        Sett_AccountDAO AccountDao=new Sett_AccountDAO();
        long AccountTypeID=1;
        if(DetailInfo.getAccountID()>0)
            AccountTypeID=AccountDao.findByID(DetailInfo.getAccountID()).getAccountTypeID();
        Sett_AccountTypeDAO AccountTypeDao=new Sett_AccountTypeDAO();
        AccountTypeInfo accountType=AccountTypeDao.findByID(AccountTypeID);
        if(DetailInfo.getTransDirectionID()==SETTConstant.DebitOrCredit.DEBIT)
		{
			//���ƾ֤����
			if(accountType.getBalanceDirection()==SETTConstant.DebitOrCredit.CREDIT)
			{
				return true;
			}
			//����ƾ֤����
			else
			{
				return false;
			}
		}
		//���׷����
		else
		{
			//���ƾ֤����
			if(accountType.getBalanceDirection()==SETTConstant.DebitOrCredit.DEBIT)
			{
				return false;
			}
			//����ƾ֤����
			else
			{ 
				return true;
			}
		}
    }
    /**
     * ���ں��Զ��������ֵ����ִ�ӡ��ȫ��ͬ����������Զ��������ֵĴ�ӡ����
     * @param pi
     * @param out
     * @throws Exception
     */
    public static void printDiscountVoucher(PrintInfo pi, JspWriter out) throws Exception
	{
    	abatementPrintVoucher.printDiscountVoucher(pi,out);
	}
    
	public static void main(java.lang.String[] args) throws Exception
	{
		try
		{
		    compatibilityPrintVoucher dao = new compatibilityPrintVoucher();
		    TransCompatibilityDetailInfo DetailInfo = new TransCompatibilityDetailInfo();
		    DetailInfo.setAccountID(1);
		    //System.out.println(dao.isPaySide(DetailInfo));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
        finally
        {
        }
	}

}

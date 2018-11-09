/*
 * Created on 2004-9-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.bizlogic.SubAccountTypeSettingBiz;
import com.iss.itreasury.settlement.setting.dao.Sett_AccountTypeSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.AccountTypeSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeCurrentSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeLoanSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.TransferSettingInfo;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AccountTypeSettingDelegation {
	
	SubAccountTypeSettingBiz biz = new SubAccountTypeSettingBiz();
	Sett_AccountTypeSettingDAO accountTypeDAO = new Sett_AccountTypeSettingDAO();
	
	public Collection findAllAccountTypeByGroupID(long lAccountGroupID) throws RemoteException, IRollbackException,SettlementException
	{
		Collection coll = null;
		coll = biz.findAllAccountTypeByGroupID(lAccountGroupID);
		System.out.println("findAllAccountTypeByGroupID()="+coll);
		return coll;
	}
	//add by qhzhou 2007.6.18
	public Collection findAllAccountTypeByGroupID(long lAccountGroupID,long lOfficeID,long lCurrencyID) throws RemoteException, IRollbackException,SettlementException
	{
		Collection coll = null;
		coll = biz.findAllAccountTypeByGroupID(lAccountGroupID,lOfficeID,lCurrencyID);
		System.out.println("findAllAccountTypeByGroupID()="+coll);
		return coll;
	}

	
	/**
	 * 根据账户类型ID，取得该账户类型的分类条件
	 * @param lAccountTypeID
	 * @return
	 */
	public String getAccountLabelByAccountTypeID(AccountTypeSettingInfo info)
	{
		String strReturn = "";
		if(info.getIsLoanType() > 0)
		{
			strReturn += "贷款种类,";
		}
		if(info.getIsConsign() > 0)
		{
			strReturn += "委托方,";
		}
		if(info.getIsLoanMonth() > 0)
		{
			strReturn += "贷款期限(月数),";
		}
		if(info.getIsLoanYear() > 0)
		{
			strReturn += "年期,";
		}
		if(info.getIsDraftType() > 0)
		{
			strReturn += "汇票种类,";
		}
		if(info.getIsClient() > 0)
		{
			strReturn += "客户编号,";
		}
		if(info.getIsTransDiscountType() > 0)
		{
			strReturn += "转贴现种类,";
		}
		if(info.getIsAssure() >0)
		{
			strReturn += "担保类型,";
		}
		if(info.getIsContract() >0)
		{
			strReturn += " 合同,";
		}
		if(info.getIsLoanNote() >0)
		{
			strReturn += " 放款通知单,";
		}
		
		if(strReturn != null && strReturn.length() > 0)
		{
			strReturn = strReturn.substring(0,strReturn.length()-1);
		}
		return strReturn;
	}
	
	/**
	 * 根据账户类型ID，取得分类条件
	 * @param lAccountTypeID
	 * @return
	 */
	public String getFixAccountLabelByID(AccountTypeSettingInfo info)
	{
		String strReturn = "存款期限,";
		
		if(info.getIsClient() > 0)
		{
			strReturn += "客户编号,";
		}
		if(info.getIsAccount() > 0)
		{
			strReturn += "账户编号,";
		}
		if(info.getIsDeposit()> 0)
		{
			strReturn += "存单号,";
		}
		if(strReturn != null && strReturn.length() > 0)
		{
			strReturn = strReturn.substring(0,strReturn.length()-1);
		}
		return strReturn;
	}
	/**
	 * 根据账户类型ID，取得分类条件
	 * @param lAccountTypeID
	 * @return
	 */
	public String getAccountLabelByID(AccountTypeSettingInfo info)
	{
		String strReturn = "";
		
		if(info.getIsClient() > 0)
		{
			strReturn += "客户编号,";
		}
		if(info.getIsAccount() > 0)
		{
			strReturn += "账户编号,";
		}
		if(info.getIsDeposit() > 0)
		{
			strReturn += "存单号";
		}
		
		if(strReturn != null && strReturn.length() > 0)
		{
			strReturn = strReturn.substring(0,strReturn.length()-1);
		}
		return strReturn;
	}
	/**
	 * 根据账户类型表sett_accountType的ID和币种查询科目。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public String getSubjectCodeByAccountTypeID(long lCurrencyID,long lOfficeID,long lRecordID) throws RemoteException, IRollbackException,SettlementException
	{
		return accountTypeDAO.getSubjectCodeByAccountTypeID(lCurrencyID,lOfficeID,lRecordID);
	}
	
	/*
	 * 取得贷款账户设置 的信息 
	 */
	public SubAccountTypeLoanSettingInfo findSubTypeLoanById(long lId) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.findSubTypeLoanById(lId);
	}
	
	/*
		 * 取得活期账户设置 的信息 
		 */
		public SubAccountTypeCurrentSettingInfo findSubTypeCurrentById(long lId) throws RemoteException, IRollbackException,SettlementException
		{
			return biz.findSubTypeCurrentById(lId);
		}
	/*
	 * 取得资产转让账户设置设置信息
	 */
	public TransferSettingInfo findTransferById(long lId) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.findTransferById(lId);
	}
	
	/*
	 * 取得该账户类型的 设置信息
	 */
	public AccountTypeSettingInfo findAccountTypeById(long lId) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.findAccountTypeById(lId);
	}
	
	/*
	 * 修改sett_AccountType数据库表，账户类型编码设置
	 */
	public long updateAccountTypeSetting(AccountTypeSettingInfo info,long lCurrencyID,String strSubject)throws RemoteException, IRollbackException,SettlementException
	{
		return biz.updateAccountTypeSetting(info,lCurrencyID,strSubject);
	}
	
	/*
	 * 根据AccountTypeID查询，所有设置的贷款编码明细列表
	 */
	public Collection findLoanAccountByAccountTypeID (long lAccountTypeID,long lOfficeID,long lCurrencyID) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.findLoanAccountByAccountTypeID(lAccountTypeID,lOfficeID,lCurrencyID);
	}
	
	/*
		 * 根据AccountTypeID查询，所有设置的贷款编码明细列表
		 */
	public Collection findCurrentAccountByAccountTypeID (long lAccountTypeID,long lOfficeID,long lCurrencyID) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.findCurrentAccountByAccountTypeID(lAccountTypeID,lOfficeID,lCurrencyID);
	}
	
	/*
	 * 根据ID修改Sett_SubAccountType_Loan表，账户类型编码设置
	 */
	public long updateSubAccountTypeLoanSetting(SubAccountTypeLoanSettingInfo subLoanInfo) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.updateSubLoanInfo(subLoanInfo);
	}
	
	
	/*
	 * 根据ID删除Sett_SubAccountType_Loan表记录，账户类型编码设置
	 */
	public long deleteSubAccountTypeLoanSetting(long lID) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.deleteSubLoanInfo(lID);
	}
	/*
	 * 新增 Sett_SubAccountType_Loan表记录，账户类型编码设置
	 */
	public long addSubAccountTypeLoanSetting(SubAccountTypeLoanSettingInfo subLoanInfo) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.addSubLoanInfo(subLoanInfo);
	}
	
	/*
	 *  新 增 Sett_SubAccountType_currnt表记录，账户类型编码设置
		 */
	public long addSubAccountTypeCurrentSetting(SubAccountTypeCurrentSettingInfo subCurrentInfo) throws RemoteException, IRollbackException,SettlementException
	{
	return biz.addSubCurrentInfo(subCurrentInfo);
	}
	/*
		* 根据ID删除Sett_SubAccountType_Current表记录，账户类型编码设置
		 */
	public long deleteSubAccountTypeCurrentSetting(long lID) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.deleteSubCurrentInfo(lID);
	}
	/*
	 * 根据ID修改Sett_SubAccountType_currnt表，账户类型编码设置
	*/
	public long updateSubAccountTypeCurrentSetting(SubAccountTypeCurrentSettingInfo subCurrentInfo) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.updateSubCurrentInfo(subCurrentInfo);
	}
	/*
	 *  新 增 Sett_SubAccountType_Transfer表记录，账户类型编码设置
	 */
	public long addSubTransferSetting(TransferSettingInfo subTransferInfo) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.addSubTransferInfo(subTransferInfo);
	}
	/*
	 * 根据ID删除Sett_SubAccountType_Transfer表记录，账户类型编码设置
	 */
	public long deleteSubTransferSetting (long lID) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.deleteSubTransferInfo(lID);
	}
	/*
	 * 根据ID修改Sett_SubAccountType_Transfer表，账户类型编码设置
	*/
	public long updateSubTransferSetting (TransferSettingInfo subTransferInfo) throws RemoteException, IRollbackException,SettlementException
	{
		return biz.updateSubTransferInfo(subTransferInfo);
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String args[]) throws Exception
	{
		AccountTypeSettingDelegation dao = new AccountTypeSettingDelegation();
		Collection resultColl = null; 
		resultColl = dao.findAllAccountTypeByGroupID(3);
		System.out.println("aaa="+resultColl);
	}
	

}

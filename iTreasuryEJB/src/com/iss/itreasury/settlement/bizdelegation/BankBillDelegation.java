/*
 * Created on 2003-9-16
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import java.util.Collection;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.bankbill.bizlogic.*;
import com.iss.itreasury.settlement.bankbill.dataentity.*;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BankBillDelegation
{
	private BankBill bankBillFacade;

	public BankBillDelegation() throws RemoteException
	{
		try
		{
			BankBillHome home = (BankBillHome) EJBHomeFactory.getFactory().lookUpHome(BankBillHome.class);
			bankBillFacade = (BankBill) home.create();
		}
		catch (Exception re)
		{
			re.printStackTrace();
			throw new RemoteException("BankBillHome cannot find");
		}
	}

	public void registerBankBill(Collection colBankBillInfo) throws RemoteException, IRollbackException
	{
		bankBillFacade.registerBankBill(colBankBillInfo);
	}

	public void requireBankBill(Collection requireBankBill) throws RemoteException, IRollbackException
	{
		bankBillFacade.requireBankBill(requireBankBill);
	}

	public void deleteBankBill(long lArrayID, long lDeleteUserID, Timestamp tsDeleteDate) throws RemoteException, IRollbackException
	{
		bankBillFacade.deleteBankBill(lArrayID, lDeleteUserID, tsDeleteDate);
	}

	public void reportLoss(long lArrayID, long lReportLossUserID, Timestamp tsReportLossDate) throws RemoteException, IRollbackException
	{
		bankBillFacade.reportLoss(lArrayID, lReportLossUserID, tsReportLossDate);
	}

	public void cancelReportLoss(long lArrayID, long lCancelReportLossUserID, Timestamp tsCancelReportLossDate) throws RemoteException, IRollbackException
	{
		bankBillFacade.cancelReportLoss(lArrayID, lCancelReportLossUserID, tsCancelReportLossDate);
	}

	public void cancelRequire(long lArrayID, long lCancelRequireUserID, Timestamp tsCancelRequireDate) throws RemoteException, IRollbackException
	{
		bankBillFacade.cancelRequire(lArrayID, lCancelRequireUserID, tsCancelRequireDate);
	}

	public BankBillDailyReportInfo findDailyReportInfo(long lOfficeID, String strQueryDate,String strOpenDate) throws RemoteException, IRollbackException
	{
		return bankBillFacade.findDailyReportInfo(lOfficeID, strQueryDate,strOpenDate);
	}

	public void resignDepositBankBill(DepositRelatedBankBillInfo objDepositRelatedBankBill) throws RemoteException, IRollbackException
	{
		bankBillFacade.resignDepositBankBill(objDepositRelatedBankBill);
	}

	public void useBankBill(long lBankID, long lBankBillTypeID, String strBankBillNo, long lRequireClientID, Timestamp tsDate, long lUserID) throws RemoteException, IRollbackException
	{
		bankBillFacade.useBankBill(lBankID, lBankBillTypeID, strBankBillNo, lRequireClientID, tsDate, lUserID);
	}

	public Collection findDetail(QueryCondition_Sett_Others_BankBill objQueryCondition_Settlement_Setting_BankBill) throws RemoteException, IRollbackException
	{
		return bankBillFacade.findDetail(objQueryCondition_Settlement_Setting_BankBill);
	}

	public Collection findDetail(QueryCondition_Sett_BankBill objQueryCondition_Settlement_Setting_BankBill) throws RemoteException, IRollbackException
	{
		return bankBillFacade.findDetail(objQueryCondition_Settlement_Setting_BankBill);
	}

	public Collection findResignedDepositBankBill(QueryCondition_Sett_Others_DepositBankBill objQueryCondition_Sett_Others_DepositBankBill) throws RemoteException, IRollbackException
	{
		return bankBillFacade.findResignedDepositBankBill(objQueryCondition_Sett_Others_DepositBankBill);
	}

	public void cancelUseBankBill(long lBankID, long lBankBillTypeID, String strBankBillNo, Timestamp tsDate, long lUserID) throws RemoteException, IRollbackException
	{
		bankBillFacade.cancelUseBankBill(lBankID, lBankBillTypeID, strBankBillNo, tsDate, lUserID);
	}

	public BankBillTypeInfo findTypeInfoByTypeID(long lTypeID) throws RemoteException, IRollbackException
	{
		return bankBillFacade.findTypeInfoByTypeID(lTypeID);
	}

	/**
	 * 获取所有的有效的注册状态的票据（申领时使用）
	 * @param lBankID
	 * @param lTypeID
	 * @param strBillNoStart
	 * @param strBillNoEnd
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findAllBillForRequire(long lBankID, long lTypeID, String strBillNoStart, String strBillNoEnd) throws RemoteException, IRollbackException
	{
		return bankBillFacade.findAllBillForRequire(lBankID, lTypeID, strBillNoStart, strBillNoEnd);
	}
}

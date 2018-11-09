/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bankbill.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import com.iss.itreasury.util.*;

import com.iss.itreasury.settlement.bankbill.dataentity.BankBillTypeInfo;
import com.iss.itreasury.settlement.bankbill.dataentity.DepositRelatedBankBillInfo;
import com.iss.itreasury.settlement.bankbill.dataentity.QueryCondition_Sett_Others_DepositBankBill;
import com.iss.itreasury.settlement.bankbill.dataentity.QueryCondition_Sett_Others_BankBill;
import com.iss.itreasury.settlement.bankbill.dataentity.QueryCondition_Sett_BankBill;
import com.iss.itreasury.settlement.bankbill.dataentity.BankBillDailyReportInfo;

//import java.rmi.RemoteException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface BankBill extends javax.ejb.EJBObject
{
	public void registerBankBill(Collection colBankBillInfo) throws java.rmi.RemoteException, IRollbackException;

	public void requireBankBill(Collection requireBankBill) throws java.rmi.RemoteException, IRollbackException;

	public void deleteBankBill(long lArrayID, long lDeleteUserID, Timestamp tsDeleteDate) throws RemoteException, IRollbackException;

	public void reportLoss(long lArrayID, long lReportLossUserID, Timestamp tsReportLossDate) throws RemoteException, IRollbackException;

	public void cancelReportLoss(long lArrayID, long lCancelReportLossUserID, Timestamp tsCancelReportLossDate) throws RemoteException, IRollbackException;

	public void cancelRequire(long lArrayID, long lCancelRequireUserID, Timestamp tsCancelRequireDate) throws RemoteException, IRollbackException;

	public void resignDepositBankBill(DepositRelatedBankBillInfo objDepositRelatedBankBill) throws RemoteException, IRollbackException;

	public void useBankBill(long lBankID, long lBankBillTypeID, String strBankBillNo, long lRequireClientID, Timestamp tsDate, long lUserID) throws RemoteException, IRollbackException;

	public Collection findDetail(QueryCondition_Sett_Others_BankBill objQueryCondition_Settlement_Setting_BankBill) throws RemoteException, IRollbackException;

	public Collection findDetail(QueryCondition_Sett_BankBill objQueryCondition_Settlement_Setting_BankBill) throws RemoteException, IRollbackException;
	
	public BankBillDailyReportInfo findDailyReportInfo(long lOfficeID, String strQueryDate,String strOpenDate) throws RemoteException, IRollbackException;
	
	public Collection findResignedDepositBankBill(QueryCondition_Sett_Others_DepositBankBill objQueryCondition_Sett_Others_DepositBankBill) throws RemoteException, IRollbackException;

	public void cancelUseBankBill(long lBankID, long lBankBillTypeID, String strBankBillNo, Timestamp tsDate, long lUserID) throws RemoteException, IRollbackException;

	public long checkIsUseable(long lTypeID, long lBankID, String strBillNo, long lRequireClientID) throws RemoteException, IRollbackException;

	public void cancelTerminateBankBill(long lBankID, long lTypeID, String strBillNo) throws RemoteException, IRollbackException;

	public void terminateBankBill(long lBankID, long lTypeID, String strBillNo) throws RemoteException, IRollbackException;

	public BankBillTypeInfo findTypeInfoByTypeID(long lTypeID) throws RemoteException, IRollbackException;
	
	
	public Collection findAllBillForRequire(long lBankID, long lTypeID,String strBillNoStart,String strBillNoEnd) throws RemoteException, IRollbackException;
	
	public void addGrantBillBank(long lBankID,long[] aryBankBillTypeID) throws RemoteException, IRollbackException;
	
	public void dropGrantBillBank(long lBankID,long[] aryBankBillTypeID) throws RemoteException, IRollbackException;
	
	public boolean isGrantBillBank(long lBankID) throws RemoteException, IRollbackException;
}

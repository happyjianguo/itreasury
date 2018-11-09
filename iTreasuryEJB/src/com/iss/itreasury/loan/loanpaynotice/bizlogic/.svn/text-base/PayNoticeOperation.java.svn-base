/*
 * Created on 2003-10-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.loanpaynotice.bizlogic;


import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.loanpaynotice.dataentity.*;
import com.iss.itreasury.loan.loanpaynotice.dao.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;

/**
 * @author zgd
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PayNoticeOperation
{
	private static Log4j log4j = null;

	public PayNoticeOperation()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	public PayNoticeRateInfo getLatelyRate(long lPayNoticeID) throws Exception
	{
	    PayNoticeRateInfo info = null;
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		
		try
		{
			info = dao.getRateValue(Constant.RateType.INTEREST, lPayNoticeID, null);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;	
	}
	/**
	 * 此方法通过放款通知单的ID查询该单据的利息
	 * @param lLoanPayNoticeID 放款通知单标识
	 */
	public double findRepayBalanceByID(long lLoanPayNoticeID) throws RemoteException
	{
		double interestOne = 0;
		try
		{
			LoanPayNoticeDao loanPayNoticeDao = new LoanPayNoticeDao();
			interestOne = loanPayNoticeDao.findRepayBalanceByID(lLoanPayNoticeID);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new RemoteException("Gen_E001");
		}
		return interestOne;
	}
	/**
	 * 此方法通过放款通知单的ID查询该单据的利息
	 * @param lLoanPayNoticeID 放款通知单标识
	 */
	public double findRepayInterestByID(double amount,long lLoanPayNoticeID,long lContractID,Timestamp payDate,long nOfficeID,long nCurrencyID) throws RemoteException
	{
		double interestOne = 0;
		try
		{
			LoanPayNoticeDao loanPayNoticeDao = new LoanPayNoticeDao();
			interestOne = loanPayNoticeDao.findRepayInterestByID(amount,lLoanPayNoticeID,lContractID, payDate,nOfficeID,nCurrencyID);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new RemoteException("Gen_E001");
		}
		return interestOne;
	}
	
}

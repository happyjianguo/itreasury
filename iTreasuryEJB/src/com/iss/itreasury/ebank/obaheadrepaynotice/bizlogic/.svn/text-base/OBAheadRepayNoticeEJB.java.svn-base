package com.iss.itreasury.ebank.obaheadrepaynotice.bizlogic;

import java.rmi.*;
import javax.ejb.*;
import com.iss.itreasury.ebank.obaheadrepaynotice.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.ebank.obaheadrepaynotice.dao.OBAheadRepayNoticeDao;
import com.iss.itreasury.loan.loanpaynotice.dao.*;
import com.iss.itreasury.loan.loanpaynotice.dataentity.*;

public class OBAheadRepayNoticeEJB implements SessionBean {
	private javax.ejb.SessionContext mySessionCtx = null;
	private Log4j log4j = new Log4j(Constant.ModuleType.EBANK, this); //
	SessionContext sessionContext;
  public void ejbCreate() {
  }
  public void ejbRemove() {
  }
  public void ejbActivate() {
  }
  public void ejbPassivate() {
  }
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }

  public AheadRepayNoticeInfo findAheadRepayNotice(long lContractID,long lLoanPayID,long lAheadRepayID) throws RemoteException, Exception
  {
	  System.out.println("hehreh0");
	  AheadRepayNoticeInfo cResult = new AheadRepayNoticeInfo();
	  ContractInfo cInfo = new ContractInfo();
	  LoanPayNoticeInfo lInfo = new LoanPayNoticeInfo();
	  //DiscountBillInfo dbInfo = new DiscountBillInfo();
	  OBAheadRepayNoticeDao AheadRepayNoticeDao = new OBAheadRepayNoticeDao();
	  ContractDao ContractDAO = new ContractDao();
	  LoanPayNoticeDao LoanPayNoticeDao = new LoanPayNoticeDao();
	  try
	  {
		  if(lAheadRepayID>0)
		  {
			  cResult = AheadRepayNoticeDao.findAheadRepayNoticeByID(lAheadRepayID);
		  }
		  else
		  {
			  /*则调用贷款模块的ContractDao的findByID方法
				  和贷款模块下的LoanCommonSettingDao的findClientByID方法
				  以及OBDiscountApplyDao的findDiscountBillByBillsID方法*/
			  System.out.println("hehreh1");
			  cInfo = ContractDAO.findByID(lContractID);
			  System.out.println("hehreh2");
			  //cInfo = LoanCommonSettingDAO.findClientByID(info.getClientID());
			  lInfo = LoanPayNoticeDao.findLoanPayNoticeByID(lLoanPayID);
			  System.out.println("hehreh3");
			  //dbInfo = DiscountApplyDAO.findDiscountBillByBillsID(info.getContractID(),info.getBillsID());
			  //cResult = DiscountApplyDAO.findDiscountBillByBillsID();
			  cResult.setClientName(cInfo.getBorrowClientName());//借款单位
			  cResult.setContractID(cInfo.getContractID());//合同ID
			  cResult.setContractCode(cInfo.getContractCode());//合同编号
			  cResult.setIntervalNum(cInfo.getIntervalNum());//贷款期限
			  cResult.setContractAmount(cInfo.getExamineAmount());//合同金额
			  cResult.setContractBalance(cInfo.getAInfo().getBalanceAmount());//合同余额

			  cResult.setLetoutNoticeID(lInfo.getID());//放款通知单ID
			  cResult.setLetoutNoticeCode(lInfo.getCode());//放款通知单编号
			  cResult.setLetoutNoticeRate(lInfo.getInterestRate());//放款通知单利率
			  cResult.setLetoutNoticeAmount(lInfo.getAmount());//放款通知单金额
			  cResult.setLetoutNoticeBalance(lInfo.getBalance());//放款通知单余额

			  cResult.setPlanID(cInfo.getPlanVersionID());//合同计划ID



			  //cResult.setBillAmount(
			  //cResult.setBillInterest(
			  //cResult.setBillCheckAmount(


		  }
		  //to be changed
		  //cResult = DiscountApplyDAO.findDiscountBillByContractID(info);
	  }
	  catch (RemoteException e)
	  {
		  throw e;
	  }
	  catch (IRollbackException e)
	  {
		  throw e;
	  }
	  catch (Exception e)
	  {
		  e.printStackTrace();
		  throw new IRollbackException(mySessionCtx, "Gen_E001", e.getMessage());
	  }
	  finally
	  {
	  }
	  System.out.println("the result is"+cResult);
	  return cResult;

	//return null;
  }

  public long saveAheadRepayNotice(AheadRepayNoticeInfo info) throws RemoteException, Exception
  {
	  long lID = -1;
	  OBAheadRepayNoticeDao AheadRepayNoticeDAO = new OBAheadRepayNoticeDao();
	  try
	  {
		  lID = AheadRepayNoticeDAO.saveAheadRepayNotice(info);
	  }
	  catch (RemoteException e)
	  {
		  throw e;
	  }
	  catch (IRollbackException e)
	  {
		  throw e;
	  }
	  catch (Exception e)
	  {
		  e.printStackTrace();
		  throw new IRollbackException(mySessionCtx, "Gen_E001", e.getMessage());
	  }
	  finally
	  {
	  }
	  return lID;

  }

  public long updateAheadRepayNotice(AheadRepayNoticeInfo info) throws RemoteException, Exception
  {
	  return -1;
  }

  public long updateStatus(long lAheadPayID,long lStatusID) throws RemoteException, Exception
  {
	  long lID = -1;
	  OBAheadRepayNoticeDao AheadRepayNoticeDAO = new OBAheadRepayNoticeDao();
	  try
	  {
		  lID = AheadRepayNoticeDAO.updateStatus(lAheadPayID, lStatusID);
	  }
	  catch (RemoteException e)
	  {
		  throw e;
	  }
	  catch (IRollbackException e)
	  {
		  throw e;
	  }
	  catch (Exception e)
	  {
		  e.printStackTrace();
		  throw new IRollbackException(mySessionCtx, "Gen_E001", e.getMessage());
	  }
	  finally
	  {
	  }
	  return lID;
  }


}